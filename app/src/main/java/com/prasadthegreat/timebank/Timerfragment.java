package com.prasadthegreat.timebank;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Timerfragment extends Fragment {

    TextView tvSplash,tvSubSplash;
    Button btnget;
    Animation atg,btgone,btgtwo;
    ImageView ivSplash;
    String data;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Timerfragment() {
    }
    public static Timerfragment newInstance(String param1, String param2) {
        Timerfragment fragment = new Timerfragment();
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
       View view=inflater.inflate(R.layout.fragment_timerfragment, container, false);

        tvSplash=view.findViewById(R.id.tvSplash);
        tvSubSplash=view.findViewById(R.id.tvSubSplash);
        btnget=view.findViewById(R.id.btnget);
        ivSplash=view.findViewById(R.id.ivSplash);

        //load Animations
        atg= AnimationUtils.loadAnimation(getContext(),R.anim.atg);
        btgone= AnimationUtils.loadAnimation(getContext(),R.anim.btgone);
        btgtwo= AnimationUtils.loadAnimation(getContext(),R.anim.btgtwo);

        //passing Animation
        ivSplash.startAnimation(atg);
        tvSplash.startAnimation(btgone);
        tvSubSplash.startAnimation(btgone);
        btnget.startAnimation(btgtwo);

        String currentuid= FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference("notifications").child(currentuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    data = snapshot.getValue().toString().trim();
                    FirebaseDatabase.getInstance().getReference("users").child(data).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                               String name = snapshot.getValue().toString().trim();
                               tvSubSplash.setText(name + " is intrested to do your task");
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //passing event
        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getContext(),TimerActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                a.putExtra("userid",data);
                startActivity(a);
            }
        });

        return view;
    }
}