package com.mamits.zini24vendor.di.module.fragment;

import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.fragment.ChangePasswordViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ChangePasswordModule {

    @Provides
    public ChangePasswordViewModel providesChangePassword(IDataManager iDataManager, ISchedulerProvider iSchedulerProvider) {
        return new ChangePasswordViewModel(iDataManager, iSchedulerProvider);
    }
}
