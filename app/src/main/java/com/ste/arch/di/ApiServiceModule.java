package com.ste.arch.di;

import com.ste.arch.Config;
import com.ste.arch.repositories.api.GithubApiService;
import com.ste.arch.repositories.api.HeaderInterceptor;
import com.ste.arch.utils.LiveDataCallAdapterFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class ApiServiceModule {
    private static final String BASE_URL = "base_url";

    @Provides
    @Named(BASE_URL)
    String provideBaseUrl() {
        return Config.API_HOST;
    }

    @Provides
    @Singleton
    HeaderInterceptor provideHeaderInterceptor() {
        return new HeaderInterceptor();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(HeaderInterceptor headerInterceptor,
                                   HttpLoggingInterceptor httpInterceptor) {
        return new OkHttpClient.Builder().addInterceptor(headerInterceptor)
                .addInterceptor(httpInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Converter.Factory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }


    @Provides
    @Singleton
    CallAdapter.Factory provideLiveDataAdapterFactory() {
        return new LiveDataCallAdapterFactory();
    }



    @Provides
    @Singleton
    Retrofit provideRetrofit(@Named(BASE_URL) String baseUrl, OkHttpClient client,Converter.Factory converterfactory,CallAdapter.Factory calladapter) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(converterfactory)
                .addCallAdapterFactory(calladapter)
                .client(client)
                .build();
    }

    /* Specific services */
    @Provides
    @Singleton
    GithubApiService provideGitHubService(Retrofit retrofit) {
        return retrofit.create(GithubApiService.class);
    }
}