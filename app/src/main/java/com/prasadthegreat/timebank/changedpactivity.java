package com.prasadthegreat.timebank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.graphics.ColorSpace.Model;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class changedpactivity extends AppCompatActivity {

    Button mUploadimgbtn,mChangeimgbtn;
    ImageView mImage;
    Uri imageurl;

    StorageReference storageref;
    DatabaseReference root= FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changedpactivity);

        storageref=FirebaseStorage.getInstance().getReference();
        mChangeimgbtn=(Button)findViewById(R.id.changeimg);
        mImage=(ImageView)findViewById(R.id.change_image);
        mUploadimgbtn=(Button)findViewById(R.id.uploadimg);



        mChangeimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose_image();
            }
        });

        mUploadimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageurl!=null){
                    uploadToFirebase(imageurl);
                }else{
                    Toast.makeText(changedpactivity.this,"Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadToFirebase(Uri imageurl) {
    }

    private void choose_image() {
        Intent intent=new Intent();
        intent.setType("image/*");
    }

}
