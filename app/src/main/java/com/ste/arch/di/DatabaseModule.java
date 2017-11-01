package com.ste.arch.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.ste.arch.Config;
import com.ste.arch.repositories.database.ContributorDao;
import com.ste.arch.repositories.database.IssueDao;
import com.ste.arch.repositories.database.ProjectDb;

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
