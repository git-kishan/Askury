package com.droid.solver.askapp.Notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.emoji.widget.EmojiTextView;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.solver.askapp.Answer.QuestionAnswerModel;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private CircleImageView askerImage,answererImage;
    private EmojiTextView askerName,answererName,askerBio,answererBio;;
    private TextView askerTimeAgo,answererTimeAgo;
    private EmojiTextView question,answer;
    private ImageView answerImage;

    private String likerId,likerName,likerImageUrl,likerBio;
    private String askerId,answererId,answerId,questionId,type;
    private long notifiedTime;
    private boolean isStoredLocally;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_question);
        Intent intent=getIntent();
        likerId = intent.getStringExtra("likerId");
        likerName=intent.getStringExtra("likerName");
        likerImageUrl=intent.getStringExtra("likerImageUrl");
        likerBio=intent.getStringExtra("likerBio");
        askerId=intent.getStringExtra("askerId");
        answerId=intent.getStringExtra("answerId");
        answererId=intent.getStringExtra("answererId");
        questionId=intent.getStringExtra("questionId");
        type=intent.getStringExtra("type");
        notifiedTime=intent.getLongExtra("notifiedTime",System.currentTimeMillis());
        isStoredLocally=intent.getBooleanExtra("isStoredLocally", false);
        init();
        changeToolbarFont();
        loadProfileImage(true);
        fetchData();
        removeNotificationFromRemoteDatabase();

    }
    private void init(){
        toolbar=findViewById(R.id.toolbar);
        askerImage=findViewById(R.id.circleImageView2);
        answererImage=findViewById(R.id.circleImageView3);
        askerName=findViewById(R.id.textView15);
        answererName=findViewById(R.id.textView20);
        askerBio=findViewById(R.id.textView16);
        answererBio = findViewById(R.id.textView21);
        askerTimeAgo=findViewById(R.id.textView28);
        answererTimeAgo=findViewById(R.id.textView27);
        question=findViewById(R.id.textView22);
        answer=findViewById(R.id.textView19);
        answerImage=findViewById(R.id.imageView19);
        toolbar.setNavigationOnClickListener(this);
    }

    private void changeToolbarFont(){
        for(int i=0;i<toolbar.getChildCount();i++){
            View view=toolbar.getChildAt(i);
            if(view instanceof TextView ){
                TextView textView=(TextView)view;
                Typeface typeface= ResourcesCompat.getFont(this, R.font.aclonica);
                textView.setTypeface(typeface);
                break;
            }
        }
    }

    private void loadProfileImage(boolean isAnonymous){

        StorageReference reference= FirebaseStorage.getInstance().getReference();
        String askerImageUrl= Constants.PROFILE_PICTURE+"/"+askerId+Constants.SMALL_THUMBNAIL;
        String answererImageUrl=Constants.PROFILE_PICTURE+"/"+answererId+Constants.SMALL_THUMBNAIL;
        GlideApp.with(this)
                .load(reference.child(answererImageUrl))
                .error(R.drawable.ic_placeholder)
                .placeholder(R.drawable.ic_placeholder)
                .into(answererImage);

        if(!isAnonymous){
            GlideApp.with(this)
                    .load(reference.child(askerImageUrl))
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(askerImage);

        }
    }

    private void fetchData(){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        if(askerId!=null&&questionId!=null&&answerImage!=null) {
            db.collection("user")
                    .document(askerId)
                    .collection("question")
                    .document(questionId)
                    .collection("answer")
                    .document(answerId).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                if(task.getResult()!=null){
                                    QuestionAnswerModel model = task.getResult().toObject(QuestionAnswerModel.class);
                                    if(model!=null) {
                                        String maskerName = model.getAskerName();
                                        String maskerBio = model.getAskerBio();
                                        String manswererName = model.getAnswererName();
                                        String manswererBio=model.getAnswererBio();
                                        String mquestion=model.getQuestion();
                                        String manswer=model.getAnswer();
                                        boolean imageAttached=model.isImageAttached();
                                        String imageAttachedUrl=model.getImageAttachedUrl();
                                        boolean isAnonymous=model.isAnonymous();

                                        if(isAnonymous){
                                            maskerName="Someone";
                                            maskerBio="";
                                            loadProfileImage(true);

                                        }else {
                                            maskerName=maskerName==null?"Someone":maskerName;
                                            maskerBio=maskerBio==null?"":maskerBio;
                                            loadProfileImage(false);
                                        }
                                        askerName.setText(maskerName);
                                        askerBio.setText(maskerBio);
                                        answererName.setText(manswererName);
                                        answererBio.setText(manswererBio);
                                        question.setText(mquestion);
                                        answer.setText(manswer);
                                        if(imageAttached){
                                            answerImage.setVisibility(View.VISIBLE);
                                            GlideApp.with(NotificationQuestionActivity.this)
                                                    .load(imageAttachedUrl)
                                                    .placeholder(android.R.color.darker_gray)
                                                    .error(android.R.color.darker_gray)
                                                    .into(answerImage);

                                        }
                                    }
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else {
            //can't proceed at this time
        }
    }

    private void removeNotificationFromRemoteDatabase(){
        if(!isStoredLocally) {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                db.child("user")
                        .child(answererId)
                        .child("notification")
                        .child(questionId)
                        .setValue(null);
            }
        }
    }
    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    private boolean isNetworkAvailble(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(manager!=null) {
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
