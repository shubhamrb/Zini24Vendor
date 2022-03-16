package com.mamits.zini24vendor.ui.fragment.dashboard;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.services.CategoryDataModel;
import com.mamits.zini24vendor.databinding.FragmentCategoryBinding;
import com.mamits.zini24vendor.ui.adapter.CategoryAdapter;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.CategoryNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.CategoryViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class CategoryFragment extends BaseFragment<FragmentCategoryBinding, CategoryViewModel>
        implements CategoryNavigator, View.OnClickListener, CategoryAdapter.CategorySelectListener {
    private String TAG = "CategoryFragment";
    private FragmentCategoryBinding binding;

    @Inject
    CategoryViewModel mViewModel;
    private Context mContext;
    private Gson mGson;
    private List<CategoryDataModel> categoryList;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Integer> catList;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("category", new JSONArray(catList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("LIST : ", jsonObject.toString());
                updateCategory(jsonObject);
                break;
        }
    }

    private void updateCategory(JSONObject jsonObject) {
        mViewModel.updateCategory((Activity) mContext, jsonObject);
    }

    @Override
    public CategoryViewModel getMyViewModel() {
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
            setUpCategory();
            binding.swipe.setOnRefreshListener(this::fetchCategory);
            binding.btnSubmit.setOnClickListener(this);
        }
    }

    private void setUpCategory() {
        categoryList = new ArrayList<>();
        mGson = new Gson();
        binding.recyclerCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryAdapter = new CategoryAdapter(getActivity(), mViewModel, this);
        binding.recyclerCategory.setAdapter(categoryAdapter);

        fetchCategory();
    }

    private void fetchCategory() {
        mViewModel.fetchCategory((Activity) mContext);
    }

    @Override
    public int getBindingVariable() {
        return BR.categoryView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void showProgressBars() {
        showsLoading();
    }

    @Override
    public void checkInternetConnection(String message) {
        /*swipe off*/
        binding.swipe.setRefreshing(false);
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }

    @Override
    public void checkValidation(int errorCode, String message) {
        /*swipe off*/
        binding.swipe.setRefreshing(false);
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void throwable(Throwable throwable) {
        throwable.printStackTrace();
        /*swipe off*/
        binding.swipe.setRefreshing(false);
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessCategory(JsonObject jsonObject) {
        /*swipe off*/
        binding.swipe.setRefreshing(false);
        binding.progressBar.setVisibility(View.GONE);
        if (jsonObject != null) {
            if (jsonObject.get("success").getAsBoolean()) {
                mGson = new Gson();
                Type category = new TypeToken<List<CategoryDataModel>>() {
                }.getType();
                categoryList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), category);
                if (categoryList != null && categoryList.size() > 0) {
                    catList = new ArrayList<>();
                    for (CategoryDataModel model : categoryList) {
                        if (model.isStatus()) {
                            catList.add(model.getId());
                        }
                    }
                    categoryAdapter.setList(categoryList,catList);
                    binding.recyclerCategory.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerCategory.setVisibility(View.GONE);
                }
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessUpdateCategory(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                binding.btnSubmit.setVisibility(View.GONE);
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                fetchCategory();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void categorySelected(ArrayList<Integer> list) {
        catList = list;
        if (list.size() > 0) {
            binding.btnSubmit.setVisibility(View.VISIBLE);
        } else {
            binding.btnSubmit.setVisibility(View.GONE);
        }
    }
}