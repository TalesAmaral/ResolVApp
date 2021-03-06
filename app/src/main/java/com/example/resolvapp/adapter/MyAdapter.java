package com.example.resolvapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resolvapp.R;
import com.example.resolvapp.activity.EscolherActivity;
import com.example.resolvapp.activity.FazerActivity;
import com.example.resolvapp.model.Materia;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {

    List<Materia> itens;
    EscolherActivity escolherActivity;
    //Declarando o construtor
    public MyAdapter(EscolherActivity escolherActivity,List<Materia> itens){
        this.escolherActivity = escolherActivity;
        this.itens = itens;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //pegando o inflador da atividade principal
        LayoutInflater inflater = LayoutInflater.from(escolherActivity);
        View v = inflater.inflate(R.layout.layout_materia,parent,false);
        return new MyViewHolder(v);
        //Essa funcao constroi o layout
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Materia myItem = itens.get(position);
        View v = holder.itemView;
        //Setando a foto, o titulo e a descrição
        ImageView imvPhoto = v.findViewById(R.id.imvMateria);
        imvPhoto.setImageResource(myItem.icon);

        TextView tvTitle = v.findViewById(R.id.tvMateria);
        tvTitle.setText(myItem.title);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), FazerActivity.class);
                i.putExtra("idMateria", ""+myItem.id);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}