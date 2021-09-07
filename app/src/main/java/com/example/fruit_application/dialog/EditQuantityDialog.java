package com.example.fruit_application.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fruit_application.R;
import com.example.fruit_application.model.Cart;
import com.example.fruit_application.view.fragment.CartFragment;

public class EditQuantityDialog extends Dialog {
    Context context;
    LinearLayout save_btn, cancel_btn;
    EditText edtNameUser;
    CartFragment cartFragment;
    Cart cartEdit;
    int positionAdapter = -1;
    private String idUser;
    public EditQuantityDialog(@NonNull Context context, CartFragment cartFragment, String idUser) {
        super(context);
        this.context = context;
        this.cartFragment = cartFragment;
        this.idUser=idUser;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_item_cart);


        this.edtNameUser = findViewById(R.id.edtEditNameUser);
        this.save_btn = findViewById(R.id.save_edit_btn);
        this.cancel_btn = findViewById(R.id.cancelEditBtn);
        edtNameUser.setHint(String.valueOf(cartEdit.getQuantity()));
        cancel_btn.setOnClickListener(view -> {
            buttonCancelClick();
        });
        save_btn.setOnClickListener(view -> {
            if(!edtNameUser.getText().toString().isEmpty()){
                cartFragment.updateCartItem(cartEdit,idUser,positionAdapter,Integer.parseInt(edtNameUser.getEditableText().toString()));
            } else Toast.makeText(getContext(), "Please enter item quantity!", Toast.LENGTH_SHORT).show();
        });
    }
    private void buttonCancelClick()  {
        this.dismiss();
    }

    public void setCartEdit(Cart cart, int positionAdapter){
        this.cartEdit = cart;
        this.positionAdapter = positionAdapter;
    }
}