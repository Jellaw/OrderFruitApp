package com.example.fruit_application.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fruit_application.R;
import com.example.fruit_application.activities.viewModel.LoginRegisterViewModel;
import com.example.fruit_application.databinding.ActivityCreatAccountBinding;
import com.google.firebase.auth.FirebaseUser;

public class CreatAccountActivity extends AppCompatActivity {
    LoginRegisterViewModel loginRegisterViewModel;
    String idUser;
    String email, fullName, password;


    //    Button btnCreatAcc , btnBacktoSigin;
//    EditText edtFullname , edtEmailCA , edtPwCA;
//    TextView tvBackSignin, tvErrorCA;
//    String emailCA , fullnamCA , passwordCA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreatAccountBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_creat_account);
        loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginRegisterViewModel);

        loginRegisterViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    idUser=firebaseUser.getUid();
                }
            }
        });
        binding.btnBackWc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                loginRegisterViewModel.createInfoUser(idUser,email,password,fullName);
            }
        });

        binding.btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.edtEmailCreatAcc.getText().toString();
                password = binding.edtPasswordCreatAcc.getText().toString();
                fullName = binding.edtFullnameCreatAcc.getText().toString();
                if (email.length() > 0 && password.length() > 0) {
                    loginRegisterViewModel.register(email, password);
                    Toast.makeText(getApplicationContext(), "Register Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Email Address and Password Must Be Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//        btnCreatAcc = findViewById(R.id.btnCreateAcc);
//        btnBacktoSigin = findViewById(R.id.btnBackWc);
//        edtEmailCA = findViewById(R.id.edtEmailCreatAcc);
//        edtFullname = findViewById(R.id.edtFullnameCreatAcc);
//        edtPwCA = findViewById(R.id.edtPasswordCreatAcc);
//        tvBackSignin = findViewById(R.id.tvBacktoSignin);
//        tvErrorCA = findViewById(R.id.tvErrorCA);
//
//
//        btnCreatAcc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                emailCA = edtEmailCA.getText().toString().trim();
//                fullnamCA = edtFullname.getText().toString().trim();
//                passwordCA = edtPwCA.getText().toString().trim();
//
//                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//                String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
//
//                if (emailCA.isEmpty() || fullnamCA.isEmpty() || passwordCA.isEmpty()){
//                    tvErrorCA.setText("Vui lòng nhập đủ thông tin!");
//                }else {
//
//                    if (emailCA.matches(emailPattern) ){
//                        if (passwordCA.length() > 8 && passwordCA.matches(passwordPattern)){
//                            Intent intent = new Intent(CreatAccountActivity.this, WelcomeActivity.class);
//                            startActivity(intent);
//                        }else {
//                            tvErrorCA.setText("Mật khẩu nhập sai định dang!");
//                        }
//                    }else {
//                        tvErrorCA.setText("Email nhập sai định dạng!");
//                    }
//                }
//            }
//        });
//        tvBackSignin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CreatAccountActivity.this, SigninActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnBacktoSigin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CreatAccountActivity.this, SigninActivity.class);
//                startActivity(intent);
//            }
//        });
 //   }
}