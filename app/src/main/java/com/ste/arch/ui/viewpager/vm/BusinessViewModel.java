package com.ste.arch.ui.viewpager.vm;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
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




    // streaming  the item object clicking on the UI not loading it from the database

    public LiveData<IssueDataModel> getIssueContent() {
        return Transformations.map(liveDataShowIssueContent, new Function<IssueDataModel, IssueDataModel>() {
            @Override
            public IssueDataModel apply(IssueDataModel input) {
                IssueDataModel temp=input;
                input.setTitle(temp.getTitle()+" (Not from DB)");
                return temp;
            }
        });

    }

    public void setValueIssueContent(IssueDataModel issueDataModel) {
        liveDataShowIssueContent.setValue(issueDataModel);
    }











    public LiveData<ContributorTransformed> getContributorContent() {
        return liveDataShowContributorContent;
    }

    public void setValueContributorContent(ContributorTransformed contributorDataModel) {
        liveDataShowContributorContent.setValue(contributorDataModel);
    }







}