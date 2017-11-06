package com.ste.arch.repositories;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ste.arch.Utils;
import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.entities.pojos.Issue;
import com.ste.arch.entities.translator.DataTranslator;
import com.ste.arch.repositories.api.GithubApiService;
import com.ste.arch.repositories.asyncoperations.AddRecord;
import com.ste.arch.repositories.asyncoperations.SelectObject;
import com.ste.arch.repositories.asyncoperations.SelectRecord;
import com.ste.arch.repositories.asyncoperations.UpdateRecord;
import com.ste.arch.repositories.database.IssueDao;
import com.ste.arch.repositories.asyncoperations.NetworkBoundResource;
import com.ste.arch.repositories.asyncoperations.DeleteRecord;
import com.ste.arch.repositories.database.ProjectDb;

import java.util.ArrayList;
import java.util.Collections;
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
        this.db = mDb;
    }


    public LiveData<Resource<List<IssueDataModel>>> getIssues(String owner, String repo, Boolean forceRemote) {
        return new NetworkBoundResource<List<IssueDataModel>, List<Issue>>() {
            @Override
            protected void updateAll(List<Issue> item) {
                if (!item.isEmpty()) {
                    db.beginTransaction();
                    try {
                        issueDao.updateData(DataTranslator.IssueTranslator(item));
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }


                }
            }

            @Override
            protected void deleteAll(List<Issue> item) {

            }

            @Override
            protected void saveCallResult(List<Issue> item) {

            }

            @Override
            protected Boolean shouldFetch(@Nullable List<IssueDataModel> data) {
                return forceRemote;
            }

            @NonNull
            @Override
            protected LiveData<List<IssueDataModel>> loadFromDb() {
                // apply Transformation to order the results
                // model transformation
                return
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


            }

            @NonNull
            @Override
            protected Call<List<Issue>> createCall() {
                return mApiService.getIssues(owner, repo);
            }
        }.getAsLiveData();
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


    @Override
    public void addIssueRecord(IssueDataModel issueDataModel) {

        new AddRecord() {

            @Override
            protected void addRecord() {

                db.beginTransaction();
                try {
                    issueDao.insertRecord(issueDataModel);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }


            }

        };
    }


    @Override
    public void updateIssueTitleRecord(String titleold, String titlenew) {
        new UpdateRecord() {

            @Override
            protected void updateRecord() {

                db.beginTransaction();
                try {
                    issueDao.updateTitle(titleold,titlenew);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }


            }

        };
    }



    @Override
    public LiveData<Resource<IssueDataModel>> getIssueRecordById(int id) {

        return new SelectRecord<IssueDataModel>() {
            @NonNull
            @Override
            protected LiveData<IssueDataModel> selectRecordById() {
                // model transformation
                return Transformations.map(issueDao.getIssueById(id), new Function<IssueDataModel, IssueDataModel>() {
                    @Override
                    public IssueDataModel apply(IssueDataModel input) {
                        IssueDataModel temp = input;
                        if (temp!=null)
                        {
                            input.setTitle(temp.getTitle() + " (.map transformed Source: DB)");
                        }
                        return temp;
                    }
                });


            }
        }.getAsLiveData();

    }

    @Override
    public LiveData<Resource<IssueDataModel>> getWrappedIssueObject(LiveData<IssueDataModel> obj) {

        // apply a transformation to the UI click result
        return new SelectObject<IssueDataModel>() {

            @NonNull
            @Override
            protected LiveData<IssueDataModel> getObject() {
            // model transformation
                return Transformations.map(obj, new Function<IssueDataModel, IssueDataModel>() {
                    @Override
                    public IssueDataModel apply(IssueDataModel input) {
                        IssueDataModel temp = input;
                        if (temp!=null)
                        {
                            input.setTitle(temp.getTitle() + " (.map transformed Source: UI)");
                        }
                        return temp;
                    }
                });




            }
        }.getAsLiveData();


    }


    private LiveData<List<IssueDataModel>> getTransformedDbResult(List<IssueDataModel> result) {
        return new LiveData<List<IssueDataModel>>() {
            @Override
            protected void onActive() {
                setValue(result);
            }
        };
    }

}