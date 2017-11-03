
package com.ste.arch.repositories.asyncoperations;

import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

public abstract class DeleteRecord {

    @MainThread
    public DeleteRecord() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                deleteRecordById();
                return null;
            }

        }.execute();
    }


    @WorkerThread
    protected abstract void deleteRecordById();


}