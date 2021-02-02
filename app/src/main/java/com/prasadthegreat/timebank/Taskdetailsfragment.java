package com.prasadthegreat.timebank;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Taskdetailsfragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    Button mSendreq;
    String name,id,worktitle,workdata,mid;

    public Taskdetailsfragment() {
    }

    public Taskdetailsfragment(String name, String id, String worktitle, String workdata, String mid) {
        this.name = name;
        this.id = id;
        this.worktitle = worktitle;
        this.workdata = workdata;
        this.mid = mid;
    }

    public static Taskdetailsfragment newInstance(String param1, String param2) {
        Taskdetailsfragment fragment = new Taskdetailsfragment();
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
        View view= inflater.inflate(R.layout.fragment_taskdetailsfragment, container, false);

       TextView nametxt=view.findViewById(R.id.nameholder);
       TextView Idtxt=view.findViewById(R.id.idholder);
       TextView worktitletxt=view.findViewById(R.id.Titleholder);
       TextView workdatatxt=view.findViewById(R.id.dataholder);
       mSendreq=(Button)view.findViewById(R.id.sendreqbtn);
       nametxt.setText(name);
       Idtxt.setText(id);
       worktitletxt.setText(worktitle);
       workdatatxt.setText(workdata);

       mSendreq.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String current_uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
               FirebaseDatabase.getInstance().getReference().child("notifications").child(mid).setValue(current_uid);
               Toast.makeText(getContext(),"Request Sent Successfull",Toast.LENGTH_LONG).show();
           }
       });
       return view;
    }

    public void onBackpressed(){
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,new Homefragment()).addToBackStack(null).commit();
    }
}