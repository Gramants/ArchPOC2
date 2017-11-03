package com.ste.arch.repositories;

import android.arch.lifecycle.LiveData;

import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.entities.NetworkErrorObject;
import com.ste.arch.repositories.asyncoperations.Resource;

import java.util.List;



public interface IssueRepository {

    LiveData<Resource<List<IssueDataModel>>> getIssues(String owner, String repo, Boolean forceremote);

    LiveData<Resource<IssueDataModel>> getIssueRecordById(int id);

    void deleteIssueById(Integer id);




    // used only by contributors now but to delete
    LiveData<NetworkErrorObject> getNetworkError();





}
