package com.mamits.zini24vendor.data.model.services;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductDataModel implements Serializable {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("category_id")
    String category_id;

    @SerializedName("sub_category_id")
    int sub_category_id;

    @SerializedName("image")
    String image;

    @SerializedName("product_type")
    int product_type;

    @SerializedName("price")
    String price;

    @SerializedName("variation")
    List<VariationDataModel> variation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getProduct_type() {
        return product_type;
    }

    public void setProduct_type(int product_type) {
        this.product_type = product_type;
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

    @Override
    public String toString() {
        return "ProductDataModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category_id='" + category_id + '\'' +
                ", sub_category_id=" + sub_category_id +
                ", image='" + image + '\'' +
                ", product_type=" + product_type +
                ", price='" + price + '\'' +
                ", variation=" + variation +
                '}';
    }
}
