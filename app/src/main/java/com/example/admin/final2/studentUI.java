package com.example.admin.final2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class studentUI extends AppCompatActivity {
    Spinner sp;
    CheckBox bangla;
    CheckBox english;
    CheckBox math;
    CheckBox chemistry;
    CheckBox economics;
    CheckBox physics;
    private FirebaseAuth mAuth;
    TextView uname;
    ImageView img;
    TextView tutor;
    ArrayList<String> list;
    Map<String, Object> map;
    Object[] users;
    RadioGroup rg;
    public static String[] unames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_ui);
         list=new ArrayList<>();
        rg=findViewById(R.id.radiogroup);
         tutor=findViewById(R.id.textView9);
         img=findViewById(R.id.imageView);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
         chemistry=findViewById(R.id.chemistry);
         bangla=findViewById(R.id.bangla);
         math=findViewById(R.id.math);
         english=findViewById(R.id.english);
         economics=findViewById(R.id.economics);
         physics=findViewById(R.id.physics);
         uname=findViewById(R.id.textView6);
         unames=new String[20];
        SharedPreferences sharedPreferences=getSharedPreferences("account",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("accounttype","student");
        editor.commit();
         //getting the data from firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(uid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String s="";
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                for (String name: map.keySet()){

                    String key =name.toString();
                    String value = map.get(name).toString();
                    s+=key+" "+value+"\n";

                }
                uname.setText(map.get("name").toString().toUpperCase());
                Picasso.get().load(map.get("Image").toString()).into(img);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }

    public void search(View view) {
        Intent i=new Intent(studentUI.this,testing2.class);
        if (chemistry.isChecked()){
            list.add("chemistry");
        }
        if (physics.isChecked()){
            list.add("Physics");
        }
        if (bangla.isChecked()){
            list.add("bangla");
        }
        if (english.isChecked()){
            list.add("english");
        }
        if (math.isChecked()){
            list.add("math");
        }
        if (economics.isChecked()){
            list.add("economics");
        }
        int selected=rg.getCheckedRadioButtonId();
        RadioButton sel=findViewById(selected);

        if (sel.getText().toString().equals("public"))
        {
            i.putExtra("range","public");
            i.putStringArrayListExtra("subjects",list);
            startActivity(i);
        }
        else if (sel.getText().toString().equals("private"))
        {
            i.putExtra("range","private");
            i.putStringArrayListExtra("subjects",list);
            startActivity(i);
        }

        else
        {
            Toast.makeText(this, "Select Public Or Private", Toast.LENGTH_SHORT).show();
        }

    }

    public void signout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent i=new Intent(studentUI.this,MainActivity.class);
        startActivity(i);

    }
}
