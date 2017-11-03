
package com.ste.arch.repositories.asyncoperations;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
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
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public SelectRecord() {
        
        LiveData<ResultType> dbSource = selectRecordById();
        result.addSource(dbSource, newData -> result.setValue(Resource.successfromdb(newData)));

    }


    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> selectRecordById();


    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}