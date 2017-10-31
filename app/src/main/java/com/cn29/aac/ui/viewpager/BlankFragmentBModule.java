package com.cn29.aac.ui.viewpager;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;

import com.cn29.aac.R;
import com.cn29.aac.databinding.FragmentDetailBinding;
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
public class BlankFragmentBModule {

  @Provides
  PagerAgentViewModel providePagerAgentVm(BlankFragmentB blankFragmentB) {
    return ViewModelProviders.of(blankFragmentB.getActivity()).get(PagerAgentViewModel.class);
  }

  @Provides
  BusinessViewModel provideBusinessViewModel(BusinessViewModelFactory factory, BlankFragmentB blankFragmentB) {
    return ViewModelProviders.of(blankFragmentB.getActivity(), factory).get(BusinessViewModel.class);
  }

  @Provides
  RepositoryViewModel provideRepositoryViewModel(RepositoryViewModelFactory factory, BlankFragmentB blankFragmentB) {
    return ViewModelProviders.of(blankFragmentB.getActivity(), factory).get(RepositoryViewModel.class);
  }

  @Provides
  UtilityViewModel provideUtilityViewModel(UtilityViewModelFactory factory, BlankFragmentB blankFragmentB) {
    return ViewModelProviders.of(blankFragmentB.getActivity(), factory).get(UtilityViewModel.class);
  }

  @Provides
  FragmentDetailBinding provideDataBinding(BlankFragmentB blankFragmentB) {

    return DataBindingUtil
            .inflate(blankFragmentB.getActivity().getLayoutInflater(),
                    R.layout.fragment_detail, null,
                    false);
  }
}
