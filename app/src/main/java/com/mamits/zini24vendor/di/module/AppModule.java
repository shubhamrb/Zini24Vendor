package com.mamits.zini24vendor.di.module;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mamits.zini24vendor.Application;
import com.mamits.zini24vendor.data.datamanager.DataManager;
import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.data.pref.IPreferenceHelper;
import com.mamits.zini24vendor.data.pref.PreferenceHelper;
import com.mamits.zini24vendor.data.remote.ApiHeader;
import com.mamits.zini24vendor.data.remote.ApiHelper;
import com.mamits.zini24vendor.data.remote.IApiHelper;
import com.mamits.zini24vendor.di.scope.PreferenceInfo;
import com.mamits.zini24vendor.ui.utils.constants.AppConstant;
import com.mamits.zini24vendor.ui.utils.rx.AppSchedulerProvider;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application Myapplication;


    @Provides
    @Singleton
    IApiHelper provideApiHelper(ApiHelper mApiHelper) {
        return mApiHelper;
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        this.Myapplication = application;
        return application;
    }

    @Provides
    @Singleton
    IDataManager provideDataManager(DataManager appDataManager) {
        return appDataManager;
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstant.PREF_NAME;
    }

    @Provides
    @Singleton
    IPreferenceHelper providePreferencesHelper(PreferenceHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPrefs(Context context) {
           return context.getSharedPreferences(providePreferenceName(),Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(
            IPreferenceHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                preferencesHelper.getAccessToken());
    }

    @Provides
    ISchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

}
