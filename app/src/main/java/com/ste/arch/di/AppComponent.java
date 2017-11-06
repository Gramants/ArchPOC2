package com.ste.arch.di;

import android.app.Application;

import com.ste.arch.MyApplication;

import com.ste.arch.di.ui.UiBuilder;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;


@Singleton
@Component(modules = {
        //necessary modules
        AndroidInjectionModule.class, AndroidSupportInjectionModule.class,
        AppRepositoryModule.class,
        AppModule.class,
        ApiServiceModule.class,
        DatabaseModule.class,
        AppModule.class,
        UiBuilder.class})

public interface AppComponent {

    void inject(MyApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}