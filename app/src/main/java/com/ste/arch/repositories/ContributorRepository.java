package com.ste.arch.repositories;

import android.arch.lifecycle.LiveData;

import com.ste.arch.entities.ContributorDataModel;
import com.ste.arch.entities.NetworkErrorObject;

import java.util.List;


public interface ContributorRepository {

    LiveData<List<ContributorDataModel>> getContributors(String owner, String repo, Boolean forceremote);

    LiveData<ContributorDataModel> getContributorFromDb(int id);

    LiveData<NetworkErrorObject> getNetworkError();

}
