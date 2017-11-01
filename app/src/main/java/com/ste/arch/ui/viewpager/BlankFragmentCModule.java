package com.ste.arch.ui.viewpager;

import android.arch.lifecycle.ViewModelProviders;

import com.ste.arch.ui.viewpager.vm.BusinessViewModel;
import com.ste.arch.ui.viewpager.vm.BusinessViewModelFactory;
import com.ste.arch.ui.viewpager.vm.PagerAgentViewModel;
import com.ste.arch.ui.viewpager.vm.RepositoryViewModel;
import com.ste.arch.ui.viewpager.vm.RepositoryViewModelFactory;
import com.ste.arch.ui.viewpager.vm.UtilityViewModel;
import com.ste.arch.ui.viewpager.vm.UtilityViewModelFactory;

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
