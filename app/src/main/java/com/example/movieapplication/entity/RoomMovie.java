package com.example.movieapplication.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movies")
public class RoomMovie {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "name_eng")
    private String nameEng;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "premiere")
    private String premiere;

    @ColumnInfo(name = "cover")
    private String cover;

    public RoomMovie(String name, String nameEng, String description, String premiere, String cover) {
        this.name = name;
        this.nameEng = nameEng;
        this.description = description;
        this.premiere = premiere;
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPremiere() {
        return premiere;
    }

    public void setPremiere(String premiere) {
        this.premiere = premiere;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
