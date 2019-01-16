package com.example.admin.final2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createuserstudent extends AppCompatActivity {
EditText name,phone;
RadioGroup r;
Student u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuserstudent);
        name=findViewById(R.id.editText7);
        phone=findViewById(R.id.editText8);
        r=findViewById(R.id.rg);
        u=new Student();

    }

    public void submit(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid = user.getUid();
        DatabaseReference myRef = database.getReference("Users");
        int x=r.getCheckedRadioButtonId();
        RadioButton sel=findViewById(x);
        u.setArea(sel.getText().toString());
        u.setName(name.getText().toString());
        u.setPhone(phone.getText().toString());
        myRef.child(uid).setValue(u);
        Intent i=new Intent(createuserstudent.this,uploadingImage.class);
        i.putExtra("type","s");
        startActivity(i);

    }
}
