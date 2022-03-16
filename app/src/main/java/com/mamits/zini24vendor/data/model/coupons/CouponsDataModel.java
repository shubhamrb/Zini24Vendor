package com.mamits.zini24vendor.data.model.coupons;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CouponsDataModel implements Serializable {

    @SerializedName("id")
    int id;

    @SerializedName("coupon")
    String coupon;

    @SerializedName("discount_type")
    int discount_type;

    @SerializedName("discount_amount")
    String discount_amount;

    @SerializedName("description")
    String description;

    @SerializedName("from_date")
    String from_date;

    @SerializedName("to_date")
    String to_date;

    @SerializedName("min_amount")
    int min_amount;

    @SerializedName("max_amount")
    int max_amount;

    @SerializedName("image")
    String image;

    @SerializedName("usage_limit_per_coupon")
    int usage_limit_per_coupon;

    @SerializedName("usage_limit_per_user")
    int usage_limit_per_user;

    @SerializedName("createdBy")
    int createdBy;

    @SerializedName("IsActive")
    int IsActive;

    @SerializedName("created_at")
    String created_at;

    @SerializedName("updated_at")
    String updated_at;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public int getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(int discount_type) {
        this.discount_type = discount_type;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public int getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(int min_amount) {
        this.min_amount = min_amount;
    }

    public int getMax_amount() {
        return max_amount;
    }

    public void setMax_amount(int max_amount) {
        this.max_amount = max_amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUsage_limit_per_coupon() {
        return usage_limit_per_coupon;
    }

    public void setUsage_limit_per_coupon(int usage_limit_per_coupon) {
        this.usage_limit_per_coupon = usage_limit_per_coupon;
    }

    public int getUsage_limit_per_user() {
        return usage_limit_per_user;
    }

    public void setUsage_limit_per_user(int usage_limit_per_user) {
        this.usage_limit_per_user = usage_limit_per_user;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "CouponsDataModel{" +
                "id=" + id +
                ", coupon='" + coupon + '\'' +
                ", discount_type=" + discount_type +
                ", discount_amount='" + discount_amount + '\'' +
                ", description='" + description + '\'' +
                ", from_date='" + from_date + '\'' +
                ", to_date='" + to_date + '\'' +
                ", min_amount=" + min_amount +
                ", max_amount=" + max_amount +
                ", image='" + image + '\'' +
                ", usage_limit_per_coupon=" + usage_limit_per_coupon +
                ", usage_limit_per_user=" + usage_limit_per_user +
                ", createdBy=" + createdBy +
                ", IsActive=" + IsActive +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
