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
        repoViewModel = new RepositoryViewModel(mIssueRepository,mContributorRepository,mPersistentStorageProxy);
        repoViewModel.init();
    }



    @Test
    public void dontFetchWithoutObservers() {
        repoViewModel.getApiIssueResponse();
        verify(mIssueRepository, never()).getIssues(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),eq(false)
        );
    }



    @Test
    public void fetchWhenObserved() {
        ArgumentCaptor<String> user = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> repo = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> forceremote = ArgumentCaptor.forClass(Boolean.class);
        //when I click on the search of the string
        repoViewModel.setQueryString("a","b",false);
        // and observe the result
        repoViewModel.getApiIssueResponse().observeForever(mock(Observer.class));
        // verify that 1 time Iam observing and the value of the search operation is the entry of the query
        verify(mIssueRepository, times(1)).getIssues(user.capture(),
                repo.capture(),forceremote.capture());
        // nad the entry of the query is that one that I have sent from the query string
        assertThat(user.getValue(), is("a"));
        assertThat(repo.getValue(), is("b"));
    }






    /*

        @Test
    public void testNull() {
        assertThat(repoViewModel.getApiIssueResponse(), NullValue());
        assertThat(repoViewModel.getApiContributorResponse(), NullValue());
        verify(mIssueRepository, times(1)).getIssues(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),false);
    }


    @Test
    public void fetchWhenObserved() {
        ArgumentCaptor<String> owner = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> name = ArgumentCaptor.forClass(String.class);

        repoViewModel.setId("a", "b");
        repoViewModel.getRepo().observeForever(mock(Observer.class));
        verify(repository, times(1)).loadRepo(owner.capture(),
                name.capture());
        assertThat(owner.getValue(), is("a"));
        assertThat(name.getValue(), is("b"));
    }

    @Test
    public void changeWhileObserved() {
        ArgumentCaptor<String> owner = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> name = ArgumentCaptor.forClass(String.class);
        repoViewModel.getRepo().observeForever(mock(Observer.class));

        repoViewModel.setId("a", "b");
        repoViewModel.setId("c", "d");

        verify(repository, times(2)).loadRepo(owner.capture(),
                name.capture());
        assertThat(owner.getAllValues(), is(Arrays.asList("a", "c")));
        assertThat(name.getAllValues(), is(Arrays.asList("b", "d")));
    }

    @Test
    public void contributors() {
        Observer<Resource<List<Contributor>>> observer = mock(Observer.class);
        repoViewModel.getContributors().observeForever(observer);
        verifyNoMoreInteractions(observer);
        verifyNoMoreInteractions(repository);
        repoViewModel.setId("foo", "bar");
        verify(repository).loadContributors("foo", "bar");
    }

    @Test
    public void resetId() {
        Observer<RepoViewModel.RepoId> observer = mock(Observer.class);
        repoViewModel.repoId.observeForever(observer);
        verifyNoMoreInteractions(observer);
        repoViewModel.setId("foo", "bar");
        verify(observer).onChanged(new RepoViewModel.RepoId("foo", "bar"));
        reset(observer);
        repoViewModel.setId("foo", "bar");
        verifyNoMoreInteractions(observer);
        repoViewModel.setId("a", "b");
        verify(observer).onChanged(new RepoViewModel.RepoId("a", "b"));
    }

    @Test
    public void retry() {
        repoViewModel.retry();
        verifyNoMoreInteractions(repository);
        repoViewModel.setId("foo", "bar");
        verifyNoMoreInteractions(repository);
        Observer<Resource<Repo>> observer = mock(Observer.class);
        repoViewModel.getRepo().observeForever(observer);
        verify(repository).loadRepo("foo", "bar");
        reset(repository);
        repoViewModel.retry();
        verify(repository).loadRepo("foo", "bar");
    }

    @Test
    public void nullRepoId() {
        repoViewModel.setId(null, null);
        Observer<Resource<Repo>> observer1 = mock(Observer.class);
        Observer<Resource<List<Contributor>>> observer2 = mock(Observer.class);
        repoViewModel.getRepo().observeForever(observer1);
        repoViewModel.getContributors().observeForever(observer2);
        verify(observer1).onChanged(null);
        verify(observer2).onChanged(null);
    }
    */
}