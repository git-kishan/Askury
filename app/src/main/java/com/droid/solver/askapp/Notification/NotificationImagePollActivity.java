package com.droid.solver.askapp.Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.emoji.widget.EmojiTextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationImagePollActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView profileImage;
    private EmojiTextView profileName,profileBio,question;
    private TextView timeAgo;
    private ImageView image1,image2;
    private String imagePollId,imagePollQuestion,askerId,askerName,askerBio,image1Url,image2Url;
    private int image1LikeNo,image2LikeNo;
    private boolean isStoredLocally;
    private long notifiedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_image_poll);

        Intent intent=getIntent();
        imagePollId=intent.getStringExtra("imagePollId");
        imagePollQuestion=intent.getStringExtra("question");
        askerId=intent.getStringExtra("askerId");
        askerName=intent.getStringExtra("askerName");
        askerBio=intent.getStringExtra("askerBio");
        image1Url=intent.getStringExtra("image1Url");
        image2Url=intent.getStringExtra("image2Url");
        image1LikeNo=intent.getIntExtra("image1LikeNo", 0);
        image2LikeNo=intent.getIntExtra("image2LikeNo", 0);
        notifiedTime=intent.getLongExtra("notifiedTime",System.currentTimeMillis());
        isStoredLocally=intent.getBooleanExtra("isStoredLocally", false);
        initView();
        setData();
        removeNotificationFromRemoteDatabase();

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);

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

        Toolbar toolbar=findViewById(R.id.toolbar);
        profileImage=findViewById(R.id.profile_image);
        profileName=findViewById(R.id.asker_name);
        profileBio=findViewById(R.id.about_textview);
        question=findViewById(R.id.question_textview);
        timeAgo=findViewById(R.id.time_ago_textview);
        image1=findViewById(R.id.imageView5);
        image2=findViewById(R.id.imageView6);
        changeToolbarTitleFont(toolbar);
        toolbar.setNavigationOnClickListener(this);
    }

    private void setData(){
        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME,MODE_PRIVATE);
        String tempName=preferences.getString(Constants.userName, "Someone");
        String tempBio=preferences.getString(Constants.bio, "");
        String tempAskerId=preferences.getString(Constants.userId, "askerId");

        imagePollQuestion=imagePollQuestion==null?"":imagePollQuestion;
        askerName=askerName==null?tempName:askerName;
        askerBio=askerBio==null?tempBio:askerBio;
        askerId=askerId==null?tempAskerId:askerId;
        String url=Constants.PROFILE_PICTURE+"/"+askerId+Constants.SMALL_THUMBNAIL;
        image1Url=image1Url==null?"image1Url":image1Url;
        image2Url=image2Url==null?"image2Url":image2Url;

        StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);
        GlideApp.with(this).load(reference)
                .placeholder(android.R.color.darker_gray)
                .error(R.drawable.ic_placeholder)
                .into(profileImage);
        GlideApp.with(this)
                .load(image1Url)
                .into(image1);
        GlideApp.with(this)
                .load(image2Url)
                .into(image2);

        String mtemp=String.format(getString(R.string.username_created_a_poll), askerName);
        profileName.setText(mtemp);
        profileBio.setText(askerBio);
        question.setText(imagePollQuestion);
        String mTimeAgo=getTime(notifiedTime,System.currentTimeMillis() );
        timeAgo.setText(mTimeAgo);
        setPercentage();

    }

    private void setPercentage(){
        image1LikeNo=image1LikeNo<0?0:image1LikeNo;
        image2LikeNo=image2LikeNo<0?0:image2LikeNo;
        int total=image1LikeNo+image2LikeNo;
        int perA=0,perB=0;
        if(total!=0){
            if(image1LikeNo==0&&image2LikeNo!=0){
                perB=(image2LikeNo*100/total);
                perA=100-perB;
            }else if(image2LikeNo==0&&image1LikeNo!=0){
                perA=(image1LikeNo*100/total);
                perB=100-perA;
            }else {
                perA=(image1LikeNo*100/total);
                perB=100-perA;
            }
        }
        View leftView=findViewById(R.id.left_view);
        View rightView=findViewById(R.id.right_view);
        LinearLayout.LayoutParams leftViewParams=new LinearLayout.LayoutParams(
               0 , LinearLayout.LayoutParams.MATCH_PARENT,perA
        );
        LinearLayout.LayoutParams rightViewParams=new LinearLayout.LayoutParams(
                0,LinearLayout.LayoutParams.MATCH_PARENT,perB
        );
        leftView.setLayoutParams(leftViewParams);
        rightView.setLayoutParams(rightViewParams);

        String firstLikes=String.format(getString(R.string.percentage_likes), perA)+"%"+getString(R.string.likes);
        String secondLikes=String.format(getString(R.string.percentage_likes), perB)+"%"+getString(R.string.likes);

        TextView leftTextView=findViewById(R.id.left_text_view);
        TextView rightTextView=findViewById(R.id.right_text_view);
        leftTextView.setText(firstLikes);
        rightTextView.setText(secondLikes);


    }

    private void removeNotificationFromRemoteDatabase(){
        if(!isStoredLocally) {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            if (FirebaseAuth.getInstance().getCurrentUser() != null && askerId != null && imagePollId != null) {
                db.child("user")
                        .child(askerId)
                        .child("notification")
                        .child(imagePollId)
                        .setValue(null);
            }
        }
    }

    private String getTimeDifferenceInWords(long diff){
        long yearInMillis=365*24*60*60*1000L;
        long dayInMillis=24*60*60*1000L;//86400000
        long hourInMillis=60*60*1000L;//3600000
        long minuteInMillis=60*1000L;//60000
        long secondInMillis=1000L;//1000
        if(diff/yearInMillis>0){
            long years=diff/yearInMillis;
            return ""+years+" years ago";
        }
        if(diff/dayInMillis>0){
            long day=diff/dayInMillis;
            return ""+day+" days ago";

        }else if(diff/hourInMillis>0){
            long hour =diff/hourInMillis;
            return ""+hour+" hrs ago";

        }else if(diff/minuteInMillis>0){

            long minutes=diff/minuteInMillis;
            return ""+minutes+" min ago";

        } else if(diff/secondInMillis>0){
            long seconds=diff/secondInMillis;
            return ""+seconds+" sec ago";
        }
        return "";
    }

    private String getTime(long oldDate,long newDate){
        long diff=newDate-oldDate;//777600
        if (diff<0){
            return null;
        }
        return getTimeDifferenceInWords(diff);
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }
}
