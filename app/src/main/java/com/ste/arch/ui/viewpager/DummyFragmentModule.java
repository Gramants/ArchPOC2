package com.ste.arch.ui.viewpager;

import android.arch.lifecycle.ViewModelProviders;

import com.ste.arch.ui.viewpager.viewmodel.MessageRouterViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class DummyFragmentModule {

    @Provides
    MessageRouterViewModel providePagerAgentVm(DummyFragment dummyFragment) {
        return ViewModelProviders.of(dummyFragment.getActivity()).get(MessageRouterViewModel.class);
    }


}
