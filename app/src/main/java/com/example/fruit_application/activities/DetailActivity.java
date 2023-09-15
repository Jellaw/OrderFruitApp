package com.example.fruit_application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.fruit_application.R;
import com.example.fruit_application.activities.viewModel.AddToCartViewModel;
import com.example.fruit_application.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    Intent intent;
    int quantity = 1;

    AddToCartViewModel addToCartModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        addToCartModel = ViewModelProviders.of(this).get(AddToCartViewModel.class);
        binding.setLifecycleOwner(this);

        binding.setAddToCartViewModel(addToCartModel);

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
            if (quantity>0){
                quantity--;}
            if (quantity==0) {
                binding.buttonDecrease.setEnabled(false);
                binding.buttonAddToCart.setEnabled(false);
            }
            binding.txtQuantity.setText(""+quantity);
            binding.txtPrice.setText("$ "+Float.parseFloat(intent.getStringExtra("price"))*quantity);
        });
        binding.buttonAddToCart.setOnClickListener(view -> {
            Intent intent = getIntent();
            addToCartModel.setIdFruit(intent.getIntExtra("idFruit",0));
            addToCartModel.onClickAddCart(binding.buttonAddToCart, intent.getStringExtra("idUser"));
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
        addToCartModel.setNameFruit(intent.getStringExtra("name"));
        addToCartModel.setQuantity("1");
        addToCartModel.setPrice("$ "
                +Float.parseFloat(intent.getStringExtra("price"))
                *Integer.parseInt(addToCartModel.getQuantity().getValue()));
        binding.txtPrice.setText("$ "+intent.getStringExtra("price"));
    }
}