package com.prasadthegreat.timebank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

public class homeadapterclass extends FirebaseRecyclerAdapter<homemodel,homeadapterclass.myviewholder>
{
    public homeadapterclass(@NonNull FirebaseRecyclerOptions<homemodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull homemodel model) {
            holder.worktxt1.setText(model.getWorktitle());
            holder.worktitle2.setText(model.getWorkdata());

            holder.worktxt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity=(AppCompatActivity)view.getContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,new Taskdetailsfragment(model.getName(),model.getId(),model.getWorktitle(),model.getWorkdata(),model.getMid()))
                            .addToBackStack(null).commit();
                }
            });

            holder.worktitle2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlehomedata,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{


        TextView worktxt1,worktitle2;
        public myviewholder(@NonNull View itemView) {

            super(itemView);
            worktxt1=(TextView)itemView.findViewById(R.id.worktxt);
            worktitle2=(TextView)itemView.findViewById(R.id.worktitle);
        }
    }
}
