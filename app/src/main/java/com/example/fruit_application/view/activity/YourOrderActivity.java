package com.example.fruit_application.view.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fruit_application.R;
import com.example.fruit_application.adapter.OrderAdapter;
import com.example.fruit_application.databinding.ActivityYourOrderBinding;
import com.example.fruit_application.viewmodel.OrderViewModel;

public class YourOrderActivity extends AppCompatActivity {
    ActivityYourOrderBinding binding;
    OrderAdapter adapter;
    OrderViewModel orderViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_your_order);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setOrderViewModel(orderViewModel);
        initRecyclerView();
    }
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvOrder.setLayoutManager(layoutManager);
        adapter = new OrderAdapter(getApplicationContext(), orderViewModel.getDataOrder(getIntent().getStringExtra("userID")));
        new Handler().postDelayed(() -> {
            binding.rvOrder.setAdapter(adapter);
        }, 800);
        adapter.notifyDataSetChanged();
    }
}