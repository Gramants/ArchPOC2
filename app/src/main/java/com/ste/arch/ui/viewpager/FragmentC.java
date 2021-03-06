package com.ste.arch.ui.viewpager;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ste.arch.R;
import com.ste.arch.adapters.ContributorDataAdapter;
import com.ste.arch.adapters.RecyclerItemClickListener;
import com.ste.arch.entities.ContributorDataModel;
import com.ste.arch.repositories.Status;
import com.ste.arch.ui.viewpager.viewmodel.MessageRouterViewModel;
import com.ste.arch.ui.viewpager.viewmodel.RepositoryViewModel;
import com.ste.arch.ui.viewpager.viewmodel.UtilityViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentC#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentC extends DaggerFragment implements FragmentVisibility {

    @Inject
    MessageRouterViewModel messageRouterViewModel;

    @Inject
    RepositoryViewModel repositoryViewModel;

    @Inject
    UtilityViewModel utilityViewModel;

    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private ProgressBar mProgress;
    private RecyclerView mRecyclerView;
    private ContributorDataAdapter mAdapter;
    private List<ContributorDataModel> cache;

    public FragmentC() {
        // Required empty public constructor
    }

    public static FragmentC newInstance() {
        FragmentC fragment = new FragmentC();

        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle save) {
        super.onViewCreated(view, save);

        //setup the listener for the fragment C
        Observer<String> observer = msg -> textView.setText(msg);
        messageRouterViewModel.getMessageContainerC().observe(this, observer);


        repositoryViewModel.getApiContributorResponse().observe(this,
                apiResponse -> {

                    if (apiResponse.status == Status.ERROR) {
                        textView3.setText("Error in last query, reason: " + apiResponse.message);
                        mProgress.setVisibility(View.INVISIBLE);
                    } else if (apiResponse.status.equals(Status.SUCCESS)) {
                        textView3.setText("Network call successful for Contributor endpoint");
                        mProgress.setVisibility(View.INVISIBLE);
                    } else if (apiResponse.status.equals(Status.LOADING)) {
                        mProgress.setVisibility(View.VISIBLE);
                        textView3.setText("Loading Contributor from network");
                    } else if (apiResponse.status.equals(Status.SUCCESSFROMDB)) {
                        textView3.setText("Contributors loaded from Room");
                        mProgress.setVisibility(View.INVISIBLE);
                    }


                    if (apiResponse.data != null) {
                        cache = apiResponse.data;
                        textView2.setText("Contributors found:" + String.valueOf(apiResponse.data.size()));
                        mAdapter.clearContributors();
                        mAdapter.addContributors(apiResponse.data);

                    }


                }
        );


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank_c, container, false);
        textView = view.findViewById(R.id.fragment_textC);
        textView2 = view.findViewById(R.id.fragment_textC2);
        textView3 = view.findViewById(R.id.fragment_textC3);
        mProgress = (ProgressBar) view.findViewById(R.id.marker_progress);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //set the on click listener
        Button button = view.findViewById(R.id.btnC);


        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false)
        );
        mRecyclerView.hasFixedSize();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(), LinearLayoutManager.VERTICAL
        );
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mAdapter = new ContributorDataAdapter(getLayoutInflater());
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        repositoryViewModel.setContributorByUi((ContributorDataModel) cache.get(position));
                        utilityViewModel.setSnackBar("Contributor name added to Detail tab");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageRouterViewModel.sendMessageToA("Got Hello from C!");
                messageRouterViewModel.sendMessageToDummy("Got Hello from C!");
            }
        });


        return view;
    }


    @Override
    public void fragmentBecameVisible() {
        repositoryViewModel.setQueryString(null, null, false);
    }
}
