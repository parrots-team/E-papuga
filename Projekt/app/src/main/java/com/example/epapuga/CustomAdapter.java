package com.example.epapuga;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList<String> id_papuga, title, opis, data, jedzenie, zabawki;

    Animation translate_anim;


    CustomAdapter(Activity activity, Context context,
                  ArrayList id_papuga,
                  ArrayList title,
                  ArrayList opis,
                  ArrayList data,
                  ArrayList jedzenie,
                  ArrayList zabawki){
        this.activity = activity;
        this.context = context;
        this.id_papuga = id_papuga;
        this.title = title;
        this.opis = opis;
        this.data = data;
        this.jedzenie = jedzenie;
        this.zabawki = zabawki;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_profil, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.papuga_id_txt.setText(String.valueOf(id_papuga.get(position)));
        holder.papuga_title_txt.setText(String.valueOf(title.get(position)));
        holder.papuga_opis_txt.setText(String.valueOf(opis.get(position)));
        holder.papuga_urodziny_txt.setText(String.valueOf(data.get(position)));
        holder.papuga_jedzenie_txt.setText(String.valueOf(jedzenie.get(position)));
        holder.papuga_zabawki_txt.setText(String.valueOf(zabawki.get(position)));
        holder.profilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id_papuga", String.valueOf(id_papuga.get(position)));
                intent.putExtra("title", String.valueOf(title.get(position)));
                intent.putExtra("opis", String.valueOf(opis.get(position)));
                intent.putExtra("data", String.valueOf(data.get(position)));
                intent.putExtra("jedzenie", String.valueOf(jedzenie.get(position)));
                intent.putExtra("zabawki", String.valueOf(zabawki.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });





    }

    @Override
    public int getItemCount() {
        return id_papuga.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView papuga_id_txt, papuga_title_txt, papuga_opis_txt, papuga_urodziny_txt,papuga_jedzenie_txt, papuga_zabawki_txt  ;
        LinearLayout profilLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            papuga_id_txt = itemView.findViewById(R.id.papuga_id_txt);
            papuga_title_txt = itemView.findViewById(R.id.papuga_title_txt);
            papuga_opis_txt = itemView.findViewById(R.id.papuga_opis_txt);
            papuga_urodziny_txt = itemView.findViewById(R.id.papuga_urodziny_txt);
            papuga_jedzenie_txt = itemView.findViewById(R.id.papuga_jedzenie_txt);
            papuga_zabawki_txt = itemView.findViewById(R.id.papuga_zabawki_txt);
            profilLayout = itemView.findViewById(R.id.profilLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            profilLayout.setAnimation(translate_anim);

        }
    }
}
