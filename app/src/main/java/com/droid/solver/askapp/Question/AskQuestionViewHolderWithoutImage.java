package com.droid.solver.askapp.Question;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.emoji.widget.EmojiTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.droid.solver.askapp.Account.OtherAccountActivity;
import com.droid.solver.askapp.Answer.AnswerActivity;
import com.droid.solver.askapp.R;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

class AskQuestionViewHolderWithoutImage extends RecyclerView.ViewHolder {

    CircleImageView profilePicture;
    EmojiTextView profileName,about;
    EmojiTextView question,timeAgo;
    CardView cardView;
    View view;
    AskQuestionViewHolderWithoutImage(View itemView){
        super(itemView);
        cardView=itemView.findViewById(R.id.root_card_view);
        profilePicture = itemView.findViewById(R.id.profile_image);
        profileName=itemView.findViewById(R.id.asker_name);
        about=itemView.findViewById(R.id.about_textview);
        timeAgo=itemView.findViewById(R.id.time_ago_textview);
        question=itemView.findViewById(R.id.question_textview);
        view=itemView.findViewById(R.id.view9);
    }
     void onCardClicked(Context context, RootQuestionModel model){
                    Activity activity= (Activity) context;
                    Intent intent=new Intent(context,AnswerActivity.class);
                    intent.putExtra("askerUid",model.getAskerId());
                    intent.putExtra("questionId",model.getQuestionId());
                    intent.putExtra("question", model.getQuestion());
                    intent.putExtra("timeOfAsking", model.getTimeOfAsking());
                    intent.putExtra("askerName",model.getAskerName());
                    intent.putExtra("askerImageUrl", model.getAskerImageUrlLow());
                    intent.putExtra("askerBio", model.getAskerBio());
                    intent.putStringArrayListExtra("questionType",new ArrayList<>(model.getQuestionType()));
                    model.setAnswered(true);
                    context.startActivity(intent);
         activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
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
            Activity activity= (Activity) context;
            Intent intent = new Intent(context, OtherAccountActivity.class);
            intent.putExtra("profile_image", model.getAskerImageUrlLow());
            intent.putExtra("uid", model.getAskerId());
            intent.putExtra("user_name", model.getAskerName());
            intent.putExtra("bio", model.getAskerBio());
            context.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

        }
    }





}
