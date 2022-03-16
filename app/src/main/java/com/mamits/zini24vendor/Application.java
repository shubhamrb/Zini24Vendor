package com.mamits.zini24vendor;

import android.app.Activity;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.google.android.libraries.places.api.Places;
import com.google.firebase.FirebaseApp;
import com.mamits.zini24vendor.di.component.DaggerAppComponent;

import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;

public class Application extends DaggerApplication {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.getDefault());
        }

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        DaggerAppComponent mAppComponent = (DaggerAppComponent) DaggerAppComponent.builder().create(this);
        mAppComponent.inject(this);
        return mAppComponent;
    }
}
