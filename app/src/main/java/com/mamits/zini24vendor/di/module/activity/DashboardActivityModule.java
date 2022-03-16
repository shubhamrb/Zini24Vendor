package com.mamits.zini24vendor.di.module.activity;


import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.activity.DashboardActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class DashboardActivityModule {

    @Provides
    public DashboardActivityViewModel providesDashboardViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider){
    return  new DashboardActivityViewModel(mDataManger,mSchedulerProvider);
    }

}
