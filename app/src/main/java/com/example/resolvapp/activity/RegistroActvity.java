package com.example.resolvapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.resolvapp.R;
import com.example.resolvapp.util.Config;
import com.example.resolvapp.util.HttpRequest;
import com.example.resolvapp.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                EditText etEmail = findViewById(R.id.etEmailReg);
                String emailUsuario= etEmail.getText().toString();

                EditText etNome = findViewById(R.id.etNomeReg);
                String nomeUsuario = etNome.getText().toString();

                EditText etApelido= findViewById(R.id.etApelidoReg);
                String apelidoUsuario = etApelido.getText().toString();

                EditText etPassword = findViewById(R.id.etSenha);
                String senhaUsuario = etPassword.getText().toString();

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.RESOLV_APP_URl+"post_cadastro.php","POST", "UTF-8");
                        //colocando parametro

                        httpRequest.addParam("apelido",apelidoUsuario);
                        httpRequest.addParam("nome",nomeUsuario);
                        httpRequest.addParam("email",emailUsuario);
                        httpRequest.addParam("senha", senhaUsuario);

                        try {
                            //pegando a resposta
                            InputStream inputStream = httpRequest.execute();
                            String result = Util.inputStream2String(inputStream, "UTF-8");
                            httpRequest.finish();

                            Log.d("HTTP_REQUEST_RESULT",result);

                            JSONObject jsonObject = new JSONObject(result);
                            int success = jsonObject.getInt("success");
                            //vendo se deu certo
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (success == 1){
                                        String idUsuario = null;
                                        try {
                                            idUsuario = jsonObject.getString("id");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        Config.setLogin(RegistroActvity.this, apelidoUsuario, idUsuario);
                                        Config.setPassword(RegistroActvity.this, senhaUsuario);
                                        Toast.makeText(RegistroActvity.this,"Registrado com sucesso", Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(RegistroActvity.this, EscolherActivity.class);
                                        startActivity(i);
                                    } else{
                                        try {
                                            Toast.makeText(RegistroActvity.this,jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            });

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }
}