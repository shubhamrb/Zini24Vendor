package com.mamits.zini24vendor.di.module.fragment;

import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.fragment.CreateCouponViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class CreateCouponModule {

    @Provides
    public CreateCouponViewModel providesCreateCoupon(IDataManager iDataManager, ISchedulerProvider iSchedulerProvider) {
        return new CreateCouponViewModel(iDataManager, iSchedulerProvider);
    }
}
