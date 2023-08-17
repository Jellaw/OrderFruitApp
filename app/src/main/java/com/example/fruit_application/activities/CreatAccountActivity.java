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
    TextView tvBackSignin;
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


        btnCreatAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailCA = edtEmailCA.getText().toString().trim();
                fullnamCA = edtFullname.getText().toString().trim();
                passwordCA = edtPwCA.getText().toString().trim();

                if (emailCA.isEmpty() || fullnamCA.isEmpty() || passwordCA.isEmpty()){
                    edtEmailCA.setError("Vui lòng nhập đủ thông tin");
                    edtFullname.setError("Vui lòng nhập đủ thông tin");
                    edtPwCA.setError("Vui lòng nhập đủ thông tin");
                }else {

                    Intent intent = new Intent(CreatAccountActivity.this, WelcomeActivity.class);
                    startActivity(intent);
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