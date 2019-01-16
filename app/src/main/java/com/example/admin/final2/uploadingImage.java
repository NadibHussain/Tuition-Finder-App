package com.example.admin.final2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class uploadingImage extends AppCompatActivity {
    private StorageReference mStorageRef;
    private static final int g=2;
    private static final int c=1;
    private ProgressDialog progressDialog;
    private File file;
    String type;
    Uri downloadUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading_image);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        type=getIntent().getStringExtra("type");
        Toast.makeText(uploadingImage.this, type, Toast.LENGTH_SHORT).show();
        progressDialog=new ProgressDialog(this);
    }

    public void upload(View view) {

        Intent i=new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,g);

    }
    public void captureAndUpload(View view) {
        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file=getTempFile();
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(i,c);
    }
    Uri mImageUri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==g && resultCode==RESULT_OK){
            progressDialog.setMessage("uploading...");
            progressDialog.show();
            Uri uri=data.getData();

            StorageReference childref=mStorageRef.child("ProPic").child(uri.getLastPathSegment());
            Toast.makeText(this, uri.getLastPathSegment()+"", Toast.LENGTH_SHORT).show();
            childref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(uploadingImage.this, "Upload Done", Toast.LENGTH_SHORT).show();
                    downloadUrl = taskSnapshot.getDownloadUrl();
                    Intent i=new Intent(uploadingImage.this,testing.class);

                    i.putExtra("type",type);
                    i.putExtra("url",downloadUrl.toString());
                    startActivity(i);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(uploadingImage.this, ""+e, Toast.LENGTH_SHORT).show();

                }
            });}
            if (requestCode==c && resultCode==RESULT_OK){
                progressDialog.setMessage("uploading...");
                progressDialog.show();
                 mImageUri=Uri.fromFile(file);
                StorageReference childref2=mStorageRef.child("ProPic").child(mImageUri.getLastPathSegment());
                Toast.makeText(this, mImageUri.toString()+"", Toast.LENGTH_SHORT).show();
                childref2.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(uploadingImage.this, "Upload Done", Toast.LENGTH_SHORT).show();
                        downloadUrl = taskSnapshot.getDownloadUrl();
                        Intent i=new Intent(uploadingImage.this,testing.class);
                        i.putExtra("url",downloadUrl.toString());
                        i.putExtra("type",type);
                        startActivity(i);

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(uploadingImage.this, ""+e, Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
    private File getTempFile() {
        // Create an image file name
        String timeStamp =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "barfile_" + timeStamp + ".jpg";

        return new File(Environment.getExternalStorageDirectory(), imageFileName);
    }


}
