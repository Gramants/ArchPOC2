package com.ste.arch.repositories.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ste.arch.entities.ContributorDataModel;
import com.ste.arch.entities.IssueDataModel;


@Database(entities = {IssueDataModel.class,ContributorDataModel.class}, version = 1)
public abstract class ProjectDb extends RoomDatabase {
  public abstract IssueDao issueDao();
  public abstract ContributorDao contributorDao();
}
