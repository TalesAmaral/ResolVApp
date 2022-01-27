package com.example.resolvapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.resolvapp.model.Materia;
import com.example.resolvapp.adapter.MyAdapter;
import com.example.resolvapp.R;

import java.util.ArrayList;
import java.util.List;

public class EscolherActivity extends AppCompatActivity {


    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher);


        Toolbar tbEscolher = findViewById(R.id.tbEscolher);
        setSupportActionBar(tbEscolher);


        List<Materia> itens = new ArrayList<>();

        int[] nums = {R.drawable.outline_calculate_24,R.drawable.outline_precision_manufacturing_24, R.drawable.outline_menu_book_24, R.drawable.outline_science_24, R.drawable.outline_biotech_24, R.drawable.outline_psychology_24 };
        String[] titles = {"Matemática","Física", "Português","Química","Biologia", "Filosofia"};

        for (int i=0; i<nums.length;i++){
            Materia mat = new Materia();
            mat.icon = nums[i];
            mat.title = titles[i];
            mat.id = i+1;
            itens.add(mat);
        }
        // Criando o adaptador
        myAdapter = new MyAdapter(this,itens);

        // Criando e configurando o recycler view
        RecyclerView rvMaterias = findViewById(R.id.rvMaterias);
        rvMaterias.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMaterias.setLayoutManager(layoutManager);

        rvMaterias.setAdapter(myAdapter);

        //Adicionando divisor entre itens.
        DividerItemDecoration divider = new DividerItemDecoration(rvMaterias.getContext(), DividerItemDecoration.VERTICAL);
        rvMaterias.addItemDecoration(divider);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_perfil,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.opPerfil:
                Intent i = new Intent(EscolherActivity.this, PerfilActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}