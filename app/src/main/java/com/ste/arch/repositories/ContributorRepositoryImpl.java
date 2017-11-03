package com.ste.arch.repositories;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.ste.arch.Config;
import com.ste.arch.Utils;
import com.ste.arch.entities.ContributorDataModel;
import com.ste.arch.entities.NetworkErrorObject;
import com.ste.arch.entities.pojos.Contributor;
import com.ste.arch.repositories.api.GithubApiService;
import com.ste.arch.repositories.database.ContributorDao;
import com.ste.arch.repositories.database.ProjectDb;
import com.ste.arch.repositories.asyncoperations.ContributorDbManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ste.arch.entities.translator.DataTranslator.ContributorTranslator;


public class ContributorRepositoryImpl implements ContributorRepository {

    private ContributorDao contributorDao;
    private GithubApiService mApiService;
    private ProjectDb mProjectDb;
    private MutableLiveData<NetworkErrorObject> liveDataError = new MutableLiveData<>();
    @Inject
    public ContributorRepositoryImpl(ContributorDao contributorDao, ProjectDb mProjectDb, GithubApiService mApiService) {
        this.contributorDao = contributorDao;
        this.mApiService = mApiService;
        this.mProjectDb = mProjectDb;
    }


    public LiveData<List<ContributorDataModel>> getContributors(String owner, String repo, Boolean forceRemote) {
        final MutableLiveData<List<ContributorDataModel>> liveDataResult = new MutableLiveData<>();

        if (forceRemote)
        {
        Call<List<Contributor>> call = mApiService.getContributors(owner, repo);
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {

                if (response.isSuccessful()) {
                    deleteTableAndSaveDataToLocal(ContributorTranslator(response));
                }
                else
                {
                    liveDataError.setValue(new NetworkErrorObject(response.code(),response.message() , Config.CONTRIBUTOR_ENDPOINT));
                }

            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                liveDataError.setValue(new NetworkErrorObject(0,"Unknown error",Config.CONTRIBUTOR_ENDPOINT));
            }
        });




            return liveDataResult;
        }
        else
        {
            // transform the livedata result coming from the DB, change a field and order

            LiveData<List<ContributorDataModel>> transformedDbOutput=
                    Transformations.switchMap(contributorDao.getAllContributors(),
                            new Function<List<ContributorDataModel>, LiveData<List<ContributorDataModel>>>() {
                                @Override
                                public LiveData<List<ContributorDataModel>> apply(List<ContributorDataModel> contributorDataModels) {

                                    ArrayList<ContributorDataModel> result=new ArrayList<>();
                                    for (ContributorDataModel customer : contributorDataModels) {
                                        customer.setLogin(customer.getLogin().trim().toUpperCase()+" transf. by switchmap");
                                        result.add(customer);
                                    }

                                  Collections.sort(result, new Utils.CustomComparator());
                                  return getTransformedDbResult(result);
                                }
                            });



            return transformedDbOutput;

        }

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
    public LiveData<ContributorDataModel> getContributorFromDb(int id) {
        return contributorDao.getContributorById(id);
    }


    private void deleteTableAndSaveDataToLocal(ArrayList<ContributorDataModel> contributors) {
        new ContributorDbManager.AddContributorsAsyncTask(mProjectDb).execute(contributors);
    }


    @Override
    public LiveData<NetworkErrorObject> getNetworkError() {return liveDataError;}

}