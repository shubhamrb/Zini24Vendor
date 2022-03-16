package com.mamits.zini24vendor.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.services.ServiceDataModel;
import com.mamits.zini24vendor.ui.customviews.CustomCircularImageView;
import com.mamits.zini24vendor.ui.customviews.CustomTextView;
import com.mamits.zini24vendor.viewmodel.fragment.ServicesViewModel;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.OrdersViewHolder> {

    private Context mContext;
    public List<ServiceDataModel> list;
    private ServicesViewModel mViewModel;
    private Activity activity;
    private deleteListener listener;


    public ServicesAdapter(Context mContex, ServicesViewModel mViewModel, deleteListener listener) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = new ArrayList<>();
        this.mViewModel = mViewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.services_list_item, parent, false);
        return new OrdersViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        if (list.size() > 0) {
            ServiceDataModel model = list.get(position);
            if (model.getVariation().size() != 0) {
                holder.txt_service_type.setText("VARIATION");
                holder.ll_status.setVisibility(View.INVISIBLE);
            } else {
                holder.txt_service_type.setText("SIMPLE");
                holder.txt_price.setText("₹ " + model.getPrice());
                holder.ll_status.setVisibility(View.VISIBLE);
            }
            holder.txt_commission.setText("Platform charge - ₹ " + model.getAdmin_commission());
            holder.txt_service_name.setText(model.getName());
            holder.txt_service_category_sub.setText(model.getCategory().getName() + " | " + model.getSubcategory().getName());


            Glide.with(mContext).load(model.getImage()).into(holder.img);

            holder.btn_delete.setOnClickListener(v -> listener.onDeleteService(String.valueOf(model.getId())));
            holder.itemView.setOnClickListener(v -> goToEditService(v, model));
        }

    }

    private void goToEditService(View v, ServiceDataModel model) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_out_right)
                .setExitAnim(R.anim.slide_in).setPopEnterAnim(0).setPopExitAnim(R.anim.slide_out1)
                .build();
        NavController navController = Navigation.findNavController(v);
        navController.navigate(R.id.nav_add_service, bundle, options);
    }

    public interface deleteListener {
        void onDeleteService(String inventoryId);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ServiceDataModel> orderList) {
        list = orderList;
        notifyDataSetChanged();
    }

    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_commission, txt_service_type, txt_service_name, txt_service_category_sub, txt_price;
        private CustomCircularImageView img;
        private ImageView btn_delete;
        private LinearLayout ll_status;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_commission = itemView.findViewById(R.id.txt_commission);
            txt_service_type = itemView.findViewById(R.id.txt_service_type);
            txt_service_name = itemView.findViewById(R.id.txt_service_name);
            txt_service_category_sub = itemView.findViewById(R.id.txt_service_category_sub);
            txt_price = itemView.findViewById(R.id.txt_price);
            img = itemView.findViewById(R.id.img);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            ll_status = itemView.findViewById(R.id.ll_status);

        }
    }
}
