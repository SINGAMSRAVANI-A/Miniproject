package com.prasadthegreat.timebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mbottomNavigationView;
    FloatingActionButton mfloatButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        mbottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavView);
        mbottomNavigationView.setBackground(null);
        Menu menuNav=mbottomNavigationView.getMenu();
        MenuItem nav_item2 = menuNav.findItem(R.id.nothing);
        nav_item2.setEnabled(false);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new Homefragment()).commit();
        mbottomNavigationView.setOnNavigationItemSelectedListener(bottomnavMethod);

        mfloatButton=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        mfloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment  fragment=new bankfragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });

    }

    private  BottomNavigationView.OnNavigationItemSelectedListener bottomnavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment=null;
                    switch (item.getItemId()){

                        case R.id.home:
                            fragment=new Homefragment();
                            break;
                        case R.id.timer:
                            fragment=new Timerfragment();
                            break;
                        case R.id.credits:
                            fragment=new walletfragment();
                            break;
                        case R.id.profile:
                            fragment=new profilefragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                    return true;
                }
            };

    @Override
    public void onStart() {
        super.onStart();

       // FirebaseAuth.getInstance().signOut();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        }

    }
}