package com.ste.arch.ui.viewpager.vm;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ste.arch.SingleLiveEvent;
import com.ste.arch.entities.ContributorTransformed;
import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.repositories.ContributorRepository;
import com.ste.arch.repositories.IssueRepository;
import com.ste.arch.repositories.preferences.PersistentStorageProxy;


public class BusinessViewModel extends ViewModel {



    private SingleLiveEvent<IssueDataModel> liveDataShowIssueContent;
    private SingleLiveEvent<ContributorTransformed> liveDataShowContributorContent;

    public void init()
    {
        liveDataShowIssueContent= new SingleLiveEvent<>();
        liveDataShowContributorContent= new SingleLiveEvent<>();

    }


    private PersistentStorageProxy mPersistentStorageProxy;

    public BusinessViewModel(PersistentStorageProxy mPersistentStorageProxy) {
        this.mPersistentStorageProxy=mPersistentStorageProxy;
    }









}