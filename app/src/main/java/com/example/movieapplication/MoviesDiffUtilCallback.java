package com.example.movieapplication;

import android.support.v7.util.DiffUtil;

import com.example.movieapplication.entity.MovieElement;

import java.util.List;

public class MoviesDiffUtilCallback extends DiffUtil.Callback {

    private final List<MovieElement> oldList;
    private final List<MovieElement> newList;

    public MoviesDiffUtilCallback(List<MovieElement> oldList, List<MovieElement> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
