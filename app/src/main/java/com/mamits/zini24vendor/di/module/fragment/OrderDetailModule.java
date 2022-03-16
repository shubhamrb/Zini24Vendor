package com.mamits.zini24vendor.di.module.fragment;

import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.fragment.OrderDetailViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderDetailModule {

    @Provides
    public OrderDetailViewModel providesOrderDetail(IDataManager iDataManager, ISchedulerProvider iSchedulerProvider) {
        return new OrderDetailViewModel(iDataManager, iSchedulerProvider);
    }
}
