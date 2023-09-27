package com.example.daycareapp.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daycareapp.R;
import com.example.daycareapp.listeners.OrderClickListener;
import com.example.daycareapp.models.Order;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListViewHolder> {
    private final Context context;
    private final List<Order> orderList;
    private final OrderClickListener orderClickListener;

    public OrderListAdapter(Context context, List<Order> orderList, OrderClickListener orderClickListener) {
        this.context = context;
        this.orderList = orderList;
        this.orderClickListener = orderClickListener;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_list_layout, parent, false);
        return new OrderListViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.setData(order);
        holder.orderIdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderClickListener.onClick(order);
            }
        });

        holder.orderIdTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                orderClickListener.onLongClick(order, holder.orderIdTv);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

}

class OrderListViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    public TextView orderIdTv;
    public TextView orderTimeTv;
    public ImageView orderImageView;
    public OrderListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        orderIdTv = itemView.findViewById(R.id.orderId);
        orderTimeTv = itemView.findViewById(R.id.orderTime);
        orderImageView = itemView.findViewById(R.id.orderImage);
    }

    public void setData(Order order) {
        orderIdTv.setText("Caregiver id: " + order.getCaregiverId()+" ");
        orderTimeTv.setText("Baby id: " + order.getBabyId()+" ");
        String PACKAGE_NAME = context.getPackageName();
        int imageId = context.getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + "img4z", null, null);
        orderImageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), imageId));
    }
}