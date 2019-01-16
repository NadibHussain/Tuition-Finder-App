package com.example.admin.final2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class searchResult extends AppCompatActivity {
TextView tx;
DatabaseReference uiref;
private RecyclerView recyclerView;
private RecyclerView.Adapter adapter;
private List<listmodel> listmodels;
JSONArray array;
TextView t;
Object[] users;
String[] unames;
Map<String, Object> map;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listmodels=new ArrayList<>();
        unames=new String[20];





        adapter=new recycleAdapter(listmodels,this);
        recyclerView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        String email = user.getEmail();
        String uid = user.getUid();
        readData(new testing2.MyCallback() {
            @Override
            public void onCallback(Object[] o) {
               for (int x=0;x<o.length;x++)
               {
                   HashMap h=(HashMap)o[0];
                   listmodel l=new listmodel(h.get("name").toString(),h.get("name").toString(),h.get("name").toString());
                   listmodels.add(l);
               }
            }
        });



    }
    public void readData(final testing2.MyCallback myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        Query q = myRef.orderByChild("salary").equalTo(2);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String s="";
                map = (Map<String, Object>) dataSnapshot.getValue();
                Object[] keys= map.keySet().toArray();
                users=new Object[keys.length];
                for (int x=0;x<map.size();x++)
                {
                    users[x]=map.get(keys[x].toString());
                }
                myCallback.onCallback(users);
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
