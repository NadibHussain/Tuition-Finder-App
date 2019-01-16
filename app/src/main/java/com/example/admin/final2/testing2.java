package com.example.admin.final2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testing2 extends AppCompatActivity {
    Map<String, Object> map;
    Object[] users;
    String[] unames;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<listmodel> listmodels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing2);
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listmodels=new ArrayList<>();
        readData(new MyCallback() {
            @Override
            public void onCallback(Object[] o) {

                for (int x=0;x<o.length;x++)
                {
                    HashMap h=(HashMap)o[x];
                    Toast.makeText(testing2.this, h.get("name").toString()+" test", Toast.LENGTH_SHORT).show();
                    if (h!=null) {
                        listmodel l = new listmodel(h.get("name").toString().toUpperCase(), h.get("institution").toString().toUpperCase(), h.get("Image").toString(), h.get("id").toString());
                        listmodels.add(l);
                    }
                }
                adapter=new recycleAdapter(listmodels,testing2.this);
                recyclerView.setAdapter(adapter);

            }
        });


    }

    public void readData(final MyCallback myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        String x=(getIntent().getStringExtra("range"));
        //Toast.makeText(this, ""+x, Toast.LENGTH_SHORT).show();
        Query q = myRef.orderByChild("publicprivate").equalTo(x);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String s="";
                map = (Map<String, Object>) dataSnapshot.getValue();
                Toast.makeText(testing2.this, map.size()+"", Toast.LENGTH_SHORT).show();
                if(map!=null){
                    Object[] keys= map.keySet().toArray();
                    users=new Object[keys.length];
                    for (int x=0;x<map.size();x++)
                    {
                        users[x]=map.get(keys[x].toString());
                    }
                    myCallback.onCallback(users);

                }
                else
                {
                    Toast.makeText(testing2.this, "No Tutor found", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(testing2.this,studentUI.class);
                    startActivity(i);
                }

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
    public interface MyCallback {
        void onCallback(Object[] o);
    }
}

