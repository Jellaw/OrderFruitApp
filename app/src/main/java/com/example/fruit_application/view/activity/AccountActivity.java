package com.example.fruit_application.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.fruit_application.R;
import com.example.fruit_application.database.IRealmResponse;
import com.example.fruit_application.database.helper.UserDBHelper;
import com.example.fruit_application.database.modelRealm.UserRealmModel;
import com.example.fruit_application.databinding.ActivityAccountBinding;

public class AccountActivity extends AppCompatActivity {
    ActivityAccountBinding binding;
    UserDBHelper userDBHelper = UserDBHelper.getInstance();
    String fullName, address, email, password, idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account);
        binding.imageViewBack.setOnClickListener(view ->{
            onBackPressed();
        });
        showInfo();
    }
    private void showInfo(){
        idUser = getIntent().getStringExtra("userID");
        userDBHelper.getUser(idUser, new IRealmResponse<Boolean, UserRealmModel>() {
            @Override
            public Boolean executeService(UserRealmModel args) {
                if (args!=null){
                    fullName = args.getFullName();
                    address = args.getTown();
                    email = args.getEmail();
                    password = args.getPassword();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.infoEmail.setText(email);
                            binding.infoName.setText(fullName);
                            binding.infoPass.setText(password);
                            binding.infoStreet.setText(address);
                        }
                    });
                }
                return null;
            }
        });
    }
}