package com.example.hp.chatapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;


public class UsersActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private RecyclerView user_list;
    private DatabaseReference UserDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mtoolbar= (Toolbar) findViewById(R.id.user_appbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

        user_list= (RecyclerView) findViewById(R.id.users_list);
        user_list.setHasFixedSize(true);
        user_list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users,UserViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Users, UserViewHolder>(

                Users.class,
                R.layout.users_single_layout,
                UserViewHolder.class,
                UserDatabase

        ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, Users model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setUserImage(model.getThumb_image(),getApplicationContext());

                final String user_id=getRef(position).getKey();
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent profileIntent=new Intent(UsersActivity.this,ProfileActivity.class);
                        profileIntent.putExtra("user_id",user_id);
                        startActivity(profileIntent);

                    }
                });
            }
        };

        user_list.setAdapter(firebaseRecyclerAdapter);
    }


    public static class  UserViewHolder extends RecyclerView.ViewHolder
    {
        View mview;
        public UserViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }


        public void setName(String name)
        {
            TextView userNameView= (TextView) mview.findViewById(R.id.user_single_name);
            userNameView.setText(name);
        }

        public void setStatus(String status)
        {
            TextView userStatus= (TextView) mview.findViewById(R.id.uses_single_status);
            userStatus.setText(status);
        }

        public void setUserImage(String thumb_image, Context c)
        {
            CircleImageView userImage= (CircleImageView) mview.findViewById(R.id.user_single_image);
            Picasso.with(c).load(thumb_image).placeholder(R.drawable.images).into(userImage);
        }

    }
}
