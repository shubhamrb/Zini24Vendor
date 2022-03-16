package com.mamits.zini24vendor.viewmodel.fragment;


import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.navigator.fragment.ProfileNavigator;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.base.BaseViewModel;

public class ProfileViewModel extends BaseViewModel<ProfileNavigator> {

    public ProfileViewModel(IDataManager dataManager, ISchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
