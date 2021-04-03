package com.prasadthegreat.timebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

public class EditdetailsActivity extends AppCompatActivity {

    EditText mName,mId,mClass,mBranch;
    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdetails);

        mName=(EditText)findViewById(R.id.usernameupdate);
        mId=(EditText)findViewById(R.id.idnumberupdate);
        mClass=(EditText)findViewById(R.id.sectionupdate);
        mBranch=(EditText)findViewById(R.id.branchupdate);
        mSubmit=(Button)findViewById(R.id.updatedetailsbtn);

        String name=mName.getText().toString().trim();
        String id=mId.getText().toString().trim();
        String branch=mBranch.getText().toString().trim();
        String section=mBranch.getText().toString().trim();


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data=name+" "+id+" "+branch+" "+section+"Prasad";
                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                updatedetails(name,id,branch,section);
            }
        });

    }

    private void updatedetails(String name, String id, String branch, String section) {
        String currentuser= FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users");
        HashMap<String,String> user_details=new HashMap<>();


        String data=currentuser.toString().trim();

        user_details.put("id",id);
        user_details.put("name",name);
        user_details.put("branch",branch);
        user_details.put("section",section);

        myRef.child(currentuser).push().setValue(user_details).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mName.setText("");
                    mId.setText("");
                    mBranch.setText("");
                    mClass.setText("");
                    Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}