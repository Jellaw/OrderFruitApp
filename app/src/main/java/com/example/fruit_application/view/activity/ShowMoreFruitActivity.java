package com.example.fruit_application.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fruit_application.R;
import com.example.fruit_application.adapter.FruitAdapter;
import com.example.fruit_application.databinding.ActivityShowMoreFruitBinding;
import com.example.fruit_application.model.Fruit;
import com.example.fruit_application.view.manager.AndroidDeviceHeplper;

import java.util.ArrayList;
import java.util.List;

public class ShowMoreFruitActivity extends AppCompatActivity {
    List<Fruit> list = new ArrayList<>();
    ActivityShowMoreFruitBinding binding;
    FruitAdapter adapter;
    Intent intent;
    private String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_more_fruit);
        intent = getIntent();
        if(intent.getIntExtra("type",0)==1) binding.header.setText("Our Store");
        else binding.header.setText("Best Summer Fruit");
        add();
        binding.imageViewBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }
    private void add() {
        list.add(new Fruit(0,R.drawable.durian,"Durian", "2.5"));
        list.add(new Fruit(1,R.drawable.watermelon_img,"Watermelon", "1.5"));
        list.add(new Fruit(2,R.drawable.apple,"Apple", "2.0"));
        list.add(new Fruit(3,R.drawable.kiwi_xanh_500x500,"Kiwi", "2.5"));
        list.add(new Fruit(4,R.drawable.lemon_1205_1667,"Lemons", "0.5"));
        list.add(new Fruit(5,R.drawable.papaya,"Papaya", "2.5"));
        initRecyclerViewOurStore();
    }

    private void initRecyclerViewOurStore() {
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), (int) (AndroidDeviceHeplper.getWithScreen(getApplicationContext())/convertDpToPx(getApplicationContext(),170f)));
        binding.rvMoreFruit.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(this, list, intent.getStringExtra("idUser"));
        binding.rvMoreFruit.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
    public float convertPxToDp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }
}