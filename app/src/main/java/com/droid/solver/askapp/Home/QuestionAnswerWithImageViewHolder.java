package com.droid.solver.askapp.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.Answer.AnswerActivity;
import com.droid.solver.askapp.Answer.QuestionAnswerModel;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.R;
import com.like.LikeButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionAnswerWithImageViewHolder extends RecyclerView.ViewHolder {
    ImageView answerImageView;
    CircleImageView askerImageView,answererImageView;
    EmojiTextView askerName,askerBio,answererName;
    TextView timeAgo,wantToAnswerTextView;
    EmojiTextView question,answer;
    ImageView threeDot;
    LikeButton likeButton;
    TextView likeCount,answerCount;
    ImageView numberOfAnswerImageView,wantToAnswerImageView;
    public QuestionAnswerWithImageViewHolder(@NonNull View itemView) {
        super(itemView);
        askerImageView=itemView.findViewById(R.id.profile_image);
        answererImageView=itemView.findViewById(R.id.circleImageView);
        askerName=itemView.findViewById(R.id.asker_name);
        askerBio=itemView.findViewById(R.id.about_textview);
        answererName=itemView.findViewById(R.id.answerer_name);
        timeAgo=itemView.findViewById(R.id.time_ago_textview);
        answerImageView=itemView.findViewById(R.id.answer_image_view);
        wantToAnswerTextView=itemView.findViewById(R.id.textView18);
        question=itemView.findViewById(R.id.question_textview);
        answer=itemView.findViewById(R.id.answer_text_view);
        threeDot=itemView.findViewById(R.id.imageView4);
        likeButton=itemView.findViewById(R.id.likeButton);
        likeCount=itemView.findViewById(R.id.delete_poll_text_view);
        answerCount=itemView.findViewById(R.id.textView17);
        numberOfAnswerImageView=itemView.findViewById(R.id.imageView2);
        wantToAnswerImageView=itemView.findViewById(R.id.imageView3);
    }
    public void onThreeDotClicked(Context context){
        View dialogView = LayoutInflater.from(context).inflate(R.layout.question_answer_overflow_dialog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().getAttributes().windowAnimations=R.style.customAnimations_successfull;
        alertDialog.show();
    }
    public void onWantToAnswer(Context context, RootQuestionModel model){

        Intent intent=new Intent(context,AnswerActivity.class);
        intent.putExtra("askerUid", model.getAskerId());
        intent.putExtra("questionId", model.getQuestionId());
        intent.putExtra("question", model.getQuestion());
        intent.putExtra("timeOfAsking", model.getTimeOfAsking());
        intent.putExtra("askerName", model.getAskerName());
        intent.putExtra("askerImageUrl", model.getAskerImageUrlLow());
        intent.putExtra("askerBio", model.getAskerBio());
        intent.putStringArrayListExtra("questionType", (ArrayList<String>)model.getQuestionType());
        context.startActivity(intent);

    }

    public void onAnswersClicked(Context context,RootQuestionModel model){
        Toast.makeText(context, "number of answer clicked", Toast.LENGTH_SHORT).show();
    }
    public void onLiked(Context context,RootQuestionModel model){

        Toast.makeText(context, "like ", Toast.LENGTH_SHORT).show();
    }
    public void onDisliked(Context context,RootQuestionModel model){
        Toast.makeText(context, "dislike ", Toast.LENGTH_SHORT).show();

    }
    public void onNumberOfAnswerClicked(Context context,RootQuestionModel rootQuestionModel){
        Intent intent =new Intent(context, com.droid.solver.askapp.Home.AnswerActivity.class);
        context.startActivity(intent);
    }


}
