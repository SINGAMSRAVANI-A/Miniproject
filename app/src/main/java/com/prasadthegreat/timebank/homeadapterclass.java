package com.prasadthegreat.timebank;

import android.content.Context;
import android.net.Uri;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class homeadapterclass extends FirebaseRecyclerAdapter<homemodel,homeadapterclass.myviewholder>
{
    Context context;
    ImageView imageView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
    private FirebaseAuth Auth = FirebaseAuth.getInstance();
    public homeadapterclass(@NonNull FirebaseRecyclerOptions<homemodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull homemodel model) {

            holder.worktxt1.setText(model.getWorktitle());
            holder.worktitle2.setText(model.getWorkdata());

            Uri imagelink= Uri.parse(model.getProfilepic());
            Picasso.get().load(imagelink).into(holder.imageView);
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
        getUserInfo();
    }

    private void getUserInfo() {
        databaseReference.child(Auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount()>0){
                    //String name = snapshot.child("name").getValue().toString();

                    if(snapshot.hasChild("image")){
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        ImageView imageView;
        public myviewholder(@NonNull View itemView) {

            super(itemView);

            imageView=(ImageView) itemView.findViewById(R.id.image);
            worktxt1=(TextView)itemView.findViewById(R.id.worktxt);
            worktitle2=(TextView)itemView.findViewById(R.id.worktitle);
        }
    }
}
