package com.example.resolvapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button telaRegistro =findViewById(R.id.btnRegistrarLog);
        Toolbar tbLogin = findViewById(R.id.tbLogin);
        setSupportActionBar(tbLogin);

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
                EditText etLogin = findViewById(R.id.etLoginLog);
                String loginUsuario = etLogin.getText().toString();

                EditText etPassword = findViewById(R.id.pwdLoginLog);
                String pwdUsuario = etPassword.getText().toString();

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.RESOLV_APP_URl+"post_login.php","POST", "UTF-8");
                        //colocando parametro

                        httpRequest.addParam("apelido",loginUsuario);
                        httpRequest.addParam("senha", pwdUsuario);

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

                                        Config.setLogin(LoginActivity.this, loginUsuario, idUsuario);
                                        Config.setPassword(LoginActivity.this, pwdUsuario);
                                        Toast.makeText(LoginActivity.this,"Logado com sucesso", Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(LoginActivity.this, EscolherActivity.class);
                                        startActivity(i);
                                    } else{
                                        try {
                                            Toast.makeText(LoginActivity.this,jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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