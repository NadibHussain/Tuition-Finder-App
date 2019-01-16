package com.example.admin.final2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class accountType extends AppCompatActivity {
RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);
        rg=findViewById(R.id.radiogroup);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    }


    public void selectType(View view) {
        int selected=rg.getCheckedRadioButtonId();
        RadioButton sel=findViewById(selected);

        if (sel.getText().toString().equals("Student"))
        {
            Intent i=new Intent(accountType.this,createuserstudent.class);
            i.putExtra("type","Student");
            startActivity(i);
        }
        if (sel.getText().toString().equals("Tutor"))
        {
            Intent i=new Intent(accountType.this,createUser.class);
            i.putExtra("type","Tutor");
            startActivity(i);
        }
    }
}
