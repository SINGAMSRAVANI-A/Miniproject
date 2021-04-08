package com.prasadthegreat.timebank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class leaderboardfragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    leaderboardadapter myleaderadapter;

    public leaderboardfragment() {}

    public static leaderboardfragment newInstance(String param1, String param2) {
        leaderboardfragment fragment = new leaderboardfragment();
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
        View view=inflater.inflate(R.layout.fragment_leaderboard, container, false);
       RecyclerView mrecyclerView=(RecyclerView)view.findViewById(R.id.leaderboardrecycler);


        mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<homemodel> options =
                new FirebaseRecyclerOptions.Builder<homemodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("timecredits").limitToLast(5), homemodel.class)
                        .build();
        myleaderadapter=new leaderboardadapter(options);
        mrecyclerView.setAdapter(myleaderadapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myleaderadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myleaderadapter.stopListening();
    }
}