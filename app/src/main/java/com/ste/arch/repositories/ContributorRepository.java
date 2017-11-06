package com.ste.arch.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.ste.arch.entities.ContributorDataModel;
import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.entities.NetworkErrorObject;
import com.ste.arch.repositories.asyncoperations.Resource;

import java.util.List;


public interface ContributorRepository {

    LiveData<Resource<List<ContributorDataModel>>> getContributors(String owner, String repo, Boolean forceremote);

    LiveData<Resource<ContributorDataModel>> getWrappedIssueObject(LiveData<ContributorDataModel> obj);
}
