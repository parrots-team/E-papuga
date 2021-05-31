package com.example.epapuga.profil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.epapuga.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.HolderRecord> {

    //variables
    private Context context;
    private ArrayList<ModelRecord> recordsList;

    //DB helper
    MyDbHelper dbHelper;

    //constructor
    public AdapterRecord(Context context, ArrayList<ModelRecord> recordsList) {
        this.context = context;
        this.recordsList = recordsList;

        dbHelper = new MyDbHelper(context);
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
        final String id = model.getId();
        final  String name = model.getName();
        final String image = model.getImage();
        final String date = model.getDate();
        final String food = model.getFood();
        final String toy = model.getToy();
        final String words = model.getWords();
        final String character = model.getCharacter();
        final String addedTime = model.getAddedTime();
        final String updateTime = model.getUpdatedTime();

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
                //show options menu
                showMoreDialog(
                        ""+position,
                        ""+id,
                        ""+name,
                        ""+image,
                        ""+date,
                        ""+food,
                        ""+toy,
                        ""+words,
                        ""+character,
                        ""+addedTime,
                        ""+updateTime
                        );
            }
        });

        Log.d("ImagePath", "onBindViewHolder: "+image);

    }

    private void showMoreDialog(String position, String id, String name, String image,
                                String date, String food, String toy, String words, String character,
                                String addedTime, String updateTime) {

        //options to display in dialog
        String[] options = {"Edit", "Delete"};
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //add items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle item clicks
                if (which==0){
                    //Edit is clicked

                    //start AddUpdateRecordActivity to update existing record
                    Intent intent = new Intent(context, AddUpdateRecordActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("NAME", name);
                    intent.putExtra("IMAGE", image);
                    intent.putExtra("DATE", date);
                    intent.putExtra("FOOD", food);
                    intent.putExtra("TOY", toy);
                    intent.putExtra("WORDS", words);
                    intent.putExtra("CHARACTER", character);
                    intent.putExtra("ADDED_TIME", addedTime);
                    intent.putExtra("UPDATED_TIME", updateTime);
                    intent.putExtra("isEditMode", true);
                    context.startActivity(intent);
                }
                else if (which==1){
                    //delete is clicked
                    dbHelper.deleteData(id);
                    //refresh record by calling activity's onResume method
                    ((Profil)context).onResume();
                }
            }
        });
        //show dialog
        builder.create().show();
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
