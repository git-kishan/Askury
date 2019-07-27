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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.Account.OtherAccount.OtherAccountActivity;
import com.droid.solver.askapp.Answer.QuestionAnswerModel;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private CircleImageView askerImage,answererImage;
    private EmojiTextView askerName,answererName,askerBio,answererBio;
    private EmojiTextView question,answer;
    private ImageView answerImage;
    private String askerId,answererId,answerId,questionId;
    private boolean isStoredLocally;
    private boolean isAnonymous;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_question);
        Intent intent=getIntent();
        askerId=intent.getStringExtra("askerId");
        answerId=intent.getStringExtra("answerId");
        answererId=intent.getStringExtra("answererId");
        questionId=intent.getStringExtra("questionId");
        isStoredLocally=intent.getBooleanExtra("isStoredLocally", false);
        init();
        changeToolbarFont();
        loadProfileImage(true);
        if(isNetworkAvailble()){
            fetchData();
            removeNotificationFromRemoteDatabase();
        }


    }
    private void init(){
        toolbar=findViewById(R.id.toolbar);
        askerImage=findViewById(R.id.circleImageView2);
        answererImage=findViewById(R.id.circleImageView3);
        askerName=findViewById(R.id.textView15);
        answererName=findViewById(R.id.textView20);
        askerBio=findViewById(R.id.textView16);
        answererBio = findViewById(R.id.textView21);
        question=findViewById(R.id.textView22);
        answer=findViewById(R.id.textView1);
        answerImage=findViewById(R.id.imageView19);
        toolbar.setNavigationOnClickListener(this);
        answererImage.setOnClickListener(this);
        askerImage.setOnClickListener(this);
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
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up); }

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
                                        isAnonymous=model.isAnonymous();
                                        mquestion=mquestion==null?"":mquestion.trim();
                                        manswer=manswer==null?"":manswer.trim();

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
            Toast.makeText(NotificationQuestionActivity.this, "Something went wrong,try after some time",Toast.LENGTH_LONG).show();
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
        Intent intent=new Intent(NotificationQuestionActivity.this, OtherAccountActivity.class);
        switch (view.getId()){
            case R.id.circleImageView2://asker image
                if(isAnonymous){
                    Toast.makeText(NotificationQuestionActivity.this, "Anonymous user", Toast.LENGTH_LONG).show();
                }else {
                    intent.putExtra("uid", askerId);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }
                break;
            case R.id.circleImageView3://answerer image
                intent.putExtra("uid", answererId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
                default:
                    onBackPressed();
                    break;
        }

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
