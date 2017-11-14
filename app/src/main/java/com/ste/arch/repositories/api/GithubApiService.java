package com.ste.arch.repositories.api;


import android.arch.lifecycle.LiveData;

import com.ste.arch.entities.pojos.Contributor;
import com.ste.arch.entities.pojos.Issue;
import com.ste.arch.repositories.Resource;

import java.util.List;


import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApiService {
    @GET("/repos/{owner}/{repo}/issues")
    LiveData<Resource<List<Issue>>> getIssues(@Path("owner") String owner, @Path("repo") String repo);


    @GET("/repos/{owner}/{repo}/contributors")
    LiveData<Resource<List<Contributor>>> getContributors(@Path("owner") String owner, @Path("repo") String repo);
}
