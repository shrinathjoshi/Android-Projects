package com.example.hp.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference UserReference;
    private FirebaseUser mCurrentUser;


    private CircleImageView setting_image;
    TextView setting_display_name,setting_status;
    Button btnStatus,btnImage;


    //Storage Firebase
    private StorageReference mStorageRef;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setting_image= (CircleImageView) findViewById(R.id.setting_image);
        setting_display_name= (TextView) findViewById(R.id.setting_display_name);
        setting_status= (TextView) findViewById(R.id.setting_status);
        btnStatus= (Button) findViewById(R.id.btnStatus);
        btnImage= (Button) findViewById(R.id.btnImage);

        //Initializing Storage Reference
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        String uid=mCurrentUser.getUid();
        UserReference= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        UserReference.keepSynced(true);

        UserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("name").getValue().toString();
                final String image=dataSnapshot.child("image").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
                String ThumbImage=dataSnapshot.child("thumb_image").getValue().toString();

                setting_display_name.setText(name);
                setting_status.setText(status);

                if(!image.equals("default"))
                   // Picasso.with(SettingsActivity.this).load(image).placeholder(R.drawable.images).into(setting_image);
                    Picasso.with(SettingsActivity.this).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.images).into(setting_image
                            , new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {

                                    Picasso.with(SettingsActivity.this).load(image).placeholder(R.drawable.images).into(setting_image);
                                }
                            });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s=setting_status.getText().toString();
                Intent i=new Intent(SettingsActivity.this,StatusActivity.class);
                i.putExtra("status",s);
                startActivity(i);
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getImage=new Intent();
                getImage.setType("image/*");
                getImage.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(getImage,"Select Image"),100);
/*
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SettingsActivity.this);
*/

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==100)
        {
            Uri imageUri=data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(SettingsActivity.this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {


                progressDialog=new ProgressDialog(SettingsActivity.this);
                progressDialog.setTitle("Uploading Image");
                progressDialog.setMessage("Please wait while we are uploading your image");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Uri resultUri = result.getUri();

                File thumb_bitmapfile=new File(resultUri.getPath());

                String cuid=mCurrentUser.getUid();
                Bitmap thumb_bitmap=null;
                try {
                    thumb_bitmap = new Compressor(this).setMaxWidth(200).setMaxHeight(200)
                            .setQuality(75).compressToBitmap(thumb_bitmapfile);


                } catch (IOException e) {
                    e.printStackTrace();
                }


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();


                StorageReference filepath=mStorageRef.child("Profile Images").child (cuid + ".jpeg");
                final StorageReference thumb_filepath=mStorageRef.child("Profile Images").child("thumbs").child (cuid + ".jpeg");



                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful())
                        {

                        @SuppressWarnings("VisibleForTests") final
                        String downloadUri=task.getResult().getDownloadUrl().toString();

                            UploadTask uploadTask = thumb_filepath.putBytes(thumb_byte);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {

                                    final String thumb_downloadUrl=thumb_task.getResult().getDownloadUrl().toString();
                                    if(thumb_task.isSuccessful())
                                    {

                                        Map update_hashmap=new HashMap<String, String>();
                                        update_hashmap.put("image",downloadUri);
                                        update_hashmap.put("thumb_image",thumb_downloadUrl);


                                        UserReference.updateChildren(update_hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful())
                                                {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(SettingsActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(SettingsActivity.this, "Not Working", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

            } else
                if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
            }
        }
    }
/*
    public  static  String random()
    {
        Random generate=new Random();
        StringBuilder sb=new StringBuilder();
        int length=generate.nextInt(10);

        char temp;
        for(int i=0;i<length;i++)
        {
            temp=(char)(generate.nextInt(96)+32);
            sb.append(temp);
        }
        return sb.toString();
    }
    */
}
