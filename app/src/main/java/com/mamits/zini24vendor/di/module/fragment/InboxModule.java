package com.mamits.zini24vendor.di.module.fragment;

import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.fragment.OrdersViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class InboxModule {

    @Provides
    public OrdersViewModel providesInbox(IDataManager iDataManager, ISchedulerProvider iSchedulerProvider) {
        return new OrdersViewModel(iDataManager, iSchedulerProvider);
    }
}
