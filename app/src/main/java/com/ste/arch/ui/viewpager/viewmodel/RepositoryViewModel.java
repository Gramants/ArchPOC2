package com.ste.arch.ui.viewpager.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.ste.arch.entities.ContributorDataModel;
import com.ste.arch.entities.ContributorTransformed;
import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.entities.QueryString;
import com.ste.arch.repositories.ContributorRepository;
import com.ste.arch.repositories.IssueRepository;
import com.ste.arch.repositories.Resource;
import com.ste.arch.repositories.preferences.PersistentStorageProxy;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;

// http://www.zoftino.com/android-livedata-examples NEWORK CHECK

public class RepositoryViewModel extends ViewModel {

    @VisibleForTesting
    private MutableLiveData<QueryString> mQueryStringObject;

    private MutableLiveData<Integer> mSelectedId;
    private MutableLiveData<IssueDataModel> mSelectedIssue;
    private MutableLiveData<ContributorDataModel> mSelectedContributor;


    private IssueRepository mIssueRepository;
    private ContributorRepository mContributorRepository;
    private PersistentStorageProxy mPersistentStorageProxy;


    // ISSUE
//issue list streamer
    private LiveData<Resource<List<IssueDataModel>>> mResultIssueListDataModel;

    private LiveData<Resource<PagedList<IssueDataModel>>> mResultIssueListDataModelPaged;

    //issue item object streamers
    private LiveData<Resource<IssueDataModel>> mResultIssueItemDataModel;
    private LiveData<Resource<IssueDataModel>> mResultIssueItemDataModelByObject;
    // stream container of issue object from ui or from db
    private MediatorLiveData<Resource<IssueDataModel>> mIssueResultItemMixer;


    // CONTRIBUTOR
//contributor list streamer
    private LiveData<Resource<List<ContributorDataModel>>> mResultContributorListDataModel;
    private LiveData<Resource<ContributorDataModel>> mResultContributorItemDataModelByObject;


    @Inject
    public RepositoryViewModel(IssueRepository mIssueRepository, ContributorRepository mContributorRepository, PersistentStorageProxy mPersistentStorageProxy) {
        this.mIssueRepository = mIssueRepository;
        this.mContributorRepository = mContributorRepository;
        this.mPersistentStorageProxy = mPersistentStorageProxy;
    }


    public void init() {

        // invoked only by the factory of the container Activity
        // UI click and message events
        mQueryStringObject = new MutableLiveData<>();


// ISSUE
        mSelectedId = new MutableLiveData<>();
        mSelectedIssue = new MutableLiveData<>();
        // init issue List and Item observable
        mResultIssueListDataModel = new MutableLiveData<>();
        // init issue Item  and Id item
        mResultIssueListDataModelPaged = new MutableLiveData<>();

        mResultIssueItemDataModel = new MutableLiveData<>();
        mIssueResultItemMixer = new MediatorLiveData<>();

// CONTRIBUTOR
        mResultContributorListDataModel = new MutableLiveData<>();
        mSelectedContributor = new MutableLiveData<>();


        //Load async issues   //STEP2-a
        // UI event transformation
        /*
        mResultIssueListDataModel = Transformations.switchMap(mQueryStringObject, mQueryStringObject -> {
            return loadIssues(mQueryStringObject.getUser(), mQueryStringObject.getRepo(), mQueryStringObject.getForceremote());
        });
        */

        mResultIssueListDataModelPaged = Transformations.switchMap(mQueryStringObject, mQueryStringObject -> {
            return loadIssuesPaged(mQueryStringObject.getUser(), mQueryStringObject.getRepo(), mQueryStringObject.getForceremote());
        });

        //select a record by id stream on click
        // UI event transformation
        mResultIssueItemDataModel = Transformations.switchMap(mSelectedId, mSelectedId -> {
            return setIssueRecordById(mSelectedId);
        });

        // UI event transformation single click on issue item
        mResultIssueItemDataModelByObject = Transformations.switchMap(mSelectedIssue, mSelectedIssue -> {
            return wrapIssueObject();
        });

        // UI event transformation single click on contributor item
        mResultContributorItemDataModelByObject = Transformations.switchMap(mSelectedContributor, mSelectedContributor -> {
            return wrapContributorObject();
        });


        // ISSUE mix in one stream the 2 source to be catched by fragment B
        // 1
        Observer<Resource<IssueDataModel>> issueByObject = new Observer<Resource<IssueDataModel>>() {
            @Override
            public void onChanged(@Nullable Resource<IssueDataModel> s) {
                mIssueResultItemMixer.setValue(mResultIssueItemDataModelByObject.getValue());
            }
        };
        //2
        Observer<Resource<IssueDataModel>> issueByDB = new Observer<Resource<IssueDataModel>>() {
            @Override
            public void onChanged(@Nullable Resource<IssueDataModel> s) {
                mIssueResultItemMixer.setValue(mResultIssueItemDataModel.getValue());
            }
        };

        mIssueResultItemMixer.addSource(mResultIssueItemDataModelByObject, issueByObject);
        mIssueResultItemMixer.addSource(mResultIssueItemDataModel, issueByDB);


        // LOAD CONTRIBUTOTS

        //Load async contributors  //STEP2-b
        // UI event transformation
        mResultContributorListDataModel = Transformations.switchMap(mQueryStringObject, mQueryStringObject -> {
            return loadContributors(mQueryStringObject.getUser(), mQueryStringObject.getRepo(), mQueryStringObject.getForceremote());
        });

    }


