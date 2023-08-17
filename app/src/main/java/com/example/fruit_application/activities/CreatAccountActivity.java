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

public class CreatAccountActivity extends AppCompatActivity {
    Button btnCreatAcc , btnBacktoSigin;
    EditText edtFullname , edtEmailCA , edtPwCA;
    TextView tvBackSignin, tvErrorCA;
    String emailCA , fullnamCA , passwordCA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_account);

        btnCreatAcc = findViewById(R.id.btnCreateAcc);
        btnBacktoSigin = findViewById(R.id.btnBackWc);
        edtEmailCA = findViewById(R.id.edtEmailCreatAcc);
        edtFullname = findViewById(R.id.edtFullnameCreatAcc);
        edtPwCA = findViewById(R.id.edtPasswordCreatAcc);
        tvBackSignin = findViewById(R.id.tvBacktoSignin);
        tvErrorCA = findViewById(R.id.tvErrorCA);


        btnCreatAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailCA = edtEmailCA.getText().toString().trim();
                fullnamCA = edtFullname.getText().toString().trim();
                passwordCA = edtPwCA.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";

                if (emailCA.isEmpty() || fullnamCA.isEmpty() || passwordCA.isEmpty()){
                    tvErrorCA.setText("Vui lòng nhập đủ thông tin!");
                }else {

                    if (emailCA.matches(emailPattern) ){
                        if (passwordCA.length() > 8 && passwordCA.matches(passwordPattern)){
                            Intent intent = new Intent(CreatAccountActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        }else {
                            tvErrorCA.setText("Mật khẩu nhập sai định dang!");
                        }
                    }else {
                        tvErrorCA.setText("Email nhập sai định dạng!");
                    }
                }
            }
        });
        tvBackSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatAccountActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
        btnBacktoSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatAccountActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
    }
}