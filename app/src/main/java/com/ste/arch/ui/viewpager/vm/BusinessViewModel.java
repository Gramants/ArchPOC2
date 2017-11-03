package com.ste.arch.ui.viewpager.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
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

    private MediatorLiveData<IssueDataModel> mIssueDetail;

    private SingleLiveEvent<IssueDataModel> liveDataShowIssueContent;
    private SingleLiveEvent<ContributorTransformed> liveDataShowContributorContent;

    public void init()
    {
        liveDataShowIssueContent= new SingleLiveEvent<>();
        liveDataShowContributorContent= new SingleLiveEvent<>();

    }


    private IssueRepository mIssueRepository;
    private ContributorRepository mContributorRepository;
    private PersistentStorageProxy mPersistentStorageProxy;


    public BusinessViewModel(IssueRepository mIssueRepository,ContributorRepository mContributorRepository,PersistentStorageProxy mPersistentStorageProxy) {
        this.mIssueRepository=mIssueRepository;
        this.mContributorRepository= mContributorRepository;
        this.mPersistentStorageProxy=mPersistentStorageProxy;
    }




    public LiveData<IssueDataModel> getIssueContent() {
        return liveDataShowIssueContent;
        /*
        return Transformations.map(liveDataShowIssueContent, new Function<IssueDataModel, IssueDataModel>() {
            @Override
            public IssueDataModel apply(IssueDataModel input) {
                IssueDataModel temp=input;
                Log.e("STEFANO","transformed");
                input.setTitle(temp.getTitle()+" transfORMED!");
                return temp;
            }
        });
        */

    }



    public LiveData<ContributorTransformed> getContributorContent() {
        return liveDataShowContributorContent;
    }

    public void setValueContributorContent(ContributorTransformed contributorDataModel) {
        liveDataShowContributorContent.setValue(contributorDataModel);
    }

    public void setValueIssueContent(IssueDataModel issueDataModel) {
        liveDataShowIssueContent.setValue(issueDataModel);
    }





}