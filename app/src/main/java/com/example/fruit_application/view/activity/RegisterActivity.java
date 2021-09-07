package com.example.fruit_application.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fruit_application.R;
import com.example.fruit_application.databinding.ActivityRegisterBinding;
import com.example.fruit_application.viewmodel.LoginRegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    LoginRegisterViewModel loginRegisterViewModel;
    String uID;
    String email, fullName, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginRegisterViewModel);
        loginRegisterViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    uID=firebaseUser.getUid();
                }
            }
        });
        binding.backToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                loginRegisterViewModel.createInfoUser(uID,email,password,fullName);
            }
        });

        binding.buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 email = binding.editTextEmail.getText().toString();
                 password = binding.editTextPassword.getText().toString();
                 fullName = binding.editTextFullname.getText().toString();
                if (email.length() > 0 && password.length() > 0) {
                    loginRegisterViewModel.register(email, password);
                    Toast.makeText(getApplicationContext(), "Register Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Email Address and Password Must Be Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}