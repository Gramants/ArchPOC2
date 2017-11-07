package com.ste.arch.ui.viewpager;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ste.arch.R;
import com.ste.arch.ui.viewpager.viewmodel.MessageRouterViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class DummyFragment extends DaggerFragment implements FragmentVisibility {

    private TextView textView;

    @Inject
    MessageRouterViewModel messageRouterViewModel;


    public DummyFragment() {

    }

    public static DummyFragment newInstance() {
        DummyFragment fragment = new DummyFragment();

        return fragment;
    }


    @Override
    public void onViewCreated(View view, Bundle save) {
        super.onViewCreated(view, save);

        Observer<String> observer = msg -> textView.setText(msg);
        messageRouterViewModel.getMessageContainerDummy().observe(this, observer);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        textView = view.findViewById(R.id.fragment_text2);
        return view;
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
