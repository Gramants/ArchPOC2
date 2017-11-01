package com.ste.arch.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import com.ste.arch.repositories.api.checknetwork.CheckNetwork;
import com.ste.arch.repositories.api.checknetwork.CheckNetworkImpl;
import com.ste.arch.repositories.preferences.PersistentStorageProxy;
import com.ste.arch.repositories.preferences.PersistentStorageProxyImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {
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