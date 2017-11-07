package com.ste.arch.ui.viewpager;

import android.arch.lifecycle.ViewModelProviders;

import com.ste.arch.ui.viewpager.viewmodel.MessageRouterViewModel;
import com.ste.arch.ui.viewpager.viewmodel.RepositoryViewModel;
import com.ste.arch.ui.viewpager.viewmodel.RepositoryViewModelFactory;
import com.ste.arch.ui.viewpager.viewmodel.UtilityViewModel;
import com.ste.arch.ui.viewpager.viewmodel.UtilityViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class PagerActivityModule {

    @Provides
    MessageRouterViewModel providePagerAgentVm(PagerActivity pagerActivity) {
        MessageRouterViewModel messageRouterViewModel = ViewModelProviders.of(pagerActivity).get(MessageRouterViewModel.class);
        messageRouterViewModel.init();
        return messageRouterViewModel;
    }


    @Provides
    RepositoryViewModel provideRepositoryViewModel(RepositoryViewModelFactory factory, PagerActivity pagerActivity) {
        RepositoryViewModel repositoryViewModel = ViewModelProviders.of(pagerActivity, factory).get(RepositoryViewModel.class);
        repositoryViewModel.init();
        return repositoryViewModel;
    }

    @Provides
    UtilityViewModel provideUtilityViewModel(UtilityViewModelFactory factory, PagerActivity pagerActivity) {
        UtilityViewModel utilityViewModel = ViewModelProviders.of(pagerActivity, factory).get(UtilityViewModel.class);
        utilityViewModel.init();
        return utilityViewModel;
    }
}
