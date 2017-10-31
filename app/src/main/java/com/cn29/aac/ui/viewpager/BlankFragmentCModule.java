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
public class BlankFragmentCModule {

  @Provides
  PagerAgentViewModel providePagerAgentVm(BlankFragmentC blankFragmentC) {
    return ViewModelProviders.of(blankFragmentC.getActivity()).get(PagerAgentViewModel.class);
  }

  @Provides
  BusinessViewModel provideBusinessViewModel(BusinessViewModelFactory factory, BlankFragmentC blankFragmentC) {
    return ViewModelProviders.of(blankFragmentC.getActivity(), factory).get(BusinessViewModel.class);
  }


  @Provides
  RepositoryViewModel provideRepositoryViewModel(RepositoryViewModelFactory factory, BlankFragmentC blankFragmentC) {
    return ViewModelProviders.of(blankFragmentC.getActivity(), factory).get(RepositoryViewModel.class);
  }

  @Provides
  UtilityViewModel provideUtilityViewModel(UtilityViewModelFactory factory, BlankFragmentC blankFragmentC) {
    return ViewModelProviders.of(blankFragmentC.getActivity(), factory).get(UtilityViewModel.class);
  }
}
