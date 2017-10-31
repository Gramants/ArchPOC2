package com.cn29.aac.di;

import com.cn29.aac.repositories.ContributorRepository;
import com.cn29.aac.repositories.ContributorRepositoryImpl;
import com.cn29.aac.repositories.IssueRepository;
import com.cn29.aac.repositories.IssueRepositoryImpl;
import com.cn29.aac.repositories.api.GithubApiService;
import com.cn29.aac.repositories.database.ContributorDao;
import com.cn29.aac.repositories.database.IssueDao;
import com.cn29.aac.repositories.database.ProjectDb;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;



@Module
public class AppRepositoryModule {


    @Provides
    @Singleton
    public IssueRepositoryImpl provideIssueRepositoryImpl(IssueDao issueDao, ProjectDb projectdb, GithubApiService mApiService) {
        return new IssueRepositoryImpl(issueDao, projectdb, mApiService);
    }

    @Provides
    @Singleton
    public IssueRepository provideIssueRepository(IssueRepositoryImpl issuerepository) {
        return issuerepository;
    }

    @Provides
    @Singleton
    public ContributorRepositoryImpl provideContributorRepositoryImpl(ContributorDao contributorDao, ProjectDb projectdb, GithubApiService mApiService) {
        return new ContributorRepositoryImpl(contributorDao, projectdb, mApiService);
    }

    @Provides
    @Singleton
    public ContributorRepository provideContributorRepository(ContributorRepositoryImpl contributorrepository) {
        return contributorrepository;
    }



}

