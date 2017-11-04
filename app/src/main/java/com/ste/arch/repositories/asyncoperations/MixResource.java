
package com.ste.arch.repositories.asyncoperations;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.ste.arch.entities.IssueDataModel;

public abstract class MixResource<ResultType> {
    private final MediatorLiveData<ResultType> result = new MediatorLiveData<>();

    @MainThread
    public MixResource() {

        LiveData<ResultType> dbSource = getDbData();
        LiveData<ResultType> uiSource = getUiData();

        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (dbSource!=null) {
                result.addSource(dbSource, newData -> result.setValue(newData));
            }
        });
        result.addSource(uiSource, data -> {
            result.removeSource(uiSource);
            if (uiSource!=null) {
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