package com.mamits.zini24vendor.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mamits.zini24vendor.ui.fragment.dashboard.HomeFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.InboxFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.PaymentFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.coupons.CouponFragment;
import com.mamits.zini24vendor.ui.fragment.dashboard.services.ServicesFragment;
import com.mamits.zini24vendor.ui.fragment.orders.OrdersFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new HomeFragment();
        } else if (position == 1) {
            fragment = new OrdersFragment();
        } else if (position == 2) {
            fragment = new InboxFragment();
        } else if (position == 3) {
            fragment = new ServicesFragment();
        } else if (position == 4) {
            fragment = new PaymentFragment();
        } else  {
            fragment = new CouponFragment();
        }
       /* else {
            fragment = new CategoryFragment();
        }*/
        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        if (position == 0) {
            title = "Home";
        } else if (position == 1) {
            title = "Orders";
        } else if (position == 2) {
            title = "Chat";
        } else if (position == 3) {
            title = "Services";
        } else if (position == 4) {
            title = "Payments";
        } else  {
            title = "Coupons";
        }
       /* else {
            title = "Category";
        }*/
        return title;
    }

}
