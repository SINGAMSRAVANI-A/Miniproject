package com.prasadthegreat.timebank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class leaderboardadapter extends FirebaseRecyclerAdapter<homemodel,leaderboardadapter.leaderviewholder> {

    public leaderboardadapter(@NonNull FirebaseRecyclerOptions<homemodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull leaderviewholder holder, int position, @NonNull homemodel model) {
        holder.idnum.setText(""+(position+1));
        holder.mName.setText(model.getName());
        holder.mPoints.setText(model.getTimecredits());
    }

    @NonNull
    @Override
    public leaderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleleaderdata,parent,false);
        return new leaderviewholder(view);
    }

    public class leaderviewholder extends RecyclerView.ViewHolder{

        TextView mName,mPoints,idnum;
        public leaderviewholder(@NonNull View itemView) {
            super(itemView);


            idnum=(TextView)itemView.findViewById(R.id.idnum);
            mName=(TextView)itemView.findViewById(R.id.leadername);
            mPoints=(TextView)itemView.findViewById(R.id.leaderpoints);
        }
    }
}
