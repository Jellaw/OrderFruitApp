package com.example.fruit_application.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fruit_application.R;
import com.example.fruit_application.databinding.ActivityLoginBinding;
import com.example.fruit_application.viewmodel.LoginRegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    LoginRegisterViewModel loginRegisterViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginRegisterViewModel);
        loginRegisterViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("idUser", firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                if (email.length() > 0 && password.length() > 0) {
                    loginRegisterViewModel.login(email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Email Address and Password Must Be Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}