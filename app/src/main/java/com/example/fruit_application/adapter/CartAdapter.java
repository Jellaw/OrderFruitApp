package com.example.fruit_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fruit_application.Model.Cart;
import com.example.fruit_application.R;
import com.example.fruit_application.databinding.ItemCartBinding;
import com.example.fruit_application.fragments.Cartfragment;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<Cart> list;
    Context context;
    CallbackItemCart callbackItemCart;
    Cartfragment cartfragment;

    public CartAdapter(Context context, Cartfragment cartfragment, List<Cart> list, CallbackItemCart callbackItemCart) {
        this.list = list;
        this.context = context;
        this.callbackItemCart = callbackItemCart;
        this.cartfragment = cartfragment;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_cart, parent, false);
        return new CartAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getImgFruit())
                .into(holder.binding.imageViewFruit);
        holder.binding.tvPrice.setText(list.get(position).getPriceFruit());
        holder.binding.tvName.setText(list.get(position).getNamefruit());
        holder.binding.tvKg.setText("" + list.get(position).getQuanlity()+ "kg");
        holder.binding.deleteCart.setOnClickListener(view -> {
            callbackItemCart.deleteCart(position);
        });
        holder.binding.editCart.setOnClickListener(view -> {
            callbackItemCart.editCart(position);
        });
        cartfragment.idCart = list.get(0).getIdCart();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ItemCartBinding binding;
        public ViewHolder(@NonNull ItemCartBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
