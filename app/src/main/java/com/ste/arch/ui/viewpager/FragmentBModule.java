package com.ste.arch.ui.viewpager;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;

import com.ste.arch.R;
import com.ste.arch.databinding.FragmentDetailBinding;
import com.ste.arch.ui.viewpager.viewmodel.MessageRouterViewModel;
import com.ste.arch.ui.viewpager.viewmodel.RepositoryViewModel;
import com.ste.arch.ui.viewpager.viewmodel.RepositoryViewModelFactory;
import com.ste.arch.ui.viewpager.viewmodel.UtilityViewModel;
import com.ste.arch.ui.viewpager.viewmodel.UtilityViewModelFactory;

import dagger.Module;
import dagger.Provides;


@Module
public class FragmentBModule {

  @Provides
  MessageRouterViewModel providePagerAgentVm(FragmentB fragmentB) {
    return ViewModelProviders.of(fragmentB.getActivity()).get(MessageRouterViewModel.class);
  }

  @Provides
  RepositoryViewModel provideRepositoryViewModel(RepositoryViewModelFactory factory, FragmentB fragmentB) {
    return ViewModelProviders.of(fragmentB.getActivity(), factory).get(RepositoryViewModel.class);
  }

  @Provides
  UtilityViewModel provideUtilityViewModel(UtilityViewModelFactory factory, FragmentB fragmentB) {
    return ViewModelProviders.of(fragmentB.getActivity(), factory).get(UtilityViewModel.class);
  }

  @Provides
  FragmentDetailBinding provideDataBinding(FragmentB fragmentB) {

    return DataBindingUtil
            .inflate(fragmentB.getActivity().getLayoutInflater(),
                    R.layout.fragment_detail, null,
                    false);
  }
}
