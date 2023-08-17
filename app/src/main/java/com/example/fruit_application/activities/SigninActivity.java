package com.example.fruit_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fruit_application.R;

import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity {
    TextView tvForgetPw , tvCreatAcc ,tvError;
    Button btnSignin ;
    EditText edtEmailSignin, edtPwSigin;
    String email , password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        tvCreatAcc = findViewById(R.id.tvCreateAcc);
        tvForgetPw = findViewById(R.id.tvForgetPw);
        btnSignin = findViewById(R.id.btnSignin);
        edtEmailSignin = findViewById(R.id.edtEmailSiginin);
        edtPwSigin = findViewById(R.id.edtPasswordSignin);
        tvError = findViewById(R.id.tvError);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtEmailSignin.getText().toString().trim();
                password = edtPwSigin.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";


                if (email.isEmpty() || password.isEmpty()){
                    tvError.setText("Vui lòng đủ thông tin!");
                }else {
                    if (email.matches(emailPattern) ){
                        if (password.length() > 8 && password.matches(passwordPattern)){
                            Intent intent = new Intent(SigninActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        }else {
                            tvError.setText("Mật khẩu nhập sai định dang!");
                        }
                    }else {
                        tvError.setText("Email nhập sai định dạng!");
                    }

                }
            }
        });
        tvCreatAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, CreatAccountActivity.class);
                startActivity(intent);
            }
        });
    }

}