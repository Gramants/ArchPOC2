package com.ste.arch.ui.viewpager.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.ste.arch.entities.ContributorDataModel;
import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.entities.NetworkErrorObject;
import com.ste.arch.entities.QueryString;
import com.ste.arch.repositories.ContributorRepository;
import com.ste.arch.repositories.IssueRepository;
import com.ste.arch.repositories.asyncoperations.MixResource;
import com.ste.arch.repositories.asyncoperations.MixResource2;
import com.ste.arch.repositories.asyncoperations.Resource;
import com.ste.arch.repositories.preferences.PersistentStorageProxy;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;

// http://www.zoftino.com/android-livedata-examples NEWORK CHECK

public class RepositoryViewModel extends ViewModel {


    private MediatorLiveData<List<ContributorDataModel>> mApiContributorResponse;



    private MutableLiveData<QueryString> mQueryStringObject;
    private MutableLiveData<String> mMessageSnackbar;

    private MutableLiveData<Integer> mSelectedId;
    private MutableLiveData<IssueDataModel> mSelectedIssue;



    private LiveData<String> mResultMessageSnackbar;
    private LiveData<List<ContributorDataModel>> mResultContributorDataModel;


    private IssueRepository mIssueRepository;
    private ContributorRepository mContributorRepository;
    private PersistentStorageProxy mPersistentStorageProxy;


    private LiveData<Resource<List<IssueDataModel>>> mResultIssueListDataModel;
    private LiveData<Resource<IssueDataModel>> mResultIssueItemDataModel;
    private LiveData<Resource<IssueDataModel>> mResultIssueItemDataModelByObject;
    private MixResource2 trigger;

    private MediatorLiveData<Resource<IssueDataModel>> mediatorLiveData;

    @Inject
    public RepositoryViewModel(IssueRepository mIssueRepository, ContributorRepository mContributorRepository, PersistentStorageProxy mPersistentStorageProxy) {
        this.mIssueRepository = mIssueRepository;
        this.mContributorRepository = mContributorRepository;
        this.mPersistentStorageProxy = mPersistentStorageProxy;
    }




    public void init() {

        // invoked only by the factory of the container Activity


        mApiContributorResponse = new MediatorLiveData<>();




        // UI events
        mQueryStringObject = new MutableLiveData<>();

        mMessageSnackbar = new MutableLiveData<>();

        mSelectedId = new MutableLiveData<>();

        mSelectedIssue = new MutableLiveData<>();



        // init issue List and Item observable
        mResultIssueListDataModel=new MutableLiveData<>();

        // init issue Item  and Id item
        mResultIssueItemDataModel=new MutableLiveData<>();



        mediatorLiveData = new MediatorLiveData<>();



        //Load async issues   //STEP2-a
        mResultIssueListDataModel= Transformations.switchMap(mQueryStringObject, mQueryStringObject -> {
            return loadIssues(mQueryStringObject.getUser(), mQueryStringObject.getRepo(), mQueryStringObject.getForceremote());
        });

        //Load async contributors  //STEP2-b
        mResultContributorDataModel = Transformations.switchMap(mQueryStringObject, mQueryStringObject -> {
            return loadContributor(mQueryStringObject.getUser(), mQueryStringObject.getRepo(), mQueryStringObject.getForceremote());
        });

        //send msg to snackbar   //STEP2-c
        mResultMessageSnackbar = Transformations.switchMap(mQueryStringObject, mQueryStringObject -> {
            return mQueryStringObject.getForceremote() == false ? null : loadSnackBar("Search string: " + mQueryStringObject.getUser() + "/" + mQueryStringObject.getRepo());
        });

        //select a record by id stream on click
        mResultIssueItemDataModel = Transformations.switchMap(mSelectedId, mSelectedId -> {
            return  setIssueRecordById(mSelectedId);
        });


        mResultIssueItemDataModelByObject = Transformations.switchMap(mSelectedIssue, mSelectedIssue -> {
            return  setIssueByObject(mSelectedIssue);
        });


        //trigger = new MixResource2(mResultIssueItemDataModel, mResultIssueItemDataModelByObject);


        Observer<Resource<IssueDataModel>> issueByObject = new Observer<Resource<IssueDataModel>>() {
            @Override
            public void onChanged(@Nullable Resource<IssueDataModel> s) {
                    mediatorLiveData.setValue(mResultIssueItemDataModelByObject.getValue());
            }
        };

        Observer<Resource<IssueDataModel>> issueByDB = new Observer<Resource<IssueDataModel>>() {
            @Override
            public void onChanged(@Nullable Resource<IssueDataModel> s) {
                mediatorLiveData.setValue(mResultIssueItemDataModel.getValue());
            }
        };


        mediatorLiveData.addSource(mResultIssueItemDataModelByObject, issueByObject);
        mediatorLiveData.addSource(mResultIssueItemDataModel,issueByDB);



    }




    //STEP1
    // the initial query string fires 3 transformed stream Load async issues,Load async contributors and send msg to snackbar
    public void setQueryString(String user, String repo, boolean forceremote) {
        mQueryStringObject.setValue(new QueryString(user, repo, forceremote));
    }


// -----------  LOAD ISSUES
//Load async issues read the issues stream from db or from network passing vars from the object wrapping the search string
//STEP3

