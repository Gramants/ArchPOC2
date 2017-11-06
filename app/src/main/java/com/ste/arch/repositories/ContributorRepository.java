package com.ste.arch.repositories;

import android.arch.lifecycle.LiveData;

import com.ste.arch.entities.ContributorDataModel;

import java.util.List;


public interface ContributorRepository {

    LiveData<Resource<List<ContributorDataModel>>> getContributors(String owner, String repo, Boolean forceremote);

    LiveData<Resource<ContributorDataModel>> getWrappedIssueObject(LiveData<ContributorDataModel> obj);
}
