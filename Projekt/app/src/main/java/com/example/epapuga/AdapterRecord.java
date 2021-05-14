package com.example.epapuga;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.HolderRecord> {

    //variables
    private Context context;
    private ArrayList<ModelRecord> recordsList;

    //constructor
    public AdapterRecord(Context context, ArrayList<ModelRecord> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
    }

    @NonNull
    @NotNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_record, parent, false);
        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterRecord.HolderRecord holder, int position) {
        //get data, set data, handel view clicks in this method

        //get data
        ModelRecord model = recordsList.get(position);
        String id = model.getId();
        String name = model.getName();
        String image = model.getImage();
        String date = model.getDate();
        String food = model.getFood();
        String toy = model.getToy();
        String words = model.getWords();
        String character = model.getCharacter();
        String addedTime = model.getAddedTime();
        String updateTime = model.getUpdatedTime();

        //set data to views
        holder.nameTv.setText(name);
        holder.dateTv.setText(date);
        //if user doesn't attach image then imageUri will be null, so set a default image in that case
        if (image.equals("null")){
            //no image in record, set default
            holder.profileIv.setImageResource(R.drawable.ic_person_black);
        }
        else{
            //have image in record
            holder.profileIv.setImageURI(Uri.parse(image));
        }



        //handle item clicks (go to detail record activity)
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecordDetailActivity.class);
                intent.putExtra("RECORD_ID", id);
                context.startActivity(intent);
            }
        });

        //handle more button click listener (show options like edit, delete et)
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return recordsList.size(); //return size of list/number of records
    }

    class HolderRecord extends RecyclerView.ViewHolder{

        //views
        ImageView profileIv;
        TextView nameTv, dateTv;
        ImageButton moreBtn;

        public HolderRecord(@NonNull View itemView) {
            super(itemView);

            //init views
            profileIv = itemView.findViewById(R.id.profileIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            moreBtn = itemView.findViewById(R.id.moreBtn);
        }

    }
}
