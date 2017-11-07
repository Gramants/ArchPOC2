package com.ste.arch.di.ui;

import com.ste.arch.ui.viewpager.DummyFragment;
import com.ste.arch.ui.viewpager.FragmentA;
import com.ste.arch.ui.viewpager.FragmentAModule;
import com.ste.arch.ui.viewpager.FragmentB;
import com.ste.arch.ui.viewpager.FragmentBModule;
import com.ste.arch.ui.viewpager.FragmentC;
import com.ste.arch.ui.viewpager.FragmentCModule;
import com.ste.arch.ui.viewpager.DummyFragmentModule;
import com.ste.arch.ui.viewpager.PagerActivity;
import com.ste.arch.ui.viewpager.PagerActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class UiBuilder {


  @ContributesAndroidInjector(modules = PagerActivityModule.class)
  abstract PagerActivity bindPagerActivity();

  @ContributesAndroidInjector(modules = FragmentAModule.class)
  abstract FragmentA bindBlankFragmentA();

  @ContributesAndroidInjector(modules = FragmentBModule.class)
  abstract FragmentB bindBlankFragmentB();

  @ContributesAndroidInjector(modules = FragmentCModule.class)
  abstract FragmentC bindBlankFragmentC();

  @ContributesAndroidInjector(modules = DummyFragmentModule.class)
  abstract DummyFragment bindBlankFragment();





}