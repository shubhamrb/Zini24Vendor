package com.mamits.zini24vendor.viewmodel.fragment;


import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.navigator.fragment.DashboardFragmentNavigator;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.base.BaseViewModel;

public class DashboardFragmentViewModel extends BaseViewModel<DashboardFragmentNavigator> {

    public DashboardFragmentViewModel(IDataManager dataManager, ISchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


}
