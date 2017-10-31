package com.cn29.aac.ui.viewpager;

import android.arch.lifecycle.ViewModelProviders;

import com.cn29.aac.repositories.ContributorRepository;
import com.cn29.aac.repositories.IssueRepository;
import com.cn29.aac.repositories.api.checknetwork.CheckNetwork;
import com.cn29.aac.repositories.preferences.PersistentStorageProxy;
import com.cn29.aac.ui.viewpager.vm.BusinessViewModel;
import com.cn29.aac.ui.viewpager.vm.BusinessViewModelFactory;
import com.cn29.aac.ui.viewpager.vm.PagerAgentViewModel;
import com.cn29.aac.ui.viewpager.vm.RepositoryViewModel;
import com.cn29.aac.ui.viewpager.vm.RepositoryViewModelFactory;
import com.cn29.aac.ui.viewpager.vm.UtilityViewModel;
import com.cn29.aac.ui.viewpager.vm.UtilityViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Charles Ng on 3/10/2017.
 */

@Module
public class PagerActivityModule {

  @Provides
  PagerAgentViewModel providePagerAgentVm(PagerActivity pagerActivity) {

    PagerAgentViewModel pagerAgentViewModel = ViewModelProviders.of(pagerActivity)
        .get(PagerAgentViewModel.class);
    pagerAgentViewModel.init();
    return pagerAgentViewModel;
  }



    @Provides
    BusinessViewModel provideBusinessViewModel(BusinessViewModelFactory factory, PagerActivity pagerActivity) {
        BusinessViewModel businessViewModel= ViewModelProviders.of(pagerActivity, factory).get(BusinessViewModel.class);
        businessViewModel.init();
        return businessViewModel;
    }

    @Provides
    RepositoryViewModel provideRepositoryViewModel(RepositoryViewModelFactory factory, PagerActivity pagerActivity) {
        RepositoryViewModel repositoryViewModel= ViewModelProviders.of(pagerActivity, factory).get(RepositoryViewModel.class);
        repositoryViewModel.init();
        return repositoryViewModel;
    }

    @Provides
    UtilityViewModel provideUtilityViewModel(UtilityViewModelFactory factory, PagerActivity pagerActivity) {
        UtilityViewModel utilityViewModel= ViewModelProviders.of(pagerActivity, factory).get(UtilityViewModel.class);
        utilityViewModel.init();
        return utilityViewModel;
    }
}
