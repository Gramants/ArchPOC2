package com.ste.arch.ui.viewpager;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.ste.arch.entities.ContributorTransformed;
import com.ste.arch.ui.viewpager.vm.BusinessViewModel;
import com.ste.arch.ui.viewpager.vm.PagerAgentViewModel;
import com.ste.arch.ui.viewpager.vm.RepositoryViewModel;
import com.ste.arch.ui.viewpager.vm.UtilityViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragmentC#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentC extends DaggerFragment implements FragmentVisibility {

    @Inject
    PagerAgentViewModel pagerAgentViewModel;

    @Inject
    BusinessViewModel businessViewModel;

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
    public BlankFragmentC() {
        // Required empty public constructor
    }

    public static BlankFragmentC newInstance() {
        BlankFragmentC fragment = new BlankFragmentC();

        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view,Bundle save) {
        super.onViewCreated(view,save);
        Log.e("STEFANO","onviewcreated C");

        //setup the listener for the fragment C
        Observer<String> observer = msg -> textView.setText(msg);
        pagerAgentViewModel.getMessageContainerC().observe(this, observer);


        repositoryViewModel.getApiContributorResponse().observe(this,
                apiResponse -> {

                    if (apiResponse.size()>0)
                    {
                        cache=apiResponse;
                        textView2.setText("Contributors found:"+String.valueOf(apiResponse.size()) );
                        textView3.setText("No error");
                        mProgress.setVisibility(View.INVISIBLE);
                        mAdapter.clearContributors();
                        mAdapter.addContributors(apiResponse);
                    }
                }
        );


        utilityViewModel.getShowDialogIssueAndContributor().observe(this, showDialog -> {
            mProgress.setVisibility(showDialog ? View.VISIBLE : View.INVISIBLE);
        });

         repositoryViewModel.getContributorNetworkErrorResponse().observe(this, networkError -> {
          textView3.setText("No contributor found from Network call");
          mProgress.setVisibility(View.INVISIBLE);
        });
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
                       businessViewModel.setValueContributorContent(new ContributorTransformed( ((ContributorDataModel) cache.get(position)).getLogin()+" translated"));
                       utilityViewModel.setSnackBar("Contributor name added to Detail tab");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );








        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pagerAgentViewModel.sendMessageToA("Got Hello from C!");
                pagerAgentViewModel.sendMessageToB("Got Hello from C!");
                //pagerAgentViewModel.sendMessageToC("Hello C from C");
            }
        });



        return view;
    }




    @Override
    public void fragmentBecameVisible() {
        Log.e("STEFANO","fragmentBecameVisible C load  from Room");
        repositoryViewModel.setQueryString(null,null,false);
    }
}
