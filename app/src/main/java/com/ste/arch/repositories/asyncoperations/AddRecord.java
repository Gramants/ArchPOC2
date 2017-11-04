
package com.ste.arch.repositories.asyncoperations;

import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

public abstract class AddRecord {

    @MainThread
    public AddRecord() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                addRecord();
                return null;
            }

        }.execute();
    }


    @WorkerThread
    protected abstract void addRecord();


}