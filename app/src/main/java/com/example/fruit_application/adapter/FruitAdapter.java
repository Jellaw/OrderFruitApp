package com.example.fruit_application.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fruit_application.R;
import com.example.fruit_application.databinding.FruitItemBinding;
import com.example.fruit_application.model.Fruit;
import com.example.fruit_application.view.activity.DetailActivity;


import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private List<Fruit> list;
    private Context context;
    private String idUser;
    public FruitAdapter(Context context, List<Fruit> list, String idUser) {
        this.context = context;
        this.list = list;
        this.idUser = idUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FruitItemBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.fruit_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getImg())
                .into(holder.binding.imageView6);
        holder.binding.fruitPrice.setText("$ "+list.get(position).getPrice());
        holder.binding.fruitName.setText(list.get(position).getName());

        int img = list.get(position).getImg();
        String name = list.get(position).getName();
        String price = list.get(position).getPrice();
        int idFruit = list.get(position).getId();
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            holder.itemView.setBackgroundColor(Color.parseColor("#80EDEAEA"));
            new Handler().postDelayed(() -> {
                holder.itemView.setBackgroundResource(R.drawable.background_item_fruit);
            }, 500);
            intent.putExtra("img", img);
            intent.putExtra("name", name);
            intent.putExtra("price", price);
            intent.putExtra("idUser", idUser);
            intent.putExtra("idFruit", idFruit);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final FruitItemBinding binding;
//        public ObservableField<String> fruitName = new ObservableField<>();
//        public ObservableField<String> fruitPrice = new ObservableField<>();
//        public ObservableField<Integer> fruitImg = new ObservableField<>();
        public ViewHolder(@NonNull FruitItemBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
