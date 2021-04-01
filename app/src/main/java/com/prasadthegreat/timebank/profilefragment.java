package com.prasadthegreat.timebank;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class profilefragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;
    Button mChangedetailsbtn, mLogoutButton;
    ImageView mChangedpbtn;
    private Toolbar mToolbar;
    TextView mname,mid,taskcomplete,timecredits,mstatus;

    public profilefragment() {

    }
    public static profilefragment newInstance(String param1, String param2) {
        profilefragment fragment = new profilefragment();
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

        View view=inflater.inflate(R.layout.fragment_profilefragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mChangedetailsbtn=(Button)view.findViewById(R.id.changedatabtn);
        mChangedpbtn=(ImageView)view.findViewById(R.id.profile_image);
        mLogoutButton = (Button) view.findViewById(R.id.logout);

        mname=(TextView)view.findViewById(R.id.fullname_field);
        mid=(TextView)view.findViewById(R.id.profile_id);
        taskcomplete=(TextView)view.findViewById(R.id.task_label);
        timecredits=(TextView)view.findViewById(R.id.timecredits_label);
        mstatus=(TextView)view.findViewById(R.id.profile_status);


        if(user!=null){
            String uid=user.getUid();
            DatabaseReference namedb= FirebaseDatabase.getInstance().getReference("users").child(uid);
           namedb.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   String name=snapshot.child("name").getValue().toString().trim();
                   String id=snapshot.child("id").getValue().toString().trim();
                   String task=snapshot.child("taskcompleted").getValue().toString().trim();
                   String time=snapshot.child("timecredits").getValue().toString().trim();
                   String status=snapshot.child("status").getValue().toString().trim();

                   taskcomplete.setText(task);
                   timecredits.setText(time);
                   mid.setText("id: "+id);
                   mname.setText(name);
                   mstatus.setText(status);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });

           mLogoutButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mAuth.signOut();
                   Toast.makeText(getActivity().getApplicationContext(), "Logged Out", Toast.LENGTH_LONG).show();
                   startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
               }
           });

        }



        mChangedetailsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),EditdetailsActivity.class);
                startActivity(intent);
            }
        });

        mChangedpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),changedpactivity.class);
                startActivity(intent);
            }
        });
        mToolbar=(Toolbar) view.findViewById(R.id.mytoolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);



        return view;
    }

}