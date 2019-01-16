package com.example.admin.final2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class testing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String s=getIntent().getStringExtra("url");

        DatabaseReference myRef = database.getReference("Users").child(user.getUid().toString());
        myRef.child("Image").setValue(getIntent().getStringExtra("url"));
        if(getIntent().getStringExtra("type").equals("t")){
            Intent i =new Intent(testing.this,TutorUI.class);
            startActivity(i);
        }
        else {
            Intent i = new Intent(testing.this, studentUI.class);
            startActivity(i);
        }
    }
}
