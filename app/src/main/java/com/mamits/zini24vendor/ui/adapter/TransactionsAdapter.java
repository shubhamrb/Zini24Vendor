package com.mamits.zini24vendor.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.payments.TransactionsDataModel;
import com.mamits.zini24vendor.ui.customviews.CustomCircularImageView;
import com.mamits.zini24vendor.ui.customviews.CustomTextView;
import com.mamits.zini24vendor.ui.utils.DateConvertor;
import com.mamits.zini24vendor.viewmodel.fragment.TransactionsViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {

    private Context mContext;
    public List<TransactionsDataModel> list;
    private TransactionsViewModel mViewModel;
    private Activity activity;


    public TransactionsAdapter(Context mContex, TransactionsViewModel mViewModel) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = new ArrayList<>();
        this.mViewModel = mViewModel;
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.orders_list_item, parent, false);
        return new TransactionsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        if (list.size() > 0) {
            TransactionsDataModel model = list.get(position);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                Date d1 = formatter.parse(model.getCreated_at());
                String date = new DateConvertor().getDate(d1.getTime(), DateConvertor.FORMAT_dd_MM_yyyy_HH_mm_ss);
                holder.txt_date.setText(date);

            } catch (Exception e) {
                holder.txt_date.setText(model.getCreated_at());
                e.printStackTrace();
            }
            holder.txt_order_id.setText(String.format("#%s", model.getId()));
            holder.txt_username.setText(model.getPayment_type());
            holder.txt_service_category.setText(model.getDescription());
            switch (model.getType()) {
                case "Debit":
                    holder.txt_status.setText("Debit");
                    holder.txt_status.setTextColor(mContext.getResources().getColor(R.color.red_ff2502));
                    break;
                case "Credit":
                    holder.txt_status.setText("Credit");
                    holder.txt_status.setTextColor(mContext.getResources().getColor(R.color.green_39ae00));
                    break;
            }
            holder.txt_price.setText("₹ " + model.getAmount());

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<TransactionsDataModel> orderList) {
        list = orderList;
        notifyDataSetChanged();
    }

    public static class TransactionsViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_date, txt_order_id, txt_username, txt_service_category, txt_status, txt_price;
        private CustomCircularImageView img;

        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
            txt_username = itemView.findViewById(R.id.txt_username);
            txt_service_category = itemView.findViewById(R.id.txt_service_category);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_price = itemView.findViewById(R.id.txt_price);
            img = itemView.findViewById(R.id.img);

        }
    }
}
