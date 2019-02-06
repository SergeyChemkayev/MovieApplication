package com.example.movieapplication.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.movieapplication.entity.RoomMovie;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class RoomMoviesDao {

    @Query("SELECT * FROM movies")
    public abstract Flowable<List<RoomMovie>> getAll();

    @Query("DELETE FROM movies")
    public abstract void removeAll();

    @Insert
    public abstract void insertAll(RoomMovie ... movies);

    @Transaction
    public void updateData(RoomMovie ... movies){
        removeAll();
        insertAll(movies);
    }
}
