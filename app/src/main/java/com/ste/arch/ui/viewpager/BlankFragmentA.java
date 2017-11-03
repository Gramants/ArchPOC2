package com.ste.arch.ui.viewpager;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ste.arch.R;
import com.ste.arch.adapters.IssueDataAdapter;
import com.ste.arch.adapters.RecyclerItemClickListener;
import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.repositories.asyncoperations.Status;
import com.ste.arch.ui.viewpager.vm.BusinessViewModel;
import com.ste.arch.ui.viewpager.vm.PagerAgentViewModel;
import com.ste.arch.ui.viewpager.vm.RepositoryViewModel;
import com.ste.arch.ui.viewpager.vm.UtilityViewModel;

import java.util.List;

import dagger.android.support.DaggerFragment;
import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragmentA#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentA extends DaggerFragment implements FragmentVisibility {

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
    private Boolean mIsVisible;
    private IssueDataAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<IssueDataModel> cache;
    public BlankFragmentA() {
    // Required empty public constructor
  }

  // TODO: Rename and change types and number of parameters
  public static BlankFragmentA newInstance() {
    BlankFragmentA fragment = new BlankFragmentA();
    return fragment;
  }



    @Override
    public void onResume() {
        super.onResume();
        // as it is the first fragment of the pager, the first time the fragmentBecameVisible() is not called
        // but onresume yes
        Log.e("STEFANO","onresume load  from Room");
        repositoryViewModel.setQueryString(null,null,false);

    }
  @Override
  public void onViewCreated(View view,Bundle save) {
    super.onViewCreated(view,save);



      Observer<String> observer = msg -> textView.setText(msg);
      pagerAgentViewModel.getMessageContainerA().observe(this, observer);


      repositoryViewModel.getApiIssueResponseNew().observe(this,
              apiResponse -> {

                if (apiResponse.status==Status.ERROR)
                {
                    textView3.setText(apiResponse.message);
                }
                else if (apiResponse.status.equals(Status.SUCCESS) )
                {
                      textView3.setText("Network call succesfull for Issues");
                }
                else if (apiResponse.status.equals(Status.LOADING) )
                {
                    textView3.setText("Loading Issues from network");
                }
                else
                {
                    textView3.setText("Issue loaded from Room");
                }



                if (apiResponse.data!=null)
                {
                      cache=apiResponse.data;
                      textView2.setText("Issues found:"+String.valueOf(apiResponse.data.size()) );
                      mAdapter.clearIssues();
                      mAdapter.addIssues(apiResponse.data);

                }

                  mProgress.setVisibility(View.INVISIBLE);

              }
      );




  }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }






    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        utilityViewModel.getShowDialogIssueAndContributor().observe(this, showDialog -> {
            mProgress.setVisibility(showDialog ? View.VISIBLE : View.INVISIBLE);
        });
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
      mAdapter = new IssueDataAdapter(getLayoutInflater());
      mRecyclerView.setAdapter(mAdapter);


      mRecyclerView.addOnItemTouchListener(
              new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                  @Override
                  public void onItemClick(View view, int position) {
                    businessViewModel.setValueIssueContent((IssueDataModel) cache.get(position));
                    utilityViewModel.setSnackBar("Issue Detail in Fragment B passing a cached object");
                  }

                  @Override
                  public void onLongItemClick(View view, int position) {

                      //mBusinessViewModel.loadIssue(0,(IssueDataModel) cache.get(position));

                      //mBusinessViewModel.loadIssue(((IssueDataModel) cache.get(position)).getId());
                      //mUtilityViewModel.setSnackBar("Record from db opened in Detail on LongClick");

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
              final int position = viewHolder.getAdapterPosition(); //swiped position

              //FUNZIONA!!
              //repositoryViewModel.deleteRecordByIdNew(((IssueDataModel) cache.get(position)).getId());
              repositoryViewModel.deleteIssueRecordById(((IssueDataModel) cache.get(position)).getId());

              //mUtilityViewModel.setSnackBar("Record deleted and Room livedata list refreshed");


          }
      };
      ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
      itemTouchHelper.attachToRecyclerView(mRecyclerView);

    button.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        //pagerAgentViewModel.sendMessageToA("Hello A from A");
        pagerAgentViewModel.sendMessageToB("Got Hello from A!");
        pagerAgentViewModel.sendMessageToC("Got Hello from A!");
      }
    });
    return view;
  }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mIsVisible=true;
        }
        else {
            mIsVisible=false;
        }
    }

    @Override
    public void fragmentBecameVisible() {
      Log.e("STEFANO","fragmentBecameVisible load  from Room");
        repositoryViewModel.setQueryString(null,null,false);
    }
}




