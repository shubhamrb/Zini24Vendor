package com.mamits.zini24vendor.di.module;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.mamits.zini24vendor.ViewModelProviderFactory;
import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.base.BaseActivity;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.activity.MainActivityViewModel;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {
    private BaseActivity<?, ?> activity;

    public ActivityModule(BaseActivity<?, ?> activity) {
        this.activity = activity;
    }

    @Provides
    public MainActivityViewModel providesMainActivityViewModel(IDataManager MainActivityViewModel, ISchedulerProvider mSchedulerProvider) {
        Supplier<MainActivityViewModel> supplier = () -> new MainActivityViewModel(MainActivityViewModel, mSchedulerProvider);
        ViewModelProviderFactory<MainActivityViewModel> factory = new ViewModelProviderFactory<>(MainActivityViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainActivityViewModel.class);
    }


}
