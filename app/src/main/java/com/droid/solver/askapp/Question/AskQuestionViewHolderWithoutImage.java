package com.droid.solver.askapp.Question;

import android.content.Context;
import android.content.Intent;
import androidx.emoji.widget.EmojiTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.droid.solver.askapp.Account.OtherAccountActivity;
import com.droid.solver.askapp.Answer.AnswerActivity;
import com.droid.solver.askapp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AskQuestionViewHolderWithoutImage extends RecyclerView.ViewHolder {

    ImageView shareImage;
    CircleImageView profilePicture;
    EmojiTextView profileName,about,tapToMore;
    EmojiTextView question,timeAgo;
    CardView cardView;
    View view;
    public AskQuestionViewHolderWithoutImage(View itemView){
        super(itemView);
        cardView=itemView.findViewById(R.id.root_card_view);
        shareImage=itemView.findViewById(R.id.share_image_view);
        profilePicture = itemView.findViewById(R.id.profile_image);
        profileName=itemView.findViewById(R.id.asker_name);
        about=itemView.findViewById(R.id.about_textview);
        timeAgo=itemView.findViewById(R.id.time_ago_textview);
        question=itemView.findViewById(R.id.question_textview);
        tapToMore=itemView.findViewById(R.id.tap_to_more_textview);
        view=itemView.findViewById(R.id.view9);
    }
     void onCardClicked(Context context,int i,RootQuestionModel model){
                    Intent intent=new Intent(context,AnswerActivity.class);
                    intent.putExtra("askerUid",model.getAskerId());
                    intent.putExtra("questionId",model.getQuestionId());
                    intent.putExtra("question", model.getQuestion());
                    intent.putExtra("timeOfAsking", model.getTimeOfAsking());
                    intent.putExtra("askerName",model.getAskerName());
                    intent.putExtra("askerImageUrl", model.getAskerImageUrlLow());
                    intent.putExtra("askerBio", model.getAskerBio());
                    intent.putStringArrayListExtra("questionType",new ArrayList<String>(model.getQuestionType()));
                    model.setAnswered(true);
                    context.startActivity(intent);
    }

    void onProfilePictureClicked(final Context context,RootQuestionModel model){
        goToProfileActivity(context,model);
    }
    void onProfileNameClicked(final Context context,final RootQuestionModel model){
        goToProfileActivity(context,model);
    }
    private void goToProfileActivity(final Context context,final RootQuestionModel model){

        if(model!=null&&model.isAnonymous()){
            Toast.makeText(context, "Anonymously asked", Toast.LENGTH_LONG).show();
        }else if(model!=null){
            Intent intent = new Intent(context, OtherAccountActivity.class);
            intent.putExtra("profile_image", model.getAskerImageUrlLow());
            intent.putExtra("uid", model.getAskerId());
            intent.putExtra("user_name", model.getAskerName());
            intent.putExtra("bio", model.getAskerBio());
            context.startActivity(intent);
        }
    }


}
