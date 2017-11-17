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

import com.ste.arch.api.util.TestUtil;
import com.ste.arch.entities.IssueDataModel;
import com.ste.arch.entities.pojos.Issue;
import com.ste.arch.entities.translator.DataTranslator;
import com.ste.arch.repositories.IssueRepository;
import com.ste.arch.repositories.IssueRepositoryImpl;
import com.ste.arch.repositories.Resource;
import com.ste.arch.repositories.Status;
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

import static com.ste.arch.api.util.ApiUtil.successCall;
import static com.ste.arch.api.util.ApiUtil.successResourceCall;
import static com.ste.arch.entities.translator.DataTranslator.IssueTranslator;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void loadAllIssueFromDb() throws IOException {
    MutableLiveData<List<IssueDataModel>> DbData = new MutableLiveData<>();
    when(dao.getAllIssue()).thenReturn(DbData);


    IssueDataModel issue= TestUtil.createRepoDataModel("test", "test", 3);
    List<IssueDataModel> list = Collections.singletonList(issue);


    Observer<List<IssueDataModel>> observer = mock(Observer.class);
    DbData.observeForever(observer);


    DbData.setValue(list);
    verify(observer).onChanged(list);
    }



    @Test
    public void loadIssueFromNetwork() throws IOException {
        MutableLiveData<Resource<List<Issue>>> networkData = new MutableLiveData<>();
        when(service.getIssues("test","test")).thenReturn(networkData);

        Issue issue= TestUtil.createRepo("test", "test", 3);
        List<Issue> list = Collections.singletonList(issue);


        Observer<Resource<List<Issue>>> observer = mock(Observer.class);
        networkData.observeForever(observer);

        networkData.setValue(Resource.success(list));
        verify(observer).onChanged(refEq(Resource.success(list)));
    }


}
