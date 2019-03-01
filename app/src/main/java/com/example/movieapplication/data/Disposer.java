package com.example.movieapplication.data;

import io.reactivex.disposables.Disposable;

public interface Disposer {

    void add(Disposable disposable);
    void dispose();
}
