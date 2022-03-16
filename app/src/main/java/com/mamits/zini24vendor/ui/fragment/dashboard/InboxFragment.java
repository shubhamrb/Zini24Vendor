package com.mamits.zini24vendor.ui.fragment.dashboard;

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
import com.mamits.zini24vendor.databinding.FragmentInboxBinding;
import com.mamits.zini24vendor.ui.adapter.InboxAdapter;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.OrdersNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.OrdersViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class InboxFragment extends BaseFragment<FragmentInboxBinding, OrdersViewModel> implements OrdersNavigator, View.OnClickListener {
    private String TAG = "InboxFragment";
    private FragmentInboxBinding binding;

    @Inject
    OrdersViewModel mViewModel;
    private Context mContext;
    private Gson mGson;
    private List<OrdersDataModel> inboxList;
    private InboxAdapter inboxAdapter;
    private int SELECTED_FILTER = 2;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_filter:
                openFilterDialog(v);
                break;
        }
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

        rl_pending.setOnClickListener(v1 -> loadInbox(1, popupWindow));
        rl_accept.setOnClickListener(v1 -> loadInbox(2, popupWindow));
        rl_complete.setOnClickListener(v1 -> loadInbox(5, popupWindow));
        rl_cancel.setOnClickListener(v1 -> loadInbox(4, popupWindow));
        rl_reject.setOnClickListener(v1 -> loadInbox(3, popupWindow));
        rl_all.setOnClickListener(v1 -> loadInbox(0, popupWindow));

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAsDropDown(v, 0, 0);
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
            setUpInbox();
            binding.swipe.setOnRefreshListener(() -> loadInbox(SELECTED_FILTER, null));
        }
    }

    private void setUpInbox() {
        inboxList = new ArrayList<>();
        mGson = new Gson();
        binding.recyclerChats.setLayoutManager(new LinearLayoutManager(getActivity()));
        inboxAdapter = new InboxAdapter(getActivity(), mViewModel);
        binding.recyclerChats.setAdapter(inboxAdapter);

        loadInbox(SELECTED_FILTER, null);
    }

    private void loadInbox(int status, PopupWindow popupWindow) {
        SELECTED_FILTER = status;
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
                binding.txtFilter.setText("Pending");
                break;
            case 2:
                binding.txtFilter.setText("Accept");
                break;
            case 5:
                binding.txtFilter.setText("Complete");
                break;
            case 4:
                binding.txtFilter.setText("Cancel");
                break;
            case 3:
                binding.txtFilter.setText("Reject");
                break;
            case 0:
                binding.txtFilter.setText("All");
                break;
        }
        mViewModel.fetchOrders((Activity) mContext, status);
    }

    @Override
    public int getBindingVariable() {
        return BR.inboxView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_inbox;
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
                inboxList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), orderDataList);

                if (inboxList != null && inboxList.size() > 0) {
                    inboxAdapter.setList(inboxList);
                    binding.recyclerChats.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerChats.setVisibility(View.GONE);
                }

            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

}