    public LiveData<Resource<List<IssueDataModel>>> loadIssues(String user, String repo, Boolean forceremote) {
        return mIssueRepository.getIssues(user, repo, forceremote);
    }


// async issues observable, observed by UI
//STEP4

    @NonNull
    public LiveData<Resource<List<IssueDataModel>>> getApiIssueResponse() {
        return mResultIssueListDataModel;
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

    @NonNull
    public LiveData<Resource<IssueDataModel>> getRecordFromDb() {
        // observable of the stream to be observed by the fragment
        return mResultIssueItemDataModel;
    }



// ADD RECORD

    public void addIssueRecord(IssueDataModel issueDataModel) {
        mIssueRepository.addIssueRecord(issueDataModel);
    }

    // update record

    public void updateIssueTitleRecord(String titleold, String titlenew) {
        mIssueRepository.updateIssueTitleRecord(titleold,titlenew);

    }


// set from UI the object ISSue from UI cached list, fire the chain reaction and set the observable

    public void setIssueByUi(IssueDataModel issuebyui) {
        mSelectedIssue.setValue(issuebyui);
    }

    @NonNull
    public LiveData<Resource<IssueDataModel>> getIssueItemDataModelByObject() {
        // observable of the stream to be observed by the fragment
        return mResultIssueItemDataModelByObject;
    }

   // transformed stream from the chain reaction
    @NonNull
    public LiveData<Resource<IssueDataModel>> setIssueByObject(IssueDataModel obj) {
        // stream of the actual data coming from the transformation fired by the item click on the UI
        return mIssueRepository.getWrappedIssueObject(mSelectedIssue);
    }


    //LiveData<Resource<IssueDataModel>> y = Transformations.switchMap(trigger, trigger.);





    public LiveData<Resource<IssueDataModel>> setMixedDetailResult(LiveData<Resource<IssueDataModel>> obj1, LiveData<Resource<IssueDataModel>> obj2) {
//https://plus.google.com/+MichielPijnackerHordijk/posts/QGXF9gRomVi
        return  Transformations.switchMap(trigger, trigger -> {
            return null;
        });
/*
        return new MixResource<Resource<IssueDataModel>,Resource<IssueDataModel>>() {

            @NonNull
            @Override
            protected LiveData<Resource<IssueDataModel>> getUiData() {
                return obj1;
            }

            @NonNull
            @Override
            protected LiveData<Resource<IssueDataModel>> getDbData() {
                return obj2;
            }
        }.getAsLiveData();

*/

    }



    public LiveData<Resource<IssueDataModel>> getMixedDetailResult() {
        //return mResultIssueItemDataModel;
        return mediatorLiveData;
        //return mResultIssueItemDataModelByObject;
        //return setMixedDetailResult(mResultIssueItemDataModel,mResultIssueItemDataModelByObject);

    }


/*
    class FoorBarLiveData extends MediatorLiveData<Pair<Foo, Bar>> {
        public FoorBarLiveData(LiveData<Foo> fooLiveData, LiveData<Bar> barLiveData) {
            addSource(fooLiveData, new Observer<Foo> {
                public void onChanged(@Nullable Foo foo) {
                    setValue(Pair.create(foo, barLiveData.getValue()));
                }
            });
            addSource(barLiveData, new Observer<Bar> {
                public void onChanged(@Nullable Bar bar) {
                    setValue(Pair.create(fooLiveData.getValue(), bar));
                }
            });
        }
    }
*/

// set the stream from db at the startup or fron network the contributors

    public LiveData<List<ContributorDataModel>> loadContributor(@NonNull String user, String repo, Boolean forceremote) {
        mApiContributorResponse.addSource(
                mContributorRepository.getContributors(user, repo, forceremote),
                apiContributorResponse -> mApiContributorResponse.setValue(apiContributorResponse)
        );
        return mApiContributorResponse;
    }


    // get the stream  from the observables of issues and contributors wrapped in mutablelivedata
    @NonNull
    public LiveData<List<ContributorDataModel>> getApiContributorResponse() {
        return mResultContributorDataModel;
    }


    // delete a record from the db by pk


    // catch the error network object from the network call of issues and contributors
    public LiveData<NetworkErrorObject> getIssueNetworkErrorResponse() {
        return mIssueRepository.getNetworkError();
    }

    public LiveData<NetworkErrorObject> getContributorNetworkErrorResponse() {
        return mContributorRepository.getNetworkError();
    }


    // saving the good query string to sharedpref
    public void saveSearchString(String searchstring) {
        mPersistentStorageProxy.setSearchStringTemp(searchstring);
    }


    // Snackbar management


    public LiveData<String> loadSnackBar(String temp) {
        mMessageSnackbar.setValue(temp);
        return mMessageSnackbar;
    }



    public LiveData<String> getSnackBar() {
        return mResultMessageSnackbar;
    }


    public void getResultsFromDatabase() {
        setQueryString(null,null,false);
    }



}