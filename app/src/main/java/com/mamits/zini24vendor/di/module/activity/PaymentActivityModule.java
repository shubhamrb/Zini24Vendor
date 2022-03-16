package com.mamits.zini24vendor.di.module.activity;


import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.activity.PaymentActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PaymentActivityModule {

    @Provides
    public PaymentActivityViewModel providesPaymentActivityViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider){
    return  new PaymentActivityViewModel(mDataManger,mSchedulerProvider);
    }

}
