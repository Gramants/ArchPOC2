package com.cn29.aac.repositories;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.cn29.aac.Config;
import com.cn29.aac.Utils;
import com.cn29.aac.entities.IssueDataModel;
import com.cn29.aac.entities.NetworkErrorObject;
import com.cn29.aac.entities.pojos.Issue;
import com.cn29.aac.repositories.api.GithubApiService;
import com.cn29.aac.repositories.database.IssueDao;
import com.cn29.aac.repositories.database.ProjectDb;
import com.cn29.aac.repositories.database.asyncdml.IssueDbManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cn29.aac.entities.translator.DataTranslator.IssueTranslator;


public class IssueRepositoryImpl implements IssueRepository {

    private IssueDao issueDao;
    private GithubApiService mApiService;
    private ProjectDb mProjectDb;
    private MutableLiveData<NetworkErrorObject> liveDataError = new MutableLiveData<>();

    @Inject
    public IssueRepositoryImpl(IssueDao issueDao, ProjectDb mProjectDb, GithubApiService mApiService) {
        this.issueDao = issueDao;
        this.mApiService = mApiService;
        this.mProjectDb = mProjectDb;
    }

    public LiveData<List<IssueDataModel>> getIssues(String owner, String repo, Boolean forceRemote) {

        final MutableLiveData<List<IssueDataModel>> liveDataResult = new MutableLiveData<>();

        if (forceRemote) {
            // network call
            Call<List<Issue>> call = mApiService.getIssues(owner, repo);
            call.enqueue(new Callback<List<Issue>>() {
                @Override
                public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {

                    if (response.isSuccessful()) {
                        deleteTableAndSaveDataToLocal(IssueTranslator(response));
                    } else {
                        liveDataError.setValue(new NetworkErrorObject(response.code(), response.message(), Config.ISSUE_ENDPOINT));
                    }

                }

                @Override
                public void onFailure(Call<List<Issue>> call, Throwable t) {
                    liveDataError.setValue(new NetworkErrorObject(0, "Unknown error", Config.ISSUE_ENDPOINT));
                }
            });

            return liveDataResult;
        } else {
        // db call and transformation for test transform operator
            LiveData<List<IssueDataModel>> transformedDbOutput =
                    Transformations.switchMap(issueDao.getAllIssue(),
                            new Function<List<IssueDataModel>, LiveData<List<IssueDataModel>>>() {
                                @Override
                                public LiveData<List<IssueDataModel>> apply(List<IssueDataModel> issueDataModels) {

                                    ArrayList<IssueDataModel> result = new ArrayList<>();
                                    for (IssueDataModel issue : issueDataModels) {
                                        issue.setTitle(issue.getTitle().trim().toUpperCase() + " ORDERED");
                                        result.add(issue);
                                    }
                                    Collections.sort(result, new Utils.CustomComparator());
                                    return getTransformedDbResult(result);
                                }
                            });

            return transformedDbOutput;


        }

    }

    private LiveData<List<IssueDataModel>> getTransformedDbResult(List<IssueDataModel> result) {
        return new LiveData<List<IssueDataModel>>() {
            @Override
            protected void onActive() {
                setValue(result);
            }
        };
    }


    @Override
    public LiveData<NetworkErrorObject> getNetworkError() {
        return liveDataError;
    }


    @Override
    public LiveData<IssueDataModel> getIssueFromDb(int id) {
        return issueDao.getIssueById(id);

    }

    @Override
    public void deleteIssueRecordById(int id) {
        new IssueDbManager.DeleteIssueByIdAsyncTask(mProjectDb).execute(id);
    }


    private void deleteTableAndSaveDataToLocal(ArrayList<IssueDataModel> issues) {
        new IssueDbManager.AddIssueAsyncTask(mProjectDb).execute(issues);
    }


}