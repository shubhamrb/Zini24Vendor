package com.mamits.zini24vendor.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HomeDataModel implements Serializable {

    @SerializedName("totalorder")
    private int totalorder;

    @SerializedName("totalaccept")
    private int totalaccept;

    @SerializedName("totalreject")
    private int totalreject;

    @SerializedName("totalpending")
    private int totalpending;

    @SerializedName("totalcancel")
    private int totalcancel;

    @SerializedName("totalcomplete")
    private int totalcomplete;

    @SerializedName("paytoadmin")
    private double paytoadmin;

    public double getPaytoadmin() {
        return paytoadmin;
    }

    public void setPaytoadmin(double paytoadmin) {
        this.paytoadmin = paytoadmin;
    }

    public int getTotalorder() {
        return totalorder;
    }

    public void setTotalorder(int totalorder) {
        this.totalorder = totalorder;
    }

    public int getTotalaccept() {
        return totalaccept;
    }

    public void setTotalaccept(int totalaccept) {
        this.totalaccept = totalaccept;
    }

    public int getTotalreject() {
        return totalreject;
    }

    public void setTotalreject(int totalreject) {
        this.totalreject = totalreject;
    }

    public int getTotalpending() {
        return totalpending;
    }

    public void setTotalpending(int totalpending) {
        this.totalpending = totalpending;
    }

    public int getTotalcancel() {
        return totalcancel;
    }

    public void setTotalcancel(int totalcancel) {
        this.totalcancel = totalcancel;
    }

    public int getTotalcomplete() {
        return totalcomplete;
    }

    public void setTotalcomplete(int totalcomplete) {
        this.totalcomplete = totalcomplete;
    }

    @Override
    public String toString() {
        return "HomeDataModel{" +
                "totalorder=" + totalorder +
                ", totalaccept=" + totalaccept +
                ", totalreject=" + totalreject +
                ", totalpending=" + totalpending +
                ", totalcancel=" + totalcancel +
                ", totalcomplete=" + totalcomplete +
                ", paytoadmin=" + paytoadmin +
                '}';
    }
}
