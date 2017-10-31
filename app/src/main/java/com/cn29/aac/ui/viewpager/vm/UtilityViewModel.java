package com.cn29.aac.ui.viewpager.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cn29.aac.entities.ContributorDataModel;
import com.cn29.aac.repositories.api.checknetwork.CheckNetwork;
import com.cn29.aac.repositories.preferences.PersistentStorageProxy;

import javax.inject.Inject;


public class UtilityViewModel extends ViewModel {

    private MutableLiveData<Boolean> liveDataShowProgressInObserverFragments;
    private MutableLiveData<String> livedatasavedstring;
    private MutableLiveData<String> livedatasnackbar;
    private MutableLiveData<Boolean> liveDataIsInternetConnected;

    public void init()
    {
        liveDataShowProgressInObserverFragments = new MutableLiveData<>();
        livedatasavedstring = new MutableLiveData<>();
        livedatasnackbar = new MutableLiveData<>();
        liveDataIsInternetConnected = new MutableLiveData<>();
    }


    private PersistentStorageProxy mPersistentStorageProxy;
    private CheckNetwork mCheckNetwork;

    @Inject
    public UtilityViewModel(PersistentStorageProxy mPersistentStorageProxy,CheckNetwork mCheckNetwork) {
        this.mPersistentStorageProxy=mPersistentStorageProxy;
        this.mCheckNetwork=mCheckNetwork;
    }



    public LiveData<String> getSearchString() {
        livedatasavedstring.setValue(mPersistentStorageProxy.getSearchString());
        return livedatasavedstring;
    }


    public MutableLiveData<String> getSnackBar() {
        return livedatasnackbar;
    }

    public void setSnackBar(String msg) {
        livedatasnackbar.setValue(msg);
    }


    public MutableLiveData<Boolean> getShowDialogIssueAndContributor() {
        return liveDataShowProgressInObserverFragments;
    }

    public void setShowProgressInObserverFragments() {
        liveDataShowProgressInObserverFragments.setValue(true);
    }


    public void askNetworkStatus() {
        liveDataIsInternetConnected.setValue(mCheckNetwork.isConnectedToInternet());
    }

    public MutableLiveData<Boolean> isInternetConnected() {
        return liveDataIsInternetConnected;
    }


}