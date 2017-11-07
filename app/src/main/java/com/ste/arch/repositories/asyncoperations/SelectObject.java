
package com.ste.arch.repositories.asyncoperations;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ste.arch.repositories.Resource;

public abstract class SelectObject<ResultType> {
    private final MediatorLiveData<Resource<ResultType>> distinctLiveData = new MediatorLiveData<>();

    @MainThread
    public SelectObject() {

        LiveData<ResultType> objectSource = getObject();

        distinctLiveData.addSource(objectSource, new Observer<ResultType>() {
            private Boolean initialized = false;
            private ResultType lastObj = null;

            @Override
            public void onChanged(@Nullable ResultType obj) {

                if (!initialized) {
                    initialized = true;
                    lastObj = obj;
                    distinctLiveData.postValue(Resource.successfromui(lastObj));
                } else if ((obj == null && lastObj != null) || obj != lastObj) {
                    lastObj = obj;
                    distinctLiveData.postValue(Resource.successfromui(lastObj));
                } else {
                    distinctLiveData.postValue(null);
                }

            }
        });

    }


    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> getObject();


    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return distinctLiveData;
    }


}

