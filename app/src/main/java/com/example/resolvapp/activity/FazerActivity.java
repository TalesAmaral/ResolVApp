package com.example.resolvapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resolvapp.R;
import com.example.resolvapp.util.Config;
import com.example.resolvapp.util.HttpRequest;
import com.example.resolvapp.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FazerActivity extends AppCompatActivity {

    String altCorreta;
    String idQuestao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer);

        Toolbar tbFazer = findViewById(R.id.tbFazer);
        setSupportActionBar(tbFazer);
        TextView tvTitulo = findViewById(R.id.tvTitulo);
        TextView tvEnunciado = findViewById(R.id.tvEnunciado);
        TextView tvResolucao = findViewById(R.id.tvResolucao);
        tvResolucao.setVisibility(View.INVISIBLE);
        RadioGroup rgAlternativas = findViewById(R.id.rgAlternativas);
        FloatingActionButton fabProxima = findViewById(R.id.fabProxima);
        String idMateria = getIntent().getStringExtra("idMateria");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpRequest httpRequest = new HttpRequest(Config.RESOLV_APP_URl+"get_questao.php","GET", "UTF-8");
                //colocando parametro

                httpRequest.addParam("id",idMateria);

                try {
                    InputStream inputStream = httpRequest.execute();
                    String result = Util.inputStream2String(inputStream, "UTF-8");
                    httpRequest.finish();

                    Log.d("HTTP_REQUEST_RESULT",result);

                    JSONObject jsonObject = new JSONObject(result);
                    int success = jsonObject.getInt("success");
                    //vendo se deu certo
                    if (success == 1){
                        JSONObject questao = jsonObject.getJSONArray("questao").getJSONObject(0);
                        String enunciado = questao.getString("enunciado");
                        String titulo = "("+questao.getString("vestibular")+" - "+questao.getString("ano")+") ";
                        JSONArray alternativas = questao.getJSONArray("alternativas");
                        String solucao =  questao.getString("solucao");
                        idQuestao = questao.getString("id");

                        altCorreta = questao.getString("altCorreta");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvEnunciado.setText(Html.fromHtml(enunciado));
                                tvTitulo.setText(titulo);
                                tvResolucao.setText(Html.fromHtml(solucao));
                                for (int i =0;i<5;i++){
                                    RadioButton radioAltenativa = (RadioButton) rgAlternativas.getChildAt(i);
                                    try {
                                        radioAltenativa.setText(alternativas.getString(i));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        fabProxima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idCorreta = 0;
                if (rgAlternativas.getCheckedRadioButtonId() != -1) {
                    if (tvResolucao.getVisibility() == View.INVISIBLE) {
                        tvResolucao.setVisibility(View.VISIBLE);

                        int idMarcada = rgAlternativas.getCheckedRadioButtonId();
                        for (int i = 0; i < 5; i++) {
                            RadioButton radioAltenativa = (RadioButton) rgAlternativas.getChildAt(i);
                            radioAltenativa.setClickable(false);
                            int idRadio = radioAltenativa.getId();
                            if (radioAltenativa.getText().toString().equals(altCorreta)) {
                                idCorreta = idRadio;
                            }
                        }
                        RadioButton botaoCerto = findViewById(idCorreta);
                        botaoCerto.setBackgroundColor(getResources().getColor(R.color.green));
                        if (idCorreta != idMarcada) {
                            RadioButton botaoErrado = findViewById(idMarcada);
                            botaoErrado.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                        final String acertou = (idCorreta == idMarcada) ? "1" : "0";
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                HttpRequest httpRequest = new HttpRequest(Config.RESOLV_APP_URl+"post_questao.php","POST", "UTF-8");
                                //colocando parametro

                                String x = idQuestao;
                                String y = Config.getId(FazerActivity.this);
                                httpRequest.addParam("idQuestao",idQuestao);
                                httpRequest.addParam("idUsuario",Config.getId(FazerActivity.this));
                                httpRequest.addParam("acertou",  acertou );
                                try {
                                    InputStream inputStream = httpRequest.execute();
                                    String result = Util.inputStream2String(inputStream, "UTF-8");
                                    httpRequest.finish();

                                    Log.d("HTTP_REQUEST_RESULT",result);

                                    JSONObject jsonObject = new JSONObject(result);
                                    int success = jsonObject.getInt("success");
                                    //vendo se deu certo
                                    if (success != 1){
                                        Log.w("erro", "insert deu errado");

                                    }

                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    } else{
                        Intent i = getIntent();
                        i.putExtra("idMateria", idMateria);
                        finish();
                        startActivity(i);
                    }
                }
            }
        });
    }
}