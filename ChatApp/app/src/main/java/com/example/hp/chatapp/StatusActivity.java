package com.example.hp.chatapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private Button btnStatusChange;
    private TextInputLayout status_input;

    private DatabaseReference mstatusDatabse;
    private FirebaseUser mcurrentUser;


    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);


        mtoolbar= (Toolbar) findViewById(R.id.status_appbar);
        btnStatusChange= (Button) findViewById(R.id.btnStatuschange);
        status_input= (TextInputLayout) findViewById(R.id.status_input);

        progressDialog=new ProgressDialog(this);

        mcurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        String uid=mcurrentUser.getUid();

        mstatusDatabse= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String status=getIntent().getStringExtra("status");

        status_input.getEditText().setText(status);

        btnStatusChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog=new ProgressDialog(StatusActivity.this);
                progressDialog.setTitle("Saving Changes");
                progressDialog.setMessage("Please Wait while we save the changes");
                progressDialog.show();
                String status=status_input.getEditText().getText().toString();
                mstatusDatabse.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(StatusActivity.this, "Error::", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
