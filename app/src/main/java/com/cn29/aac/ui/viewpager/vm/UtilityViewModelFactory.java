package com.cn29.aac.ui.viewpager.vm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.cn29.aac.repositories.ContributorRepository;
import com.cn29.aac.repositories.IssueRepository;
import com.cn29.aac.repositories.api.checknetwork.CheckNetwork;
import com.cn29.aac.repositories.preferences.PersistentStorageProxy;
import com.cn29.aac.ui.viewpager.vm.UtilityViewModel;

import javax.inject.Inject;

/**
 * Created by Stefano on 21/10/2017.
 */

public class UtilityViewModelFactory  extends ViewModelProvider.NewInstanceFactory{
    private PersistentStorageProxy mPersistentStorageProxy;
    private CheckNetwork mCheckNetwork;

    @Inject
    public UtilityViewModelFactory(PersistentStorageProxy mPersistentStorageProxy,CheckNetwork mCheckNetwork) {
        this.mPersistentStorageProxy=mPersistentStorageProxy;
        this.mCheckNetwork=mCheckNetwork;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new UtilityViewModel (mPersistentStorageProxy,mCheckNetwork);
    }
}
