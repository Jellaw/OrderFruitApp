package com.example.fruit_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruit_application.Class.Animations;
import com.example.fruit_application.Model.Order;
import com.example.fruit_application.R;
import com.example.fruit_application.databinding.ItemOrderBinding;

import java.util.List;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<Order> orders;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_order,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bi.orderID.setText("Order #"+(orders.get(position).getIdOrder()+10000));
        holder.bi.orderFruitName.setText(orders.get(position).getNameFruit());
        holder.bi.orderQuantity.setText(orders.get(position).getQuantity()+"kg");
        holder.bi.orderTotal.setText(orders.get(position).getTotalPrice());
        holder.bi.orderFree.setText(orders.get(position).getDelivery());
        holder.bi.orderPrice.setText("$"+Float.parseFloat(orders.get(position).getTotalPrice().substring(2))/Float.parseFloat(orders.get(position).getQuantity()));
        holder.bi.viewMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean show = toggleLayout(!orders.get(holder.getAdapterPosition()).getExpand(), v, holder.bi.layoutExpand);
                orders.get(holder.getAdapterPosition()).setExpand(show);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemOrderBinding bi;

        public ViewHolder(@NonNull ItemOrderBinding itemView) {
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
