package com.droid.solver.askapp.Question;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AskQuestionViewHolderWithImage extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView shareImage,questionImage;
    CircleImageView profilePicture;
    TextView profileName,about,tapToMore;
    TextView question,timeAgo;
    CardView cardView;
    QuestionClickListener questionClickListener;
    public AskQuestionViewHolderWithImage(View itemView){
        super(itemView);
        cardView=itemView.findViewById(R.id.root_card_view);
        shareImage=itemView.findViewById(R.id.share_image_view);
        questionImage = itemView.findViewById(R.id.question_image);
        profilePicture=itemView.findViewById(R.id.profile_image);
        profileName=itemView.findViewById(R.id.profile_name_textview);
        about=itemView.findViewById(R.id.about_textview);
        tapToMore=itemView.findViewById(R.id.tap_to_more_textview);
        question=itemView.findViewById(R.id.question_textview);
        timeAgo=itemView.findViewById(R.id.time_ago_textview);
        cardView.setOnClickListener(this);
        profilePicture.setOnClickListener(this);
        shareImage.setOnClickListener(this);
        questionClickListener=new QuestionFragment();

    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.root_card_view:
                questionClickListener.onCardItemClicked(getAdapterPosition());
                break;
            case R.id.share_image_view:
                questionClickListener.onShareImageClicked(getAdapterPosition());
                break;
            case R.id.profile_image:
                questionClickListener.onProfileImageClicked(getAdapterPosition());
                break;
        }
    }

}
