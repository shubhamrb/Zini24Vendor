package com.mamits.zini24vendor.di.module.fragment;

import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.fragment.DashboardFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class DashboardFragmentModule {

    @Provides
    public DashboardFragmentViewModel providesDashboardFragment(IDataManager iDataManager, ISchedulerProvider iSchedulerProvider) {
        return new DashboardFragmentViewModel(iDataManager, iSchedulerProvider);
    }
}
