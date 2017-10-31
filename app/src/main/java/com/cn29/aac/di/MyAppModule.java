package com.cn29.aac.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import com.cn29.aac.repositories.api.checknetwork.CheckNetwork;
import com.cn29.aac.repositories.api.checknetwork.CheckNetworkImpl;
import com.cn29.aac.repositories.preferences.PersistentStorageProxy;
import com.cn29.aac.repositories.preferences.PersistentStorageProxyImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class MyAppModule {
    @Singleton
    @Provides
    Context provideContext(Application application) {
        return application;
    }

    @Singleton
    @Provides
    SharedPreferences provideDefaultSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Singleton
    @Provides
    PersistentStorageProxy providePersistentStorageProxy(SharedPreferences preferences) {
        return new PersistentStorageProxyImpl(preferences);
    }

    @Singleton
    @Provides
    ConnectivityManager provideConnectivityManager(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

    }

    @Singleton
    @Provides
    CheckNetwork provideCheckNetworkImpl(ConnectivityManager connectivitymanager) {
        return new CheckNetworkImpl(connectivitymanager);
}

}