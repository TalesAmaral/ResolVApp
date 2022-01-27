package com.example.resolvapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

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

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Toolbar tbPerfil = findViewById(R.id.tbPerfil);
        setSupportActionBar(tbPerfil);

        TextView tvApelido = findViewById(R.id.tvApelido);
        TextView tvNome = findViewById(R.id.tvNome);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvQuantidade = findViewById(R.id.tvQuantidade);
        TextView tvPorcentagem = findViewById(R.id.tvPorcentagem);

        Button btnSair =findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.setLogin(PerfilActivity.this,"","");
                Config.setPassword(PerfilActivity.this, "");
                Intent i = new Intent(PerfilActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpRequest httpRequest = new HttpRequest(Config.RESOLV_APP_URl+"get_perfil.php","GET", "UTF-8");
                //colocando parametro

                httpRequest.addParam("id",Config.getId(PerfilActivity.this));

                try {
                    InputStream inputStream = httpRequest.execute();
                    String result = Util.inputStream2String(inputStream, "UTF-8");
                    httpRequest.finish();

                    Log.d("HTTP_REQUEST_RESULT",result);

                    JSONObject jsonObject = new JSONObject(result);
                    int success = jsonObject.getInt("success");
                    //vendo se deu certo
                    if (success == 1){
                        JSONObject questao = jsonObject.getJSONArray("perfil").getJSONObject(0);
                        String Apelido = questao.getString("apelido");
                        String Nome = "Nome: "+questao.getString("nome");
                        String Email = "Email: "+questao.getString("email");
                        int Quantidade =  Integer.parseInt(questao.getString("questoes"));
                        int Acertos = Integer.parseInt(questao.getString("acertos"));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvApelido.setText(Apelido);
                                tvNome.setText(Nome);
                                tvEmail.setText(Email);
                                tvQuantidade.setText("Quantidade de questões realizadas: "+Quantidade);
                                if (Quantidade != 0) {
                                    tvPorcentagem.setText("Porcentagem de acertos: " + Acertos * 100 / Quantidade + "%");
                                }else{
                                    tvPorcentagem.setText("Porcentagem de acertos: 0%");
                                }
                            }
                        });

                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}