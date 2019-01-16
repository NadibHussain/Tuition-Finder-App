package com.example.admin.final2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email,password;
    Button login;
    String account;
    private ProgressDialog progressDialog;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.editText3);
        password=findViewById(R.id.editText4);
        login=findViewById(R.id.button3);
        progressDialog=new ProgressDialog(this);
        rg=findViewById(R.id.accounttype);


    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        SharedPreferences sharedPreferences=getSharedPreferences("account",MODE_PRIVATE);
        String sf=sharedPreferences.getString("accounttype","");
        if(currentUser==null)
        {
            Toast.makeText(this, "no user", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "user found", Toast.LENGTH_SHORT).show();
            if(sf.equals("tutor"))
            {
                Intent i=new Intent(MainActivity.this,TutorUI.class);
                startActivity(i);

            }
            else
            {
                Intent i=new Intent(MainActivity.this,studentUI.class);
                startActivity(i);
            }

        }

    }

    public void signInExisting(View view) {
        progressDialog.setMessage("Signing in...");
        progressDialog.show();
        int q=rg.getCheckedRadioButtonId();
        RadioButton sel=findViewById(q);
        account=sel.getText().toString();
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Login success.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressDialog.dismiss();
                            if(account.equals("tutor"))
                            {
                                Intent i=new Intent(MainActivity.this,TutorUI.class);
                                startActivity(i);

                            }
                            else
                            {
                                Intent i=new Intent(MainActivity.this,studentUI.class);
                                startActivity(i);
                            }

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }

                        // ...
                    }
                });
    }

    public void signup(View view) {
        Intent i=new Intent(MainActivity.this,signup.class);
        startActivity(i);
    }


}
