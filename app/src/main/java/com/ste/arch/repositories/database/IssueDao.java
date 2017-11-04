package com.ste.arch.repositories.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.ste.arch.entities.IssueDataModel;

import java.util.List;




@Dao
public abstract class IssueDao {
  @Query("SELECT * FROM Issues")
  public abstract LiveData<List<IssueDataModel>> getAllIssue();

  @Query("SELECT * FROM Issues where id = :id")
  public abstract LiveData<IssueDataModel> getIssueById(int id);

  @Query("DELETE FROM Issues")
  public abstract void deleteAll();

  @Query("DELETE FROM Issues where  id = :id")
  public abstract void deleteById(int id);

  @Query("UPDATE Issues SET title = :titlenew  WHERE title = :titleold")
  public abstract void updateTitle(String titleold,String titlenew);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract void insert(List<IssueDataModel> issue);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract void insertRecord(IssueDataModel issue);

  @Transaction
  public void updateData(List<IssueDataModel> issue) {
    deleteAll();
    insert(issue);
  }

  // do this
 //https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1


}
