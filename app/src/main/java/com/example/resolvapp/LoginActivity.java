package com.example.resolvapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button telaRegistro=findViewById(R.id.btnRegistrarLog);
        telaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistroActvity.class);
                startActivity(i);
            }
        });

        Button telaMateria=findViewById(R.id.btnLoginLog);
        telaMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.setLogin(LoginActivity.this, "tales");
                Intent i = new Intent(LoginActivity.this, EscolherActivity.class);
                startActivity(i);
            }
        });
    }
}