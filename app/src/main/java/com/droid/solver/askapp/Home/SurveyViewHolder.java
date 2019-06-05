package com.droid.solver.askapp.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SurveyViewHolder extends RecyclerView.ViewHolder {
    EmojiTextView profileName,bio,question;
    CircleImageView profileImage;
    ImageView threeDot;
    LinearLayout container1,container2,container3,container4;
    EmojiTextView option1TextView,option2TextView,option3TextView,option4TextView;
    EmojiTextView percent1,percent2,percent3,percent4;
    View view1,view2,view3,view4;
    Context context;
    Animation animation;
    public SurveyViewHolder(@NonNull View itemView,Context context) {
        super(itemView);
        this.context=context;
        profileName=itemView.findViewById(R.id.asker_name);
        bio=itemView.findViewById(R.id.about_textview);
        question=itemView.findViewById(R.id.question_textview);
        profileImage=itemView.findViewById(R.id.profile_image);
        threeDot=itemView.findViewById(R.id.imageView8);
        container1=itemView.findViewById(R.id.linearLayout3);//first layout
        container2=itemView.findViewById(R.id.linearLayout4);
        container3=itemView.findViewById(R.id.linearLayout5);
        container4=itemView.findViewById(R.id.linearLayout6);
        option1TextView=itemView.findViewById(R.id.option1);//option
        option2TextView=itemView.findViewById(R.id.option2);
        option3TextView=itemView.findViewById(R.id.option3);
        option4TextView=itemView.findViewById(R.id.option4);
        percent1=itemView.findViewById(R.id.percent1);
        percent2=itemView.findViewById(R.id.percent2);
        percent3=itemView.findViewById(R.id.percent3);
        percent4=itemView.findViewById(R.id.percent4);
        view1=itemView.findViewById(R.id.view1);
        view2=itemView.findViewById(R.id.view2);
        view3=itemView.findViewById(R.id.view3);
        view4=itemView.findViewById(R.id.view4);
        container1.setVisibility(View.GONE);
        container2.setVisibility(View.GONE);
        container3.setVisibility(View.GONE);
        container4.setVisibility(View.GONE);


    }
    void onContainer1Clicked(int option1Count,int option2Count,int option3Count,int option4Count,String askerId,String surveyId){
        animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
        animation.setDuration(500);
        container1.startAnimation(animation);
        changeBackGroundOfLinearLayout();
        changeColor(container1.getId());
    }
    void onContainer2Clicked(int option1Count,int option2Count,int option3Count,int option4Count,String askerId,String surveyId){
        animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
        animation.setDuration(500);
        container2.startAnimation(animation);
        changeBackGroundOfLinearLayout();
        changeColor(container2.getId());
    }
    void onContainer3Clicked(int option1Count,int option2Count,int option3Count,int option4Count,String askerId,String surveyId){
        animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
        animation.setDuration(500);
        container3.startAnimation(animation);
        changeBackGroundOfLinearLayout();
        changeColor(container3.getId());
    }
    void onContainer4Clicked(int option1Count,int option2Count,int option3Count,int option4Count,String askerId,String surveyId){
        animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
        animation.setDuration(500);
        container4.startAnimation(animation);
        changeBackGroundOfLinearLayout();
        changeColor(container4.getId());
    }

    private void changeColor(int id){
        switch (id){
            case R.id.linearLayout3:
                view1.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_green, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                option1TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_green, null));
                option2TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option3TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option4TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout4:
                view1.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_green, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                option1TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option2TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_green, null));
                option3TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option4TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout5:
                view1.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_green, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                option1TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option2TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option3TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_green, null));
                option4TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout6:
                view1.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_green, null));
                option1TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option2TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option3TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option4TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_green, null));
                break;
        }
    }
    private void changeBackGroundOfLinearLayout(){
        container1.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.survey_outline_box, null));
        container2.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.survey_outline_box, null));
        container3.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.survey_outline_box, null));
        container4.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.survey_outline_box, null));
        percent1.setVisibility(View.VISIBLE);
        percent2.setVisibility(View.VISIBLE);
        percent3.setVisibility(View.VISIBLE);
        percent4.setVisibility(View.VISIBLE);

    }
}
