package com.cn29.aac.repositories;

import android.arch.lifecycle.LiveData;

import com.cn29.aac.entities.ContributorDataModel;
import com.cn29.aac.entities.NetworkErrorObject;

import java.util.List;


public interface ContributorRepository {

    LiveData<List<ContributorDataModel>> getContributors(String owner, String repo, Boolean forceremote);

    LiveData<ContributorDataModel> getContributorFromDb(int id);

    LiveData<NetworkErrorObject> getNetworkError();

}
