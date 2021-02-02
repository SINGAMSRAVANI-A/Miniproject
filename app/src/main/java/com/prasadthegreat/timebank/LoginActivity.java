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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText mUsernametxt,mPasswordtxt;
    Button mRegbtn,mForgotbtn,mLoginbtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mUsernametxt=(EditText)findViewById(R.id.logemailtxt);
        mPasswordtxt=(EditText)findViewById(R.id.logpasstxt);
        mRegbtn=(Button)findViewById(R.id.btnRegLogin);
        mLoginbtn=(Button)findViewById(R.id.loginbtn);
        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUsernametxt.getText().toString().isEmpty() || mPasswordtxt.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please,fill details",Toast.LENGTH_SHORT).show();
                }else{
                    login_user(mUsernametxt,mPasswordtxt);
                }
            }
        });

        mRegbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });


    }

    private void login_user(EditText mUsernametxt, EditText mPasswordtxt) {
        String gmail=mUsernametxt.getText().toString().trim();
        String password=mPasswordtxt.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(gmail,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        });
    }
}