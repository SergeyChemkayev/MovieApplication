package com.example.movieapplication.entity;

public class MovieProgress implements MovieElement {

    public static final int TYPE = 0;
    public static final String ID = "PROGRESS_ID_FILLER";

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public String getId() {
        return ID;
    }
}
