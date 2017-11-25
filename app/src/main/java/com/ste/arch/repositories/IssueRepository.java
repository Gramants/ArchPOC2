package com.ste.arch.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.ste.arch.entities.IssueDataModel;

import java.util.List;



public interface IssueRepository {

    //LiveData<Resource<List<IssueDataModel>>> getIssues(String owner, String repo, Boolean forceremote);

    LiveData<Resource<PagedList<IssueDataModel>>> getIssuesPaged(String user, String repo, Boolean forceremote);

    LiveData<Resource<IssueDataModel>> getIssueRecordById(int id);

    LiveData<Resource<IssueDataModel>> getWrappedIssueObject(LiveData<IssueDataModel> obj);


    void deleteIssueById(Integer id);

    void addIssueRecord(IssueDataModel issueDataModel);

    void updateIssueTitleRecord(String titleold, String titlenew);



}
