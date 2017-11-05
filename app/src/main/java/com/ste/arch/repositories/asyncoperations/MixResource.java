
package com.ste.arch.repositories.asyncoperations;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ste.arch.entities.IssueDataModel;

public abstract class MixResource<ResultType, R> {
    private final MediatorLiveData<ResultType> result = new MediatorLiveData<>();

    @MainThread
    public MixResource() {

        LiveData<ResultType> dbSource = getDbData();
        LiveData<ResultType> uiSource = getUiData();


        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (dbSource!=null) {
                Log.e("STEFANO", "dbSource!=null");
                result.addSource(dbSource, newData -> result.setValue(newData));
            }
            else
            {
                Log.e("STEFANO", "dbSource=null");
                result.addSource(dbSource, newData -> result.setValue(null));
            }
        });

        result.addSource(uiSource, data -> {
            result.removeSource(uiSource);
            if (uiSource!=null) {
                Log.e("STEFANO", "duiSource!=null");
                result.addSource(uiSource, newData -> result.setValue(newData));
            }
        });


    }


    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> getUiData();

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> getDbData();




    public final LiveData<ResultType> getAsLiveData() {
        return result;
    }
}