package com.prasadthegreat.timebank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class changedpactivity extends AppCompatActivity {


    StorageReference storageref;
    Button mUploadimgbtn,mChangeimgbtn;
    ImageView mImage;
    Uri imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changedpactivity);

        storageref=FirebaseStorage.getInstance().getReference();
        mChangeimgbtn=(Button)findViewById(R.id.changeimg);
        mImage=(ImageView)findViewById(R.id.change_image);



        mChangeimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose_image();
            }
        });
    }

    void choose_image() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && requestCode==RESULT_OK && data!=null && data.getData()!=null){
            imageurl=data.getData();
            mImage.setImageURI(imageurl);
            uploadimage();
        }
    }

    void uploadimage() {

        ProgressDialog progressDialog=new ProgressDialog(getApplicationContext());
        progressDialog.setTitle("uploading....");
        progressDialog.show();
        final String randomkey= FirebaseAuth.getInstance().getUid().toString().trim();
        StorageReference riversRef = storageref.child("images/"+randomkey);

        riversRef.putFile(imageurl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(R.id.content),"Image Uploaded",Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"upload Failed....",Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressprecentage=(100.00*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setMessage("Percentage: "+(int) progressprecentage + "%");
            }
        });
    }
}