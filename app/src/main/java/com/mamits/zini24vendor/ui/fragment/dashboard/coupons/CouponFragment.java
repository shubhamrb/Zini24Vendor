package com.mamits.zini24vendor.ui.fragment.dashboard.coupons;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.coupons.CouponsDataModel;
import com.mamits.zini24vendor.databinding.FragmentCouponBinding;
import com.mamits.zini24vendor.ui.adapter.CouponsAdapter;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.CouponNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.CouponViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class CouponFragment extends BaseFragment<FragmentCouponBinding,
        CouponViewModel> implements CouponNavigator, View.OnClickListener, CouponsAdapter.deleteListener {
    private String TAG = "CouponFragment";
    private FragmentCouponBinding binding;

    @Inject
    CouponViewModel mViewModel;
    private Context mContext;
    private Gson mGson;
    private List<CouponsDataModel> couponsList;
    private CouponsAdapter couponsAdapter;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                goToCreateCoupon(v);
                break;
        }
    }

    private void goToCreateCoupon(View v) {
        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_out_right)
                .setExitAnim(R.anim.slide_in).setPopEnterAnim(0).setPopExitAnim(R.anim.slide_out1)
                .build();
        NavController navController = Navigation.findNavController(v);
        navController.navigate(R.id.nav_create_coupon, null, options);
    }

    @Override
    public CouponViewModel getMyViewModel() {
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
            binding.progressBar.setVisibility(View.VISIBLE);
            setUpCoupons();
            binding.btnAdd.setOnClickListener(this);
            binding.swipe.setOnRefreshListener(this::loadCoupons);
        } else {
            loadCoupons();
        }
    }

    private void setUpCoupons() {
        couponsList = new ArrayList<>();
        mGson = new Gson();
        binding.recyclerCoupons.setLayoutManager(new LinearLayoutManager(getActivity()));
        couponsAdapter = new CouponsAdapter(getActivity(), mViewModel, this);
        binding.recyclerCoupons.setAdapter(couponsAdapter);

        loadCoupons();
    }

    private void loadCoupons() {
        mViewModel.fetchCoupons((Activity) mContext);
    }

    @Override
    public int getBindingVariable() {
        return BR.couponsView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    public void showProgressBars() {
        showsLoading();
    }

    @Override
    public void checkInternetConnection(String message) {
        binding.progressBar.setVisibility(View.GONE);
        /*swipe off*/
        binding.swipe.setRefreshing(false);
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }

    @Override
    public void checkValidation(int errorCode, String message) {
        binding.progressBar.setVisibility(View.GONE);
        /*swipe off*/
        binding.swipe.setRefreshing(false);
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void throwable(Throwable throwable) {
        binding.progressBar.setVisibility(View.GONE);
        /*swipe off*/
        binding.swipe.setRefreshing(false);
        throwable.printStackTrace();
    }

    @Override
    public void onSuccessFetchCoupons(JsonObject jsonObject) {
        binding.progressBar.setVisibility(View.GONE);
        /*swipe off*/
        binding.swipe.setRefreshing(false);
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type couponsDataList = new TypeToken<List<CouponsDataModel>>() {
                }.getType();
                couponsList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), couponsDataList);

                if (couponsList != null && couponsList.size() > 0) {
                    couponsAdapter.setList(couponsList);
                    binding.recyclerCoupons.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerCoupons.setVisibility(View.GONE);
                }

            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessDeleteCoupon(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                loadCoupons();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDeleteCoupon(String couponid) {
        new AlertDialog.Builder(mContext)
                .setTitle("Delete Coupon")
                .setMessage("Are you sure you want to delete this coupon ?")

                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Continue with delete operation
                    mViewModel.deleteCoupon((Activity) mContext, couponid);
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}