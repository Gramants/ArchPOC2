package com.cn29.aac.diold.ui;

import com.cn29.aac.ui.viewpager.BlankFragmentA;
import com.cn29.aac.ui.viewpager.BlankFragmentAModule;
import com.cn29.aac.ui.viewpager.BlankFragmentB;
import com.cn29.aac.ui.viewpager.BlankFragmentBModule;
import com.cn29.aac.ui.viewpager.BlankFragmentC;
import com.cn29.aac.ui.viewpager.BlankFragmentCModule;
import com.cn29.aac.ui.viewpager.PagerActivity;
import com.cn29.aac.ui.viewpager.PagerActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class UiBuilder {

  //view pager
  @ContributesAndroidInjector(modules = PagerActivityModule.class)
  abstract PagerActivity bindPagerActivity();

  @ContributesAndroidInjector(modules = BlankFragmentAModule.class)
  abstract BlankFragmentA bindBlankFragmentA();

  @ContributesAndroidInjector(modules = BlankFragmentBModule.class)
  abstract BlankFragmentB bindBlankFragmentB();

  @ContributesAndroidInjector(modules = BlankFragmentCModule.class)
  abstract BlankFragmentC bindBlankFragmentC();
  //master detail


}