package com.mamits.zini24vendor.di.builder;


import com.mamits.zini24vendor.di.module.activity.DashboardActivityModule;
import com.mamits.zini24vendor.di.module.activity.MainActivityModule;
import com.mamits.zini24vendor.di.module.activity.PaymentActivityModule;
import com.mamits.zini24vendor.di.module.activity.ProfileActivityModule;
import com.mamits.zini24vendor.di.module.activity.RegisterActivityModule;
import com.mamits.zini24vendor.di.module.fragment.AddServiceModule;
import com.mamits.zini24vendor.di.module.fragment.CategoryModule;
import com.mamits.zini24vendor.di.module.fragment.ChangePasswordModule;
import com.mamits.zini24vendor.di.module.fragment.CouponModule;
import com.mamits.zini24vendor.di.module.fragment.CreateCouponModule;
import com.mamits.zini24vendor.di.module.fragment.DashboardFragmentModule;
import com.mamits.zini24vendor.di.module.fragment.HelpSupportModule;
import com.mamits.zini24vendor.di.module.fragment.HomeModule;
import com.mamits.zini24vendor.di.module.fragment.InboxModule;
import com.mamits.zini24vendor.di.module.fragment.MessageModule;
import com.mamits.zini24vendor.di.module.fragment.OrderDetailModule;
import com.mamits.zini24vendor.di.module.fragment.OrdersModule;
import com.mamits.zini24vendor.di.module.fragment.PaymentsModule;
import com.mamits.zini24vendor.di.module.fragment.ProfileModule;
import com.mamits.zini24vendor.di.module.fragment.ServicesModule;
import com.mamits.zini24vendor.di.module.fragment.TransactionsModule;
import com.mamits.zini24vendor.di.scope.ActivityScope;
import com.mamits.zini24vendor.di.scope.FragmentScope;
import com.mamits.zini24vendor.ui.activity.DashboardActivity;
import com.mamits.zini24vendor.ui.activity.MainActivity;
import com.mamits.zini24vendor.ui.activity.PaymentActivity;
import com.mamits.zini24vendor.ui.activity.ProfileActivity;
import com.mamits.zini24vendor.ui.activity.RegisterActivity;
import com.mamits.zini24vendor.ui.fragment.DashboardFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.CategoryFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.HomeFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.InboxFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.MessageFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.PaymentFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.coupons.CouponFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.coupons.CreateCouponFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.drawer.ChangePasswordFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.drawer.HelpSupportFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.drawer.ProfileFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.drawer.TransactionsFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.services.AddServiceFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.services.ServicesFragment;
import com.mamits.zini24vendor.ui.fragment.orders.OrderDetailsFragment;
import com.mamits.zini24vendor.ui.fragment.orders.OrdersFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    @ActivityScope
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {PaymentActivityModule.class})
    @ActivityScope
    abstract PaymentActivity bindPaymentActivity();

    @ContributesAndroidInjector(modules = {RegisterActivityModule.class})
    @ActivityScope
    abstract RegisterActivity bindRegisterActivity();

    @ContributesAndroidInjector(modules = {DashboardActivityModule.class})
    @ActivityScope
    abstract DashboardActivity bindDashboardActivity();

    @ContributesAndroidInjector(modules = {DashboardFragmentModule.class})
    @FragmentScope
    abstract DashboardFragment bindDashboardFragment();

    @ContributesAndroidInjector(modules = {HomeModule.class})
    @FragmentScope
    abstract HomeFragment bindHomeFragment();

    @ContributesAndroidInjector(modules = {OrdersModule.class})
    @FragmentScope
    abstract OrdersFragment bindOrdersFragment();

    @ContributesAndroidInjector(modules = {OrderDetailModule.class})
    @FragmentScope
    abstract OrderDetailsFragment bindOrderDetailFragment();

    @ContributesAndroidInjector(modules = {PaymentsModule.class})
    @FragmentScope
    abstract PaymentFragment bindPaymentsFragment();

    @ContributesAndroidInjector(modules = {TransactionsModule.class})
    @FragmentScope
    abstract TransactionsFragment bindTransactionsFragment();

    @ContributesAndroidInjector(modules = {HelpSupportModule.class})
    @FragmentScope
    abstract HelpSupportFragment bindHelpSupportFragment();

    @ContributesAndroidInjector(modules = {InboxModule.class})
    @FragmentScope
    abstract InboxFragment bindInboxFragment();

    @ContributesAndroidInjector(modules = {MessageModule.class})
    @FragmentScope
    abstract MessageFragment bindMessageFragment();

    @ContributesAndroidInjector(modules = {CouponModule.class})
    @FragmentScope
    abstract CouponFragment bindCouponFragment();

    @ContributesAndroidInjector(modules = {CreateCouponModule.class})
    @FragmentScope
    abstract CreateCouponFragment bindCreateCouponFragment();

    @ContributesAndroidInjector(modules = {ServicesModule.class})
    @FragmentScope
    abstract ServicesFragment bindServicesFragment();

    @ContributesAndroidInjector(modules = {AddServiceModule.class})
    @FragmentScope
    abstract AddServiceFragment bindAddServicesFragment();

    @ContributesAndroidInjector(modules = {ChangePasswordModule.class})
    @FragmentScope
    abstract ChangePasswordFragment bindChangePassFragment();

    @ContributesAndroidInjector(modules = {ProfileModule.class})
    @FragmentScope
    abstract ProfileFragment bindProfileFragment();

    @ContributesAndroidInjector(modules = {CategoryModule.class})
    @FragmentScope
    abstract CategoryFragment bindCategoryFragment();

    @ContributesAndroidInjector(modules = {ProfileActivityModule.class})
    @ActivityScope
    abstract ProfileActivity bindProfileActivity();
}
