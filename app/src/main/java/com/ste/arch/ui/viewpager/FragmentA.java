package com.ste.arch.ui.viewpager;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ste.arch.R;
import com.ste.arch.adapters.IssueDataAdapter;
import com.ste.arch.adapters.IssueDataAdapterPaged;
import com.ste.arch.adapters.RecyclerItemClickListener;
import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.repositories.Status;
import com.ste.arch.ui.viewpager.viewmodel.MessageRouterViewModel;
import com.ste.arch.ui.viewpager.viewmodel.RepositoryViewModel;
import com.ste.arch.ui.viewpager.viewmodel.UtilityViewModel;

import java.util.List;

import dagger.android.support.DaggerFragment;

import javax.inject.Inject;


public class FragmentA extends DaggerFragment implements FragmentVisibility {

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
    private IssueDataAdapterPaged mAdapter;
    private RecyclerView mRecyclerView;
    private List<IssueDataModel> cache;

    public FragmentA() {

    }

    public static FragmentA newInstance() {
        FragmentA fragment = new FragmentA();
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        // as it is the first fragment of the pager, the first time the fragmentBecameVisible() is not called
        // but onresume yes
        repositoryViewModel.getResultsFromDatabase();
    }

    @Override
    public void onViewCreated(View view, Bundle save) {
        super.onViewCreated(view, save);


        Observer<String> observer = msg -> textView.setText(msg);
        messageRouterViewModel.getMessageContainerA().observe(this, observer);

/*
        repositoryViewModel.getApiIssueResponse().observe(this,
                apiResponse -> {

                    if (apiResponse.status == Status.ERROR) {
                        textView3.setText("Error in last query, reason: " + apiResponse.message);
                        mProgress.setVisibility(View.INVISIBLE);
                    } else if (apiResponse.status.equals(Status.SUCCESS)) {
                        textView3.setText("Network call successful for Issues endpoint");
                        mProgress.setVisibility(View.INVISIBLE);
                    } else if (apiResponse.status.equals(Status.LOADING)) {
                        mProgress.setVisibility(View.VISIBLE);
                        textView3.setText("Loading Issues from network");
                    } else if (apiResponse.status.equals(Status.SUCCESSFROMDB)) {
                        textView3.setText("Issue loaded from Room");
                        mProgress.setVisibility(View.INVISIBLE);
                    }


                    if (apiResponse.data != null) {
                        cache = apiResponse.data;
                        textView2.setText("Issues found:" + String.valueOf(apiResponse.data.size()));
                        mAdapter.clearIssues();
                        mAdapter.addIssues(apiResponse.data);

                    }


                }
        );
*/
        repositoryViewModel.getApiIssueResponsePaged().observe(this,
                apiResponse -> {

                        if (apiResponse.status == Status.ERROR) {
                            textView3.setText("Error in last query, reason: " + apiResponse.message);
                            mProgress.setVisibility(View.INVISIBLE);
                        } else if (apiResponse.status.equals(Status.SUCCESS)) {
                            textView3.setText("Network call successful for Issues endpoint");
                            mProgress.setVisibility(View.INVISIBLE);
                        } else if (apiResponse.status.equals(Status.LOADING)) {
                            mProgress.setVisibility(View.VISIBLE);
                            textView3.setText("Loading Issues from network");
                        } else if (apiResponse.status.equals(Status.SUCCESSFROMDB)) {
                            textView3.setText("Issue loaded from Room");
                            mProgress.setVisibility(View.INVISIBLE);
                        }


                        if (apiResponse.data != null) {
                            cache = apiResponse.data;
                            textView2.setText("Issues found:" + String.valueOf(apiResponse.data.size()));
                            mAdapter.clearIssues();
                            mAdapter.addIssues(apiResponse.data);

                        }



                    }
        );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank_a, container, false);

        textView = view.findViewById(R.id.fragment_textA);
        textView2 = view.findViewById(R.id.fragment_textA2);
        textView3 = view.findViewById(R.id.fragment_textA3);

        mProgress = (ProgressBar) view.findViewById(R.id.marker_progress);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        // set the onclick listener
        Button button = view.findViewById(R.id.btnA);


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
        mAdapter = new IssueDataAdapterPaged(getLayoutInflater());
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        repositoryViewModel.setIssueByUi((IssueDataModel) cache.get(position));
                        utilityViewModel.setSnackBar("Item passed to Fragment B using a cached List object (not DB)");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        repositoryViewModel.setRecordIdToStream(((IssueDataModel) cache.get(position)).getId());
                        utilityViewModel.setSnackBar("Item passed to Fragment B selecting the DB record");
                    }
                })
        );

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                repositoryViewModel.deleteIssueRecordById(((IssueDataModel) cache.get(position)).getId());
                utilityViewModel.setSnackBar("Record deleted and Room livedata list refreshed");
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageRouterViewModel.sendMessageToDummy("Got Hello from A!");
                messageRouterViewModel.sendMessageToC("Got Hello from A!");
            }
        });

        return view;
    }


    @Override
    public void fragmentBecameVisible() {
        repositoryViewModel.getResultsFromDatabase();
    }
}




