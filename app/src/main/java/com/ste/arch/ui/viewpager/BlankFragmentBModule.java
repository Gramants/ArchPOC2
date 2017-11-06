package com.ste.arch.ui.viewpager;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;

import com.ste.arch.R;
import com.ste.arch.databinding.FragmentDetailBinding;
import com.ste.arch.ui.viewpager.vm.PagerAgentViewModel;
import com.ste.arch.ui.viewpager.vm.RepositoryViewModel;
import com.ste.arch.ui.viewpager.vm.RepositoryViewModelFactory;
import com.ste.arch.ui.viewpager.vm.UtilityViewModel;
import com.ste.arch.ui.viewpager.vm.UtilityViewModelFactory;

import dagger.Module;
import dagger.Provides;


@Module
public class BlankFragmentBModule {

  @Provides
  PagerAgentViewModel providePagerAgentVm(BlankFragmentB blankFragmentB) {
    return ViewModelProviders.of(blankFragmentB.getActivity()).get(PagerAgentViewModel.class);
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
