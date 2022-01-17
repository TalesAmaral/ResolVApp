package com.example.resolvapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FazerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer);
        TextView tvTitulo = findViewById(R.id.tvTitulo);
        TextView tvEnunciado = findViewById(R.id.tvEnunciado);
        RadioGroup rgAlternativas = findViewById(R.id.rgAlternativas);
        FloatingActionButton fabProxima = findViewById(R.id.fabProxima);
        final Boolean a = getIntent().getExtras().getBoolean("a");
        if (a){
            tvTitulo.setText("AFA 1994");
            tvEnunciado.setText("Duas caixas A e B, contêm extamente 5 bolas cada uma. Retiram-se duas bolas de cada caixa, aleatoriamente. O número de elementos do espaço amostral relativo a esse experimento é exatamente:");
            int[] alternativas = {40,50,100,400,500};
            for (int i =0;i<5;i++){
                RadioButton radioAltenativa = (RadioButton) rgAlternativas.getChildAt(i);
                radioAltenativa.setText(String.valueOf(alternativas[i]));
            }
        }else{
            tvTitulo.setText("(UPE) - Sobre o Pensamento Mítico");

            tvTitulo.setVisibility(View.INVISIBLE);
            fabProxima.setVisibility(View.INVISIBLE);

            tvEnunciado.setText("Para nós, os mitos primitivos não passam de histórias fantasiosas que são contadas ao lado das histórias da Branca de Neve ou da Bela Adormecida. O mito, porém, não é isso. Quando vira uma história, uma lenda, ele perde a sua força de mito.\n" +
                    "ARANHA, Maria Lúcia; MARTINS, Maria Helena. Temas de Filosofia, 1992, p. 62. Adaptado.\n" +
                    "Sobre esse assunto, é CORRETO afirmar que:");
            String[] alternativas = {"o mito nasce da razão, com a força de dominar o mundo para a garantia da segurança do humano.",
                                    "o mito está desligado do desejo, ausentes do querer que as coisas ocorram de uma determinada forma.",
                                    "o mito tem como característica singular o crivo da racionalidade, ou seja, a sua aceitação tem de atender o questionamento e a certeza.",
                                    "a força do mito está atrelada às histórias fantasiosas cuja função principal é explicar a realidade nas suas narrativas.",
                                    "o pensamento mítico encontrou, na cultura grega, a forma privilegiada de se organizar e de se estruturar"};
            for (int i =0;i<5;i++) {
                RadioButton radioAltenativa = (RadioButton) rgAlternativas.getChildAt(i);
                radioAltenativa.setText(String.valueOf(alternativas[i]));
            }
        }
        fabProxima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                i.putExtra("a", !a);
                finish();
                startActivity(i);
            }
        });
    }
}