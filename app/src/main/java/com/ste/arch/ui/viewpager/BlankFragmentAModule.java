package com.ste.arch.ui.viewpager;

import android.arch.lifecycle.ViewModelProviders;

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
public class BlankFragmentAModule {

    @Provides
    PagerAgentViewModel providePagerAgentVm(BlankFragmentA blankFragmentA) {
        return ViewModelProviders.of(blankFragmentA.getActivity()).get(PagerAgentViewModel.class);
    }

    @Provides
    RepositoryViewModel provideRepositoryViewModel(RepositoryViewModelFactory factory, BlankFragmentA blankFragmentA) {
        return ViewModelProviders.of(blankFragmentA.getActivity(), factory).get(RepositoryViewModel.class);
    }

    @Provides
    UtilityViewModel provideUtilityViewModel(UtilityViewModelFactory factory, BlankFragmentA blankFragmentA) {
        return ViewModelProviders.of(blankFragmentA.getActivity(), factory).get(UtilityViewModel.class);
    }
}
