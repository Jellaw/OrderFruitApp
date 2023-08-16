package com.example.fruit_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_account);

        btnCreatAcc = findViewById(R.id.btnCreateAcc);
        btnBacktoSigin = findViewById(R.id.btnBackSignin);
        edtEmailCA = findViewById(R.id.edtEmailCreatAcc);
        edtFullname = findViewById(R.id.edtFullnameCreatAcc);
        edtPwCA = findViewById(R.id.edtPasswordCreatAcc);
        tvBackSignin = findViewById(R.id.tvBacktoSignin);

        btnCreatAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatAccountActivity.this, SigninActivity.class);
                startActivity(intent);
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