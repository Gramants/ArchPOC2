
package com.ste.arch.repositories.asyncoperations;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.ste.arch.entities.IssueDataModel;

import io.reactivex.annotations.Nullable;

public class MixResource2 extends MediatorLiveData<Pair<Resource<IssueDataModel>, Resource<IssueDataModel>>> {
    public MixResource2(LiveData<Resource<IssueDataModel>> dbLiveData, LiveData<Resource<IssueDataModel>> uiLiveData) {
        addSource(dbLiveData, new Observer<Resource<IssueDataModel>>() {
            public void onChanged(@Nullable Resource<IssueDataModel> db) {
                setValue(Pair.create(db, dbLiveData.getValue()));
            }
        });
        addSource(uiLiveData, new Observer<Resource<IssueDataModel>>() {
            public void onChanged(@Nullable Resource<IssueDataModel> ui) {
                setValue(Pair.create(ui, uiLiveData.getValue()));
            }
        });
    }
}