package com.prasadthegreat.timebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.StringValue;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {

    Button btnstart,btnstop;
    ImageView icanchor;
    Animation roundingalone;
    Chronometer timerHere;
    int elapsedMillis = 0;
    static DecimalFormat df2 = new DecimalFormat("#.##");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        btnstart=findViewById(R.id.btnstart);
        btnstop=findViewById(R.id.btnstop);
        icanchor=findViewById(R.id.icanchor);
        timerHere=findViewById(R.id.timerHere);

        //Create optional animation
        btnstop.setAlpha(0);

        //load animations
        roundingalone= AnimationUtils.loadAnimation(this,R.anim.roundingalone);



        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //passing animation
                icanchor.startAnimation(roundingalone);
                btnstop.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnstart.animate().alpha(0).setDuration(300).start();

                //start time

                timerHere.setBase(SystemClock.elapsedRealtime());
                timerHere.start();

            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icanchor.clearAnimation();
                timerHere.stop();
                elapsedMillis = (int) (SystemClock.elapsedRealtime() -timerHere.getBase());
                long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedMillis);
                String time=String.valueOf(seconds);
                Toast.makeText(getApplicationContext(), time,Toast.LENGTH_LONG).show();

                Intent intent=getIntent();

                String current_user= intent.getStringExtra("userid");

                FirebaseDatabase.getInstance().getReference("users").child(current_user).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String pre_time=snapshot.child("timecredits").getValue().toString().trim();
                        float current_time=Float.parseFloat(pre_time)+ (Float.parseFloat(time)/60);
                        FirebaseDatabase.getInstance().getReference("users").child(current_user).child("timecredits").setValue(df2.format(current_time));

                        String pre_task=snapshot.child("taskcompleted").getValue().toString().trim();
                        int current_task=Integer.parseInt(pre_task)+1;
                        FirebaseDatabase.getInstance().getReference("users").child(current_user).child("taskcompleted").setValue(current_task);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}