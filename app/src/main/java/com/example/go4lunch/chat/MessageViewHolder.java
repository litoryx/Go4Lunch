package com.example.go4lunch.chat;


import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.go4lunch.R;
import com.google.android.gms.fido.fido2.api.common.RequestOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    private final int colorCurrentUser;
    private final int colorRemoteUser;
    private boolean isSender;
    private TextView messageTextView;
    private TextView dateTextView;
    private ImageView profilIsMentor;
    private ImageView profilImage;
    private ImageView senderImageView;
    private LinearLayout messageTextContainer;
    private LinearLayout profileContainer;
    private LinearLayout messageContainer;


    public MessageViewHolder(@NonNull View itemView, boolean isSender) {
        super(itemView);
        this.isSender = isSender;
        messageTextView = itemView.findViewById(R.id.messageTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
        profilIsMentor = itemView.findViewById(R.id.profileIsMentor);
        profilImage = itemView.findViewById(R.id.profileImage);
        senderImageView = itemView.findViewById(R.id.senderImageView);
        messageTextContainer = itemView.findViewById(R.id.messageTextContainer);
        profileContainer = itemView.findViewById(R.id.profileContainer);
        messageContainer = itemView.findViewById(R.id.messageContainer);

        // Setup default colros
        colorCurrentUser = ContextCompat.getColor(itemView.getContext(), R.color.colorAccent);
        colorRemoteUser = ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary);
    }

    public void updateWithMessage(Message message, RequestManager glide){

        // Update message
        messageTextView.setText(message.getMessage());
        messageTextView.setTextAlignment(isSender ? View.TEXT_ALIGNMENT_TEXT_END : View.TEXT_ALIGNMENT_TEXT_START);

        // Update date
        if (message.getDateCreated() != null) dateTextView.setText(this.convertDateToHour(message.getDateCreated()));

        // Update isMentor
        profilIsMentor.setVisibility(message.getUserSender().getIsMentor() ? View.VISIBLE : View.INVISIBLE);

        // Update profile picture
        if (message.getUserSender().getUrlPicture() != null)
            glide.load(message.getUserSender().getUrlPicture())
                    .circleCrop()
                    .into(profilImage);

        // Update image sent
        if (message.getUrlImage() != null){
            glide.load(message.getUrlImage())
                    .into(senderImageView);
            senderImageView.setVisibility(View.VISIBLE);
        } else {
            senderImageView.setVisibility(View.GONE);
        }

        updateLayoutFromSenderType();
    }

    private void updateLayoutFromSenderType(){

        //Update Message Bubble Color Background
        ((GradientDrawable) messageTextContainer.getBackground()).setColor(isSender ? colorCurrentUser : colorRemoteUser);
        messageTextContainer.requestLayout();

        if(!isSender){
            updateProfileContainer();
            updateMessageContainer();
        }
    }

    private void updateProfileContainer(){
        // Update the constraint for the profile container (Push it to the left for receiver message)
        ConstraintLayout.LayoutParams profileContainerLayoutParams = (ConstraintLayout.LayoutParams) profileContainer.getLayoutParams();
        profileContainerLayoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
        profileContainerLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
       profileContainer.requestLayout();
    }

    private void updateMessageContainer(){
        // Update the constraint for the message container (Push it to the right of the profile container for receiver message)
        ConstraintLayout.LayoutParams messageContainerLayoutParams = (ConstraintLayout.LayoutParams) messageContainer.getLayoutParams();
        messageContainerLayoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
        messageContainerLayoutParams.endToStart = ConstraintLayout.LayoutParams.UNSET;
        messageContainerLayoutParams.startToEnd = profileContainer.getId();
        messageContainerLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        messageContainerLayoutParams.horizontalBias = 0.0f;
        messageContainer.requestLayout();

        // Update the constraint (gravity) for the text of the message (content + date) (Align it to the left for receiver message)
        LinearLayout.LayoutParams messageTextLayoutParams = (LinearLayout.LayoutParams) messageTextContainer.getLayoutParams();
        messageTextLayoutParams.gravity = Gravity.START;
        messageTextContainer.requestLayout();

        LinearLayout.LayoutParams dateLayoutParams = (LinearLayout.LayoutParams) dateTextView.getLayoutParams();
        dateLayoutParams.gravity = Gravity.BOTTOM | Gravity.START;
        dateTextView.requestLayout();

    }

    private String convertDateToHour(Date date){
        DateFormat dfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dfTime.format(date);
    }

}
