package com.example.fruit_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruit_application.R;
import com.example.fruit_application.animation.Animations;
import com.example.fruit_application.databinding.OrderItemBinding;
import com.example.fruit_application.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<Order> orders;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderItemBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.order_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.bi.orderID.setText("Order #"+(orders.get(i).getIdOrder()+10000));
        holder.bi.orderFruitName.setText(orders.get(i).getNameFruit());
        holder.bi.orderQuantity.setText(orders.get(i).getQuantity()+"kg");
        holder.bi.orderTotal.setText(orders.get(i).getTotalPrice());
        holder.bi.orderFree.setText(orders.get(i).getDelivery());
        holder.bi.orderPrice.setText("$"+Float.parseFloat(orders.get(i).getTotalPrice().substring(2))/Float.parseFloat(orders.get(i).getQuantity()));
        holder.bi.viewMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean show = toggleLayout(!orders.get(i).getExpand(), v, holder.bi.layoutExpand);
                orders.get(i).setExpand(show);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final OrderItemBinding bi;

        public ViewHolder(@NonNull OrderItemBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;

        }
    }
    private boolean toggleLayout(boolean isExpanded, View v, ConstraintLayout layoutExpand) {
        Animations.toggleArrow(v, isExpanded);
        if (isExpanded) {
            Animations.expand(layoutExpand);
        } else {
            Animations.collapse(layoutExpand);
        }
        return isExpanded;
    }
}