package com.mamits.zini24vendor.ui.utils.constants;

import com.mamits.zini24vendor.BuildConfig;

public class ApiConstant {
    public static final String LOGIN_END_POINT = BuildConfig.BASE_URL + "api/vendorsignin";
    public static final String HOME_DATA_END_POINT = BuildConfig.BASE_URL + "api/auth/vendordashboard";
    public static final String PAYMENT_KEYS_END_POINT = BuildConfig.BASE_URL + "api/getSetting";
    public static final String FETCH_CFSTOKEN_END_POINT = BuildConfig.BASE_URL + "api/auth/cashfreetoken";
    public static final String FETCH_PAYTM_TOKEN_END_POINT = BuildConfig.BASE_URL + "api/auth/paytmtoken";
    public static final String ORDERS_END_POINT = BuildConfig.BASE_URL + "api/auth/vendorOrder";
    public static final String STATE_CITY_END_POINT = BuildConfig.BASE_URL + "api/statecity";
    public static final String PAYMENTS_END_POINT = BuildConfig.BASE_URL + "api/auth/paymenthistory";
    public static final String TRANSACTIONS_END_POINT = BuildConfig.BASE_URL + "api/auth/transactionHistory";
    public static final String HELP_END_POINT = BuildConfig.BASE_URL + "api/auth/helpandsupport";
    public static final String SEND_OTP_END_POINT = BuildConfig.BASE_URL + "api/sendOtp";
    public static final String SIGNUP_END_POINT = BuildConfig.BASE_URL + "api/signup";
    public static final String REGISTRATION_END_POINT = BuildConfig.BASE_URL + "api/vendorsignup";
    public static final String VERIFY_OTP_END_POINT = BuildConfig.BASE_URL + "api/forgotpassword";
    public static final String UPDATE_PIN_END_POINT = BuildConfig.BASE_URL + "api/updatepassword";
    public static final String UPDATE_ORDER_STATUS_END_POINT = BuildConfig.BASE_URL + "api/auth/updateOrderStatus";
    public static final String CHECK_PAYMENT_STATUS_END_POINT = BuildConfig.BASE_URL + "api/auth/checkpaymentstatus";
    public static final String FETCH_MESSAGES_END_POINT = BuildConfig.BASE_URL + "api/auth/getLatestMessage";
    public static final String SEND_MESSAGE_END_POINT = BuildConfig.BASE_URL + "api/auth/saveChatMessage";
    public static final String FETCH_COUPONS_END_POINT = BuildConfig.BASE_URL + "api/auth/getCoupon";
    public static final String CREATE_COUPON_END_POINT = BuildConfig.BASE_URL + "api/auth/addCoupon";
    public static final String FETCH_SERVICES_END_POINT = BuildConfig.BASE_URL + "api/auth/getInventoryList";
    public static final String DELETE_COUPON_END_POINT = BuildConfig.BASE_URL + "api/auth/deleteCoupon";
    public static final String DELETE_SERVICE_END_POINT = BuildConfig.BASE_URL + "api/auth/deleteStock";
    public static final String FETCH_CAT_SUB_CATEGORY_END_POINT = BuildConfig.BASE_URL + "api/auth/getStoreCategory";
    public static final String FETCH_PRODUCTS_END_POINT = BuildConfig.BASE_URL + "api/auth/getProduct";
    public static final String ADD_SERVICE_END_POINT = BuildConfig.BASE_URL + "api/auth/addStock";
    public static final String UPDATE_SERVICE_END_POINT = BuildConfig.BASE_URL + "api/auth/updateStock";
    public static final String CHANGE_PASSWORD_END_POINT = BuildConfig.BASE_URL + "api/auth/changePassword";
    public static final String COMPLETE_ORDER_END_POINT = BuildConfig.BASE_URL + "api/auth/orderComplete";
    public static final String SUBMIT_PROFILE_END_POINT = BuildConfig.BASE_URL + "api/auth/updatevendorprofile";
    public static final String UPDATE_CATEGORY_END_POINT = BuildConfig.BASE_URL + "api/auth/updateCategory";
    public static final String ALL_CATEGORY_END_POINT = BuildConfig.BASE_URL + "api/auth/getVendorCategory";
    public static final String STORE_OPEN_END_POINT = BuildConfig.BASE_URL + "api/auth/storeStatusUpdate";
    public static final String FETCH_VENDOR_PROFILE_END_POINT = BuildConfig.BASE_URL + "api/auth/vendorProfile";
    public static final String SAVE_PAYMENT_RESPONSE_END_POINT = BuildConfig.BASE_URL + "api/auth/vendorpaymentdone";
}
