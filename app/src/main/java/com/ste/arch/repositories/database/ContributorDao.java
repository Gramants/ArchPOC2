package com.ste.arch.repositories.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.ste.arch.entities.ContributorDataModel;
import com.ste.arch.entities.IssueDataModel;

import java.util.ArrayList;
import java.util.List;



@Dao
public abstract class ContributorDao {

  @Query("SELECT * FROM Contributors")
  public abstract LiveData<List<ContributorDataModel>> getAllContributors();

  @Query("DELETE FROM Contributors")
  public abstract void deleteAll();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract void insert(List<ContributorDataModel> contributor);

  @Query("UPDATE Contributors SET login = :titlenew  WHERE login = :titleold")
  public abstract void updateTitle(String titleold, String titlenew);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract void insertRecord(ContributorDataModel contributorDataModel);

  @Transaction
  public void updateData(List<ContributorDataModel> contributor) {
    deleteAll();
    insert(contributor);
  }





}
