package com.cn29.aac.ui.viewpager.vm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.cn29.aac.repositories.ContributorRepository;
import com.cn29.aac.repositories.IssueRepository;
import com.cn29.aac.repositories.preferences.PersistentStorageProxy;
import com.cn29.aac.ui.viewpager.vm.BusinessViewModel;

import javax.inject.Inject;

/**
 * Created by Stefano on 21/10/2017.
 */

public class BusinessViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private IssueRepository mIssueRepository;
    private ContributorRepository mContributorRepository;
    private PersistentStorageProxy mPersistentStorageProxy;

    @Inject
    public BusinessViewModelFactory(IssueRepository mIssueRepository,ContributorRepository mContributorRepository,PersistentStorageProxy mPersistentStorageProxy) {
        this.mIssueRepository=mIssueRepository;
        this.mContributorRepository= mContributorRepository;
        this.mPersistentStorageProxy=mPersistentStorageProxy;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new BusinessViewModel(mIssueRepository, mContributorRepository,mPersistentStorageProxy);
    }
}
