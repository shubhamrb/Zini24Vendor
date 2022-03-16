package com.mamits.zini24vendor.di.module.activity;


import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.activity.ProfileActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileActivityModule {

    @Provides
    public ProfileActivityViewModel providesProfileActivityViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new ProfileActivityViewModel(mDataManger, mSchedulerProvider);
    }

}
