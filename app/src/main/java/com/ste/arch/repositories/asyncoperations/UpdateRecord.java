
package com.ste.arch.repositories.asyncoperations;

import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

public abstract class UpdateRecord {

    @MainThread
    public UpdateRecord() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                updateRecord();
                return null;
            }

        }.execute();
    }


    @WorkerThread
    protected abstract void updateRecord();


}