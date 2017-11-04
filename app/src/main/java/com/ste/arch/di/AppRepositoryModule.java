package com.ste.arch.di;

import com.ste.arch.repositories.ContributorRepository;
import com.ste.arch.repositories.ContributorRepositoryImpl;
import com.ste.arch.repositories.IssueRepository;
import com.ste.arch.repositories.IssueRepositoryImpl;
import com.ste.arch.repositories.api.GithubApiService;
import com.ste.arch.repositories.database.ContributorDao;
import com.ste.arch.repositories.database.IssueDao;
import com.ste.arch.repositories.database.ProjectDb;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;



@Module
public class AppRepositoryModule {


    @Provides
    @Singleton
    public IssueRepositoryImpl provideIssueRepositoryImpl(ProjectDb projectDb, IssueDao issueDao, GithubApiService mApiService) {
        return new IssueRepositoryImpl(projectDb,issueDao, mApiService);
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

