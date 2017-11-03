package com.ste.arch.ui.viewpager;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SearchView;

import com.ste.arch.R;
import com.ste.arch.ui.viewpager.vm.BusinessViewModel;
import com.ste.arch.ui.viewpager.vm.PagerAgentViewModel;
import com.ste.arch.ui.viewpager.vm.RepositoryViewModel;
import com.ste.arch.ui.viewpager.vm.UtilityViewModel;

import dagger.android.support.DaggerAppCompatActivity;
import javax.inject.Inject;


public class PagerActivity extends DaggerAppCompatActivity {

    @Inject
    PagerAgentViewModel pagerAgentViewModel;

    @Inject
    BusinessViewModel businessViewModel;

    @Inject
    RepositoryViewModel repositoryViewModel;

    @Inject
    UtilityViewModel utilityViewModel;


    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private CoordinatorLayout mCoordinator;
    private TabLayout mTablayout;
    private SearchView mSearchView;
    private Toolbar mToolbar;
    private String mSearchString;
    private CharSequence titles[]= {"Fragment A","Fragment B", "Dummy", "Dummy", "Dummy", "Fragment C"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        mCoordinator = findViewById(R.id.container);
        mSearchView = findViewById(R.id.searchstring);
        mTablayout = findViewById(R.id.tablayout);
        mPager = findViewById(R.id.pager);
        mToolbar = findViewById(R.id.toolbar);
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setSupportActionBar(mToolbar);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String repo) {
                mSearchString = repo;
                utilityViewModel.askNetworkStatus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        mTablayout.setupWithViewPager(mPager);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()  {
            @Override
            public void onPageScrolled(final int position, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int position) {
                mToolbar.setTitle(titles[position]);
                FragmentVisibility fragment = (FragmentVisibility) mPagerAdapter.instantiateItem(mPager, position);
                if (fragment != null) {
                    fragment.fragmentBecameVisible();

                }
            }

            @Override
            public void onPageScrollStateChanged(final int position) {
            }
        });


        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        observeEvents();
    }

    private void observeEvents() {

        utilityViewModel.isInternetConnected().observe(this, isInternetConnected -> {

            if ( (isInternetConnected) && (!TextUtils.isEmpty(mSearchString)) )
            {
                doSearch(mSearchString);
            }
            else
            {
                handleSnackBar("No internet connection!");
            }

        });



        repositoryViewModel.getSnackBar().observe(this, snackMsg -> {
            handleSnackBar(snackMsg);
        });

        utilityViewModel.getSnackBar().observe(this, snackMsg -> {
            handleSnackBar(snackMsg);
        });

        repositoryViewModel.getApiIssueResponse().observe(this,
                apiResponse -> {
                   Log.e("STEFANO","network call saved issue list by activity in Room");
                }
        );

        repositoryViewModel.getApiContributorResponse().observe(this,
                apiResponse -> {
                    Log.e("STEFANO","network call saved contributor list by activity in Room");
                }
        );
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public static final int FRAGMENT_A_POS = 0;
        public static final int FRAGMENT_B_POS = 1;
        public static final int FRAGMENT_C_POS = 2;
        public static final int FRAGMENT_D_POS = 3;
        public static final int FRAGMENT_E_POS = 4;
        public static final int FRAGMENT_F_POS = 5;
        public static final int FRAGMENT_COUNT = 6;
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case FRAGMENT_A_POS :
                    return BlankFragmentA.newInstance();
                case FRAGMENT_B_POS:
                    return BlankFragmentB.newInstance();
                case FRAGMENT_C_POS:
                    return BlankFragment.newInstance();
                case FRAGMENT_D_POS:
                    return BlankFragment.newInstance();
                case FRAGMENT_E_POS:
                    return BlankFragment.newInstance();
                case FRAGMENT_F_POS :
                    return BlankFragmentC.newInstance();
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }
        @Override
        public CharSequence getPageTitle(int position) {

            return titles[position];
        }
    }


    private void handleSnackBar(String snackMsg) {
        Snackbar snackbar = Snackbar.make(mCoordinator, snackMsg, Snackbar.LENGTH_LONG);
        snackbar.show();

    }




    private void doSearch(String mSearchString) {

        if (mSearchString.length() > 0) {
            String[] query = mSearchString.split("/");
            if (query.length == 2) {
                utilityViewModel.setShowProgressInObserverFragments();
                repositoryViewModel.setQueryString(query[0], query[1], true);
            } else {
                handleSnackBar("Error wrong format of input. Required format owner/repository_name");
            }
        } else {
            handleSnackBar("IssueRepository name empty. Required format owner/repository_name");
        }
    }

}
