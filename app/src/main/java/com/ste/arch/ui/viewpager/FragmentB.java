package com.ste.arch.ui.viewpager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ste.arch.databinding.FragmentDetailBinding;
import com.ste.arch.entities.ContributorTransformed;
import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.repositories.Status;
import com.ste.arch.ui.viewpager.viewmodel.MessageRouterViewModel;
import com.ste.arch.ui.viewpager.viewmodel.RepositoryViewModel;
import com.ste.arch.ui.viewpager.viewmodel.UtilityViewModel;

import dagger.android.support.DaggerFragment;

import javax.inject.Inject;


public class FragmentB extends DaggerFragment implements FragmentVisibility {

    @Inject
    MessageRouterViewModel messageRouterViewModel;

    @Inject
    RepositoryViewModel repositoryViewModel;

    @Inject
    UtilityViewModel utilityViewModel;

    @Inject
    FragmentDetailBinding mFragmentDetailBinding;

    private IssueDataModel mClickedIssueModel;
    private ContributorTransformed mClickedContributorModel;


    public FragmentB() {
        // Required empty public constructor
    }

    public static FragmentB newInstance() {
        FragmentB fragment = new FragmentB();
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if ((mClickedContributorModel != null) || (mClickedIssueModel != null)) {
            outState.putParcelable("contributor", mClickedContributorModel);
            outState.putParcelable("issue", mClickedIssueModel);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelable("contributor") != null) {
                mFragmentDetailBinding.setContributor(savedInstanceState.getParcelable("contributor"));
                mClickedContributorModel = savedInstanceState.getParcelable("contributor");
            }
            if (savedInstanceState.getParcelable("issue") != null) {
                mFragmentDetailBinding.setIssue(savedInstanceState.getParcelable("issue"));
                mClickedIssueModel = savedInstanceState.getParcelable("issue");
            }

        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return mFragmentDetailBinding.getRoot();
    }


    @Override
    public void fragmentBecameVisible() {

    //observer Issue object
        repositoryViewModel.getMixedDetailIssueResult().observe(this, resource -> {
            {
                if (resource != null) {

                    if (resource.status.equals(Status.SUCCESSFROMDB)) {
                        // can perform other actions
                    } else if (resource.status.equals(Status.SUCCESSFROMUI)) {
                        // can perform other actions
                    }

                    if (resource.data != null) {
                        mFragmentDetailBinding.setIssue(resource.data);
                        this.mClickedIssueModel = resource.data;
                    }
                    mFragmentDetailBinding.executePendingBindings();
                }
            }
        });

        //observer Contributor object
        repositoryViewModel.getContributorObjectTransformed().observe(this, resource -> {
            {
                mFragmentDetailBinding.setContributor(resource);
                this.mClickedContributorModel = resource;
                mFragmentDetailBinding.executePendingBindings();
            }
        });


    }

}
