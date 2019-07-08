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
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationSurveyActivity extends AppCompatActivity implements View.OnClickListener {

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
        setData();



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
        toolbar.setNavigationOnClickListener(this);
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
        percent1.setVisibility(View.VISIBLE);
        percent2.setVisibility(View.VISIBLE);
        percent3.setVisibility(View.VISIBLE);
        percent4.setVisibility(View.VISIBLE);


    }

    private void setData(){
        StorageReference reference= FirebaseStorage.getInstance().getReference();
        String url= Constants.PROFILE_PICTURE+"/"+askerId+Constants.SMALL_THUMBNAIL;
        GlideApp.with(this)
                .load(reference.child(url))
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(profileImage);
        askerName=askerName==null?"Someone":askerName;
        String doingASuvey=String.format(getString(R.string.some_one_is_doing_a_survey),askerName);
        profileName.setText(doingASuvey);
        profileBio.setText(askerBio);
        question.setText(askerQuestion);

        if(option1){
            option1View.setText(option1Value);
            linearLayout1.setVisibility(View.VISIBLE);
            view1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
            option1View.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));
        }
        if(option2){
            option2View.setText(option2Value);
            linearLayout2.setVisibility(View.VISIBLE);
            view2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
            option2View.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));


        }
        if(option3){
            option3View.setText(option3Value);
            linearLayout3.setVisibility(View.VISIBLE);
            view3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
            option3View.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));



        }
        if(option4){
            option4View.setText(option4Value);
            linearLayout4.setVisibility(View.VISIBLE);
            view4.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.survey_light_pink, null));
            option4View.setTextColor(ResourcesCompat.getColor(getResources(), R.color.survey_dark_pink, null));

        }
        showSelectionPercentage();

    }
    private void showSelectionPercentage(){
        percent1.setTextColor(ResourcesCompat.getColor(getResources(),android.R.color.black,null));
        percent2.setTextColor(ResourcesCompat.getColor(getResources(),android.R.color.black,null));
        percent3.setTextColor(ResourcesCompat.getColor(getResources(),android.R.color.black,null));
        percent4.setTextColor(ResourcesCompat.getColor(getResources(),android.R.color.black,null));
        if(option1&&option2&&option3&&option4) {

            int total=option1Count+option2Count+option3Count+option4Count;

            int firstPercentage=(option1Count*100)/total;
            int secondPercentage=(option2Count*100)/total;
            int thirdPercentage=(option3Count*100)/total;
            int fourthPercentage=100-(firstPercentage+secondPercentage+thirdPercentage);
            String a=firstPercentage+"%";
            String b=secondPercentage+"%";
            String c=thirdPercentage+"%";
            String d=fourthPercentage+"%";
            percent1.setText(a);
            percent2.setText(b);
            percent3.setText(c);
            percent4.setText(d);

        }
        else if(option1&&option2&&option3){
            int total=option1Count+option2Count+option3Count;
            int firstPercentage=(option1Count*100)/total;
            int secondPercentage=(option2Count*100)/total;
            int thirdPercentage=100-(firstPercentage+secondPercentage);
            String a=firstPercentage+"%";
            String b=secondPercentage+"%";
            String c=thirdPercentage+"%";
            percent1.setText(a);
            percent2.setText(b);
            percent3.setText(c);
        }
        else if(option2&&option3&&option4){
            int total=option2Count+option3Count+option4Count;
            int secondPercentage=(option2Count*100)/total;
            int thirdPercentage=(option3Count*100)/total;
            int fourthPercentage=100-(secondPercentage+thirdPercentage);
            String b=secondPercentage+"%";
            String c=thirdPercentage+"%";
            String d=fourthPercentage+"%";
            percent2.setText(b);
            percent3.setText(c);
            percent4.setText(d);
        }
        else if(option3&&option4&&option1){

            int total=option1Count+option3Count+option4Count;
            int firstPercentage=(option1Count*100)/total;
            int thirdPercentage=(option3Count*100)/total;
            int fourthPercentage=100-(firstPercentage+thirdPercentage);
            String a=firstPercentage+"%";
            String c=thirdPercentage+"%";
            String d=fourthPercentage+"%";
            percent1.setText(a);
            percent3.setText(c);
            percent4.setText(d);
        }
        else if(option4&&option1&&option2){

            int total=option1Count+option2Count+option4Count;
            int firstPercentage=(option1Count*100)/total;
            int secondPercentage=(option2Count*100)/total;
            int fourthPercentage=100-(firstPercentage+secondPercentage);
            String a=firstPercentage+"%";
            String b=secondPercentage+"%";
            String d=fourthPercentage+"%";
            percent1.setText(a);
            percent2.setText(b);
            percent4.setText(d);
        }
        else if(option1&&option2){
            int total=option1Count+option2Count;
            int firstPercentage=(option1Count*100)/total;
            int secondPercentage=100-(firstPercentage);
            String a=firstPercentage+"%";
            String b=secondPercentage+"%";
            percent1.setText(a);
            percent2.setText(b);
        }
        else if(option1&&option3){

            int total=option1Count+option3Count;
            int firstPercentage=(option1Count*100)/total;
            int thirdPercentage=100-(firstPercentage);
            String a=firstPercentage+"%";
            String c=thirdPercentage+"%";
            percent1.setText(a);
            percent3.setText(c);
        }
        else if(option1&&option4){

            int total=option1Count+option4Count;
            int firstPercentage=(option1Count*100)/total;
            int fourthPercentage=100-(firstPercentage);
            String a=firstPercentage+"%";
            String d=fourthPercentage+"%";
            percent1.setText(a);
            percent4.setText(d);
        }
        else if(option2&&option3){
            int total=option2Count+option3Count;
            int thirdPercentage=(option3Count*100)/total;
            int secondPercentage=100-(thirdPercentage);
            String b=secondPercentage+"%";
            String c=thirdPercentage+"%";
            percent3.setText(c);
            percent2.setText(b);
        }
        else if(option2&&option4){

            int total=option4Count+option2Count;
            int fourthPercentage=(option4Count*100)/total;
            int secondPercentage=100-(fourthPercentage);
            String b=secondPercentage+"%";
            String d=fourthPercentage+"%";
            percent4.setText(d);
            percent2.setText(b);
        }
        else if(option3&&option4){

            int total=option3Count+option4Count;
            int thirdPercentage=(option3Count*100)/total;
            int fourthPercentage=100-(thirdPercentage);
            String c=thirdPercentage+"%";
            String d=fourthPercentage+"%";
            percent3.setText(c);
            percent4.setText(d);
        }

    }

    @Override
    public void onClick(View view) {

        onBackPressed();
    }
}
