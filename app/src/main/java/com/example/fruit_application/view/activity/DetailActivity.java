package com.example.fruit_application.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fruit_application.R;
import com.example.fruit_application.databinding.ActivityDetailBinding;
import com.example.fruit_application.viewmodel.AddToCartViewModel;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    AddToCartViewModel addToCartViewModel;
    Intent intent;
    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail);
        addToCartViewModel = ViewModelProviders.of(this).get(AddToCartViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setAddToCartViewModel(addToCartViewModel);

        binding.imageViewBack.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.buttonIncrease.setOnClickListener(view -> {
            quantity++;
            if (quantity>0){
                binding.buttonDecrease.setEnabled(true);
                binding.buttonAddToCart.setEnabled(true);
            }
            binding.txtQuantity.setText(""+quantity);
            binding.txtPrice.setText("$ "+Float.parseFloat(intent.getStringExtra("price"))*quantity);
        });
        binding.buttonDecrease.setOnClickListener(view -> {
            if (quantity>0)
            quantity--;
            if (quantity==0) {
                binding.buttonDecrease.setEnabled(false);
                binding.buttonAddToCart.setEnabled(false);
            }
            binding.txtQuantity.setText(""+quantity);
            binding.txtPrice.setText("$ "+Float.parseFloat(intent.getStringExtra("price"))*quantity);
        });
        binding.buttonAddToCart.setOnClickListener(view -> {
            Intent intent = getIntent();
            addToCartViewModel.setIdFruit(intent.getIntExtra("idFruit",0));
            addToCartViewModel.onClickAddCart(binding.buttonAddToCart, intent.getStringExtra("idUser"));
            Toast.makeText(getApplicationContext(), "The product has been added to cart!", Toast.LENGTH_SHORT).show();
        });
        setData();
    }
    private void setData(){
        intent = getIntent();
        Glide.with(getApplicationContext())
                .load(intent.getIntExtra("img",0))
                .into(binding.imageViewFruit);
        binding.headerDetail.setText(intent.getStringExtra("name"));
        addToCartViewModel.setNameFruit(intent.getStringExtra("name"));
        addToCartViewModel.setQuantity("1");
        addToCartViewModel.setPrice("$ "
                +Float.parseFloat(intent.getStringExtra("price"))
                *Integer.parseInt(addToCartViewModel.getQuantity().getValue()));
        binding.txtPrice.setText("$ "+intent.getStringExtra("price"));
    }
}