
package com.ste.arch.repositories.asyncoperations;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.ste.arch.Config;
import com.ste.arch.entities.NetworkErrorObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ste.arch.entities.translator.DataTranslator.ContributorTranslator;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource() {
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
        Log.e("STEFANO","fetchFromNetwork");
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
        createCall().enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                result.removeSource(dbSource);
                if (response.isSuccessful()) {
                    Log.e("STEFANO","result.removeSource");
                    saveResultAndReInit(response.body());
                }
                else
                {
                    Log.e("STEFANO","onFailure "+response.message());
                    onFetchFailed();
                    result.removeSource(dbSource);
                    result.addSource(dbSource, newData -> result.setValue(Resource.error(response.message(), newData)));

                }

            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                Log.e("STEFANO","onFailure "+t.getMessage());
                onFetchFailed();
                result.removeSource(dbSource);
                result.addSource(dbSource, newData -> result.setValue(Resource.error(t.getMessage(), newData)));
            }
        });
    }

    @MainThread
    private void saveResultAndReInit(RequestType response) {
        Log.e("STEFANO","saveResultAndReInit");

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
    protected abstract Call<RequestType> createCall();

    @MainThread
    protected void onFetchFailed() {
    }

    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}