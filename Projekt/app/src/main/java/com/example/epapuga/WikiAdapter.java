package com.example.epapuga;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class WikiAdapter extends RecyclerView.Adapter<WikiAdapter.MyViewHolder>
{

    String data1[], data2[], data3[];
    int images[];
    Context context;

    public WikiAdapter(Context ct, String s1[], String s2[],String s3[], int img[])
    {
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 =s3;
        images = img;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.papugedia_txt.setText(data1[position]);
        holder.opis_txt.setText(data2[position]);
        holder.opis_txt2.setText(data3[position]);
        holder.wikiImgView.setImageResource(images[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WikiActivity2.class);
                intent.putExtra("data1",data1[position]);
                intent.putExtra("data2",data2[position]);
                intent.putExtra("data3",data3[position]);
                intent.putExtra("wikiImgView",images[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView papugedia_txt, opis_txt, opis_txt2;
        ImageView wikiImgView;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            papugedia_txt = itemView.findViewById(R.id.papugedia_txt);
            opis_txt = itemView.findViewById(R.id.opis_txt);
            wikiImgView = itemView.findViewById(R.id.wikiImgView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            opis_txt2 = itemView.findViewById(R.id.opis_txt2);
        }
    }

}
