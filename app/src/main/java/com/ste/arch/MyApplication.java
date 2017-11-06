package com.ste.arch;

import android.app.Activity;
import android.app.Application;


import com.ste.arch.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import javax.inject.Inject;



public class MyApplication extends Application implements HasActivityInjector{

  @Inject
  DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

  public void onCreate() {
    super.onCreate();
    DaggerAppComponent.builder().application(this).build().inject(this);

  }
  @Override
  public AndroidInjector<Activity> activityInjector() {
    return activityDispatchingAndroidInjector;
  }

}
