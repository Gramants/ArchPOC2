package com.ste.arch.di;

import com.ste.arch.App;

import com.ste.arch.di.ui.UiBuilder;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;


@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AppRepositoryModule.class,
        AppModule.class,
        ApiServiceModule.class,
        DatabaseModule.class,
        AppModule.class,
        UiBuilder.class})

public interface AppComponent {

    void inject(App app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(android.app.Application application);

        AppComponent build();
    }
}