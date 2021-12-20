package com.example.resolvapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistroActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_actvity);

        Button telaLogin=findViewById(R.id.btnVoltarReg);
        telaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistroActvity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        Button telaMateria=findViewById(R.id.btnRegistrarReg);
        telaMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistroActvity.this, EscolherActivity.class);
                startActivity(i);
            }
        });
    }
}