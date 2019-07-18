package com.droid.solver.askapp.Notification;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.emoji.widget.EmojiTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.droid.solver.askapp.Account.OtherAccountActivity;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.like.LikeButton;
import com.like.OnLikeListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAnswerActivity extends AppCompatActivity implements View.OnClickListener, OnLikeListener {

    private CircleImageView askerImage,answererImage;
    private EmojiTextView askerName,askerBio,answererName,answererBio;
    private TextView timeOfAsking,timeOfAnswering;
    private EmojiTextView question,answer;
    private LikeButton likeButton;
    private TextView likeCountTextView;

    private String maskerId,manswererId;
    private String maskerName,maskerBio,manswererName,manswererBio;
    private long mtimeOfAsking,mtimeOfAnswering;
    private String mquestion,manswer,imageAttachedUrl,manswerId;
    private boolean anonymous,isLikedByMe,imageAttached;
    private int fontUsed,likeCount;
    public  int [] fontId=new int[]{R.font.open_sans,R.font.abril_fatface,R.font.aclonica,R.font.bubbler_one,R.font.bitter,R.font.geo};
    private boolean isStoredLocally;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_answer);

        Intent intent=getIntent();
        maskerId=intent.getStringExtra("askerId");
        manswererId=intent.getStringExtra("answererId");
        maskerName=intent.getStringExtra("askerName");
        maskerBio=intent.getStringExtra("askerBio");
        manswererName=intent.getStringExtra("answererName");
        manswererBio=intent.getStringExtra("answererBio");
        mtimeOfAsking=intent.getLongExtra("timeOfAsking", System.currentTimeMillis());
        mtimeOfAnswering=intent.getLongExtra("timeOfAnswering",System.currentTimeMillis());
        mquestion=intent.getStringExtra("question");
        manswer=intent.getStringExtra("answer");
        manswerId=intent.getStringExtra("answerId");
        anonymous=intent.getBooleanExtra("anonymous", false);
        fontUsed=intent.getIntExtra("fontUsed", 0);
        isLikedByMe=intent.getBooleanExtra("isLikedByMe", false);
        likeCount=intent.getIntExtra("likeCount", 0);
        imageAttached=intent.getBooleanExtra("imageAttached", false);
        imageAttachedUrl=intent.getStringExtra("imageAttachedUrl");
        isStoredLocally=intent.getBooleanExtra("isStoredLocally", false);
        Toolbar toolbar=findViewById(R.id.toolbar);
        changeToolbarFont(toolbar);
        askerImage=findViewById(R.id.circleImageView2);
        answererImage=findViewById(R.id.circleImageView3);
        askerName=findViewById(R.id.textView15);
        answererName=findViewById(R.id.textView20);
        askerBio=findViewById(R.id.textView16);
        answererBio=findViewById(R.id.textView21);
        timeOfAsking=findViewById(R.id.textView28);
        timeOfAnswering=findViewById(R.id.textView27);
        question=findViewById(R.id.textView22);
        answer=findViewById(R.id.textView1);
        likeButton=findViewById(R.id.likeButton);
        likeCountTextView=findViewById(R.id.textView25);
        setData();
        removeNotificationFromRemoteDatabase();
        askerImage.setOnClickListener(this);
        answererImage.setOnClickListener(this);
        askerName.setOnClickListener(this);
        answererName.setOnClickListener(this);
        likeButton.setOnLikeListener(this);
        askerImage.setOnClickListener(this);
        askerName.setOnClickListener(this);
        answererImage.setOnClickListener(this);
        answererName.setOnClickListener(this);


    }
    private void changeToolbarFont(Toolbar toolbar){
        for(int i=0;i<toolbar.getChildCount();i++){
            View view=toolbar.getChildAt(i);
            if(view instanceof TextView){
                TextView title= (TextView) view;
                Typeface typeface= ResourcesCompat.getFont(this , R.font.aclonica);
                title.setTypeface(typeface);
                break;
            }
        }
        toolbar.setNavigationOnClickListener(this);

    }

    private void setData(){

        String answererUrl=Constants.PROFILE_PICTURE+"/"+manswererId+Constants.SMALL_THUMBNAIL;
        StorageReference reference= FirebaseStorage.getInstance().getReference();
        if(!anonymous) {
            String askerUrl= Constants.PROFILE_PICTURE+"/"+maskerId+Constants.SMALL_THUMBNAIL;
            GlideApp.with(this)
                    .load(reference.child(askerUrl))
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(askerImage);
            askerName.setText(maskerName);
            askerBio.setText(maskerBio);
        }else {
            GlideApp.with(this)
                    .load(reference.child("url"))
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(askerImage);
            askerName.setText(getString(R.string.someoneasked));

        }


        GlideApp.with(this)
                .load(reference.child(answererUrl))
                .error(R.drawable.ic_placeholder)
                .placeholder(R.drawable.ic_placeholder)
                .into(answererImage);



        answererName.setText(manswererName);
        answererBio.setText(manswererBio);
        question.setText(mquestion);
        answer.setText(manswer);
        fontUsed=fontUsed>fontId.length-1?0:fontUsed;
        Typeface typeface=ResourcesCompat.getFont(this, fontId[fontUsed]);
        answer.setTypeface(typeface);
        likeCount=likeCount<0?0:likeCount;
        likeCountTextView.setText(String.valueOf(likeCount));
        if(isLikedByMe){
            likeButton.setLiked(true);
        }

        String questionAskingTime=getTime(mtimeOfAsking,System.currentTimeMillis());
        String answeringTime=getTime(mtimeOfAnswering, System.currentTimeMillis());

        timeOfAsking.setText(questionAskingTime);
        timeOfAnswering.setText(answeringTime);


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);

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

    public String getTime(long oldDate,long newDate){
        long diff=newDate-oldDate;//777600
        if (diff<0){
            return null;
        }
        return getTimeDifferenceInWords(diff);
    }

    private void removeNotificationFromRemoteDatabase(){
        if(!isStoredLocally) {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            if (FirebaseAuth.getInstance().getCurrentUser() != null && maskerId != null && manswererId != null) {
                db.child("user")
                        .child(maskerId)
                        .child("notification")
                        .child(manswerId)
                        .setValue(null);
            }
        }
    }
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(NotificationAnswerActivity.this, OtherAccountActivity.class);

        switch (view.getId()){
            case R.id.circleImageView2://on asker image clicked
                intent.putExtra("profile_image", maskerId);
                intent.putExtra("uid",maskerId);
                intent.putExtra("user_name",maskerName);
                intent.putExtra("bio",maskerBio);
                startActivity(intent);
                break;
            case R.id.textView15://on asker name clicked
                intent.putExtra("profile_image", maskerId);
                intent.putExtra("uid",maskerId);
                intent.putExtra("user_name",maskerName);
                intent.putExtra("bio",maskerBio);
                startActivity(intent);
                break;
            case R.id.circleImageView3://on answerer image clicked
                intent.putExtra("profile_image", manswererId);
                intent.putExtra("uid",manswererId);
                intent.putExtra("user_name",manswererName);
                intent.putExtra("bio",manswererBio);
                startActivity(intent);
                break;
            case R.id.textView20://on answerer name clicked
                intent.putExtra("profile_image", manswererId);
                intent.putExtra("uid",manswererId);
                intent.putExtra("user_name",manswererName);
                intent.putExtra("bio",manswererBio);
                startActivity(intent);
                break;
            default:
                onBackPressed();
                break;
        }
    }

    @Override
    public void liked(LikeButton likeButton) {

    }

    @Override
    public void unLiked(LikeButton likeButton) {

    }
}
