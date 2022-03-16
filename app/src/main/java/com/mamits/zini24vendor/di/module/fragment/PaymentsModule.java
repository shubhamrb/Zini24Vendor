package com.mamits.zini24vendor.di.module.fragment;

import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.fragment.PaymentsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PaymentsModule {

    @Provides
    public PaymentsViewModel providesPayments(IDataManager iDataManager, ISchedulerProvider iSchedulerProvider) {
        return new PaymentsViewModel(iDataManager, iSchedulerProvider);
    }
}
