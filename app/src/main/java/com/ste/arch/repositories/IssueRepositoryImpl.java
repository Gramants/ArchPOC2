package com.ste.arch.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.entities.NetworkErrorObject;
import com.ste.arch.entities.pojos.Issue;
import com.ste.arch.entities.translator.DataTranslator;
import com.ste.arch.repositories.api.GithubApiService;
import com.ste.arch.repositories.asyncoperations.Resource;
import com.ste.arch.repositories.asyncoperations.SelectRecord;
import com.ste.arch.repositories.database.IssueDao;
import com.ste.arch.repositories.asyncoperations.NetworkBoundResource;
import com.ste.arch.repositories.asyncoperations.DeleteRecord;
import com.ste.arch.repositories.database.ProjectDb;

import java.util.List;

import javax.inject.Inject;


import retrofit2.Call;


public class IssueRepositoryImpl implements IssueRepository {

    private IssueDao issueDao;
    private GithubApiService mApiService;
    private ProjectDb db;
    @Inject
    public IssueRepositoryImpl(ProjectDb mDb, IssueDao issueDao, GithubApiService mApiService) {
        this.issueDao = issueDao;
        this.mApiService = mApiService;
        this.db=mDb;
    }


    public LiveData<Resource<List<IssueDataModel>>> getIssues(String owner, String repo, Boolean forceRemote)  {
        return new NetworkBoundResource<List<IssueDataModel>, List<Issue>>() {
            @Override
            protected void deleteAll(List<Issue> item) {
                if (!item.isEmpty()) {
                    db.beginTransaction();
                    try {
                        issueDao.deleteAll();
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }


                }
            }

            @Override
            protected void saveCallResult(List<Issue> item) {
                if (!item.isEmpty())
                {
                    db.beginTransaction();
                    try {
                        issueDao.insert(DataTranslator.IssueTranslator(item));
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }


                }
            }

            @Override
            protected Boolean shouldFetch(@Nullable List<IssueDataModel> data) {
                //
                return forceRemote;

            }

            @NonNull
            @Override
            protected LiveData<List<IssueDataModel>> loadFromDb() {

                return issueDao.getAllIssue();
            }

            @NonNull
            @Override
            protected Call<List<Issue>> createCall() {
                return mApiService.getIssues(owner,repo);
            }
        }.getAsLiveData();
    }





    @Override
    public LiveData<NetworkErrorObject> getNetworkError() {
        return null;
        // not used togliere
    }


    public void deleteIssueById(Integer id) {
        new DeleteRecord() {
            @Override
            protected void deleteRecordById() {

                db.beginTransaction();
                try {
                    issueDao.deleteById(id);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }


            }

        };
    }




/*
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
*/



    @Override
    public LiveData<Resource<IssueDataModel>> getIssueRecordById(int id) {

        return new SelectRecord<IssueDataModel>() {
            @NonNull
            @Override
            protected LiveData<IssueDataModel> selectRecordById() {
                Log.e("STEFANO","inside the fun record selected "+String.valueOf(id));
                return issueDao.getIssueById(id);
            }
        }.getAsLiveData();

    }







    /*
    @Override
    public LiveData<Resource<List<IssueDataModel>>> deleteIssueRecordByIdNew(Integer id) {

        Log.e("STEFANO","delete recrod by id "+String.valueOf(id));

        return new DeleteRecordBoundResource<List<IssueDataModel>>() {

            @Override
            protected void deleteRecordById() {
                Log.e("STEFANO","deleteRecordById() INSIDE");
                issueDao.deleteById(id);
            }

            @NonNull
            @Override
            protected LiveData<List<IssueDataModel>> loadFromDb() {
                return null;
            }
        }.getAsLiveData();

    }
   */


}