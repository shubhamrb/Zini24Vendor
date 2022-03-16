package com.mamits.zini24vendor.ui.fragment.dashboard.coupons;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.gson.JsonObject;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.databinding.FragmentCreateCouponBinding;
import com.mamits.zini24vendor.ui.activity.DashboardActivity;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.CreateCouponNavigator;
import com.mamits.zini24vendor.ui.utils.calender.SimpleCalender;
import com.mamits.zini24vendor.viewmodel.fragment.CreateCouponViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;


public class CreateCouponFragment extends BaseFragment<FragmentCreateCouponBinding, CreateCouponViewModel>
        implements CreateCouponNavigator, View.OnClickListener {

    private String TAG = "CreateCouponFragment";
    private FragmentCreateCouponBinding binding;

    @Inject
    CreateCouponViewModel mViewModel;
    private Context mContext;
    private int dType = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                String coupon = binding.etCoupon.getText().toString();
                String disAmount = binding.etDisAmount.getText().toString();
                String fromDate = binding.etFromDate.getText().toString();
                String toDate = binding.etToDate.getText().toString();
                String minAmount = binding.etMinAmount.getText().toString();
                String maxAmount = binding.etUptoAmount.getText().toString();
                String perUser = binding.etLimitUser.getText().toString();
                String perCoupon = binding.etLimitCoupon.getText().toString();
                String dis = binding.etDis.getText().toString();

                if (coupon.trim().length() == 0
                        || disAmount.trim().length() == 0
                        || fromDate.trim().length() == 0
                        || toDate.trim().length() == 0
                        || minAmount.trim().length() == 0
                        || maxAmount.trim().length() == 0
                        || perUser.trim().length() == 0
                        || perCoupon.trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter all required details (*).", Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject couponObject = new JSONObject();
                try {
                    couponObject.put("couponcode", coupon);
                    couponObject.put("discount_type", dType);
                    couponObject.put("discount_amount", Integer.parseInt(disAmount));
                    couponObject.put("description", dis);
                    couponObject.put("from_date", fromDate);
                    couponObject.put("to_date", toDate);
                    couponObject.put("min_amount", Integer.parseInt(minAmount));
                    couponObject.put("usage_limit_per_coupon", Integer.parseInt(perCoupon));
                    couponObject.put("usage_limit_per_user", Integer.parseInt(perUser));
                    couponObject.put("max_amount", Integer.parseInt(maxAmount));

                    createCoupon(couponObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void createCoupon(JSONObject couponObject) {
        mViewModel.createCoupon((Activity) mContext, couponObject);
    }


    @Override
    public CreateCouponViewModel getMyViewModel() {
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
            discountType();
            binding.etFromDate.setOnClickListener(v -> {
                new SimpleCalender(getChildFragmentManager()).showSimpleCalender(binding.etFromDate);
            });

            binding.etToDate.setOnClickListener(v -> {
                new SimpleCalender(getChildFragmentManager()).showSimpleCalender(binding.etToDate);
            });

            binding.btnSubmit.setOnClickListener(this);
        }
    }

    private void discountType() {
        ArrayList<String> list = new ArrayList<>();
        list.add("%");
        list.add("Flat");

        ArrayAdapter adapter = new ArrayAdapter(mContext, R.layout.spinner_layout, list);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dType = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                dType = 0;
            }
        });
    }

    @Override
    public int getBindingVariable() {
        return BR.createCouponView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_create_coupon;
    }

    @Override
    public void showProgressBars() {
        showsLoading();
    }

    @Override
    public void checkInternetConnection(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }

    @Override
    public void checkValidation(int errorCode, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void throwable(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccessCouponCreated(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(((DashboardActivity) mContext).findViewById(R.id.nav_host_fragment)).navigateUp();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}