package com.ste.arch.di;

import android.app.Application;
import com.ste.arch.MyApplication;

import com.ste.arch.di.ui.UiBuilder;
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
        AppModule.class,
        ApiServiceModule.class,
        DatabaseModule.class,
/*
    //app modules
    AppModule.class,
    //repo modules
    RepoModule.class,
    //datasource modules
    RESTModule.class,
    RoomModule.class,
    AuthModule.class,*/



    AppModule.class,

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