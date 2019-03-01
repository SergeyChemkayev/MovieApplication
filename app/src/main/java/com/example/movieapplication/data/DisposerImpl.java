package com.example.movieapplication.data;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DisposerImpl implements Disposer{
    private CompositeDisposable compositeDisposable;

    public DisposerImpl() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void add(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
