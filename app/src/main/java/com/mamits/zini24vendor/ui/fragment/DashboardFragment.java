package com.mamits.zini24vendor.ui.fragment;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.databinding.FragmentDashboardBinding;
import com.mamits.zini24vendor.ui.adapter.ViewPagerAdapter;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.DashboardFragmentNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.DashboardFragmentViewModel;

import java.util.Calendar;

import javax.inject.Inject;

public class DashboardFragment extends BaseFragment<FragmentDashboardBinding, DashboardFragmentViewModel> implements DashboardFragmentNavigator, View.OnClickListener {

    private String TAG = "DashboardFragment";
    private FragmentDashboardBinding binding;

    @Inject
    DashboardFragmentViewModel mViewModel;
    private Context mContext;
    private ViewPagerAdapter viewPagerAdapter;
    private String action;
    private int CURRENT_PAGE = 0;

    @Override
    public DashboardFragmentViewModel getMyViewModel() {
        return mViewModel;
    }

    @Override
    protected void initView(View view, boolean isRefresh) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);
        if (getActivity() != null) {
            mContext = getActivity();
        } else if (getBaseActivity() != null) {
            mContext = getBaseActivity();
        } else if (view.getContext() != null) {
            mContext = view.getContext();
        }
        if (isRefresh) {
            setUpViewPager();
            setWishText();
        }
    }

    private void setUpViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.viewPager.setOffscreenPageLimit(1);
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.txtWish.setVisibility(View.VISIBLE);
                } else {
                    binding.txtWish.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        action = mViewModel.getmDataManger().getNotificationType();
        mViewModel.getmDataManger().setNotificationType(null);

        if (action != null && action.trim().length() != 0) {
            final Handler handler = new Handler();
            switch (action) {
                case "order":
                    handler.postDelayed(() -> {
                        binding.viewPager.setCurrentItem(1);
                    }, 1000);
                    break;
                case "home":
                    handler.postDelayed(() -> {
                        binding.viewPager.setCurrentItem(0);
                    }, 1000);
                    break;
                case "chat":
                    handler.postDelayed(() -> {
                        binding.viewPager.setCurrentItem(2);
                    }, 1000);
                    break;

            }
        }
    }

    public boolean goToHome() {
        if (binding.viewPager.getCurrentItem() == 0) {
            return false;
        } else {
            binding.viewPager.setCurrentItem(0);
            return true;
        }
    }


    private void setWishText() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay < 12) {
            binding.txtWish.setText(String.format("Good Morning\n%s", mViewModel.getmDataManger().getUsername()));
        } else if (timeOfDay < 16) {
            binding.txtWish.setText(String.format("Good Afternoon\n%s", mViewModel.getmDataManger().getUsername()));
        } else if (timeOfDay < 21) {
            binding.txtWish.setText(String.format("Good Evening\n%s", mViewModel.getmDataManger().getUsername()));
        } else {
            binding.txtWish.setText(String.format("Good Night\n%s", mViewModel.getmDataManger().getUsername()));
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.dashboardFragmentView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dashboard;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void showProgressBars() {

    }

    @Override
    public void checkInternetConnection(String message) {

    }

    @Override
    public void hideProgressBars() {

    }

    @Override
    public void checkValidation(int errorCode, String message) {

    }

    @Override
    public void throwable(Throwable throwable) {

    }

}