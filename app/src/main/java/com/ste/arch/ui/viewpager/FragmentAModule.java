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
public class FragmentAModule {

    @Provides
    MessageRouterViewModel providePagerAgentVm(FragmentA fragmentA) {
        return ViewModelProviders.of(fragmentA.getActivity()).get(MessageRouterViewModel.class);
    }

    @Provides
    RepositoryViewModel provideRepositoryViewModel(RepositoryViewModelFactory factory, FragmentA fragmentA) {
        return ViewModelProviders.of(fragmentA.getActivity(), factory).get(RepositoryViewModel.class);
    }

    @Provides
    UtilityViewModel provideUtilityViewModel(UtilityViewModelFactory factory, FragmentA fragmentA) {
        return ViewModelProviders.of(fragmentA.getActivity(), factory).get(UtilityViewModel.class);
    }
}
