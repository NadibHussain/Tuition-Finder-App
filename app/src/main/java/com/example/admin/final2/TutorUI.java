package com.example.admin.final2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TutorUI extends AppCompatActivity {
    TextView uname,tutor;
    ImageView img;
    ListView listView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<String> list;
    ListAdapter adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_ui);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        SharedPreferences sharedPreferences=getSharedPreferences("account",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("accounttype","tutor");
        list=new ArrayList<>();
        adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView=findViewById(R.id.listview);
        editor.commit();
        uname=findViewById(R.id.textView16);
        img=findViewById(R.id.imageView2);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database.getReference("message").child(uid);
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String,Object> map = (Map<String, Object>) dataSnapshot.getValue();
                if(map!=null)
                {
                    for(String s:map.keySet())
                    {   Toast.makeText(TutorUI.this, s, Toast.LENGTH_SHORT).show();
                        String t=s.replace('_','@');
                        list.add(t);
                    }
                    listView.setAdapter(adapter2);
                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        DatabaseReference myRef = database.getReference("Users").child(uid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String s="";
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                uname.setText(map.get("name").toString().toUpperCase());
                Picasso.get().load(map.get("Image").toString()).into(img);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
    public void signout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent i=new Intent(TutorUI.this,MainActivity.class);
        startActivity(i);

    }
}
