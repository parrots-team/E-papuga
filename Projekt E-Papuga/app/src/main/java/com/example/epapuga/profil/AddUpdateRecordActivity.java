package com.example.epapuga.profil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.example.epapuga.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class AddUpdateRecordActivity extends AppCompatActivity {


    //views
    private CircularImageView profileIv;
    private EditText nameEt, dateEt, foodEt, toyEt, wordsEt, characterEt;
    private FloatingActionButton saveBtn;

    //permission constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //image pick constants
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    //arrays of permissions
    private String[] cameraPermissions; //camera and storage
    private String[] storagePermissions; //only storage
    //variables (will contain data to save)
    private Uri imageUri;
    private String id, name, date, food, toy, words, character, addedTime, updatedTime;
    private boolean isEditMode = false;

    //db helper
    private MyDbHelper dbHelper;

    //actionbar
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_record);

        //init
        actionBar = getSupportActionBar();

        //title
        actionBar.setTitle("Add Record");

        //back button
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        //init views
        profileIv = findViewById(R.id.profileIv);
        nameEt = findViewById(R.id.nameEt);
        dateEt = findViewById(R.id.dateEt);
        foodEt = findViewById(R.id.foodEt);
        toyEt = findViewById(R.id.toyEt);
        wordsEt = findViewById(R.id.wordsEt);
        characterEt = findViewById(R.id.characterEt);
        saveBtn = findViewById(R.id.saveBtn);

        //get data from intent
        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);
        if (isEditMode) {
            //update data

            actionBar.setTitle("Update Data");

            id = intent.getStringExtra("ID");
            name = intent.getStringExtra("NAME");
            imageUri = Uri.parse(intent.getStringExtra("IMAGE"));
            date = intent.getStringExtra("DATE");
            food = intent.getStringExtra("FOOD");
            toy = intent.getStringExtra("TOY");
            words = intent.getStringExtra("WORDS");
            character = intent.getStringExtra("CHARACTER");
            addedTime = intent.getStringExtra("ADDED_TIME");
            updatedTime = intent.getStringExtra("UPDATED_TIME");

            //set data to views
            nameEt.setText(name);
            dateEt.setText(date);
            foodEt.setText(food);
            toyEt.setText(toy);
            wordsEt.setText(words);
            characterEt.setText(character);

            //if no image was selected while adding data, imageUri value will be "null"
            if(imageUri.toString().equals("null")){
                //no image, set default
                profileIv.setImageResource(R.drawable.ic_person_black);
            }
            else{
                //have image, set
                profileIv.setImageURI(imageUri);
            }

        }
        else{
            //add data

            actionBar.setTitle("Add Record");

        }

        //init db helper
        dbHelper = new MyDbHelper(this);

        //init permission arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE };
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE };

        //click image view to show image pick dialog
        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //show image pick dialog
                imagePickDialog();
            }
        });

        //click save button to save record
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });
    }

    private void inputData() {
        //get data
        name = ""+nameEt.getText().toString().trim();
        date = ""+dateEt.getText().toString().trim();
        food = ""+foodEt.getText().toString().trim();
        toy = ""+toyEt.getText().toString().trim();
        words = ""+wordsEt.getText().toString().trim();
        character = ""+characterEt.getText().toString().trim();

        if(isEditMode){
            //update data

            String timestamp = ""+System.currentTimeMillis();
            dbHelper.updateRecord(
                    ""+id,
                    ""+name,
                    ""+imageUri,
                    ""+date,
                    ""+food,
                    ""+toy,
                    ""+words,
                    ""+character,
                    ""+addedTime, //added time will be same
                    ""+timestamp //update time will be changed
            );
            Toast.makeText(this, "Zaktualizowane...", Toast.LENGTH_SHORT).show();
        }
        else {
            //new data

            //save to db
            String timestamp = ""+System.currentTimeMillis();
            long id = dbHelper.insertRecord(
                    ""+name,
                    ""+imageUri,
                    ""+date,
                    ""+food,
                    ""+toy,
                    ""+words,
                    ""+character,
                    ""+timestamp,
                    ""+timestamp
            );

            Toast.makeText(this, "Record Added against ID: "+id, Toast.LENGTH_SHORT).show();
        }
    }

    private void imagePickDialog() {
        //options to display in dialog
        String[] options = {"Camera", "Gallery"};
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //title
        builder.setTitle("Pick image From");
        //set items/options
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle clicks
                if (which==0){
                    //camera clicked
                    if (!checkCameraPermissions()){
                        requestCameraPermission();
                    }
                    else{
                        //permission already granted
                        pickFromCamera();
                    }
                }
                else if (which==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        //permission already granted
                        pickFromGallery();
                    }
                }
            }
        });

        //create/show dialog
        builder.create().show();
    }

    private void pickFromGallery() {
        //intent to pick image from gallery, the image will be returned in onActivityResult method
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*"); //only images
        startActivityForResult(galleryIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void pickFromCamera() {
        //intent to pick image from camera, the image will be returned in onActivityResult method

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Nazwa");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Opis");
        //put image uri
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //intent to open camera for image
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);

    }

    private boolean checkStoragePermission(){
        //check if storage permission is enabled or not
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission(){
        //request the storage permission
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions(){
        //check if camera permissions is enabled or not
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission(){
        //request the camera permissions
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    private void copyFileOrDirectory(String srcDir, String desDir){
        //create filler in specified directory

        try{
            File src = new File(srcDir);
            File des = new File(desDir,src.getName());
            if (src.isDirectory()){
                String[] files = src.list();
                int filesLength = files.length;
                for (String file : files){
                    String src1 = new File(src, file).getPath();
                    String dst1 = des.getPath();
                    
                    copyFileOrDirectory(src1, dst1);
                }
            }
            else {
                copyFile(src, des);
            }
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void copyFile(File srcDir, File desDir) throws IOException {
        if (!desDir.getParentFile().exists()){
            desDir.mkdirs(); //create if not exists
        }

        if (!desDir.exists()){
            desDir.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(srcDir).getChannel();
            destination = new FileOutputStream(desDir).getChannel();
            destination.transferFrom(source, 0, source.size());

            imageUri = Uri.parse(desDir.getPath()); // uri of saved image
            Log.d("ImagePath", "copyFile: "+imageUri);
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            //close resources
            if (source!=null){
                source.close();
            }
            if (destination!=null){
                destination.close();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go back by clicking back button of actionbar
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //result of permission allowed/denied

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    //if allowed returns true otherwise false
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        //both permission allowed
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this, "Aparat & Galeria uprawnienia są wymagane", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    //if allowed returns true otherwise false
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        //storage permission allowed
                        pickFromGallery();
                    }
                    else {
                        Toast.makeText(this, "Uprawnienia do galerii są wymagane...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
       //image picked from camera or gallery will be reveived here

        if(resultCode == RESULT_OK){
            //image is picked

            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //picked from gallery

                //crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //picked from camera

                //crop image
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            //croped image received
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                   Uri resultUri = result.getUri();
                   imageUri = resultUri;
                   //set image
                    profileIv.setImageURI(resultUri);

                    copyFileOrDirectory(""+imageUri.getPath(), ""+getDir("SQLiteRecordImages", MODE_PRIVATE));
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //error
                    Exception error = result.getError();
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}