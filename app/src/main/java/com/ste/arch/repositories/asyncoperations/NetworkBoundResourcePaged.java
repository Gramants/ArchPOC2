
package com.ste.arch.repositories.asyncoperations;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.ste.arch.repositories.Resource;
import com.ste.arch.repositories.Status;

public abstract class NetworkBoundResourcePaged<ResultType, RequestType> {
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResourcePaged() {
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> result.setValue(Resource.successfromdb(newData)));
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<Resource<RequestType>> apiResponse = createCall();
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));

        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);

            if (response.status.equals(Status.SUCCESS)) {
                saveResultAndReInit(response.data);

            } else {
                onFetchFailed(response.message);
                result.addSource(dbSource, newData -> result.setValue(Resource.error(response.message, newData)));

            }
        });


    }



    @MainThread
    private void saveResultAndReInit(RequestType response) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

                updateAll(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), newData -> result.setValue(Resource.success(newData)));
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void updateAll(RequestType response);

    @WorkerThread
    protected abstract void deleteAll(RequestType response);

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract Boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<Resource<RequestType>> createCall();

    @MainThread
    protected abstract void onFetchFailed(String errormessage);
    @MainThread
    protected abstract void onLoading();

    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}