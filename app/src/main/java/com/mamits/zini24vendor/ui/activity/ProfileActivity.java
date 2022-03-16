package com.mamits.zini24vendor.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.profile.CityModel;
import com.mamits.zini24vendor.data.model.profile.StateModel;
import com.mamits.zini24vendor.data.model.services.CategoryDataModel;
import com.mamits.zini24vendor.databinding.ActivityProfileBinding;
import com.mamits.zini24vendor.ui.adapter.CityAdapter;
import com.mamits.zini24vendor.ui.adapter.StateAdapter;
import com.mamits.zini24vendor.ui.base.BaseActivity;
import com.mamits.zini24vendor.ui.navigator.activity.ProfileActivityNavigator;
import com.mamits.zini24vendor.ui.utils.commonClasses.CommonUtils;
import com.mamits.zini24vendor.ui.utils.commonClasses.TimePickerFragment;
import com.mamits.zini24vendor.viewmodel.activity.ProfileActivityViewModel;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import gun0912.tedimagepicker.builder.TedImagePicker;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding, ProfileActivityViewModel>
        implements ProfileActivityNavigator, View.OnClickListener, TimePickerFragment.onTimeListener, PickiTCallbacks {
    String TAG = "ProfileActivity";
    @Inject
    ProfileActivityViewModel mViewModel;
    ActivityProfileBinding binding;
    private int AUTOCOMPLETE_REQUEST_CODE = 1;
    private Gson mGson;
    private List<StateModel> stateCityList;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private PickiT pickiT;
    private File uploadedFile, ownerFile, shopFile, qrFile, banner1, banner2, banner3, banner4;
    private int IMAGE_TYPE = -1;
    private int selectedImageCount = 0, selectedState = -1, selectedCity = -1;
    private boolean isOnline, isShop, isUpi;
    private List<String> payModeArray, categoryArray;
    private String currentPhotoPath = "";
    private List<CategoryDataModel> categoryList;
    private String[] listItems = null;
    boolean[] checkedItems = null;
    List<String> selectedItems = null;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_address:
                initializeLocation();
                break;
            case R.id.tv_category:
                openCategoryList();
                break;
            case R.id.et_open_time:
                showTimePickerDialog(view, true);
                break;
            case R.id.et_close_time:
                showTimePickerDialog(view, false);
                break;
            case R.id.img_owner:
                if (checkPermission()) {
                    IMAGE_TYPE = 0;
                    openFileChooser();
                } else {
                    requestPermission();
                }
                break;
            case R.id.img_shop:
                if (checkPermission()) {
                    IMAGE_TYPE = 1;
                    openFileChooser();
                } else {
                    requestPermission();
                }
                break;
            case R.id.img_qr:
                if (checkPermission()) {
                    IMAGE_TYPE = 2;
                    openFileChooser();
                } else {
                    requestPermission();
                }
                break;
            case R.id.img_banner1:
                if (checkPermission()) {
                    IMAGE_TYPE = 3;
                    openFileChooser();
                } else {
                    requestPermission();
                }
                break;
            case R.id.img_banner2:
                if (checkPermission()) {
                    IMAGE_TYPE = 4;
                    openFileChooser();
                } else {
                    requestPermission();
                }
                break;
            case R.id.img_banner3:
                if (checkPermission()) {
                    IMAGE_TYPE = 5;
                    openFileChooser();
                } else {
                    requestPermission();
                }
                break;
            case R.id.img_banner4:
                if (checkPermission()) {
                    IMAGE_TYPE = 6;
                    openFileChooser();
                } else {
                    requestPermission();
                }
                break;
            case R.id.btn_submit:
                submitProfile();
                break;
        }
    }

    private void openCategoryList() {
        // initially set the null for the text preview
        binding.tvCategory.setText(null);

        // initialise the alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the title for the alert dialog
        builder.setTitle("Choose category");

        // set the icon for the alert dialog
        builder.setIcon(R.drawable.logo);

        // now this is the function which sets the alert dialog for multiple item selection ready
        builder.setMultiChoiceItems(listItems, checkedItems, (dialog, which, isChecked) -> {
            checkedItems[which] = isChecked;
            String currentItem = selectedItems.get(which);
        });

        // alert dialog shouldn't be cancellable
        builder.setCancelable(false);

        // handle the positive button of the dialog
        builder.setPositiveButton("Done", (dialog, which) -> {
            for (int i = 0; i < checkedItems.length; i++) {
                if (checkedItems[i]) {
                    binding.tvCategory.setText(String.format("%s%s, ", binding.tvCategory.getText(), selectedItems.get(i)));
                    categoryArray.add(String.valueOf(categoryList.get(i).getId()));
                }
            }
        });

        // handle the negative button of the alert dialog
        builder.setNegativeButton("CANCEL", (dialog, which) -> {

        });

        // handle the neutral button of the dialog to clear
        // the selected items boolean checkedItem
        builder.setNeutralButton("CLEAR ALL", (dialog, which) -> {
            Arrays.fill(checkedItems, false);
        });

        // create the builder
        builder.create();

        // create the alert dialog with the
        // alert dialog builder instance
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void submitProfile() {
        String shop_name = binding.etShopName.getText().toString();
        String owner_name = binding.etOwnerName.getText().toString();
        String et_email = binding.etEmail.getText().toString();
        String et_number = binding.etNumber.getText().toString();
        String et_address = binding.etAddress.getText().toString();
        String et_desc = binding.etDescription.getText().toString();

        String tv_lat = binding.etLat.getText().toString();
        String tv_lng = binding.etLong.getText().toString();
        String et_zipcode = binding.etZipcode.getText().toString();
        String et_open = binding.etOpenTime.getText().toString();
        String et_close = binding.etCloseTime.getText().toString();
        String et_pin = binding.etPin.getText().toString();
        String et_upi = binding.etUpi.getText().toString();
        String et_whatsapp = binding.etWhatsapp.getText().toString();
        String et_account = binding.etAccount.getText().toString();
        String et_bank = binding.etBank.getText().toString();
        String et_ifsc = binding.etIfsc.getText().toString();

        if (categoryArray != null && categoryArray.isEmpty()) {
            Toast.makeText(this, "Please select category.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (shop_name.isEmpty()) {
            Toast.makeText(this, "Please enter your shop name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (owner_name.isEmpty()) {
            Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_email.isEmpty()) {
            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CommonUtils.isEmailValid(et_email)) {
            Toast.makeText(this, "Please enter valid email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_number.isEmpty()) {
            Toast.makeText(this, "Please enter your contact number.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_number.length() != 10) {
            Toast.makeText(this, "Please enter valid contact number.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_address.isEmpty()) {
            Toast.makeText(this, "Please enter your address.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedState == -1) {
            Toast.makeText(this, "Please select your state.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedCity == -1) {
            Toast.makeText(this, "Please select your city.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tv_lat.isEmpty()) {
            Toast.makeText(this, "We couldn't find your latitude.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tv_lng.isEmpty()) {
            Toast.makeText(this, "We couldn't find your longitude.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_zipcode.isEmpty()) {
            Toast.makeText(this, "Please select your zipcode.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_open.isEmpty()) {
            Toast.makeText(this, "Please enter shop open time.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_close.isEmpty()) {
            Toast.makeText(this, "Please enter shop close time.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (payModeArray != null && payModeArray.size() == 0) {
            Toast.makeText(this, "Please select at least one payment mode.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isUpi && qrFile == null) {
            Toast.makeText(this, "Please select the QR image.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_desc.isEmpty()) {
            Toast.makeText(this, "Please enter the description.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isUpi && et_upi.isEmpty()) {
            Toast.makeText(this, "Please enter your UPI.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_whatsapp.isEmpty()) {
            Toast.makeText(this, "Please enter your WhatsApp number.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (et_whatsapp.length() != 10) {
            Toast.makeText(this, "Please enter valid WhatsApp number.", Toast.LENGTH_SHORT).show();
            return;
        }

        mViewModel.submitProfile(this, categoryArray, shop_name, owner_name, et_email, et_number
                , et_address, selectedState, selectedCity, tv_lat, tv_lng, et_zipcode, et_open
                , et_close, payModeArray, ownerFile, shopFile, banner1, banner2, banner3, banner4, et_desc
                , qrFile, et_upi, et_whatsapp, et_account, et_bank, et_ifsc);

    }

    /*open file chooser*/
    private void openFileChooser() {
        /*Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        chooserIntent.putExtra(Intent.EXTRA_INTENT, intent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose an image");

        someActivityResultLauncher.launch(chooserIntent);*/

        TedImagePicker.with(this)
                .max(1,"Max limit is 1")
                .start(sourceUri -> {
                    File file = getImageFile(); // 2
                    Uri destinationUri = Uri.fromFile(file);  // 3
                    openCropActivity(sourceUri, destinationUri);  // 4
                });

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED;
    }

    public void showTimePickerDialog(View v, boolean isOpenTime) {
        DialogFragment newFragment = new TimePickerFragment(isOpenTime, this);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void initializeLocation() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    if (place.getLatLng() != null) {
                        binding.etAddress.setText(place.getName());
                        binding.etLat.setText(String.valueOf(place.getLatLng().latitude));
                        binding.etLong.setText(String.valueOf(place.getLatLng().longitude));
                    }
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = UCrop.getOutput(data);
                pickiT.getPath(uri, Build.VERSION.SDK_INT);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int getBindingVariable() {
        return BR.profileActivityView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);

        binding.etAddress.setOnClickListener(this);
        binding.etOpenTime.setOnClickListener(this);
        binding.etCloseTime.setOnClickListener(this);
        binding.imgOwner.setOnClickListener(this);
        binding.imgShop.setOnClickListener(this);
        binding.imgBanner1.setOnClickListener(this);
        binding.imgBanner2.setOnClickListener(this);
        binding.imgBanner3.setOnClickListener(this);
        binding.imgBanner4.setOnClickListener(this);
        binding.imgQr.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        binding.tvCategory.setOnClickListener(this);

        payModeArray = new ArrayList<>();
        categoryArray = new ArrayList<>();
        getStateCity();
        fetchCategory();
        binding.spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos > 0) {
                    selectedState = stateCityList.get(pos).getId();

                    CityModel model = new CityModel(-1, "Select City");
                    List<CityModel> cityModelList = stateCityList.get(pos).getCity();
                    cityModelList.add(0, model);

                    CityAdapter adapter = new CityAdapter(ProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, cityModelList);
                    binding.spinnerCity.setAdapter(adapter);

                } else {
                    selectedState = -1;
                    selectedCity = -1;
                    CityAdapter adapter = new CityAdapter(ProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, new ArrayList<>());
                    binding.spinnerCity.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                if (pos > 0) {
                    selectedCity = stateCityList.get(binding.spinnerState.getSelectedItemPosition()).getCity().get(pos).getId();
                } else {
                    selectedCity = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            Intent data = result.getData();

                            if (data != null) {
                                if (data.getClipData() != null) {
                                    selectedImageCount = data.getClipData().getItemCount();
                                    if (selectedImageCount > 3) {
                                        Toast.makeText(this, "You can not select more than 3 images.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    for (int i = 0; i < selectedImageCount; i++) {
                                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                        pickiT.getPath(imageUri, Build.VERSION.SDK_INT);
                                    }
                                } else if (data.getData() != null) {
                                    Uri sourceUri = data.getData();
                                    File file = getImageFile(); // 2
                                    Uri destinationUri = Uri.fromFile(file);  // 3
                                    openCropActivity(sourceUri, destinationUri);  // 4

                                }

                            } else {
                                IMAGE_TYPE = -1;
                                Log.e(TAG, "Data is null");
                            }
                        } catch (Exception e) {
                            IMAGE_TYPE = -1;
                            e.printStackTrace();
                        }
                    } else {
                        IMAGE_TYPE = -1;
                    }
                });
        pickiT = new PickiT(this, this, this);

        binding.btnOnline.setOnClickListener(view -> {
            if (isOnline) {
                payModeArray.remove("Online");
                binding.btnOnline.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                binding.btnOnline.setTextColor(getResources().getColor(R.color.black));
            } else {
                payModeArray.add("Online");
                binding.btnOnline.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                binding.btnOnline.setTextColor(getResources().getColor(R.color.white));
            }
            isOnline = !isOnline;
        });
        binding.btnShop.setOnClickListener(view -> {
            if (isShop) {
                payModeArray.remove("Pay on Shop");
                binding.btnShop.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                binding.btnShop.setTextColor(getResources().getColor(R.color.black));
            } else {
                payModeArray.add("Pay on Shop");
                binding.btnShop.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                binding.btnShop.setTextColor(getResources().getColor(R.color.white));
            }
            isShop = !isShop;
        });
        binding.btnUpi.setOnClickListener(view -> {
            if (isUpi) {
                payModeArray.remove("upi");
                binding.btnUpi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                binding.btnUpi.setTextColor(getResources().getColor(R.color.black));
            } else {
                payModeArray.add("upi");
                binding.btnUpi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                binding.btnUpi.setTextColor(getResources().getColor(R.color.white));
            }
            isUpi = !isUpi;
        });
    }

    private void fetchCategory() {
        mViewModel.fetchCategory(this);
    }

    private File getImageFile() {
        String imageFileName = "JPEG_" + System.currentTimeMillis() + "_";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM
                ), "Camera"
        );
        File file = null;
        try {
            file = File.createTempFile(
                    imageFileName, ".jpg", storageDir
            );
            currentPhotoPath = "file:" + file.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void openCropActivity(Uri sourceUri, Uri destinationUri) {
        UCrop.of(sourceUri, destinationUri)
                .withMaxResultSize(100, 100)
                .withAspectRatio(5f, 5f)
                .start(this);
    }


    private void getStateCity() {
        mViewModel.fetchStateCity(this);
    }

    @Override
    protected ProfileActivityViewModel getMyViewModel() {
        return mViewModel;
    }

    @Override
    public void showLoader() {
        showLoading();
    }

    @Override
    public void hideLoader() {
        hideLoading();
    }

    @Override
    public void checkValidation(int type, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void throwable(Throwable it) {
        it.printStackTrace();
    }

    @Override
    public void checkInternetConnection(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessStateCity(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type orderDataList = new TypeToken<List<StateModel>>() {
                }.getType();
                stateCityList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), orderDataList);
                StateModel model = new StateModel(-1, "Select State");
                stateCityList.add(0, model);
                StateAdapter adapter = new StateAdapter(this, R.layout.support_simple_spinner_dropdown_item, stateCityList);
                binding.spinnerState.setAdapter(adapter);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessCategory(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("success").getAsBoolean()) {
                mGson = new Gson();
                Type category = new TypeToken<List<CategoryDataModel>>() {
                }.getType();
                categoryList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), category);

                if (categoryList != null && categoryList.size() > 0) {
                    listItems = new String[categoryList.size()];
                    for (int i = 0; i < categoryList.size(); i++) {
                        listItems[i] = categoryList.get(i).getName();
                    }

                    checkedItems = new boolean[listItems.length];
                    selectedItems = Arrays.asList(listItems);
                }
            } else {
                String message = jsonObject.get("message").getAsString();
            }
        }

    }

    @Override
    public void onSuccessProfileUpdated(JsonObject jsonObject) {
        if (jsonObject != null) {
            String message = jsonObject.get("message").getAsString();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            mViewModel.getmDataManger().settProfileStatus(1);
            startActivity(new Intent(this, DashboardActivity.class));
            finishAffinity();
        }
    }

    @Override
    public void onOpeningTimeSelect(int hr, int min) {
        binding.etOpenTime.setText(String.format(Locale.getDefault(), "%d:%d", hr, min));
    }

    @Override
    public void onClosingTimeSelect(int hr, int min) {
        binding.etCloseTime.setText(String.format(Locale.getDefault(), "%d:%d", hr, min));
    }

    @Override
    public void PickiTonUriReturned() {
        mViewModel.getmNavigator().get().showLoader();
    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonCompleteListener(String finalFilePath, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        mViewModel.getmNavigator().get().hideLoader();
        if (wasSuccessful) {
            if (finalFilePath != null) {
                uploadedFile = new File(finalFilePath);

                long fileSizeInBytes = uploadedFile.length();
                long fileSizeInKB = fileSizeInBytes / 1024;
                long fileSizeInMB = fileSizeInKB / 1024;

                if (fileSizeInMB > 5) {
                    IMAGE_TYPE = -1;
                    Toast.makeText(this, "File size is too big. (Max : 5 MB)", Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (IMAGE_TYPE) {
                    case 0:
                        Glide.with(this).load(uploadedFile).placeholder(R.drawable.add_photo).error(R.drawable.add_photo).into(binding.imgOwner);
                        ownerFile = uploadedFile;
                        IMAGE_TYPE = -1;
                        break;
                    case 1:
                        Glide.with(this).load(uploadedFile).placeholder(R.drawable.add_photo).error(R.drawable.add_photo).into(binding.imgShop);
                        shopFile = uploadedFile;
                        IMAGE_TYPE = -1;
                        break;
                    case 2:
                        Glide.with(this).load(uploadedFile).placeholder(R.drawable.add_photo).error(R.drawable.add_photo).into(binding.imgQr);
                        qrFile = uploadedFile;
                        IMAGE_TYPE = -1;
                        break;
                    case 3:
                        Glide.with(this).load(uploadedFile).placeholder(R.drawable.add_photo).error(R.drawable.add_photo).into(binding.imgBanner1);
                        banner1 = uploadedFile;
                        IMAGE_TYPE = -1;
                        break;
                    case 4:
                        Glide.with(this).load(uploadedFile).placeholder(R.drawable.add_photo).error(R.drawable.add_photo).into(binding.imgBanner2);
                        banner2 = uploadedFile;
                        IMAGE_TYPE = -1;
                        break;
                    case 5:
                        Glide.with(this).load(uploadedFile).placeholder(R.drawable.add_photo).error(R.drawable.add_photo).into(binding.imgBanner3);
                        banner3 = uploadedFile;
                        IMAGE_TYPE = -1;
                        break;
                    case 6:
                        Glide.with(this).load(uploadedFile).placeholder(R.drawable.add_photo).error(R.drawable.add_photo).into(binding.imgBanner4);
                        banner4 = uploadedFile;
                        IMAGE_TYPE = -1;
                        break;
                }

            } else {
                IMAGE_TYPE = -1;
                Log.e(TAG, "filename is null");
            }
        } else {
            IMAGE_TYPE = -1;
            Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }
}