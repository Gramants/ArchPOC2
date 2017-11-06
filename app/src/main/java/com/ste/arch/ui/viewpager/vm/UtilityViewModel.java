package com.ste.arch.ui.viewpager.vm;


import android.arch.lifecycle.ViewModel;

import com.ste.arch.SingleLiveEvent;
import com.ste.arch.repositories.api.checknetwork.CheckNetwork;
import com.ste.arch.repositories.preferences.PersistentStorageProxy;

import javax.inject.Inject;


public class UtilityViewModel extends ViewModel {


    private SingleLiveEvent<String> livedatasavedstring;
    private SingleLiveEvent<String> livedatasnackbar;
    private SingleLiveEvent<Boolean> liveDataIsInternetConnected;



    public void init()
    {
        livedatasavedstring = new SingleLiveEvent<>();
        livedatasnackbar = new SingleLiveEvent<>();
        liveDataIsInternetConnected = new SingleLiveEvent<>();
    }


    private PersistentStorageProxy mPersistentStorageProxy;
    private CheckNetwork mCheckNetwork;

    @Inject
    public UtilityViewModel(PersistentStorageProxy mPersistentStorageProxy,CheckNetwork mCheckNetwork) {
        this.mPersistentStorageProxy=mPersistentStorageProxy;
        this.mCheckNetwork=mCheckNetwork;
    }



    public void setObservableNetworkStatus() {
        liveDataIsInternetConnected.setValue(mCheckNetwork.isConnectedToInternet());
    }

    public SingleLiveEvent<Boolean> getObservableNetworkStatus() {
        return liveDataIsInternetConnected;
    }



    public  SingleLiveEvent<String> getObservableSavedSearchString() {
        livedatasavedstring.setValue(mPersistentStorageProxy.getSearchString());
        return livedatasavedstring;
    }

    public  void setSavedSearchString(String searchstring) {
        mPersistentStorageProxy.setSearchString(searchstring);
    }



    public SingleLiveEvent<String> getSnackBar() {
        return livedatasnackbar;
    }

    public void setSnackBar(String msg) {
        livedatasnackbar.setValue(msg);
    }

}