package com.example.hp.chatapp;

import android.app.ProgressDialog;
import android.icu.text.DateFormat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    ImageView mprofileImage;
    TextView mprofileName,mprofileStatus,mprofileFriendCount;
    Button mprofileSendReqBtn,mprofile_DeclineBtn;
    ProgressDialog mprogressDialog;
    DatabaseReference mUsersDatabase;
    DatabaseReference mFriendRequestDatabase;
    DatabaseReference mFriendDatabase;
    DatabaseReference mNotificationDatabase;
    DatabaseReference mRootRef;


    FirebaseUser mCurrent_User;
    String name;
    String current_State;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mprofileName= (TextView) findViewById(R.id.profile_display_name);
        mprofileImage= (ImageView) findViewById(R.id.profile_image);
        mprofileFriendCount= (TextView) findViewById(R.id.profile_totalFriends);
        mprofileStatus= (TextView) findViewById(R.id.profile_status);
        mprofileSendReqBtn= (Button) findViewById(R.id.profile_send_req_btn);
        mprofile_DeclineBtn= (Button) findViewById(R.id.profile_decline_btn);

        current_State="not_Friend";

        mprofile_DeclineBtn.setVisibility(View.INVISIBLE);
        mprofile_DeclineBtn.setEnabled(false);


        mprogressDialog=new ProgressDialog(ProfileActivity.this);
        mprogressDialog.setTitle("Loading User Data");
        mprogressDialog.setMessage("Please Wait while we load the data");
        mprogressDialog.setCanceledOnTouchOutside(false);
        mprogressDialog.show();

        final String uid=getIntent().getStringExtra("user_id");

        mRootRef=FirebaseDatabase.getInstance().getReference();
        mUsersDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mFriendRequestDatabase= FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mFriendDatabase=FirebaseDatabase.getInstance().getReference().child("Friends");
        mNotificationDatabase=FirebaseDatabase.getInstance().getReference().child("notification");
        mCurrent_User= FirebaseAuth.getInstance().getCurrentUser();

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String displayName=dataSnapshot.child("name").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
                String image=dataSnapshot.child("image").getValue().toString();
                name=displayName;

                mprofileName.setText(displayName);
                mprofileStatus.setText(status);

                Picasso.with(ProfileActivity.this).load(image).placeholder(R.drawable.images).into(mprofileImage);



                //---------Friends List

                mFriendRequestDatabase.child(mCurrent_User.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(uid)) {
                            String req_type = dataSnapshot.child(uid).child("request_type").getValue().toString();

                            if (req_type.equals("received")) {
                                current_State = "req_received";
                                mprofileSendReqBtn.setText("ACCEPT FRIEND REQUEST");

                                mprofile_DeclineBtn.setVisibility(View.VISIBLE);
                                mprofile_DeclineBtn.setEnabled(true);




                            } else if (req_type.equals("sent")) {
                                current_State = "req_sent";
                                mprofileSendReqBtn.setText("CANCEL FRIEND REQUEST");

                                mprofile_DeclineBtn.setVisibility(View.INVISIBLE);
                                mprofile_DeclineBtn.setEnabled(false);
                            }


                            mprogressDialog.dismiss();
                        }
                        else
                            {
                                mFriendDatabase.child(mCurrent_User.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild(uid))
                                        {
                                            current_State="friends";
                                            String t="UNFRIEND "+name.toUpperCase();
                                            mprofileSendReqBtn.setText(t);

                                            mprofile_DeclineBtn.setVisibility(View.INVISIBLE);
                                            mprofile_DeclineBtn.setEnabled(false);


                                        }

                                        mprogressDialog.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mprofileSendReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mprofileSendReqBtn.setEnabled(false);
                if(current_State.equals("not_Friend"))
                {
                    mFriendRequestDatabase.child(mCurrent_User.getUid()).child(uid).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                mFriendRequestDatabase.child(uid).child(mCurrent_User.getUid()).child("request_type").setValue("received")
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {


                                                HashMap<String,String> notificationdata=new HashMap<String, String>();
                                                notificationdata.put("from",mCurrent_User.getUid());
                                                notificationdata.put("type","request");

                                                mNotificationDatabase.child(uid).push().setValue(notificationdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        mprofileSendReqBtn.setEnabled(true);
                                                        current_State="req_sent";
                                                        mprofileSendReqBtn.setText("CANCEL FRIEND REQUEST");

                                                        mprofile_DeclineBtn.setVisibility(View.INVISIBLE);
                                                        mprofile_DeclineBtn.setEnabled(false);

                                                    }
                                                });




                                                Toast.makeText(ProfileActivity.this, "Sent Friend Request", Toast.LENGTH_SHORT).show();


                                            }
                                        });
                            }
                            else
                            {
                                Toast.makeText(ProfileActivity.this, "Failed Sending Request", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


                //-------------CANCEL FRIEND REQUEST

                if(current_State.equalsIgnoreCase("req_sent"))
                {
                    mFriendRequestDatabase.child(mCurrent_User.getUid()).child(uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            mFriendRequestDatabase.child(uid).child(mCurrent_User.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mprofileSendReqBtn.setEnabled(true);
                                    current_State="not_Friend";
                                    mprofileSendReqBtn.setText("SEND FRIEND REQUEST");

                                    mprofile_DeclineBtn.setVisibility(View.INVISIBLE);
                                    mprofile_DeclineBtn.setEnabled(false);



                                }
                            });
                        }
                    });
                }


                if(current_State.equals("req_received"))
                {
                    final String currentDate= DateFormat.getDateTimeInstance().format(new Date());
                    mFriendDatabase.child(mCurrent_User.getUid()).child(uid).child("date").setValue(currentDate).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mFriendDatabase.child(uid).child(mCurrent_User.getUid()).child("date").setValue(currentDate)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {


                                                    mFriendRequestDatabase.child(mCurrent_User.getUid()).child(uid).child("date").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            mFriendRequestDatabase.child(uid).child(mCurrent_User.getUid()).child("date").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {

                                                                    mprofileSendReqBtn.setEnabled(true);
                                                                    current_State="friends";
                                                                    String t="UNFRIEND "+name.toUpperCase();
                                                                    mprofileSendReqBtn.setText(t);

                                                                    mprofile_DeclineBtn.setVisibility(View.INVISIBLE);
                                                                    mprofile_DeclineBtn.setEnabled(false);



                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                }
                            });
                }


                if(current_State.equals("friends"))
                {
                    Map unfriendMap=new HashMap<>();
                    unfriendMap.put("Friends/"+mCurrent_User.getUid() + "/"+ uid,null);
                    unfriendMap.put("Friends/"+uid+"/"+mCurrent_User.getUid(),null);

                    mRootRef.updateChildren(unfriendMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError==null)
                            {
                                current_State="not_Friend";
                                mprofileSendReqBtn.setText("SEND FRIEND REQUEST");

                                mprofile_DeclineBtn.setVisibility(View.INVISIBLE);
                                mprofile_DeclineBtn.setEnabled(false);
                            }


                            mprofileSendReqBtn.setEnabled(true);
                        }
                    });
                }



            }
        });
    }
}
