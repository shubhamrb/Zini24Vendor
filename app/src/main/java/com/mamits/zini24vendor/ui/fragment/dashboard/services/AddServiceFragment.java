package com.mamits.zini24vendor.ui.fragment.dashboard.services;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.services.CategoryDataModel;
import com.mamits.zini24vendor.data.model.services.ProductDataModel;
import com.mamits.zini24vendor.data.model.services.ServiceDataModel;
import com.mamits.zini24vendor.data.model.services.SubCategoryDataModel;
import com.mamits.zini24vendor.data.model.services.VariationDataModel;
import com.mamits.zini24vendor.databinding.FragmentAddServiceBinding;
import com.mamits.zini24vendor.ui.activity.DashboardActivity;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.AddServiceNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.AddServiceViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class AddServiceFragment extends BaseFragment<FragmentAddServiceBinding, AddServiceViewModel>
        implements AddServiceNavigator, View.OnClickListener {

    private String TAG = "AddServiceFragment";
    private FragmentAddServiceBinding binding;

    @Inject
    AddServiceViewModel mViewModel;
    private Context mContext;
    private List<CategoryDataModel> catSubCategoryList;
    private List<ProductDataModel> productsList;
    private Gson mGson;
    private ArrayAdapter catAdapter, subAdapter, servicesAdapter;
    private String selected_cat = "Select Category", selected_sub_cat = "Select Subcategory", selected_service = "Select Services";
    private JSONObject finalObject = null;
    private ServiceDataModel model;
    private boolean isUpdate = false;
    private String inventoryId = "";
    private List<VariationDataModel> variationList;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                String price = binding.etPrice.getText().toString();

                if (selected_cat.equals("Select Category")
                        || selected_sub_cat.equals("Select Subcategory")
                        || selected_service.equals("Select Services")) {
                    Toast.makeText(mContext, "Please enter all required details (*).", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (price.trim().length() == 0) {
                    price = "0.00";
                }
                try {
                    finalObject.put("price", price);

                    JSONArray jsonArray;
                    if (variationList.size() == 0) {
                        jsonArray = new JSONArray();
                    } else {
                        String var1 = binding.etVar1.getText().toString();
                        if (var1.trim().length() == 0) {
                            var1 = null;
                        }
                        variationList.get(0).setValue(var1);

                        if (variationList.size() == 2) {
                            String var2 = binding.etVar2.getText().toString();
                            if (var2.trim().length() == 0) {
                                var2 = null;
                            }
                            variationList.get(1).setValue(var2);
                        }

                        Gson gson = new GsonBuilder().serializeNulls().create();
                        String listString = gson.toJson(
                                variationList,
                                new TypeToken<List<VariationDataModel>>() {
                                }.getType());

                        jsonArray = new JSONArray(listString);
                    }

                    finalObject.put("variation", jsonArray);

                    if (isUpdate) {
                        finalObject.put("inventoryid", inventoryId);
                        updateService(finalObject);
                    } else {
                        addService(finalObject);
                    }
                    Log.e(TAG, "JSON : " + finalObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void updateService(JSONObject finalObject) {
        mViewModel.updateService((Activity) mContext, finalObject);
    }

    private void addService(JSONObject productDataModel) {
        mViewModel.addService((Activity) mContext, productDataModel);
    }

    @Override
    public AddServiceViewModel getMyViewModel() {
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
            Bundle bundle = getArguments();
            if (bundle != null) {
                model = (ServiceDataModel) bundle.getSerializable("model");
                inventoryId = String.valueOf(model.getId());
                isUpdate = true;
                binding.txtH1.setText("Edit Service");
                binding.btnSubmit.setText("Update");
            } else {
                binding.txtH1.setText("Add Service");
                binding.btnSubmit.setText("Submit");
            }
            loadCategorySubCategory();
            binding.btnSubmit.setOnClickListener(this);
        }
    }

    private void loadCategorySubCategory() {
        catSubCategoryList = new ArrayList<>();
        mViewModel.fetchCategorySubCategory((Activity) mContext);
    }

    @Override
    public int getBindingVariable() {
        return BR.addServiceView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_service;
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
    public void onSuccessServiceAdded(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("success").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                int messageId = jsonObject.get("messageId").getAsInt();
                if (messageId == 204) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Already Added")
                            .setMessage(message)
                            .setPositiveButton("View", (dialog, which) -> {
                                try {
                                    ServiceDataModel model = mGson.fromJson(jsonObject.get("data").getAsJsonObject().toString(), ServiceDataModel.class);
                                    goToEditService(model);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            })

                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(((DashboardActivity) mContext).findViewById(R.id.nav_host_fragment)).navigateUp();

                }
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goToEditService(ServiceDataModel model) {
        try {
            Bundle bundle = new Bundle();
            bundle.putSerializable("model", model);
            NavOptions options = new NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_out_right)
                    .setExitAnim(R.anim.slide_in).setPopEnterAnim(0).setPopExitAnim(R.anim.slide_out1)
                    .build();
            NavController navController = Navigation.findNavController(((DashboardActivity) mContext).findViewById(R.id.nav_host_fragment));
            navController.popBackStack(R.id.nav_add_service, true);
            navController.navigate(R.id.nav_add_service, bundle, options);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessCatSubCat(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("success").getAsBoolean()) {
                mGson = new Gson();
                Type category = new TypeToken<List<CategoryDataModel>>() {
                }.getType();
                catSubCategoryList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), category);
                fillSpinners();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessProducts(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("success").getAsBoolean()) {
                mGson = new Gson();
                Type products = new TypeToken<List<ProductDataModel>>() {
                }.getType();
                productsList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), products);

                if (productsList.size() == 0) {
                    variationList = new ArrayList<>();
                    binding.rlVar1.setVisibility(View.GONE);
                    binding.rlVar2.setVisibility(View.GONE);
                    binding.rlPrice.setVisibility(View.GONE);
                    binding.btnSubmit.setVisibility(View.GONE);
                } else {
                    binding.btnSubmit.setVisibility(View.VISIBLE);
                }
                fillServicesSpinner();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fillSpinners() {
        List<String> categoryList = new ArrayList<>();
        categoryList.add("Select Category");
        List<String> subCategoryList = new ArrayList<>();
        subCategoryList.add("Select Subcategory");

        for (CategoryDataModel model : catSubCategoryList) {
            categoryList.add(model.getName());
        }

        /*category*/
        catAdapter = new ArrayAdapter(mContext, R.layout.spinner_layout, categoryList);
        binding.spinnerCategory.setAdapter(catAdapter);

        /*sub category*/
        subAdapter = new ArrayAdapter(mContext, R.layout.spinner_layout, subCategoryList);
        binding.spinnerSubcategory.setAdapter(subAdapter);

        if (model != null) {
            binding.spinnerCategory.setSelection(categoryList.indexOf(model.getCategory().getName()));

            String catname = model.getCategory().getName();
            int index = categoryList.indexOf(catname);
            for (SubCategoryDataModel model : catSubCategoryList.get(index - 1).getSubcategory()) {
                subCategoryList.add(model.getName());
            }

        }

        binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    subCategoryList.clear();
                    subCategoryList.add("Select Subcategory");
                    if (i > 0) {
                        selected_cat = String.valueOf(catSubCategoryList.get(i - 1).getId());

                        for (SubCategoryDataModel model : catSubCategoryList.get(i - 1).getSubcategory()) {
                            subCategoryList.add(model.getName());
                        }

                    } else {
                        selected_cat = "Select Category";
                    }
                    if (model != null) {
                        binding.spinnerSubcategory.setSelection(subCategoryList.indexOf(model.getSubcategory().getName()));
                    } else {
                        binding.spinnerSubcategory.setSelection(0);
                    }

                    if (!selected_cat.equals("Select Category") && !selected_sub_cat.equals("Select Subcategory")) {
                        fetchProducts();
                    } else {
                        if (servicesAdapter != null) {
                            servicesAdapter.clear();
                            servicesAdapter.notifyDataSetChanged();
                            finalObject = null;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.spinnerSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i > 0) {
                        selected_sub_cat = String.valueOf(catSubCategoryList.get(binding.spinnerCategory.getSelectedItemPosition() - 1).getSubcategory().get(i - 1).getId());
                    } else {
                        selected_sub_cat = "Select Subcategory";
                    }

                    if (!selected_cat.equals("Select Category") && !selected_sub_cat.equals("Select Subcategory")) {
                        fetchProducts();
                    } else {
                        if (servicesAdapter != null) {
                            servicesAdapter.clear();
                            servicesAdapter.notifyDataSetChanged();
                            finalObject = null;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void fetchProducts() {
        mViewModel.fetchProducts((Activity) mContext, selected_cat, selected_sub_cat);
    }

    private void fillServicesSpinner() {
        List<String> servicesList = new ArrayList<>();
        /*services*/
        for (ProductDataModel model : productsList) {
            servicesList.add(model.getName());
        }
        servicesAdapter = new ArrayAdapter(mContext, R.layout.spinner_layout, servicesList);
        binding.spinnerServices.setAdapter(servicesAdapter);
        if (model != null) {
            binding.spinnerServices.setSelection(servicesList.indexOf(model.getName()));
        }
        binding.spinnerServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    selected_service = String.valueOf(productsList.get(i).getId());

                    finalObject = new JSONObject();
                    finalObject.put("category", productsList.get(i).getCategory_id());
                    finalObject.put("subcategory", String.valueOf(productsList.get(i).getSub_category_id()));
                    finalObject.put("product", String.valueOf(productsList.get(i).getId()));
                    finalObject.put("product_type", String.valueOf(productsList.get(i).getProduct_type()));


                    if (productsList.get(i).getProduct_type() == 0) {
                        variationList = new ArrayList<>();
                        binding.rlVar1.setVisibility(View.GONE);
                        binding.rlVar2.setVisibility(View.GONE);

                        if (model != null && model.getPrice() != null) {
                            double finalPrice = Double.parseDouble(model.getPrice()) - Double.parseDouble(model.getAdmin_commission());
                            binding.etPrice.setText("" + finalPrice);
                        }
                        binding.rlPrice.setVisibility(View.VISIBLE);
                    } else {
                        if (model != null) {
                            variationList = model.getVariation();
                        } else {
                            variationList = productsList.get(i).getVariation();
                        }
                        binding.rlPrice.setVisibility(View.GONE);
                        setUpVariations(variationList);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if (productsList.size() == 0) {
            setUpVariations(variationList);
        }
    }

    private void setUpVariations(List<VariationDataModel> list) {
        if (list.size() != 0) {
            binding.rlVar1.setVisibility(View.GONE);
            binding.rlVar2.setVisibility(View.GONE);

            VariationDataModel var = list.get(0);
            String name = var.getName();
            if (name != null) {
                binding.labelVar1.setText(name);
            } else {
                binding.labelVar1.setText("null");
            }
            String value = var.getValue();
            if (value != null) {
                binding.etVar1.setText(value);
            } else {
                binding.etVar1.setHint(binding.labelVar1.getText().toString());
            }

            binding.rlVar1.setVisibility(View.VISIBLE);

            if (list.size() == 2) {
                VariationDataModel var2 = list.get(1);
                String name2 = var2.getName();
                if (name2 != null) {
                    binding.labelVar2.setText(name2);
                } else {
                    binding.labelVar2.setText("null");
                }

                String value2 = var2.getValue();
                if (value2 != null) {
                    binding.etVar2.setText(value2);
                } else {
                    binding.etVar2.setHint(binding.labelVar2.getText().toString());
                }
                binding.rlVar2.setVisibility(View.VISIBLE);
            }


        } else {
            binding.rlVar1.setVisibility(View.GONE);
            binding.rlVar2.setVisibility(View.GONE);
        }

        model = null;
    }
}