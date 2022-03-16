package com.mamits.zini24vendor.ui.fragment.orders;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.orders.OrdersDataModel;
import com.mamits.zini24vendor.databinding.FragmentOrdersBinding;
import com.mamits.zini24vendor.ui.adapter.OrdersAdapter;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.OrdersNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.OrdersViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class OrdersFragment extends BaseFragment<FragmentOrdersBinding, OrdersViewModel> implements OrdersNavigator, View.OnClickListener {
    private String TAG = "OrdersFragment";
    private FragmentOrdersBinding binding;

    @Inject
    OrdersViewModel mViewModel;
    private Context mContext;
    private Gson mGson;
    private List<OrdersDataModel> ordersList;
    private OrdersAdapter ordersAdapter;
    private int SELECTED_FILTER = 1;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_filter:
                openFilterDialog(v);
                break;
        }
    }

    @Override
    public OrdersViewModel getMyViewModel() {
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
            binding.txtFilter.setOnClickListener(this);
            binding.progressBar.setVisibility(View.VISIBLE);
            setUpOrders();
            binding.swipe.setOnRefreshListener(() -> loadOrders(SELECTED_FILTER, null));
        } else {
            loadOrders(SELECTED_FILTER, null);
        }
    }

    private void setUpOrders() {
        ordersList = new ArrayList<>();
        mGson = new Gson();
        binding.recyclerOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        ordersAdapter = new OrdersAdapter(getActivity(), mViewModel);
        binding.recyclerOrders.setAdapter(ordersAdapter);

        loadOrders(SELECTED_FILTER, null);
    }

    private void loadOrders(int status, PopupWindow popupWindow) {
        SELECTED_FILTER=status;
        try {
            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
                mViewModel.getmNavigator().get().showProgressBars();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (status) {
            case 1:
                binding.txtH1.setText("Pending Orders");
                binding.txtFilter.setText("Pending");
                break;
            case 2:
                binding.txtH1.setText("Accepted Orders");
                binding.txtFilter.setText("Accept");
                break;
            case 5:
                binding.txtH1.setText("Completed Orders");
                binding.txtFilter.setText("Complete");
                break;
            case 4:
                binding.txtH1.setText("Canceled Orders");
                binding.txtFilter.setText("Cancel");
                break;
            case 3:
                binding.txtH1.setText("Rejected Orders");
                binding.txtFilter.setText("Reject");
                break;
            case 0:
                binding.txtH1.setText("All Orders");
                binding.txtFilter.setText("All");
                break;
        }
        mViewModel.fetchOrders((Activity) mContext, status);
    }

    private void openFilterDialog(View v) {
        LayoutInflater layoutInflater
                = (LayoutInflater) mContext
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.orders_filter, null);

        RelativeLayout rl_all = popupView.findViewById(R.id.rl_all);
        RelativeLayout rl_pending = popupView.findViewById(R.id.rl_pending);
        RelativeLayout rl_accept = popupView.findViewById(R.id.rl_accept);
        RelativeLayout rl_reject = popupView.findViewById(R.id.rl_reject);
        RelativeLayout rl_cancel = popupView.findViewById(R.id.rl_cancel);
        RelativeLayout rl_complete = popupView.findViewById(R.id.rl_complete);


        PopupWindow popupWindow = new PopupWindow(
                popupView,
                mContext.getResources().getDimensionPixelOffset(R.dimen._200sdp),
                ViewGroup.LayoutParams.WRAP_CONTENT);

        rl_pending.setOnClickListener(v1 -> loadOrders(1, popupWindow));
        rl_accept.setOnClickListener(v1 -> loadOrders(2, popupWindow));
        rl_complete.setOnClickListener(v1 -> loadOrders(5, popupWindow));
        rl_cancel.setOnClickListener(v1 -> loadOrders(4, popupWindow));
        rl_reject.setOnClickListener(v1 -> loadOrders(3, popupWindow));
        rl_all.setOnClickListener(v1 -> loadOrders(0, popupWindow));

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAsDropDown(v, 0, 0);
    }

    @Override
    public int getBindingVariable() {
        return BR.ordersView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_orders;
    }

    @Override
    public void showProgressBars() {
        showsLoading();
    }

    @Override
    public void checkInternetConnection(String message) {
        binding.progressBar.setVisibility(View.GONE);
        binding.swipe.setRefreshing(false);
    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }

    @Override
    public void checkValidation(int errorCode, String message) {
        binding.progressBar.setVisibility(View.GONE);
        binding.swipe.setRefreshing(false);
    }

    @Override
    public void throwable(Throwable throwable) {
        binding.progressBar.setVisibility(View.GONE);
        binding.swipe.setRefreshing(false);
        throwable.printStackTrace();
    }

    @Override
    public void onSuccessOrders(JsonObject jsonObject) {
        binding.swipe.setRefreshing(false);
        binding.progressBar.setVisibility(View.GONE);
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type orderDataList = new TypeToken<List<OrdersDataModel>>() {
                }.getType();
                ordersList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), orderDataList);

                if (ordersList != null && ordersList.size() > 0) {
                    ordersAdapter.setList(ordersList);
                    binding.recyclerOrders.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerOrders.setVisibility(View.GONE);
                }

            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}