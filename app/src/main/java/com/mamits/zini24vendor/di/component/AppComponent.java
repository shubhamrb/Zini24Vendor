package com.mamits.zini24vendor.di.component;


import com.mamits.zini24vendor.Application;
import com.mamits.zini24vendor.di.builder.ActivityBuilder;
import com.mamits.zini24vendor.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {AppModule.class, AndroidInjectionModule.class, AndroidSupportInjectionModule.class, ActivityBuilder.class})
public interface AppComponent extends AndroidInjector<Application> {

    @Component.Builder
     abstract class Builder extends  AndroidInjector.Builder<Application> {};

}
