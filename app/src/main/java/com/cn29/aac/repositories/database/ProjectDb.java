package com.cn29.aac.repositories.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.cn29.aac.entities.ContributorDataModel;
import com.cn29.aac.entities.IssueDataModel;


@Database(entities = {IssueDataModel.class,ContributorDataModel.class}, version = 1)
public abstract class ProjectDb extends RoomDatabase {
  public abstract IssueDao issueDao();
  public abstract ContributorDao contributorDao();
}
