package com.cn29.aac.repositories.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.cn29.aac.entities.ContributorDataModel;

import java.util.List;



@Dao
public interface ContributorDao {

  @Query("SELECT * FROM Contributors")
  LiveData<List<ContributorDataModel>> getAllContributors();

  @Query("SELECT * FROM Contributors where id = :id")
  LiveData<ContributorDataModel> getContributorById(int id);

  @Query("DELETE FROM Contributors")
  void deleteAll();

  @Query("DELETE FROM Contributors where  id = :id")
  void deleteById(int id);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(List<ContributorDataModel> contributor);

}
