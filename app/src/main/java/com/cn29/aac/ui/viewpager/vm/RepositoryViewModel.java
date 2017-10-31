package com.cn29.aac.ui.viewpager.vm;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cn29.aac.entities.ContributorDataModel;
import com.cn29.aac.entities.IssueDataModel;
import com.cn29.aac.entities.NetworkErrorObject;
import com.cn29.aac.entities.QueryString;
import com.cn29.aac.repositories.ContributorRepository;
import com.cn29.aac.repositories.IssueRepository;
import com.cn29.aac.repositories.preferences.PersistentStorageProxy;

import java.util.List;

import javax.inject.Inject;


public class RepositoryViewModel extends ViewModel {



    private MediatorLiveData<List<IssueDataModel>> mApiIssueResponse;
    private MediatorLiveData<List<ContributorDataModel>> mApiContributorResponse;

    private MutableLiveData<QueryString> mQueryStringObject;
    private MutableLiveData<String> mMessageSnackbar;

    private LiveData<String> mResultMessageSnackbar;
    private LiveData<List<IssueDataModel>> mResultIssueDataModel;
    private LiveData<List<ContributorDataModel>> mResultContributorDataModel;


    private IssueRepository mIssueRepository;
    private ContributorRepository mContributorRepository;
    private PersistentStorageProxy mPersistentStorageProxy;

    public void init()
    {
        mApiIssueResponse = new MediatorLiveData<>();
        mApiContributorResponse = new MediatorLiveData<>();
        mQueryStringObject = new MutableLiveData<>();
        mMessageSnackbar = new MutableLiveData<>();

        mResultIssueDataModel = Transformations.switchMap(mQueryStringObject, mQueryStringObject->{
            Log.e("STEFANO","scatta");
            return loadIssues(mQueryStringObject.getUser(),mQueryStringObject.getRepo(),mQueryStringObject.getForceremote());
        });

        mResultContributorDataModel = Transformations.switchMap(mQueryStringObject, mQueryStringObject->{
            Log.e("STEFANO","scatta2");
            return loadContributor(mQueryStringObject.getUser(),mQueryStringObject.getRepo(),mQueryStringObject.getForceremote());
        });

        mResultMessageSnackbar = Transformations.switchMap(mQueryStringObject,  mQueryStringObject->{
            Log.e("STEFANO","scatta3");
            return mQueryStringObject.getForceremote()==false?null:loadSnackBar("Search string: "+mQueryStringObject.getUser()+"/"+mQueryStringObject.getRepo());
        });

    }

    public LiveData<String> loadSnackBar(String temp) {
         mMessageSnackbar.setValue(temp);
         return mMessageSnackbar;
    }


    public LiveData<String> getSnackBar() {
        return mResultMessageSnackbar;
    }



    @Inject
    public RepositoryViewModel(IssueRepository mIssueRepository,ContributorRepository mContributorRepository,PersistentStorageProxy mPersistentStorageProxy) {

        this.mIssueRepository=mIssueRepository;
        this.mContributorRepository= mContributorRepository;
        this.mPersistentStorageProxy=mPersistentStorageProxy;

    }


    // given the initial query string
    public void setQueryString(String user, String repo, boolean forceremote) {
        Log.e("STEFANO","dentro repository view model");
        mQueryStringObject.setValue(new QueryString(user,repo,forceremote));
    }


    // saving the good query string to sharedpref
    public void saveSearchString(String searchstring) {
        mPersistentStorageProxy.setSearchStringTemp(searchstring);
    }



// set the stream  from db at the startup or fron network the issues

    public LiveData<List<IssueDataModel>> loadIssues(String user, String repo, Boolean forceremote) {
        mApiIssueResponse.addSource(
                mIssueRepository.getIssues(user, repo, forceremote),
                apiIssueResponse -> mApiIssueResponse.setValue(apiIssueResponse)
        );
        // save searchstring only if some data from remote

/*
        if ((forceremote) && (getApiIssueResponse().getValue().size()>0)) {
            saveSearchString(user + "/" + repo);
        }
*/
        return mApiIssueResponse;
    }


// set the stream from db at the startup or fron network the contributors

    public LiveData<List<ContributorDataModel>> loadContributor(@NonNull String user, String repo, Boolean forceremote) {
        mApiContributorResponse.addSource(
                mContributorRepository.getContributors(user, repo, forceremote),
                apiContributorResponse -> mApiContributorResponse.setValue(apiContributorResponse)
        );
        return mApiContributorResponse;
    }


// get the stream  from the observables of issues and contributors wrapped in mutablelivedata
    @NonNull
    public LiveData<List<ContributorDataModel>> getApiContributorResponse() {
        return mResultContributorDataModel;
    }

    @NonNull
    public LiveData<List<IssueDataModel>> getApiIssueResponse() {
        return mResultIssueDataModel;
    }


  // delete a record from the db by pk

    public void deleteIssueRecordById(Integer id) {
        mIssueRepository.deleteIssueRecordById(id);
    }


    // catch the error network object from the network call of issues and contributors
    public LiveData<NetworkErrorObject> getIssueNetworkErrorResponse() {
        return mIssueRepository.getNetworkError();
    }

    public LiveData<NetworkErrorObject> getContributorNetworkErrorResponse() {
        return mContributorRepository.getNetworkError();
    }


}