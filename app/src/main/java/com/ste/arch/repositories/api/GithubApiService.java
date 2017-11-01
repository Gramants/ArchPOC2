package com.ste.arch.repositories.api;

import com.ste.arch.entities.IssueResponse;
import com.ste.arch.entities.pojos.Contributor;
import com.ste.arch.entities.pojos.Issue;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApiService {
    @GET("/repos/{owner}/{repo}/issues")
    Call<List<Issue>> getIssues(@Path("owner") String owner, @Path("repo") String repo);


    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> getContributors(@Path("owner") String owner, @Path("repo") String repo);
}
