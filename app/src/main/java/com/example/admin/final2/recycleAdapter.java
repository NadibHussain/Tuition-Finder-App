package com.example.admin.final2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class recycleAdapter extends RecyclerView.Adapter<recycleAdapter.ViewHolder> {
    List<listmodel>listmodels;
    private Context context;

    public recycleAdapter(List<listmodel> listmodels, Context context) {
        this.listmodels = listmodels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        listmodel l=listmodels.get(position);
        holder.name.setText(l.getName());
        holder.uni.setText(l.getUni());
        holder.id.setText(l.getUid());

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user.getEmail();
                String name = user.getDisplayName();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                String path1="hello my email id "+email+" and i want to hire you";
                String path=path1.replace('.','_');
                myRef.child(holder.id.getText().toString()).child(path).setValue("true");
                Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
            }
        });
        Picasso.get().load(l.getImg()).into( holder.img);

    }

    @Override
    public int getItemCount() {
        return listmodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView name,uni,id;
    Button btn;
    public ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.textView21);
            name=itemView.findViewById(R.id.itemname);
            uni=itemView.findViewById(R.id.uni);
            btn=itemView.findViewById(R.id.button10);
            img=itemView.findViewById(R.id.imageView3);
        }
    }
}
