package com.droid.solver.askapp.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import androidx.emoji.widget.EmojiTextView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.solver.askapp.Account.OtherAccountActivity;
import com.droid.solver.askapp.Answer.AnswerActivity;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.Main.MainActivity;
import com.droid.solver.askapp.Question.Following;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.homeAnswer.DetailAnswerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.like.LikeButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;

class QuestionAnswerWithImageViewHolder extends RecyclerView.ViewHolder {

    ImageView answerImageView;
    CircleImageView askerImageView,answererImageView;
    EmojiTextView askerName,askerBio,answererName;
    TextView timeAgo;
    EmojiTextView question,answer;
    ImageView threeDot;
    LikeButton likeButton;
    TextView likeCount,answerCount;
    private FirebaseUser user;
    private FirebaseFirestore firestoreRootRef;
    ImageView numberOfAnswerImageView,wantToAnswerImageView;
    private Context context;
    private final int FOLLOW=1;
    private final int UNFOLLOW=2;
    private HomeMessageListener homeMessageListener;

    QuestionAnswerWithImageViewHolder(@NonNull View itemView,final Context context) {
        super(itemView);
        this.context=context;
        homeMessageListener = (HomeMessageListener) context;
        firestoreRootRef=FirebaseFirestore.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        askerImageView=itemView.findViewById(R.id.profile_image);
        answererImageView=itemView.findViewById(R.id.circleImageView);
        askerName=itemView.findViewById(R.id.asker_name);
        askerBio=itemView.findViewById(R.id.about_textview);
        answererName=itemView.findViewById(R.id.answerer_name);
        timeAgo=itemView.findViewById(R.id.time_ago_textview);
        answerImageView=itemView.findViewById(R.id.answer_image_view);
        question=itemView.findViewById(R.id.question_textview);
        answer=itemView.findViewById(R.id.answer_text_view);
        threeDot=itemView.findViewById(R.id.imageView4);
        likeButton=itemView.findViewById(R.id.likeButton);
        likeCount=itemView.findViewById(R.id.delete_poll_text_view);
        answerCount=itemView.findViewById(R.id.textView17);
        numberOfAnswerImageView=itemView.findViewById(R.id.imageView2);
        wantToAnswerImageView=itemView.findViewById(R.id.imageView3);
    }

