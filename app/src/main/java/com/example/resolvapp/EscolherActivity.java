package com.example.resolvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class EscolherActivity extends AppCompatActivity {


    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher);




        List<Materia> itens = new ArrayList<>();

        int[] nums = {R.drawable.ic_baseline_calculate_24,R.drawable.ic_baseline_science_24};
        String[] titles = {"Matemática","Química"};

        for (int i=0; i<nums.length;i++){
            Materia mat = new Materia();
            mat.icon = nums[i];
            mat.title = titles[i];
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
}