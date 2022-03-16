package com.mamits.zini24vendor.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.coupons.CouponsDataModel;
import com.mamits.zini24vendor.ui.customviews.CustomTextView;
import com.mamits.zini24vendor.ui.utils.DateConvertor;
import com.mamits.zini24vendor.viewmodel.fragment.CouponViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.CouponsViewHolder> {

    private Context mContext;
    public List<CouponsDataModel> list;
    private CouponViewModel mViewModel;
    private Activity activity;
    private deleteListener listener;

    public CouponsAdapter(Context mContex, CouponViewModel mViewModel, deleteListener listener) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = new ArrayList<>();
        this.mViewModel = mViewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CouponsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.coupons_list_item, parent, false);
        return new CouponsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponsViewHolder holder, int position) {
        if (list.size() > 0) {
            CouponsDataModel model = list.get(position);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date d1 = formatter.parse(model.getFrom_date());
                Date d2 = formatter.parse(model.getTo_date());
                String f_date = new DateConvertor().getDate(d1.getTime(), DateConvertor.FORMAT_dd_MM_yyyy);
                String t_date = new DateConvertor().getDate(d2.getTime(), DateConvertor.FORMAT_dd_MM_yyyy);
                holder.txt_date.setText(String.format("%s - %s", f_date, t_date));

            } catch (Exception e) {
                holder.txt_date.setText(String.format("%s - %s", model.getFrom_date(), model.getTo_date()));
                e.printStackTrace();
            }

            holder.txt_discount_amount.setText(String.format("%s %s", model.getDiscount_amount(), model.getDiscount_type() == 1 ? "Flat" : "%"));
            holder.txt_coupon.setText(model.getCoupon());
            holder.txt_disc.setText(String.format(Locale.getDefault(), "Per user - %d   |   Per coupon - %d", model.getUsage_limit_per_user(), model.getUsage_limit_per_coupon()));
            switch (model.getIsActive()) {
                case 0:
                    holder.txt_status.setText("Inactive");
                    holder.txt_status.setTextColor(mContext.getResources().getColor(R.color.red_ff2502));
                    break;
                case 1:
                    holder.txt_status.setText("Active");
                    holder.txt_status.setTextColor(mContext.getResources().getColor(R.color.green_39ae00));
                    break;
            }
            holder.txt_min_max_price.setText(String.format(Locale.getDefault(), "₹ min %d - max %d", model.getMin_amount(), model.getMax_amount()));

            holder.btn_delete.setOnClickListener(v -> {
                listener.onDeleteCoupon(String.valueOf(model.getId()));
            });
        }
    }

    public interface deleteListener {
        void onDeleteCoupon(String couponid);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CouponsDataModel> orderList) {
        list = orderList;
        notifyDataSetChanged();
    }

    public static class CouponsViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_date, txt_discount_amount, txt_coupon, txt_disc, txt_status, txt_min_max_price;
        private ImageView btn_delete;

        public CouponsViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_discount_amount = itemView.findViewById(R.id.txt_discount_amount);
            txt_coupon = itemView.findViewById(R.id.txt_coupon);
            txt_disc = itemView.findViewById(R.id.txt_disc);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_min_max_price = itemView.findViewById(R.id.txt_min_max_price);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
