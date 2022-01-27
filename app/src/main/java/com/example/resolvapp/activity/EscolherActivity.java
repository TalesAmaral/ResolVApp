package com.example.resolvapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
}