    // the observable is a mix of the 2 possibilities
    public LiveData<Resource<IssueDataModel>> getMixedDetailIssueResult() {
        return mIssueResultItemMixer;
    }

    //Model transform
// transform the contributor livedata to become a different livedata
    public LiveData<ContributorTransformed> getContributorObjectTransformed() {
        return Transformations.map(mResultContributorItemDataModelByObject, new Function<Resource<ContributorDataModel>, ContributorTransformed>() {
            @Override
            public ContributorTransformed apply(Resource<ContributorDataModel> input) {
                return new ContributorTransformed(input.data.getLogin() + " (.map Object transformation)");
            }
        });
    }


    // UI insert the search string
    @VisibleForTesting
    //STEP1
    // the initial query string fires 3 transformed stream Load async issues,Load async contributors and send msg to snackbar
    public void setQueryString(String user, String repo, boolean forceremote) {
        mQueryStringObject.setValue(new QueryString(user, repo, forceremote));
    }


// -----------  LOAD ISSUES
//Load async issues read the issues stream from db or from network passing vars from the object wrapping the search string
//STEP3

    /*
    public LiveData<Resource<List<IssueDataModel>>> loadIssues(String user, String repo, Boolean forceremote) {
        return mIssueRepository.getIssues(user, repo, forceremote);
    }
    */

    public LiveData<Resource<PagedList<IssueDataModel>>> loadIssuesPaged(String user, String repo, Boolean forceremote) {
        return mIssueRepository.getIssuesPaged(user, repo, forceremote);
    }
    //Load async contributors read the issues stream from db or from network passing vars from the object wrapping the search string
//STEP3.1
    public LiveData<Resource<List<ContributorDataModel>>> loadContributors(String user, String repo, Boolean forceremote) {
        return mContributorRepository.getContributors(user, repo, forceremote);
    }


// async issues observable, observed by UI
//STEP4

    @NonNull
    public LiveData<Resource<List<IssueDataModel>>> getApiIssueResponse() {
        return mResultIssueListDataModel;
    }

    @NonNull
    public LiveData<Resource<PagedList<IssueDataModel>>> getApiIssueResponsePaged() {
        return mResultIssueListDataModelPaged;
    }

    @NonNull
    public LiveData<Resource<List<ContributorDataModel>>> getApiContributorResponse() {
        return mResultContributorListDataModel;
    }


// -----------  DELETE ISSUE RECORD
// invoked  by UI

    public void deleteIssueRecordById(Integer id) {
        mIssueRepository.deleteIssueById(id);
    }


// -----------  SELECT and stream the Issue record by ID


    @NonNull
    public LiveData<Integer> setRecordIdToStream(Integer id) {
        //get action click with the id
        mSelectedId.setValue(id);
        return mSelectedId;
    }

    @NonNull
    public LiveData<Resource<IssueDataModel>> setIssueRecordById(int id) {
        // stream of the actual data coming from the transformation fired by the item click on the UI
        return mIssueRepository.getIssueRecordById(id);
    }


// ADD RECORD

    public void addIssueRecord(IssueDataModel issueDataModel) {
        mIssueRepository.addIssueRecord(issueDataModel);
    }

    public void addContributorRecord(ContributorDataModel contributorDataModel) {
        mContributorRepository.addContributorRecord(contributorDataModel);
    }

    // update record

    public void updateIssueTitleRecord(String titleold, String titlenew) {
        mIssueRepository.updateIssueTitleRecord(titleold, titlenew);

    }

    public void updateContributorRecord(String titleold, String titlenew) {
        mContributorRepository.updateContributorTitleRecord(titleold, titlenew);
    }


// set from UI the object ISSue from UI cached list, fire the chain reaction and set the observable

    public void setIssueByUi(IssueDataModel issuebyui) {
        mSelectedIssue.setValue(issuebyui);
    }


    // transformed stream from the chain reaction
    @NonNull
    public LiveData<Resource<IssueDataModel>> wrapIssueObject() {
        // stream of the actual data coming from the transformation fired by the item click on the UI
        return mIssueRepository.getWrappedIssueObject(mSelectedIssue);
    }

// set from UI the object Contributor from UI cached list, fire the chain reaction and set the observable

    public void setContributorByUi(ContributorDataModel contributorbyui) {
        mSelectedContributor.setValue(contributorbyui);
    }

    // transformed stream from the chain reaction
    @NonNull
    public LiveData<Resource<ContributorDataModel>> wrapContributorObject() {
        // stream of the actual data coming from the transformation fired by the item click on the UI
        return mContributorRepository.getWrappedIssueObject(mSelectedContributor);
    }


    // call the starting select from db
    public void getResultsFromDatabase() {
        setQueryString(null, null, false);
    }


}