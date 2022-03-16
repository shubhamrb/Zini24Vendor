package com.mamits.zini24vendor.di.module.fragment;

import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.fragment.OrdersViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class OrdersModule {

    @Provides
    public OrdersViewModel providesOrders(IDataManager iDataManager, ISchedulerProvider iSchedulerProvider) {
        return new OrdersViewModel(iDataManager, iSchedulerProvider);
    }
}
