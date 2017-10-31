package com.cn29.aac.diold;

import android.app.Application;
import com.cn29.aac.MyApplication;

import com.cn29.aac.di.ApiServiceModule;
import com.cn29.aac.di.AppRepositoryModule;
import com.cn29.aac.di.DatabaseModule;
import com.cn29.aac.di.MyAppModule;
import com.cn29.aac.diold.ui.UiBuilder;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;


/**
 * Created by Charles Ng on 27/9/2017.
 */
@Singleton
@Component(modules = {
    //necessary modules
    AndroidInjectionModule.class, AndroidSupportInjectionModule.class,
        AppRepositoryModule.class,
        MyAppModule.class,
        ApiServiceModule.class,
        DatabaseModule.class,
/*
    //app modules
    MyAppModule.class,
    //repo modules
    RepoModule.class,
    //datasource modules
    RESTModule.class,
    RoomModule.class,
    AuthModule.class,*/



    MyAppModule.class,

    //ui modules
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