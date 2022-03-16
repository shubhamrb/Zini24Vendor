package com.mamits.zini24vendor.data.model.services;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ServiceDataModel implements Serializable {

    @SerializedName("id")
    int id;

    @SerializedName("product_type")
    int product_type;

    @SerializedName("product_id")
    int product_id;

    @SerializedName("name")
    String name;

    @SerializedName("category")
    CategoryDataModel category;

    @SerializedName("subcategory")
    SubCategoryDataModel subcategory;

    @SerializedName("price")
    String price;

    @SerializedName("admin_commission")
    String admin_commission;

    @SerializedName("variation")
    List<VariationDataModel> variation;

    @SerializedName("image")
    String image;

    public String getAdmin_commission() {
        return admin_commission;
    }

    public void setAdmin_commission(String admin_commission) {
        this.admin_commission = admin_commission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_type() {
        return product_type;
    }

    public void setProduct_type(int product_type) {
        this.product_type = product_type;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryDataModel getCategory() {
        return category;
    }

    public void setCategory(CategoryDataModel category) {
        this.category = category;
    }

    public SubCategoryDataModel getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(SubCategoryDataModel subcategory) {
        this.subcategory = subcategory;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<VariationDataModel> getVariation() {
        return variation;
    }

    public void setVariation(List<VariationDataModel> variation) {
        this.variation = variation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ServiceDataModel{" +
                "id=" + id +
                ", product_type=" + product_type +
                ", product_id=" + product_id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", subcategory=" + subcategory +
                ", price='" + price + '\'' +
                ", admin_commission='" + admin_commission + '\'' +
                ", variation=" + variation +
                ", image='" + image + '\'' +
                '}';
    }
}
