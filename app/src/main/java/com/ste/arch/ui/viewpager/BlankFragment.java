package com.ste.arch.ui.viewpager;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ste.arch.R;
import com.ste.arch.ui.viewpager.vm.PagerAgentViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class BlankFragment  extends DaggerFragment implements FragmentVisibility {

    private TextView textView;

    @Inject
    PagerAgentViewModel pagerAgentViewModel;


    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();

        return fragment;
    }


    @Override
    public void onViewCreated(View view, Bundle save) {
        super.onViewCreated(view, save);

        Observer<String> observer = msg -> textView.setText(msg);
        pagerAgentViewModel.getMessageContainerDummy().observe(this, observer);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        textView = view.findViewById(R.id.fragment_text2);
        return view;
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
