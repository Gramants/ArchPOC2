package com.ste.arch.repositories;

import android.arch.lifecycle.LiveData;

import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.entities.NetworkErrorObject;

import java.util.List;



public interface IssueRepository {

    LiveData<Resource<List<IssueDataModel>>> getIssues(String owner, String repo, Boolean forceremote);

    LiveData<NetworkErrorObject> getNetworkError();

    LiveData<IssueDataModel> getIssueFromDb(int id);

    void deleteIssueRecordById(int id);


}
