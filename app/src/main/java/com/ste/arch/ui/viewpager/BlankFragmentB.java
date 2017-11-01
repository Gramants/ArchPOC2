package com.ste.arch.ui.viewpager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ste.arch.databinding.FragmentDetailBinding;
import com.ste.arch.entities.ContributorTransformed;
import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.ui.viewpager.vm.BusinessViewModel;
import com.ste.arch.ui.viewpager.vm.PagerAgentViewModel;
import com.ste.arch.ui.viewpager.vm.RepositoryViewModel;
import com.ste.arch.ui.viewpager.vm.UtilityViewModel;

import dagger.android.support.DaggerFragment;

import javax.inject.Inject;


public class BlankFragmentB extends DaggerFragment implements FragmentVisibility {

    @Inject
    PagerAgentViewModel pagerAgentViewModel;

    @Inject
    BusinessViewModel businessViewModel;

    @Inject
    RepositoryViewModel repositoryViewModel;

    @Inject
    UtilityViewModel utilityViewModel;

    @Inject
    FragmentDetailBinding mFragmentDetailBinding;

    private IssueDataModel mClickedIssueModel;
    private ContributorTransformed mClickedContributorModel;


    public BlankFragmentB() {
        // Required empty public constructor
    }

    public static BlankFragmentB newInstance() {
        BlankFragmentB fragment = new BlankFragmentB();
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("STEFANO", "onSaveInstanceState salvo");
        if ((mClickedContributorModel != null) || (mClickedIssueModel != null)) {
            Log.e("STEFANO", "salvo o issue o contributor cliccati");
            outState.putParcelable("contributor", mClickedContributorModel);
            outState.putParcelable("issue", mClickedIssueModel);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        // as it is the first fragment of the pager, the first time the fragmentBecameVisible() is not called
        // but onresume yes
        Log.e("STEFANO", "onresume ");


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
                Log.e("STEFANO", "val " + ((IssueDataModel) savedInstanceState.getParcelable("issue")).getTitle());

            }
            //mFragmentDetailBinding.executePendingBindings();
            Log.e("STEFANO", "recupero da savedinstance state e faccio vedere ");
        }

        Log.e("STEFANO", "onactivitycreated listening to observable");
        businessViewModel.getIssue().observe(this, resource -> {
            {
                if (resource != null) {
                    Log.e("STEFANO", "issue da cache " + resource.getTitle());
                    mFragmentDetailBinding.setIssue(resource);
                    this.mClickedIssueModel = resource;
                    //mFragmentDetailBinding.executePendingBindings();
                }
            }
        });


        businessViewModel.getContributorContent().observe(this, resource -> {
            {
                Log.e("STEFANO", "contributor  " + resource.getTitle());
                mFragmentDetailBinding.setContributor(resource);
                this.mClickedContributorModel = resource;
                // mFragmentDetailBinding.executePendingBindings();
            }
        });


        businessViewModel.getIssueContent().observe(this, resource -> {
            {
                if (resource != null) {
                    // on delete record is firing, WHy?
                    Log.e("STEFANO", "issue da db " + resource.getTitle());
                    this.mClickedIssueModel = resource;

                    mFragmentDetailBinding.setIssue(resource);
                    // mFragmentDetailBinding.executePendingBindings();
                }
            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("STEFANO", "oncreateview cleaning all");

        return mFragmentDetailBinding.getRoot();
/*
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        textView = view.findViewById(R.id.fragment_textB);
        //set the on click listener
        Button button = view.findViewById(R.id.btnB);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pagerAgentViewModel.sendMessageToA("Got Hello from B!");
                pagerAgentViewModel.sendMessageToC("Got Hello from B!");
            }
        });
        return view;
        */
    }

    @Override
    public void fragmentBecameVisible() {


    }

}
