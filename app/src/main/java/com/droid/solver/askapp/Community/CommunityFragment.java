package com.droid.solver.askapp.Community;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.R;

public class CommunityFragment extends Fragment implements View.OnClickListener {


    LinearLayout firstLayout,secondLayout,thirdLayout,fourthLayout;
    EmojiTextView percent1,percent2,percent3,percent4;
    EmojiTextView option1,option2,option3,option4;
    View view1,view2,view3,view4;
    public CommunityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_community, container, false);
        firstLayout=view.findViewById(R.id.linearLayout3);
        secondLayout=view.findViewById(R.id.linearLayout4);
        thirdLayout=view.findViewById(R.id.linearLayout5);
        fourthLayout=view.findViewById(R.id.linearLayout6);
        percent1=view.findViewById(R.id.percent1);option1=view.findViewById(R.id.option1);
        percent2=view.findViewById(R.id.percent2);option2=view.findViewById(R.id.option2);
        percent3=view.findViewById(R.id.percent3);option3=view.findViewById(R.id.option3);
        percent4=view.findViewById(R.id.percent4);option4=view.findViewById(R.id.option4);
        firstLayout.setOnClickListener(this);view1=view.findViewById(R.id.view1);
        secondLayout.setOnClickListener(this);view2=view.findViewById(R.id.view2);
        thirdLayout.setOnClickListener(this);view3=view.findViewById(R.id.view3);
        fourthLayout.setOnClickListener(this);view4=view.findViewById(R.id.view4);
        return view;
    }


    @Override
    public void onClick(View view) {
        Animation animation=AnimationUtils.loadAnimation(getActivity(), R.anim.survey_rectangle_scalein);
        animation.setDuration(500);
        switch (view.getId()){
            case R.id.linearLayout3:
                firstLayout.startAnimation(animation);
                changeBackGroundOfLinearLayout();
                changeColor(view.getId());
                break;
            case R.id.linearLayout4:
                secondLayout.startAnimation(animation);
                changeBackGroundOfLinearLayout();
                changeColor(view.getId());
                break;
            case R.id.linearLayout5:
                thirdLayout.startAnimation(animation);
                changeBackGroundOfLinearLayout();
                changeColor(view.getId());
                break;
            case R.id.linearLayout6:
                fourthLayout.startAnimation(animation);
                changeBackGroundOfLinearLayout();
                changeColor(view.getId());
                break;

        }
    }
    private void changeColor(int id){
        switch (id){
            case R.id.linearLayout3:
                view1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_green, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                option1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_green, null));
                option2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                option3.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                option4.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout4:
                view1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_green, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                option1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                option2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_green, null));
                option3.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                option4.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout5:
                view1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_green, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                option1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                option2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                option3.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_green, null));
                option4.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout6:
                view1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_green, null));
                option1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                option2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                option3.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
                option4.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_green, null));
                break;
        }
    }
    private void changeBackGroundOfLinearLayout(){
        firstLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.survey_outline_box, null));
        secondLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.survey_outline_box, null));
        thirdLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.survey_outline_box, null));
        fourthLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.survey_outline_box, null));
        percent1.setVisibility(View.VISIBLE);
        percent2.setVisibility(View.VISIBLE);
        percent3.setVisibility(View.VISIBLE);
        percent4.setVisibility(View.VISIBLE);

    }
}