    void onWantToAnswer(Context context, RootQuestionModel model){

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

    void onAnswerClicked(Context context,RootQuestionModel model){

        Intent intent=new Intent(context, DetailAnswerActivity.class);
        intent.putExtra("askerId", model.getAskerId());
        intent.putExtra("answererId",model.getRecentAnswererId());
        intent.putExtra("askerName",model.getAskerName());
        intent.putExtra("askerBio",model.getAskerBio());
        intent.putExtra("answererName", model.getRecentAnswererName());
        intent.putExtra("answererBio", model.getRecentAnswererBio());
        intent.putExtra("timeOfAsking", model.getTimeOfAsking());
        intent.putExtra("timeOfAnswering", model.getTimeOfAnswering());
        intent.putExtra("question",model.getQuestion());
        intent.putExtra("answer",model.getRecentAnswer());
        intent.putExtra("anonymous", model.isAnonymous());
        intent.putExtra("fontUsed",model.getFontUsed());
        intent.putExtra("isLikedByMe", likeButton.isLiked());
        intent.putExtra("likeCount", model.getRecentAnswerLikeCount());
        context.startActivity(intent);
    }
    void onLiked(final Context context, final RootQuestionModel model){

        SharedPreferences preferences=context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        final String likerId=user.getUid();
        String likerName=preferences.getString(Constants.userName, null);
        String likerImageUrl=preferences.getString(Constants.profilePicLowUrl, null);
        String likerBio=preferences.getString(Constants.bio, null);
        String questionId=model.getQuestionId();
        String askerId=model.getAskerId();
        String answerId=model.getRecentAnswerId();
        String answererId=model.getRecentAnswererId();

        AnswerLikeModel answerLikeModel=new AnswerLikeModel(likerId, likerName, likerImageUrl, likerBio,questionId,askerId,answerId,answererId);

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

                Log.i("TAG", "successfully like");
            }
        });

        int count=Integer.parseInt(likeCount.getText().toString())+1;
        likeCount.setText(String.valueOf(count));
        final LocalDatabase database=new LocalDatabase(context.getApplicationContext());
        model.setLikedByMe(true);
        if(MainActivity.answerLikeList!=null) {
            MainActivity.answerLikeList.add(model.getRecentAnswerId());
        }
        database.insertSingleAnswerLikeModel(model.getRecentAnswerId());

        model.setRecentAnswerLikeCount(model.getRecentAnswerLikeCount()+1);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(getAdapterPosition()<=7){
                    database.updateRootQuestionModel(model);
                }
            }
        });
    }

    void onDisliked(final Context context, final RootQuestionModel model){
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
        final LocalDatabase database=new LocalDatabase(context.getApplicationContext());
        database.removeAnswerLikeModel(model.getRecentAnswerId());

        if(MainActivity.answerLikeList!=null&&MainActivity.answerLikeList.size()>0){
            MainActivity.answerLikeList.remove(model.getRecentAnswerId());

        }
        model.setLikedByMe(false);
            model.setRecentAnswerLikeCount(model.getRecentAnswerLikeCount()-1);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(getAdapterPosition()<=7){
                    database.updateRootQuestionModel(model);
                }
            }
        });
    }

    void onNumberOfAnswerClicked(Context context,RootQuestionModel rootQuestionModel){

        Intent intent =new Intent(context, com.droid.solver.askapp.homeAnswer.AnswerActivity.class);
        intent.putExtra("askerImageUrl", rootQuestionModel.getAskerImageUrlLow());
        intent.putExtra("timeOfAsking", rootQuestionModel.getTimeOfAsking());
        intent.putExtra("askerName", rootQuestionModel.getAskerName());
        intent.putExtra("askerBio", rootQuestionModel.getAskerBio());
        intent.putExtra("questionId", rootQuestionModel.getQuestionId());
        intent.putExtra("question", rootQuestionModel.getQuestion());
        intent.putExtra("askerId", rootQuestionModel.getAskerId());
        intent.putExtra("anonymous", rootQuestionModel.isAnonymous());
        context.startActivity(intent);
    }

    void onAskerImageViewClicked(final Context context,final RootQuestionModel rootQuestionModel){

        if(rootQuestionModel.isAnonymous()){
            homeMessageListener.onSomeMessage("Anonymous user");
        }
        else {
            Intent intent=new Intent(context, OtherAccountActivity.class);
            intent.putExtra("profile_image", rootQuestionModel.getAskerImageUrlLow());
            intent.putExtra("uid", rootQuestionModel.getAskerId());
            intent.putExtra("user_name", rootQuestionModel.getAskerName());
            intent.putExtra("bio", rootQuestionModel.getAskerBio());
            context.startActivity(intent);
        }


    }

    void onAnswererImageViewClicked(final Context context,final RootQuestionModel rootQuestionModel){
        if (rootQuestionModel.isAnonymous()) {
            homeMessageListener.onSomeMessage("Anonymous user");

        }else {
            Intent intent=new Intent(context, OtherAccountActivity.class);
            intent.putExtra("profile_image", rootQuestionModel.getRecentAnswererImageUrlLow());
            intent.putExtra("uid", rootQuestionModel.getRecentAnswererId());
            intent.putExtra("user_name", rootQuestionModel.getRecentAnswererName());
            intent.putExtra("bio", rootQuestionModel.getAskerBio());
            context.startActivity(intent);
        }

    }

    void onThreeDotClicked(final Context context, final RootQuestionModel rootQuestionModel, ArrayList<Object> list,
                                  HomeRecyclerViewAdapter adapter, String status, ArrayList<String> followingIdListFromLocalDatabase){

        @SuppressLint("InflateParams") View dialogView = LayoutInflater.from(context).inflate(R.layout.question_answer_overflow_dialog,
                null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        if(alertDialog.getWindow()!=null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.customAnimations_successfull;
        }
        TextView statusView=dialogView.findViewById(R.id.follow_text_view);
        TextView inAppropriateQuestionView=dialogView.findViewById(R.id.inappropriate_question);
        if (status.equals(HomeRecyclerViewAdapter.UNFOLLOW)) {
            statusView.setText(HomeRecyclerViewAdapter.UNFOLLOW);
        }else {
            statusView.setText(HomeRecyclerViewAdapter.FOLLOW); }


        if(FirebaseAuth.getInstance().getCurrentUser()!=null&&
                !FirebaseAuth.getInstance().getCurrentUser().getUid().equals(rootQuestionModel.getAskerId())){
            View view= dialogView.findViewById(R.id.delete_question_textview);
            view.setEnabled(false);view.setClickable(false);view.setAlpha(0.3f);
        }

        if(FirebaseAuth.getInstance().getCurrentUser()!=null&&
                FirebaseAuth.getInstance().getCurrentUser().getUid().equals(rootQuestionModel.getAskerId())){
            View view=dialogView.findViewById(R.id.report_text_view);
            view.setEnabled(false);view.setClickable(false);view.setAlpha(0.3f);
        }
        if(FirebaseAuth.getInstance().getCurrentUser()!=null&&FirebaseAuth.getInstance().getCurrentUser().getUid()
                .equals(rootQuestionModel.getAskerId())){
            statusView.setEnabled(false);statusView.setClickable(false);statusView.setAlpha(0.3f);
            inAppropriateQuestionView.setEnabled(false);inAppropriateQuestionView.setClickable(false);
            inAppropriateQuestionView.setAlpha(0.3f);
        }
        alertDialog.show();
        handleDialogItemClicked(dialogView, alertDialog,rootQuestionModel,list,adapter,followingIdListFromLocalDatabase);

    }

    private void handleDialogItemClicked(final View view,final AlertDialog dialog,final RootQuestionModel rootQuestionModel,
                                         final ArrayList<Object> list,final HomeRecyclerViewAdapter adapter,
                                         final ArrayList<String> followingIdListFromLocalDatabase){

        TextView reportTextView=view.findViewById(R.id.report_text_view);
        TextView followTextView=view.findViewById(R.id.follow_text_view);
        TextView inAppropriateTextView=view.findViewById(R.id.inappropriate_question);
        TextView deleteTextView=view.findViewById(R.id.delete_question_textview);

        reportTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onReportClicked(rootQuestionModel.getQuestionId(),list,adapter);
            }
        });
        followTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView= (TextView) view;
                String textViewText=textView.getText().toString();
                int  status;
                if(textViewText.equals(HomeRecyclerViewAdapter.FOLLOW)){
                    status=FOLLOW;
                    textView.setText(HomeRecyclerViewAdapter.UNFOLLOW);
                }else if(textViewText.equals(HomeRecyclerViewAdapter.UNFOLLOW)){
                    status=UNFOLLOW;
                    textView.setText(HomeRecyclerViewAdapter.FOLLOW);
                }else
                    status=0;

                onFollowClicked(rootQuestionModel, status, followingIdListFromLocalDatabase);
                dialog.dismiss();
            }
        }) ;

        inAppropriateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDatabase database=new LocalDatabase(context.getApplicationContext());
                database.insertReportedQuestion(rootQuestionModel.getQuestionId());
                list.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
                homeMessageListener.onSomeMessage("we'll look into your suggestion, thank you...");
                dialog.dismiss();
            }
        });
        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onDeleteClicked(rootQuestionModel, list, adapter);

            }
        });
    }

    private void onReportClicked(String questionId,ArrayList<Object> list,HomeRecyclerViewAdapter adapter){
        @SuppressLint("InflateParams") View dialogView=LayoutInflater.from(context).inflate(R.layout.question_report_dialog,
                null,false);
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog dialog=builder.create();
        if(dialog.getWindow()!=null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().getAttributes().windowAnimations = R.style.customAnimations_bounce;
        }
        dialog.show();
        onReportItemClicked(dialogView,dialog,questionId,list,adapter);
    }


    private void onReportItemClicked(final View view, final AlertDialog dialog,
                                     final String questionId,final ArrayList<Object> list,final HomeRecyclerViewAdapter adapter){

        TextView spamTextView=view.findViewById(R.id.spam);
        TextView selfPromotionTextView=view.findViewById(R.id.self_promotion);
        TextView violentTextView=view.findViewById(R.id.violent);
        TextView dontLikeTextView=view.findViewById(R.id.dontlike);

        spamTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDatabase database=new LocalDatabase(context.getApplicationContext());
                database.insertReportedQuestion(questionId);
                list.remove(getAdapterPosition());
                dialog.dismiss();
                adapter.notifyItemRemoved(getAdapterPosition());
                homeMessageListener.onSomeMessage("We'll try to not show such question");



            }
        });
        selfPromotionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDatabase database=new LocalDatabase(context.getApplicationContext());
                database.insertReportedQuestion(questionId);
                list.remove(getAdapterPosition());
                dialog.dismiss();
                adapter.notifyItemRemoved(getAdapterPosition());
                homeMessageListener.onSomeMessage("We'll try to not show such question");

            }
        });
        violentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDatabase database=new LocalDatabase(context.getApplicationContext());
                database.insertReportedQuestion(questionId);
                list.remove(getAdapterPosition());
                dialog.dismiss();
                adapter.notifyItemRemoved(getAdapterPosition());
                homeMessageListener.onSomeMessage("We'll try to not show such question");


            }
        });
        dontLikeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDatabase database=new LocalDatabase(context.getApplicationContext());
                database.insertReportedQuestion(questionId);
                list.remove(getAdapterPosition());
                dialog.dismiss();
                adapter.notifyItemRemoved(getAdapterPosition());
                homeMessageListener.onSomeMessage("We'll try to not show such question");

            }
        });
    }

    private void onDeleteClicked(final RootQuestionModel questionModel, final ArrayList<Object> list, final HomeRecyclerViewAdapter adapter){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        @SuppressLint("InflateParams") View rootview = LayoutInflater.from(context).inflate(R.layout.sure_to_delete_dialog,
                null,false );
        builder.setView(rootview);
        final AlertDialog alertDialog = builder.create();
        if(alertDialog.getWindow()!=null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.customAnimations_bounce;
        }
        alertDialog.show();

        View cancelButton=rootview.findViewById(R.id.cancel_button);
        View deleteButton=rootview.findViewById(R.id.delete_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                DocumentReference rootReference=FirebaseFirestore.getInstance().collection("question").
                        document(questionModel.getQuestionId());
                DocumentReference uploaderReference=FirebaseFirestore.getInstance().collection("user")
                        .document(questionModel.getAskerId()).collection("question")
                        .document(questionModel.getQuestionId());
                WriteBatch batch=FirebaseFirestore.getInstance().batch();
                batch.delete(rootReference);
                batch.delete(uploaderReference);

                if(isNetworkAvailable()){

                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("TAG","image poll successfully deleted");
                            homeMessageListener.onSomeMessage("Question deleted successfully");
                        }
                    });
                    list.remove(getAdapterPosition());
                    adapter.notifyItemRemoved(getAdapterPosition());

                }else {
                    homeMessageListener.onSomeMessage("No internet connection");
                }
            }
        });
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager!=null){
            NetworkInfo info=manager.getActiveNetworkInfo();
            return info!=null&&info.isConnected();
        }
        return false;
    }

    private void onFollowClicked(final RootQuestionModel questionModel, int status,
                                 final ArrayList<String> followingListFromLocalDatabase){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            if(status==FOLLOW){

                String selfUid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                SharedPreferences preferences=context.getSharedPreferences(Constants.PREFERENCE_NAME,Context.MODE_PRIVATE);
                String selfName=preferences.getString(Constants.userName, null);
                String selfImageUrl=preferences.getString(Constants.LOW_IMAGE_URL, null);
                String selfBio=preferences.getString(Constants.bio, null);

                DocumentReference selfFollowingRef=FirebaseFirestore.getInstance().collection("user")
                        .document(selfUid).collection("following")
                        .document(questionModel.getAskerId());
                DocumentReference askerFollowerRef=FirebaseFirestore.getInstance().collection("user")
                        .document(questionModel.getAskerId()).collection("follower")
                        .document(selfUid);
                DocumentReference selfFollowingCountRef=FirebaseFirestore.getInstance().collection("user")
                        .document(selfUid);
                DocumentReference askerFollowerCountRef=FirebaseFirestore.getInstance().collection("user")
                        .document(questionModel.getAskerId());

                Map<String,Object> selfFollowingMap=new HashMap<>();
                Map<String,Object> askerFollowerMap=new HashMap<>();
                Map<String ,Object> selfFollowingCountMap=new HashMap<>();
                Map<String,Object> askerFollowerCountMap=new HashMap<>();

                selfFollowingMap.put("followingId", questionModel.getAskerId());
                selfFollowingMap.put("followingName",questionModel.getAskerName());
                selfFollowingMap.put("followingImageUrl", questionModel.getAskerImageUrlLow());
                selfFollowingMap.put("followingBio",questionModel.getAskerBio());
                selfFollowingMap.put("selfId", selfUid);

                askerFollowerMap.put("followerId", selfUid);
                assert selfName != null;
                askerFollowerMap.put("followerName", selfName);
                assert selfImageUrl != null;
                askerFollowerMap.put("followerImageUrl", selfImageUrl);
                assert selfBio != null;
                askerFollowerMap.put("followerBio",selfBio);
                askerFollowerMap.put("selfId", questionModel.getAskerId());

                selfFollowingCountMap.put("followingCount", FieldValue.increment(1));
                askerFollowerCountMap.put("followerCount", FieldValue.increment(1));

                WriteBatch batch=FirebaseFirestore.getInstance().batch();
                batch.set(selfFollowingRef, selfFollowingMap, SetOptions.merge());
                batch.set(askerFollowerRef, askerFollowerMap,SetOptions.merge());
                batch.set(selfFollowingCountRef, selfFollowingCountMap,SetOptions.merge());
                batch.set(askerFollowerCountRef, askerFollowerCountMap,SetOptions.merge());



                if(isNetworkAvailable()){
                    followingListFromLocalDatabase.add(questionModel.getAskerId());
                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.i("TAG", "follower add successfully");
                            LocalDatabase database=new LocalDatabase(context.getApplicationContext());
                            String followingId=questionModel.getAskerId();
                            String followingName=questionModel.getAskerName();
                            String followingImageUrl=questionModel.getAskerImageUrlLow();
                            String followingBio=questionModel.getAskerBio();
                            Following following=new Following(followingId, followingName, followingImageUrl
                                    , followingBio);
                            ArrayList<Following> followingsList=new ArrayList<>();
                            followingsList.add(following);
                            database.insertFollowingModel(followingsList);


                        }
                    });
                }else {
                    homeMessageListener.onSomeMessage("No internet connection");

                }
            }
            else if (status == UNFOLLOW) {

                String selfUid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                DocumentReference selfFollowingRef=FirebaseFirestore.getInstance().collection("user")
                        .document(selfUid).collection("following")
                        .document(questionModel.getAskerId());
                DocumentReference askerFollowerRef=FirebaseFirestore.getInstance().collection("user")
                        .document(questionModel.getAskerId()).collection("follower")
                        .document(selfUid);
                DocumentReference selfFollowingCountRef=FirebaseFirestore.getInstance().collection("user")
                        .document(selfUid);
                DocumentReference askerFollowerCountRef=FirebaseFirestore.getInstance().collection("user")
                        .document(questionModel.getAskerId());

                Map<String ,Object> selfFollowingCountMap=new HashMap<>();
                Map<String,Object> askerFollowerCountMap=new HashMap<>();

                selfFollowingCountMap.put("followingCount", FieldValue.increment(-1));
                askerFollowerCountMap.put("followerCount", FieldValue.increment(-1));

                WriteBatch batch=FirebaseFirestore.getInstance().batch();
                batch.delete(selfFollowingRef);
                batch.delete(askerFollowerRef);
                batch.set(selfFollowingCountRef, selfFollowingCountMap,SetOptions.merge());
                batch.set(askerFollowerCountRef, askerFollowerCountMap,SetOptions.merge());


                if(isNetworkAvailable()){
                    followingListFromLocalDatabase.remove(questionModel.getAskerId());
                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.i("TAG","unfollow successfully");
                            LocalDatabase  database=new LocalDatabase(context.getApplicationContext());
                            database.removeFollowingModel(questionModel.getAskerId());

                        }
                    });

                }else {
                    homeMessageListener.onSomeMessage("No internet connection");

                }
            }
        }
        else {

            //sign in again
        }
    }
}
