package com.example.admin.final2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner sp,sp2,sp3;
    EditText ed,ed2;
    String uid;
    User u;
    RadioGroup rg;
    RadioGroup pub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        pub=findViewById(R.id.publicprivate);
        sp=findViewById(R.id.spinner2);
        sp3=findViewById(R.id.spinner);

        ed=findViewById(R.id.editText5);
        ed2=findViewById(R.id.editText6);
        rg=findViewById(R.id.radiogroup);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.grades,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.areas,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp3.setAdapter(adapter2);
        sp.setOnItemSelectedListener(this);


        u=new User();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        String email = user.getEmail();
        u.setEmail(email);
        u.setId(uid);


        u.setAccountType(getIntent().getStringExtra("type"));

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner s=(Spinner)adapterView;
        if(s.getId()==R.id.spinner){
            if(i!=0){
                String text=adapterView.getItemAtPosition(i).toString();
                u.setGrade(text);
                Toast.makeText(this, "class", Toast.LENGTH_SHORT).show();

            }

        }
        if(s.getId()==R.id.spinner2){
            if(i!=0){
                String text=adapterView.getItemAtPosition(i).toString();
                u.setArea(text,0);
                Toast.makeText(this, "Area1", Toast.LENGTH_SHORT).show();

            }

        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void sumbitdata(View view) {
        int x=rg.getCheckedRadioButtonId();
        int y=pub.getCheckedRadioButtonId();
        RadioButton publicprivate=findViewById(y);
        RadioButton sel=findViewById(x);
        if(sel!=null){
            if (sel.getText().toString().equals("3000-6000"))
            {
                u.setSalary(1);
            }
            if (sel.getText().toString().equals("7000-10000"))
            {
                u.setSalary(2);
            }
            if (sel.getText().toString().equals("10000-15000"))
            {
                u.setSalary(3);
            }

        }
        if(publicprivate!=null){
            if (publicprivate.getText().toString().equals("public"))
            {
                u.setPublicprivate("public");
            }
            if (publicprivate.getText().toString().equals("private"))
            {
                u.setPublicprivate("private");
            }

        }


        u.setName(ed.getText().toString());
        u.setInstitution(ed2.getText().toString());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.child(u.getId()+"").setValue(u);
        Intent i=new Intent(createUser.this,uploadingImage.class);
        i.putExtra("uid",uid);
        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
        i.putExtra("type","t");
        startActivity(i);


    }
}
