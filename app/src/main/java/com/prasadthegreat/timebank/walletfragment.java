package com.prasadthegreat.timebank;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class walletfragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public walletfragment() {
    }
    public static walletfragment newInstance(String param1, String param2) {
        walletfragment fragment = new walletfragment();
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

        View view=inflater.inflate(R.layout.fragment_walletfragment, container, false);

        getFragmentManager().beginTransaction().replace(R.id.dashboardcontainer,new leaderboardfragment()).commit();

        Button mtop5=(Button)view.findViewById(R.id.top5btn);
        Button mcredits=(Button)view.findViewById(R.id.freecreditsbtn);
        Button mhelp=(Button)view.findViewById(R.id.helpbtn);

        mtop5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new leaderboardfragment();
                getFragmentManager().beginTransaction().replace(R.id.dashboardcontainer,fragment).commit();
            }
        });

        mcredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new freecreditsfragment();
                getFragmentManager().beginTransaction().replace(R.id.dashboardcontainer,fragment).commit();
            }
        });

        mhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new helpfragment();
                getFragmentManager().beginTransaction().replace(R.id.dashboardcontainer,fragment).commit();
            }
        });

        return view;
    }

}