package com.example.fruit_application.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fruit_application.Model.Fruit;
import com.example.fruit_application.R;
import com.example.fruit_application.activities.DetailActivity;
import com.example.fruit_application.databinding.ItemFruitBinding;


import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> list;
    private Context context;
    private String idUser ;

    public FruitAdapter(List<Fruit> list, Context context, String idUser) {
        this.list = list;
        this.context = context;
        this.idUser = idUser;
    }


    @NonNull
    @Override
    public FruitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFruitBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_fruit, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getIgm())
                .into(holder.binding.imageViewFruitItem);
        holder.binding.fruitPrice.setText("$" + list.get(position).getPrice());
        holder.binding.fruitName.setText(list.get(position).getName());

        int img = list.get(position).getIgm();
        String name = list.get(position).getName();
        String price = list.get(position).getPrice();
        int idfr = list.get(position).getIdFruit();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("img", img);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("idFruit", idfr);
                intent.putExtra("idUser", idUser);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemFruitBinding binding;
        public ViewHolder(@NonNull ItemFruitBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
