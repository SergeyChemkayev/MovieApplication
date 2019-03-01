package com.example.movieapplication.data.modules;

import com.example.movieapplication.data.Disposer;
import com.example.movieapplication.data.DisposerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DisposerModule {

    @Provides
    @Singleton
    public Disposer provideDisposer(){
        return new DisposerImpl();
    }
}
