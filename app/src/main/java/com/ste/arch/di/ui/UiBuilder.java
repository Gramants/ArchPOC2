package com.ste.arch.di.ui;

import com.ste.arch.ui.viewpager.BlankFragment;
import com.ste.arch.ui.viewpager.BlankFragmentA;
import com.ste.arch.ui.viewpager.BlankFragmentAModule;
import com.ste.arch.ui.viewpager.BlankFragmentB;
import com.ste.arch.ui.viewpager.BlankFragmentBModule;
import com.ste.arch.ui.viewpager.BlankFragmentC;
import com.ste.arch.ui.viewpager.BlankFragmentCModule;
import com.ste.arch.ui.viewpager.BlankFragmentModule;
import com.ste.arch.ui.viewpager.PagerActivity;
import com.ste.arch.ui.viewpager.PagerActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class UiBuilder {


  @ContributesAndroidInjector(modules = PagerActivityModule.class)
  abstract PagerActivity bindPagerActivity();

  @ContributesAndroidInjector(modules = BlankFragmentAModule.class)
  abstract BlankFragmentA bindBlankFragmentA();

  @ContributesAndroidInjector(modules = BlankFragmentBModule.class)
  abstract BlankFragmentB bindBlankFragmentB();

  @ContributesAndroidInjector(modules = BlankFragmentCModule.class)
  abstract BlankFragmentC bindBlankFragmentC();

  @ContributesAndroidInjector(modules = BlankFragmentModule.class)
  abstract BlankFragment bindBlankFragment();





}