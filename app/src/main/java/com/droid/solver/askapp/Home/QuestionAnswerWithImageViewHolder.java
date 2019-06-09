package com.droid.solver.askapp.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.Answer.AnswerActivity;
import com.droid.solver.askapp.Answer.QuestionAnswerModel;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.like.LikeButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
    FirebaseUser user;
    FirebaseFirestore firestoreRootRef;
    ImageView numberOfAnswerImageView,wantToAnswerImageView;
    public QuestionAnswerWithImageViewHolder(@NonNull View itemView) {
        super(itemView);
        firestoreRootRef=FirebaseFirestore.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
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
    public void onLiked(final Context context, final RootQuestionModel model){

        SharedPreferences preferences=context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        final String likerId=user.getUid();
        String likerName=preferences.getString(Constants.userName, null);
        String likerImageUrl=preferences.getString(Constants.profilePicLowUrl, null);
        String likerBio=preferences.getString(Constants.bio, null);

        AnswerLikeModel answerLikeModel=new AnswerLikeModel(likerId, likerName, likerImageUrl, likerBio);

        Map<String ,Object> likeMap=new HashMap<>();
        likeMap.put("answerLikeCount", FieldValue.increment(1));

        Map<String,Object> rootLikeMap=new HashMap<>();
        rootLikeMap.put("recentAnswerLikeCount", FieldValue.increment(1));

        DocumentReference likerReference=firestoreRootRef.collection("user").document(model.getAskerId())
                .collection("question").document(model.getQuestionId()).collection("answer")
                .document(model.getRecentAnswerId()).collection("like").document(likerId);

        DocumentReference questionAnswerModelRef=firestoreRootRef.collection("user").document(model.getAskerId())
                .collection("question").document(model.getQuestionId()).collection("answer")
                .document(model.getRecentAnswerId());

        DocumentReference userAnswerModelRef=firestoreRootRef.collection("user").document(model.getRecentAnswererId())
                .collection("answer").document(model.getRecentAnswerId());

        DocumentReference rootQuestionModelRef=firestoreRootRef.collection("question").document(model.getQuestionId());

        DocumentReference userAnswerLikeRef=firestoreRootRef.collection("user").document(likerId).
                collection("answerLike").document("like");

        Map<String,Object> userAnswerLikeMap=new HashMap<>();
        userAnswerLikeMap.put("answerLikeId",FieldValue.arrayUnion(model.getRecentAnswerId()));

        WriteBatch writeBatch=firestoreRootRef.batch();
        writeBatch.set(likerReference, answerLikeModel);
        writeBatch.update(questionAnswerModelRef, likeMap);
        writeBatch.update(userAnswerModelRef, likeMap);
        writeBatch.update(rootQuestionModelRef, rootLikeMap);
        writeBatch.update(userAnswerLikeRef, userAnswerLikeMap);

        writeBatch.commit().addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(likerId.equals(model.getAskerId())){
                    //give answerer a point
                    //
                }
                Log.i("TAG", "successfully like");
            }
        });
        int count=Integer.parseInt(likeCount.getText().toString())+1;
        likeCount.setText(String.valueOf(count));
        LocalDatabase database=new LocalDatabase(context.getApplicationContext());
        database.insertSingleAnswerLikeModel(model.getRecentAnswerId());
    }
    public void onDisliked(final Context context, final RootQuestionModel model){
        Log.i("TAG", "disliked triggered");
        String likerId=user.getUid();
        Map<String ,Object> likeMap=new HashMap<>();
        likeMap.put("answerLikeCount", FieldValue.increment(-1));

        Map<String,Object> rootLikeMap=new HashMap<>();
        rootLikeMap.put("recentAnswerLikeCount", FieldValue.increment(-1));

        DocumentReference likerReference=firestoreRootRef.collection("user").document(model.getAskerId())
                .collection("question").document(model.getQuestionId()).collection("answer")
                .document(model.getRecentAnswerId()).collection("like").document(likerId);

        DocumentReference questionAnswerModelRef=firestoreRootRef.collection("user").document(model.getAskerId())
                .collection("question").document(model.getQuestionId()).collection("answer")
                .document(model.getRecentAnswerId());

        DocumentReference userAnswerModelRef=firestoreRootRef.collection("user").document(model.getRecentAnswererId())
                .collection("answer").document(model.getRecentAnswerId());

        DocumentReference rootQuestionModelRef=firestoreRootRef.collection("question").document(model.getQuestionId());

        DocumentReference userAnswerLikeRef=firestoreRootRef.collection("user").document(likerId).
                collection("answerLike").document("like");

        Map<String,Object> userAnswerLikeMap=new HashMap<>();
        userAnswerLikeMap.put("answerLikeId",FieldValue.arrayRemove(model.getRecentAnswerId()));

        WriteBatch writeBatch=firestoreRootRef.batch();
        writeBatch.delete(likerReference);
        writeBatch.update(questionAnswerModelRef, likeMap);
        writeBatch.update(userAnswerModelRef, likeMap);
        writeBatch.update(rootQuestionModelRef, rootLikeMap);
        writeBatch.update(userAnswerLikeRef, userAnswerLikeMap);

        writeBatch.commit().addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("TAG", "successfully dislike ");
            }
        });
        int count=Integer.parseInt(likeCount.getText().toString())-1;
        likeCount.setText(String.valueOf(count));
        LocalDatabase database=new LocalDatabase(context.getApplicationContext());
        database.removeAnswerLikeModel(model.getRecentAnswerId());
    }


    public void onNumberOfAnswerClicked(Context context,RootQuestionModel rootQuestionModel){
        Intent intent =new Intent(context, com.droid.solver.askapp.Home.AnswerActivity.class);
        context.startActivity(intent);
    }


}
