package com.cn29.aac.ui.viewpager.vm;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cn29.aac.SingleLiveEvent;
import com.cn29.aac.entities.ContributorDataModel;
import com.cn29.aac.entities.ContributorTransformed;
import com.cn29.aac.entities.IssueDataModel;
import com.cn29.aac.repositories.ContributorRepository;
import com.cn29.aac.repositories.IssueRepository;
import com.cn29.aac.repositories.preferences.PersistentStorageProxy;


public class BusinessViewModel extends ViewModel {

    private MediatorLiveData<IssueDataModel> mIssueDetail;

    private SingleLiveEvent<IssueDataModel> liveDataShowIssueContent;
    private SingleLiveEvent<ContributorTransformed> liveDataShowContributorContent;

    public void init()
    {
        liveDataShowIssueContent= new SingleLiveEvent<>();
        liveDataShowContributorContent= new SingleLiveEvent<>();
        mIssueDetail = new MediatorLiveData<>();
    }


    private IssueRepository mIssueRepository;
    private ContributorRepository mContributorRepository;
    private PersistentStorageProxy mPersistentStorageProxy;


    public BusinessViewModel(IssueRepository mIssueRepository,ContributorRepository mContributorRepository,PersistentStorageProxy mPersistentStorageProxy) {
        this.mIssueRepository=mIssueRepository;
        this.mContributorRepository= mContributorRepository;
        this.mPersistentStorageProxy=mPersistentStorageProxy;
    }



    public LiveData<IssueDataModel> loadIssue(int id) {

        Log.e("STEFANO","loadissue by id");
        mIssueDetail.addSource(
                mIssueRepository.getIssueFromDb(id), dbResponse -> mIssueDetail.setValue(dbResponse)
        );

        return mIssueDetail;
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

    @NonNull
    public LiveData<IssueDataModel> getIssue() {
        return mIssueDetail;
    }



}