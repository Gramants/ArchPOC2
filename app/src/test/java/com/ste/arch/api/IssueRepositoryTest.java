/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ste.arch.api;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.repositories.IssueRepository;
import com.ste.arch.repositories.IssueRepositoryImpl;
import com.ste.arch.repositories.Resource;
import com.ste.arch.repositories.api.GithubApiService;
import com.ste.arch.repositories.database.IssueDao;
import com.ste.arch.repositories.database.ProjectDb;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class IssueRepositoryTest {
    private IssueRepository repository;
    private IssueDao dao;
    private GithubApiService service;
    private ProjectDb db;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    @Before
    public void init() {
        dao = mock(IssueDao.class);
        service = mock(GithubApiService.class);
        db = mock(ProjectDb.class);
        when(db.issueDao()).thenReturn(dao);
        repository = new IssueRepositoryImpl(db, dao, service);
    }


    @Test
    public void loadContributors() throws IOException {
        MutableLiveData<List<IssueDataModel>> dbData = new MutableLiveData<>();
        when(dao.getAllIssue()).thenReturn(dbData);

        // verify that when I read the database the service is not firing
        verify(dao).getAllIssue();
        verify(service, never()).getIssues(anyString(), anyString());


        LiveData<Resource<List<IssueDataModel>>> data = repository.getIssues("foo", "bar",false);


    }


        /*

        Repo repo = TestUtil.createRepo("foo", "bar", "desc");
        Contributor contributor = TestUtil.createContributor(repo, "log", 3);
        // network does not send these
        contributor.setRepoOwner(null);
        contributor.setRepoName(null);
        List<Contributor> contributors = Collections.singletonList(contributor);
        LiveData<ApiResponse<List<Contributor>>> call = successCall(contributors);
        when(service.getContributors("foo", "bar"))
                .thenReturn(call);

        Observer<Resource<List<Contributor>>> observer = mock(Observer.class);
        data.observeForever(observer);

        verify(observer).onChanged(Resource.loading( null));

        MutableLiveData<List<Contributor>> updatedDbData = new MutableLiveData<>();
        when(dao.loadContributors("foo", "bar")).thenReturn(updatedDbData);
        dbData.setValue(Collections.emptyList());

        verify(service).getContributors("foo", "bar");
        ArgumentCaptor<List<Contributor>> inserted = ArgumentCaptor.forClass((Class) List.class);
        verify(dao).insertContributors(inserted.capture());


        assertThat(inserted.getValue().size(), is(1));
        Contributor first = inserted.getValue().get(0);
        assertThat(first.getRepoName(), is("bar"));
        assertThat(first.getRepoOwner(), is("foo"));

        updatedDbData.setValue(contributors);
        verify(observer).onChanged(Resource.success(contributors));
    }

    @Test
    public void searchNextPage_null() {
        when(dao.findSearchResult("foo")).thenReturn(null);
        Observer<Resource<Boolean>> observer = mock(Observer.class);
        repository.searchNextPage("foo").observeForever(observer);
        verify(observer).onChanged(null);
    }

    @Test
    public void search_fromDb() {
        List<Integer> ids = Arrays.asList(1, 2);

        Observer<Resource<List<Repo>>> observer = mock(Observer.class);
        MutableLiveData<RepoSearchResult> dbSearchResult = new MutableLiveData<>();
        MutableLiveData<List<Repo>> repositories = new MutableLiveData<>();

        when(dao.search("foo")).thenReturn(dbSearchResult);

        repository.search("foo").observeForever(observer);

        verify(observer).onChanged(Resource.loading(null));
        verifyNoMoreInteractions(service);
        reset(observer);

        RepoSearchResult dbResult = new RepoSearchResult("foo", ids, 2, null);
        when(dao.loadOrdered(ids)).thenReturn(repositories);

        dbSearchResult.postValue(dbResult);

        List<Repo> repoList = new ArrayList<>();
        repositories.postValue(repoList);
        verify(observer).onChanged(Resource.success(repoList));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void search_fromServer() {
        List<Integer> ids = Arrays.asList(1, 2);
        Repo repo1 = TestUtil.createRepo(1, "owner", "repo 1", "desc 1");
        Repo repo2 = TestUtil.createRepo(2, "owner", "repo 2", "desc 2");

        Observer<Resource<List<Repo>>> observer = mock(Observer.class);
        MutableLiveData<RepoSearchResult> dbSearchResult = new MutableLiveData<>();
        MutableLiveData<List<Repo>> repositories = new MutableLiveData<>();

        RepoSearchResponse apiResponse = new RepoSearchResponse();
        List<Repo> repoList = Arrays.asList(repo1, repo2);
        apiResponse.setItems(repoList);
        apiResponse.setTotal(2);

        MutableLiveData<ApiResponse<RepoSearchResponse>> callLiveData = new MutableLiveData<>();
        when(service.searchRepos("foo")).thenReturn(callLiveData);

        when(dao.search("foo")).thenReturn(dbSearchResult);

        repository.search("foo").observeForever(observer);

        verify(observer).onChanged(Resource.loading(null));
        verifyNoMoreInteractions(service);
        reset(observer);

        when(dao.loadOrdered(ids)).thenReturn(repositories);
        dbSearchResult.postValue(null);
        verify(dao, never()).loadOrdered(anyObject());

        verify(service).searchRepos("foo");
        MutableLiveData<RepoSearchResult> updatedResult = new MutableLiveData<>();
        when(dao.search("foo")).thenReturn(updatedResult);
        updatedResult.postValue(new RepoSearchResult("foo", ids, 2, null));

        callLiveData.postValue(new ApiResponse<>(Response.success(apiResponse)));
        verify(dao).insertRepos(repoList);
        repositories.postValue(repoList);
        verify(observer).onChanged(Resource.success(repoList));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void search_fromServer_error() {
        when(dao.search("foo")).thenReturn(AbsentLiveData.create());
        MutableLiveData<ApiResponse<RepoSearchResponse>> apiResponse = new MutableLiveData<>();
        when(service.searchRepos("foo")).thenReturn(apiResponse);

        Observer<Resource<List<Repo>>> observer = mock(Observer.class);
        repository.search("foo").observeForever(observer);
        verify(observer).onChanged(Resource.loading(null));

        apiResponse.postValue(new ApiResponse<>(new Exception("idk")));
        verify(observer).onChanged(Resource.error("idk", null));
    }
    */
}