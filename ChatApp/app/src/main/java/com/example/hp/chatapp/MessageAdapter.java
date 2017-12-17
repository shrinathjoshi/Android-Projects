package com.example.hp.chatapp;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hp on 9/22/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Messages> mMessageList;
    private FirebaseAuth mAuth;

    public MessageAdapter(List<Messages> mMessageList)
    {
        this.mMessageList=mMessageList;
    }

    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout,parent,false);

        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MessageViewHolder holder, int position) {

        mAuth=FirebaseAuth.getInstance();
        String current_user_id=mAuth.getCurrentUser().getUid();

        Messages c=mMessageList.get(position);
        String from_user=c.getFrom();

        if(from_user != null&&from_user.equals(current_user_id))
        {
            holder.messageText.setBackgroundColor(Color.WHITE);
            holder.messageText.setTextColor(Color.BLACK);
            holder.messageText.setGravity(Gravity.RIGHT);
        }
        else {
            holder.messageText.setBackgroundResource(R.drawable.message_text_background);
            holder.messageText.setTextColor(Color.WHITE);
        }
        holder.messageText.setText(c.getMessage());

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public TextView timeText;
        public CircleImageView profileImage;

        public MessageViewHolder(View itemView) {
            super(itemView);

            messageText= (TextView) itemView.findViewById(R.id.message_text_layout);
            profileImage= (CircleImageView) itemView.findViewById(R.id.message_profile_layout);

        }
    }
}
