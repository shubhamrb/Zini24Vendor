package com.mamits.zini24vendor.di.module.fragment;

import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.fragment.ProfileViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileModule {

    @Provides
    public ProfileViewModel providesProfile(IDataManager iDataManager, ISchedulerProvider iSchedulerProvider) {
        return new ProfileViewModel(iDataManager, iSchedulerProvider);
    }
}
