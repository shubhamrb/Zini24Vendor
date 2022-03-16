package com.mamits.zini24vendor.di.module.fragment;

import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.fragment.ServicesViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {

    @Provides
    public ServicesViewModel providesServices(IDataManager iDataManager, ISchedulerProvider iSchedulerProvider) {
        return new ServicesViewModel(iDataManager, iSchedulerProvider);
    }
}
