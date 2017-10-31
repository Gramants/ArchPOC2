package com.cn29.aac.ui.viewpager;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cn29.aac.R;
import com.cn29.aac.ui.viewpager.vm.BusinessViewModel;
import com.cn29.aac.ui.viewpager.vm.PagerAgentViewModel;
import com.cn29.aac.ui.viewpager.vm.RepositoryViewModel;
import com.cn29.aac.ui.viewpager.vm.UtilityViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment implements FragmentVisibility {



    private TextView textView;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        return view;
    }

    @Override
    public void fragmentBecameVisible() {
        Log.e("STEFANO","fragmentBecameVisible Blank");

    }
}
