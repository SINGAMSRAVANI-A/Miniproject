package com.prasadthegreat.timebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText mRegemail,mRegpswd,mRegname,mRegid;
    Button mRegbtn,mLoginbtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mRegemail=(EditText)findViewById(R.id.regmail);
        mRegpswd=(EditText)findViewById(R.id.regpassword);
        mRegname=(EditText)findViewById(R.id.regname);
        mRegid=(EditText)findViewById(R.id.regid);
        mRegbtn=(Button)findViewById(R.id.regbtn);
        mLoginbtn=(Button)findViewById(R.id.btnLogRegister);
        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        mRegbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=mRegid.getText().toString().trim();
                String name=mRegname.getText().toString().trim();
                String mail=mRegemail.getText().toString().trim();
                String password=mRegpswd.getText().toString().trim();

                if(id.isEmpty() || name.isEmpty() || mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Please,Fill details",Toast.LENGTH_SHORT).show();
                }else{
                    register_user(id,name,mail,password);
                }
            }
        });
    }

    private void register_user(String id, String name, String mail, String password) {
        mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String currentuser=FirebaseAuth.getInstance().getCurrentUser().getUid();


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("users");
                    HashMap<String,String> user_details=new HashMap<>();


                    String data=currentuser.toString().trim();

                    user_details.put("id",id);
                    user_details.put("name",name);
                    user_details.put("mail",mail);
                    user_details.put("password",password);
                    user_details.put("taskcompleted","0");
                    user_details.put("timecredits","10");
                    user_details.put("profilepic","null");
                    user_details.put("status","Hey,i am using time bank");
                    user_details.put("mid",data);

                    myRef.child(currentuser).setValue(user_details).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Registration Success",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"please,try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}