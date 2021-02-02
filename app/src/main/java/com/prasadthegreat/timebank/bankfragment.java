package com.prasadthegreat.timebank;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class bankfragment extends Fragment {

    EditText mWorktitle,mWorkdata;
    Button mPosttaskbtn;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public bankfragment() {

    }
    public static bankfragment newInstance(String param1, String param2) {
        bankfragment fragment = new bankfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bankfragment, container, false);

        mWorktitle=(EditText)view.findViewById(R.id.wordtileedit);
        mWorkdata=(EditText)view.findViewById(R.id.workdataedit);
        mPosttaskbtn=(Button)view.findViewById(R.id.posttaskbtn);
        mPosttaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users").child(uid);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name=snapshot.child("name").getValue().toString().trim();
                        String id=snapshot.child("id").getValue().toString().trim();
                        String worktitle=mWorktitle.getText().toString().trim();
                        String workdata=mWorkdata.getText().toString().trim();
                        String profile_pic=snapshot.child("profilepic").getValue().toString().trim();
                        String userid=snapshot.child("mid").getValue().toString().trim();

                        HashMap<String,String> taskdata=new HashMap<>();
                        taskdata.put("worktitle",worktitle);
                        taskdata.put("workdata",workdata);
                        taskdata.put("id",id);
                        taskdata.put("name",name);
                        taskdata.put("profilepic",profile_pic);
                        taskdata.put("mid",userid);


                        FirebaseDatabase.getInstance().getReference().child("tasklist").child(uid).setValue(taskdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if ((task.isSuccessful())){
                                    Toast.makeText(getContext(),"Task Posted successfully",Toast.LENGTH_SHORT).show();
                                    mWorkdata.setText("");
                                    mWorktitle.setText("");
                                }else{
                                    Toast.makeText(getContext(),"Please,Try again",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });
        return view;
    }
}