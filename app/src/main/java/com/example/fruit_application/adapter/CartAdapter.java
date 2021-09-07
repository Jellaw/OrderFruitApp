package com.example.fruit_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fruit_application.R;
import com.example.fruit_application.callback.CallbackItemCart;
import com.example.fruit_application.databinding.CartItemBinding;
import com.example.fruit_application.model.Cart;
import com.example.fruit_application.view.fragment.CartFragment;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Cart> list;
    private Context context;
    private CallbackItemCart callbackItemCart;
    private CartFragment cartFragment;
    public CartAdapter(Context context, CartFragment cartFragment, List<Cart> list, CallbackItemCart callbackItemCart) {
        this.context = context;
        this.list = list;
        this.callbackItemCart = callbackItemCart;
        this.cartFragment = cartFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.cart_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getImgFruit())
                .into(holder.binding.imageViewFruit);
        holder.binding.tvPrice.setText(list.get(position).getPrice());
        holder.binding.tvName.setText(list.get(position).getNameFruit());
        holder.binding.tvKg.setText(""+list.get(position).getQuantity()+"kg");
        holder.binding.deleteCart.setOnClickListener(view ->{
            callbackItemCart.deleteCart(position);
        });
        holder.binding.editCart.setOnClickListener(view ->{
            callbackItemCart.editCart(position);
        });
        cartFragment.idCart = list.get(0).getCartID();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CartItemBinding binding;
        public ViewHolder(@NonNull CartItemBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
