package com.example.movieapplication.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movies")
public class RoomMovieEntity {

    @PrimaryKey
    public long id;

    @ColumnInfo(name = "name")
    public String name;
    
    public String nameEng;
    public String description;
    public String premiere;
    public String cover;
}
