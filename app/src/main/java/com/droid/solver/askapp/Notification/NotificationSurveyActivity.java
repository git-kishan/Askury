package com.droid.solver.askapp.Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.emoji.widget.EmojiTextView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationSurveyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CircleImageView profileImage;
    private EmojiTextView profileName,profileBio,question;
    private LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4;
    private EmojiTextView option1View,option2View,option3View,option4View;
    private View view1,view2,view3,view4;
    private EmojiTextView percent1,percent2,percent3,percent4;

    private String surveyId,askerId,askerName,askerBio,askerImageUrlLow,askerQuestion,type;
    private long timeOfSurvey,notifiedTime;
    private String option1Value,option2Value,option3Value,option4Value;
    private int option1Count,option2Count,option3Count,option4Count;
    private boolean option1,option2,option3,option4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_survey);
        Intent intent=getIntent();
        surveyId=intent.getStringExtra("surveyId");
        askerId=intent.getStringExtra("askerId");
        askerName=intent.getStringExtra("askerName");
        askerBio=intent.getStringExtra("askerBio");
        askerImageUrlLow=intent.getStringExtra("askerImageUrlLow");
        askerQuestion=intent.getStringExtra("question");
        timeOfSurvey=intent.getLongExtra("timeOfSurvey", System.currentTimeMillis());
        option1Value=intent.getStringExtra("option1Value");
        option2Value=intent.getStringExtra("option2Value");
        option3Value=intent.getStringExtra("option3Value");
        option4Value=intent.getStringExtra("option4Value");
        option1Count=intent.getIntExtra("option1Count", 0);
        option2Count=intent.getIntExtra("option2Count", 0);
        option3Count=intent.getIntExtra("option3Count", 0);
        option4Count=intent.getIntExtra("option4Count", 0);
        option1=intent.getBooleanExtra("option1", false);
        option2=intent.getBooleanExtra("option2", false);
        option3=intent.getBooleanExtra("option3" ,false);
        option4=intent.getBooleanExtra("option4", false);
        type=intent.getStringExtra("type");
        notifiedTime=intent.getLongExtra("notifiedTime", System.currentTimeMillis());
        initView();



    }

    private void changeToolbarTitleFont(Toolbar toolbar){
        for(int i=0;i<toolbar.getChildCount();i++){
            View view=toolbar.getChildAt(i);
            if(view instanceof TextView ){
                TextView toolbarTitle= (TextView) view;
                Typeface typeface= ResourcesCompat.getFont(this, R.font.aclonica);
                toolbarTitle.setTypeface(typeface);
            }
        }
    }

    private void initView(){
        toolbar=findViewById(R.id.toolbar);
        changeToolbarTitleFont(toolbar);
        profileImage=findViewById(R.id.profile_image);
        profileName=findViewById(R.id.asker_name);
        profileBio=findViewById(R.id.about_textview);
        question=findViewById(R.id.question_textview);
        linearLayout1=findViewById(R.id.linearLayout3);
        linearLayout2=findViewById(R.id.linearLayout4);
        linearLayout3=findViewById(R.id.linearLayout5);
        linearLayout4=findViewById(R.id.linearLayout6);
        option1View=findViewById(R.id.option1);
        option2View=findViewById(R.id.option2);
        option3View=findViewById(R.id.option3);
        option4View=findViewById(R.id.option4);
        percent1=findViewById(R.id.percent1);
        percent2=findViewById(R.id.percent2);
        percent3=findViewById(R.id.percent3);
        percent4=findViewById(R.id.percent4);
        view1=findViewById(R.id.view1);
        view2=findViewById(R.id.view2);
        view3=findViewById(R.id.view3);
        view4=findViewById(R.id.view4);

        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.GONE);
        linearLayout4.setVisibility(View.GONE);

    }
}
