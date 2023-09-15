package com.example.fruit_application.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.fruit_application.R;
import com.example.fruit_application.database.RealmResponse;
import com.example.fruit_application.database.UserDBHelper;
import com.example.fruit_application.database.modelRealm.UserRealm;
import com.example.fruit_application.databinding.ActivityYourAccountBinding;

public class YourAccountActivity extends AppCompatActivity {
    ActivityYourAccountBinding binding;
    UserDBHelper userDBHelper = UserDBHelper.getInstance();
    String fullName, address, email, password, idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_your_account);
        binding.imageViewBack.setOnClickListener(view ->{
            onBackPressed();
        });
        showInfo();
    }
    private void showInfo(){
        idUser = getIntent().getStringExtra("userID");
        userDBHelper.getUser(idUser, new RealmResponse<Boolean, UserRealm>() {
            @Override
            public Boolean executeService(UserRealm args) {
                if (args!=null){
                    fullName = args.getFullname();
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
