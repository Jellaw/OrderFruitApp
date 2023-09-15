package com.example.fruit_application.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fruit_application.Model.Cart;
import com.example.fruit_application.R;
import com.example.fruit_application.fragments.Cartfragment;

public class EditQuantityDialog extends Dialog {
    Context context;
    LinearLayout btnSave , btnCancel;
    EditText edtUsername;
    Cartfragment cartfragment;
    Cart cart;
    int i = -1;
    private String idUser;

    public EditQuantityDialog(@NonNull Context context, Cartfragment cartfragment , String idUser) {
        super(context);
        this.context = context;
        this.cartfragment = cartfragment;
        this.idUser = idUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_quantity);

        this.edtUsername = findViewById(R.id.edtEditNameUser);
        this.btnSave = findViewById(R.id.save_edit_btn);
        this.btnCancel = findViewById(R.id.cancelEditBtn);

        edtUsername.setHint(String.valueOf(cart.getQuanlity()));

        btnCancel.setOnClickListener(view -> {
            this.dismiss();
        });
        btnSave.setOnClickListener(view -> {
            if (!edtUsername.getText().toString().isEmpty()){
                cartfragment.updateCartItem(cart , idUser, i , Integer.parseInt(edtUsername.getText().toString()));
            }else {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setCartEdit(Cart cart , int i){
        this.cart = cart;
        this.i = i;
    }
}
