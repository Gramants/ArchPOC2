package com.cn29.aac.repositories;

import android.arch.lifecycle.LiveData;

import com.cn29.aac.entities.IssueDataModel;
import com.cn29.aac.entities.NetworkErrorObject;

import java.util.List;



public interface IssueRepository {

    LiveData<List<IssueDataModel>> getIssues(String owner, String repo, Boolean forceremote);

    LiveData<NetworkErrorObject> getNetworkError();

    LiveData<IssueDataModel> getIssueFromDb(int id);

    void deleteIssueRecordById(int id);


}
