package com.example.fruit_application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fruit_application.R;
import com.example.fruit_application.activities.viewModel.LoginRegisterViewModel;
import com.example.fruit_application.databinding.ActivitySigninBinding;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends AppCompatActivity {
    LoginRegisterViewModel loginRegisterViewModel;

    TextView tvForgetPw, tvCreatAcc, tvError;
    Button btnSignin;
    EditText edtEmailSignin, edtPwSigin;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySigninBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_signin);
        loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginRegisterViewModel);


        tvCreatAcc = findViewById(R.id.tvCreateAcc);
        tvForgetPw = findViewById(R.id.tvForgetPw);
        btnSignin = findViewById(R.id.btnSignin);
        edtEmailSignin = findViewById(R.id.edtEmailSiginin);
        edtPwSigin = findViewById(R.id.edtPasswordSignin);
        tvError = findViewById(R.id.tvError);
        loginRegisterViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                    intent.putExtra("idUser", firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
        binding.btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtEmailSiginin.getText().toString();
                String password = binding.edtPasswordSignin.getText().toString();
                if (email.length() > 0 && password.length() > 0) {
                    loginRegisterViewModel.login(email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Email Address and Password Must Be Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.tvCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, CreatAccountActivity.class);
                startActivity(intent);
            }
        });

    }
}

//        btnSignin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                email = edtEmailSignin.getText().toString().trim();
//                password = edtPwSigin.getText().toString().trim();
//                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//                String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
//
//
//                if (email.isEmpty() || password.isEmpty()){
//                    tvError.setText("Vui lòng đủ thông tin!");
//                }else {
//                    if (email.matches(emailPattern) ){
//                        if (password.length() > 8 && password.matches(passwordPattern)){
//                            Intent intent = new Intent(SigninActivity.this, WelcomeActivity.class);
//                            startActivity(intent);
//                        }else {
//                            tvError.setText("Mật khẩu nhập sai định dang!");
//                        }
//                    }else {
//                        tvError.setText("Email nhập sai định dạng!");
//                    }
//
//                }
//            }
//        });
//        tvCreatAcc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SigninActivity.this, CreatAccountActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//}