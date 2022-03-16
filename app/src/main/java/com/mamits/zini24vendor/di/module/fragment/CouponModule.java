package com.mamits.zini24vendor.di.module.fragment;

import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.fragment.CouponViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class CouponModule {

    @Provides
    public CouponViewModel providesCoupons(IDataManager iDataManager, ISchedulerProvider iSchedulerProvider) {
        return new CouponViewModel(iDataManager, iSchedulerProvider);
    }
}
