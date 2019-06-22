package com.shivaraj.mygate.model.local;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface UsersDao {

    @Query("SELECT * FROM  users_table")
    LiveData<List<UserModel>> getAllUser();

    @Query("SELECT * FROM  users_table where pass_code =:passCode")
    LiveData<UserModel> getUser(String passCode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(UserModel... userModels);

    @Delete
    void delete(UserModel userModel);

    @Query("DELETE FROM users_table")
    void deleteAll();
}
