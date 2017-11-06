package com.ste.arch.ui.viewpager;

import android.arch.lifecycle.ViewModelProviders;

import com.ste.arch.ui.viewpager.vm.PagerAgentViewModel;
import com.ste.arch.ui.viewpager.vm.RepositoryViewModel;
import com.ste.arch.ui.viewpager.vm.RepositoryViewModelFactory;
import com.ste.arch.ui.viewpager.vm.UtilityViewModel;
import com.ste.arch.ui.viewpager.vm.UtilityViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class BlankFragmentModule {

    @Provides
    PagerAgentViewModel providePagerAgentVm(BlankFragment blankFragment) {
        return ViewModelProviders.of(blankFragment.getActivity()).get(PagerAgentViewModel.class);
    }


}
