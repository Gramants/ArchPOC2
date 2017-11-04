
package com.ste.arch.repositories.asyncoperations;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class SelectRecord<ResultType> {
    private final MediatorLiveData<Resource<ResultType>> distinctLiveData = new MediatorLiveData<>();

    @MainThread
    public SelectRecord() {

        LiveData<ResultType> dbSource = selectRecordById();

        distinctLiveData.addSource(dbSource, new Observer<ResultType>() {
            private Boolean initialized = false;
            private ResultType lastObj = null;
            @Override
            public void onChanged(@Nullable ResultType obj) {

                if (!initialized) {
                    initialized = true;
                    lastObj = obj;
                    Log.e("STEFANO", "!initialized");
                    distinctLiveData.postValue(Resource.successfromdb(lastObj));
                }
                else
                {
                    Log.e("STEFANO", "already initialized");
                    distinctLiveData.postValue(null);
                }
                /*  adding this conditions there will be the problem of multiple subscriptions!!!!!
                else if ((obj == null && lastObj != null) || obj != lastObj) {

                    if (obj == null && lastObj != null)
                        Log.e("STEFANO", "obj == null && lastObj != null");
                    else
                        Log.e("STEFANO", "obj != lastObj");

                    Log.e("STEFANO", "already initialized will not post the value");
                    lastObj = obj;
                    distinctLiveData.postValue(Resource.successfromdb(lastObj));
                }
                */


            }
        });

    }


    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> selectRecordById();


    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return distinctLiveData;
    }


}

