package com.ste.arch.ui.viewpager;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.SearchView;

import com.ste.arch.R;
import com.ste.arch.entities.ContributorDataModel;
import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.ui.viewpager.viewmodel.MessageRouterViewModel;
import com.ste.arch.ui.viewpager.viewmodel.RepositoryViewModel;
import com.ste.arch.ui.viewpager.viewmodel.UtilityViewModel;

import dagger.android.support.DaggerAppCompatActivity;

import javax.inject.Inject;


public class PagerActivity extends DaggerAppCompatActivity {

    @Inject
    MessageRouterViewModel messageRouterViewModel;

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

    private CharSequence titles[] = {"Fragment A", "Fragment B", "Dummy", "Dummy", "Dummy", "Dummy", "Fragment B", "Dummy", "Dummy", "Dummy", "Dummy", "Fragment C"};
    private FloatingActionButton mAddRecord;
    private FloatingActionButton mUpdateRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        mCoordinator = findViewById(R.id.container);
        mSearchView = findViewById(R.id.searchstring);
        mTablayout = findViewById(R.id.tablayout);
        mPager = findViewById(R.id.pager);
        mToolbar = findViewById(R.id.toolbar);

        mUpdateRecord = findViewById(R.id.fab2);
        mAddRecord = findViewById(R.id.fab1);

        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        setSupportActionBar(mToolbar);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String repo) {
                mSearchString = repo;
                utilityViewModel.setObservableNetworkStatus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        mTablayout.setupWithViewPager(mPager);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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


        mAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repositoryViewModel.addIssueRecord(new IssueDataModel("url", "url", 0, "AAA INSERTED", "A", "2017-11-03T05:29:08Z", "INSERTED", "STE", "url"));
                repositoryViewModel.addContributorRecord(new ContributorDataModel("AAA INSERTED", "STE"));

            }
        });


        mUpdateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repositoryViewModel.updateIssueTitleRecord("AAA INSERTED", "BBB UPDATED!");
                repositoryViewModel.updateContributorRecord("AAA INSERTED", "BBB UPDATED!");

            }
        });


        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        observeEvents();
    }

    private void observeEvents() {

        utilityViewModel.getObservableNetworkStatus().observe(this, isInternetConnected -> {

            if ((isInternetConnected) && (!TextUtils.isEmpty(mSearchString))) {
                doSearch(mSearchString);
            } else {
                handleSnackBar("No internet connection!");
            }

        });


        utilityViewModel.getSnackBar().observe(this, snackMsg -> {
            handleSnackBar(snackMsg);
        });


        // force network download in activity and save in db for issues and contributors
        /*
        repositoryViewModel.getApiIssueResponse().observe(this,
                apiResponse -> {
                }
        );
        */
        repositoryViewModel.getApiIssueResponsePaged().observe(this,
                apiResponse -> {
                }
        );

        repositoryViewModel.getApiContributorResponse().observe(this,
                apiResponse -> {
                }
        );


        utilityViewModel.getObservableSavedSearchString().observe(this, searchString -> {
            mSearchView.setIconified(false);
            mSearchView.setQuery(new String(searchString), false);
            mSearchView.clearFocus();
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mSearchView.clearFocus();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public static final int FRAGMENT_0_POS = 0;
        public static final int FRAGMENT_1_POS = 1;
        public static final int FRAGMENT_2_POS = 2;
        public static final int FRAGMENT_3_POS = 3;
        public static final int FRAGMENT_4_POS = 4;
        public static final int FRAGMENT_5_POS = 5;
        public static final int FRAGMENT_6_POS = 6;
        public static final int FRAGMENT_7_POS = 7;
        public static final int FRAGMENT_8_POS = 8;
        public static final int FRAGMENT_9_POS = 9;
        public static final int FRAGMENT_10_POS = 10;
        public static final int FRAGMENT_11_POS = 11;
        public static final int FRAGMENT_COUNT = 12;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRAGMENT_0_POS:
                    return FragmentA.newInstance();  // A
                case FRAGMENT_1_POS:
                    return FragmentB.newInstance();  // B  still in fragmentmanager when in A
                case FRAGMENT_2_POS:
                    return DummyFragment.newInstance();
                case FRAGMENT_3_POS:
                    return DummyFragment.newInstance();
                case FRAGMENT_4_POS:
                    return DummyFragment.newInstance();
                case FRAGMENT_5_POS:
                    return DummyFragment.newInstance();
                case FRAGMENT_6_POS:
                    return FragmentB.newInstance(); // B   not more in fragment manager when in A or B
                case FRAGMENT_7_POS:
                    return DummyFragment.newInstance();
                case FRAGMENT_8_POS:
                    return DummyFragment.newInstance();
                case FRAGMENT_9_POS:
                    return DummyFragment.newInstance();
                case FRAGMENT_10_POS:
                    return DummyFragment.newInstance();
                case FRAGMENT_11_POS:
                    return FragmentC.newInstance();  // C
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
                repositoryViewModel.setQueryString(query[0], query[1], true);
                utilityViewModel.setSavedSearchString(query[0] + "/" + query[1]);
                utilityViewModel.setSnackBar("Search string: " + query[0] + "/" + query[1]);

            } else {
                handleSnackBar("Error wrong format of input. Required format owner/repository_name");
            }
        } else {
            handleSnackBar("IssueRepository name empty. Required format owner/repository_name");
        }
    }

}
