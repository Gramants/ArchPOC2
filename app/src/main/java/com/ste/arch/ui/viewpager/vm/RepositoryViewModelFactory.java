package com.ste.arch.ui.viewpager.vm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.ste.arch.repositories.ContributorRepository;
import com.ste.arch.repositories.IssueRepository;
import com.ste.arch.repositories.preferences.PersistentStorageProxy;

import javax.inject.Inject;

/**
 * Created by Stefano on 21/10/2017.
 */

public class RepositoryViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private IssueRepository mIssueRepository;
    private ContributorRepository mContributorRepository;
    private PersistentStorageProxy mPersistentStorageProxy;

    @Inject
    public RepositoryViewModelFactory(IssueRepository mIssueRepository,ContributorRepository mContributorRepository,PersistentStorageProxy mPersistentStorageProxy) {
        this.mIssueRepository=mIssueRepository;
        this.mContributorRepository= mContributorRepository;
        this.mPersistentStorageProxy=mPersistentStorageProxy;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RepositoryViewModel(mIssueRepository, mContributorRepository,mPersistentStorageProxy);
    }
}
