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
import android.arch.lifecycle.Observer;

import com.ste.arch.repositories.ContributorRepository;
import com.ste.arch.repositories.IssueRepository;
import com.ste.arch.repositories.preferences.PersistentStorageProxy;
import com.ste.arch.ui.viewpager.viewmodel.RepositoryViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(JUnit4.class)
public class RepoViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    private IssueRepository mIssueRepository;
    private ContributorRepository mContributorRepository;
    private PersistentStorageProxy mPersistentStorageProxy;

    private RepositoryViewModel repoViewModel;

    @Before
    public void setup() {
        mIssueRepository = mock(IssueRepository.class);
        repoViewModel = new RepositoryViewModel(mIssueRepository, mContributorRepository, mPersistentStorageProxy);
        repoViewModel.init();
    }


    @Test
    public void dontFetchWithoutObservers() {
        repoViewModel.getApiIssueResponsePaged();
        verify(mIssueRepository, never()).getIssuesPaged(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), eq(false)
        );
    }


    @Test
    public void fetchWhenObserved() {
        ArgumentCaptor<String> user = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> repo = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> forceremote = ArgumentCaptor.forClass(Boolean.class);
        // observe the result
        repoViewModel.getApiIssueResponsePaged().observeForever(mock(Observer.class));
        //when I click on the search of the string
        repoViewModel.setQueryString("a", "b", false);

        // verify that 1 time Iam observing and the value of the search operation is the entry of the query
        verify(mIssueRepository, times(1)).getIssuesPaged(user.capture(),
                repo.capture(), forceremote.capture());
        // nad the entry of the query is that one that I have sent from the query string
        assertThat(user.getValue(), is("a"));
        assertThat(repo.getValue(), is("b"));
    }


    @Test
    public void changeWhileObserved() {

        ArgumentCaptor<String> user = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> repo = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> forceremote = ArgumentCaptor.forClass(Boolean.class);
        // observe the result
        repoViewModel.getApiIssueResponsePaged().observeForever(mock(Observer.class));

        //when I click on the search of the string
        repoViewModel.setQueryString("a", "b", false);
        repoViewModel.setQueryString("c", "d", false);


        // verify that 1 time Iam observing and the value of the search operation is the entry of the query
        verify(mIssueRepository, times(2)).getIssuesPaged(user.capture(),
                repo.capture(), forceremote.capture());
        // nad the entry of the query is that one that I have sent from the query string

        assertThat(user.getAllValues(), is(Arrays.asList("a", "c")));
        assertThat(repo.getAllValues(), is(Arrays.asList("b", "d")));

    }

}