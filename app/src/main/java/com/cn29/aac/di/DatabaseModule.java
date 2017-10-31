package com.cn29.aac.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.cn29.aac.Config;
import com.cn29.aac.repositories.database.ContributorDao;
import com.cn29.aac.repositories.database.IssueDao;
import com.cn29.aac.repositories.database.ProjectDb;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private static final String DATABASE = "database_name";

    @Provides
    @Named(DATABASE)
    String provideDatabaseName() {
        return Config.DATABASE_NAME;
    }

    @Provides
    @Singleton
    ProjectDb provideIssueDb(Context context, @Named(DATABASE) String databaseName) {
        return Room.databaseBuilder(context, ProjectDb.class, databaseName).build();
    }

    @Provides
    @Singleton
    IssueDao provideIssueDao(ProjectDb projectDb) {
        return projectDb.issueDao();
    }

    @Provides
    @Singleton
    ContributorDao provideContributorDao(ProjectDb projectDb) {return projectDb.contributorDao();}
}
