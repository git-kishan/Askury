package com.droid.solver.askapp.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.emoji.widget.EmojiTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.Question.UserQuestionModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.homeAnswer.AnswerActivity;
import de.hdodenhof.circleimageview.CircleImageView;

 class AccountQuestionRecyclerViewHolder extends RecyclerView.ViewHolder {
    EmojiTextView questionTextView,timeAgoTextView,askerName,askerBio;
    TextView  answerCount;
    CardView rootCardView;
    CircleImageView profileImage;
    private Context context;

    AccountQuestionRecyclerViewHolder(@NonNull View itemView, final Context context) {
        super(itemView);
        this.context = context;
        questionTextView = itemView.findViewById(R.id.question_textview);
        timeAgoTextView = itemView.findViewById(R.id.time_ago_textview);
        rootCardView = itemView.findViewById(R.id.root_card_view);
        askerName=itemView.findViewById(R.id.asker_name);
        askerBio=itemView.findViewById(R.id.about_textview);
        answerCount = itemView.findViewById(R.id.answer_count);
        profileImage=itemView.findViewById(R.id.profile_image);
    }
    void onCardClicked(final UserQuestionModel model) {
        Log.i("TAG","Card clicked in account");
        Intent intent = new Intent(context, AnswerActivity.class);
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        String userName = preferences.getString(Constants.userName, "Someone");
        String bio = preferences.getString(Constants.bio, "");
        intent.putExtra("askerImageUrl", model.getUserImageUrlLow());
        intent.putExtra("timeOfAsking", model.getTimeOfAsking());
        intent.putExtra("askerName", userName);
        intent.putExtra("askerBio", bio);
        intent.putExtra("questionId", model.getQuestionId());
        intent.putExtra("question", model.getQuestion());
        intent.putExtra("askerId", model.getUserId());
        intent.putExtra("anonymous", model.isAnonymous());
        context.startActivity(intent);
    }
    void onProfileImageClicked(final Context context, final UserQuestionModel model){
        Intent intent=new Intent(context,OtherAccountActivity.class);
        intent.putExtra("profile_image", model.getUserImageUrlLow());
        intent.putExtra("uid",model.getUserId());
        intent.putExtra("user_name",model.getUserName());
        intent.putExtra("bio",model.getUserBio());
        context.startActivity(intent);
    }
    void onAskerNameClicked(final Context context,final UserQuestionModel model){
        Intent intent=new Intent(context,OtherAccountActivity.class);
        intent.putExtra("profile_image", model.getUserImageUrlLow());
        intent.putExtra("uid",model.getUserId());
        intent.putExtra("user_name",model.getUserName());
        intent.putExtra("bio",model.getUserBio());
        context.startActivity(intent);
    }
}