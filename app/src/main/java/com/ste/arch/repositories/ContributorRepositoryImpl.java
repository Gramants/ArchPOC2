package com.ste.arch.repositories;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ste.arch.Utils;
import com.ste.arch.entities.ContributorDataModel;
import com.ste.arch.entities.pojos.Contributor;
import com.ste.arch.entities.translator.DataTranslator;
import com.ste.arch.repositories.api.GithubApiService;
import com.ste.arch.repositories.asyncoperations.AddRecord;
import com.ste.arch.repositories.asyncoperations.NetworkBoundResource;
import com.ste.arch.repositories.asyncoperations.SelectObject;
import com.ste.arch.repositories.asyncoperations.UpdateRecord;
import com.ste.arch.repositories.database.ContributorDao;
import com.ste.arch.repositories.database.ProjectDb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


import retrofit2.Call;


public class ContributorRepositoryImpl implements ContributorRepository {


    private ContributorDao contributorDao;
    private GithubApiService mApiService;
    private ProjectDb db;

    @Inject
    public ContributorRepositoryImpl(ProjectDb mDb, ContributorDao contributorDao, GithubApiService mApiService) {
        this.contributorDao = contributorDao;
        this.mApiService = mApiService;
        this.db = mDb;
    }


    public LiveData<Resource<List<ContributorDataModel>>> getContributors(String owner, String repo, Boolean forceRemote) {
        return new NetworkBoundResource<List<ContributorDataModel>, List<Contributor>>() {
            @Override
            protected void updateAll(List<Contributor> item) {
                if (!item.isEmpty()) {
                    db.beginTransaction();
                    try {
                        contributorDao.updateData(DataTranslator.ContributorTranslator(item));
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }


                }
            }

            @Override
            protected void deleteAll(List<Contributor> item) {

            }

            @Override
            protected void saveCallResult(List<Contributor> item) {

            }

            @Override
            protected Boolean shouldFetch(@Nullable List<ContributorDataModel> data) {
                return forceRemote;
            }

            @NonNull
            @Override
            protected LiveData<List<ContributorDataModel>> loadFromDb() {
                // apply Transformation to order the results
                // model transformation
                return
                        Transformations.switchMap(contributorDao.getAllContributors(),
                                new Function<List<ContributorDataModel>, LiveData<List<ContributorDataModel>>>() {
                                    @Override
                                    public LiveData<List<ContributorDataModel>> apply(List<ContributorDataModel> contributorDataModels) {

                                        ArrayList<ContributorDataModel> result = new ArrayList<>();
                                        for (ContributorDataModel contributor : contributorDataModels) {
                                            contributor.setLogin(contributor.getLogin().trim().toUpperCase() + " (Transf & ORDERED)");
                                            result.add(contributor);
                                        }
                                        Collections.sort(result, new Utils.CustomComparator());
                                        return getTransformedDbResult(result);
                                    }
                                });


            }

            @NonNull
            @Override
            protected LiveData<Resource<List<Contributor>>> createCall() {
                return mApiService.getContributors(owner, repo);
            }
        }.getAsLiveData();
    }


    private LiveData<List<ContributorDataModel>> getTransformedDbResult(List<ContributorDataModel> result) {
        return new LiveData<List<ContributorDataModel>>() {
            @Override
            protected void onActive() {
                setValue(result);
            }
        };
    }


    @Override
    public LiveData<Resource<ContributorDataModel>> getWrappedIssueObject(LiveData<ContributorDataModel> obj) {
        return new SelectObject<ContributorDataModel>() {

            @NonNull
            @Override
            protected LiveData<ContributorDataModel> getObject() {
                return obj;
            }
        }.getAsLiveData();
    }


    @Override
    public void addContributorRecord(ContributorDataModel contributorDataModel) {
        new AddRecord() {

            @Override
            protected void addRecord() {
                db.beginTransaction();
                try {
                    contributorDao.insertRecord(contributorDataModel);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }

        };
    }

    @Override
    public void updateContributorTitleRecord(String titleold, String titlenew) {
        new UpdateRecord() {
            @Override
            protected void updateRecord() {
                db.beginTransaction();
                try {
                    contributorDao.updateTitle(titleold, titlenew);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }
        };
    }

}