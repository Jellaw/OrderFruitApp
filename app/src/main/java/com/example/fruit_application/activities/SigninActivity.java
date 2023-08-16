package com.example.fruit_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fruit_application.R;

public class SigninActivity extends AppCompatActivity {
    TextView tvForgetPw , tvCreatAcc;
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

        email = edtEmailSignin.getText().toString().trim();
        password = edtPwSigin.getText().toString().trim();
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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