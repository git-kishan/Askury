package com.droid.solver.askapp.Home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import androidx.emoji.widget.EmojiTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.droid.solver.askapp.Account.OtherAccountActivity;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.Question.Following;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.Survey.AskSurveyModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

class SurveyViewHolder extends RecyclerView.ViewHolder {
    EmojiTextView profileName,bio,question;
    CircleImageView profileImage;
    ImageView threeDot;
    LinearLayout container1,container2,container3,container4;
    EmojiTextView option1TextView,option2TextView,option3TextView,option4TextView;
    private EmojiTextView percent1,percent2,percent3,percent4;
    private LinearLayout view1,view2,view3,view4;
    private Context context;
    private Animation animation;
    private FirebaseUser user;
    private FirebaseFirestore firestoreRootRef;
    private final int FOLLOW=1;
    private final int UNFOLLOW=2;
    private HomeMessageListener homeMessageListener;

     SurveyViewHolder(@NonNull View itemView,Context context) {
        super(itemView);
        this.context=context;
        homeMessageListener= (HomeMessageListener) context;
        user=FirebaseAuth.getInstance().getCurrentUser();
        firestoreRootRef=FirebaseFirestore.getInstance();
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
    void onContainer1Clicked(final AskSurveyModel surveyModel){
         new Handler().post(new Runnable() {
             @Override
             public void run() {
                 animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
                 animation.setDuration(500);
                 container1.startAnimation(animation);
                 changeColor(container1.getId());

             }
         });
        changeBackGroundOfLinearLayout();
        makeContainerUnClickable();
        updateSelectionInRemoteDatabase(1, surveyModel);

    }
    void onContainer2Clicked(final AskSurveyModel surveyModel){
         new Handler().post(new Runnable() {
             @Override
             public void run() {
                 animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
                 animation.setDuration(500);
                 container2.startAnimation(animation);
                 changeColor(container2.getId());

             }
         });
        changeBackGroundOfLinearLayout();
        makeContainerUnClickable();
        updateSelectionInRemoteDatabase(2, surveyModel);

    }
    void onContainer3Clicked(final AskSurveyModel surveyModel){
         new Handler().post(new Runnable() {
             @Override
             public void run() {
                 animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
                 animation.setDuration(500);
                 container3.startAnimation(animation);
                 changeColor(container3.getId());

             }
         });
        changeBackGroundOfLinearLayout();
        makeContainerUnClickable();
        updateSelectionInRemoteDatabase(3, surveyModel);

    }
    void onContainer4Clicked(final AskSurveyModel surveyModel){
         new Handler().post(new Runnable() {
             @Override
             public void run() {
                 animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
                 animation.setDuration(500);
                 container4.startAnimation(animation);
                 changeColor(container4.getId());

             }
         });
        changeBackGroundOfLinearLayout();
        makeContainerUnClickable();
        updateSelectionInRemoteDatabase(4, surveyModel);

    }

    private void changeColor(int id){

        Drawable lightGreenDrawable=ResourcesCompat.getDrawable(context.getResources(), R.drawable.round_corner_light_green, null);
        Drawable lightPinkDrawable=ResourcesCompat.getDrawable(context.getResources(), R.drawable.round_corner_light_pink, null);

        switch (id){
            case R.id.linearLayout3:
                view1.setBackground(lightGreenDrawable);
                view2.setBackground(lightPinkDrawable);
                view3.setBackground(lightPinkDrawable);
                view4.setBackground(lightPinkDrawable);
                option1TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_green, null));
                option2TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option3TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option4TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout4:
                view1.setBackground(lightPinkDrawable);
                view2.setBackground(lightGreenDrawable);
                view3.setBackground(lightPinkDrawable);
                view4.setBackground(lightPinkDrawable);
                option1TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option2TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_green, null));
                option3TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option4TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout5:
                view1.setBackground(lightPinkDrawable);
                view2.setBackground(lightPinkDrawable);
                view3.setBackground(lightGreenDrawable);
                view4.setBackground(lightPinkDrawable);
                option1TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option2TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option3TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_green, null));
                option4TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout6:
                view1.setBackground(lightPinkDrawable);
                view2.setBackground(lightPinkDrawable);
                view3.setBackground(lightPinkDrawable);
                view4.setBackground(lightGreenDrawable);
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

    private void makeContainerUnClickable(){
          container1.setClickable(false);
          container2.setClickable(false);
          container3.setClickable(false);
          container4.setClickable(false);

        }

        private   void updateSelectionInRemoteDatabase(final int selection, final AskSurveyModel surveyModel){

            DocumentReference rootSurveyRef=firestoreRootRef.collection("survey").document(surveyModel.getSurveyId());
            DocumentReference askerSurveyRef=firestoreRootRef.collection("user").document(surveyModel.getAskerId())
                    .collection("survey").document(surveyModel.getSurveyId());

            DocumentReference userSurveyLikeRef=firestoreRootRef.collection("user").document(user.getUid())
                    .collection("surveyParticipated").document("participated");

            final Map<String,Object > surveyMap=new HashMap<>();
            if(selection==1) {
                surveyMap.put("option1Count", FieldValue.increment(1));
                surveyModel.setOption1Count(surveyModel.getOption1Count()+1);
                showSelectionPercentage(surveyModel);
            }
            else if(selection==2) {
                surveyMap.put("option2Count", FieldValue.increment(1));
                surveyModel.setOption2Count(surveyModel.getOption2Count()+1);
                showSelectionPercentage(surveyModel);
            }
            else if(selection==3) {
                surveyMap.put("option3Count", FieldValue.increment(1));
                surveyModel.setOption3Count(surveyModel.getOption3Count()+1);
                showSelectionPercentage(surveyModel);

            }
            else if(selection==4) {
                surveyMap.put("option4Count", FieldValue.increment(1));
                surveyModel.setOption4Count(surveyModel.getOption4Count()+1);
                showSelectionPercentage(surveyModel);
            }

            final Map<String,Object> selectionMap=new HashMap<>();
            selectionMap.put(surveyModel.getSurveyId(), selection);

            final Map<String,Object> selectionLikeMap=new HashMap<>();
            selectionLikeMap.put("surveyMapList",FieldValue.arrayUnion(selectionMap));

            WriteBatch batch=firestoreRootRef.batch();
            batch.update(rootSurveyRef, surveyMap);
            batch.update(askerSurveyRef, surveyMap);
            batch.set(userSurveyLikeRef, selectionLikeMap, SetOptions.merge());

          AsyncTask.execute(new Runnable() {
              @Override
              public void run() {
                  if(getAdapterPosition()<=7) {
                      surveyModel.setOptionSelectedByMe(selection);
                      LocalDatabase database = new LocalDatabase(context.getApplicationContext());
                      database.updateSurveyAskedModel(surveyModel);
                  }
              }
          });


            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    selectionMap.clear();
                    selectionLikeMap.clear();
                    Log.i("TAG", "survey  batch written complete");
                    final HashMap<String,Integer> participatedMap=new HashMap<>();
                    participatedMap.put(surveyModel.getSurveyId(),selection);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            LocalDatabase localDatabase=new LocalDatabase(context.getApplicationContext());
                            localDatabase.removeSurveyParticipatedModel(surveyModel.getSurveyId());
                            localDatabase.insertSurveyParticipatedModel(participatedMap);
                        }
                    });
                }
            });

        }

        private void showSelectionPercentage(final AskSurveyModel surveyModel){

        boolean isOption1=surveyModel.isOption1();
        boolean isOption2=surveyModel.isOption2();
        boolean isOption3=surveyModel.isOption3();
        boolean isOption4=surveyModel.isOption4();
        percent1.setTextColor(ResourcesCompat.getColor(context.getResources(),android.R.color.black,null));
        percent2.setTextColor(ResourcesCompat.getColor(context.getResources(),android.R.color.black,null));
        percent3.setTextColor(ResourcesCompat.getColor(context.getResources(),android.R.color.black,null));
        percent4.setTextColor(ResourcesCompat.getColor(context.getResources(),android.R.color.black,null));

        if(isOption1&&isOption2&&isOption3&&isOption4) {
            int option1Count=surveyModel.getOption1Count();
            int option2Count=surveyModel.getOption2Count();
            int option3Count=surveyModel.getOption3Count();
            int option4Count=surveyModel.getOption4Count();
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
        else if(isOption1&&isOption2&&isOption3){
            int option1Count=surveyModel.getOption1Count();
            int option2Count=surveyModel.getOption2Count();
            int option3Count=surveyModel.getOption3Count();
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
        else if(isOption2&&isOption3&&isOption4){
            int option2Count=surveyModel.getOption2Count();
            int option3Count=surveyModel.getOption3Count();
            int option4Count=surveyModel.getOption4Count();
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
        else if(isOption3&&isOption4&&isOption1){
            int option1Count=surveyModel.getOption1Count();
            int option3Count=surveyModel.getOption3Count();
            int option4Count=surveyModel.getOption4Count();
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
        else if(isOption4&&isOption1&&isOption2){
            int option1Count=surveyModel.getOption1Count();
            int option2Count=surveyModel.getOption2Count();
            int option4Count=surveyModel.getOption4Count();
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
        else if(isOption1&&isOption2){
            int option1Count=surveyModel.getOption1Count();
            int option2Count=surveyModel.getOption2Count();
            int total=option1Count+option2Count;
            int firstPercentage=(option1Count*100)/total;
            int secondPercentage=100-(firstPercentage);
            String a=firstPercentage+"%";
            String b=secondPercentage+"%";
            percent1.setText(a);
            percent2.setText(b);
        }
        else if(isOption1&&isOption3){
            int option1Count=surveyModel.getOption1Count();
            int option3Count=surveyModel.getOption3Count();
            int total=option1Count+option3Count;
            int firstPercentage=(option1Count*100)/total;
            int thirdPercentage=100-(firstPercentage);
            String a=firstPercentage+"%";
            String c=thirdPercentage+"%";
            percent1.setText(a);
            percent3.setText(c);
        }
        else if(isOption1&&isOption4){
            int option1Count=surveyModel.getOption1Count();
            int option4Count=surveyModel.getOption4Count();
            int total=option1Count+option4Count;
            int firstPercentage=(option1Count*100)/total;
            int fourthPercentage=100-(firstPercentage);
            String a=firstPercentage+"%";
            String d=fourthPercentage+"%";
            percent1.setText(a);
            percent4.setText(d);
        }
        else if(isOption2&&isOption3){
            int option2Count=surveyModel.getOption2Count();
            int option3Count=surveyModel.getOption3Count();
            int total=option2Count+option3Count;
            int thirdPercentage=(option3Count*100)/total;
            int secondPercentage=100-(thirdPercentage);
            String b=secondPercentage+"%";
            String c=thirdPercentage+"%";
            percent3.setText(c);
            percent2.setText(b);
        }
        else if(isOption2&&isOption4){
            int option4Count=surveyModel.getOption4Count();
            int option2Count=surveyModel.getOption2Count();
            int total=option4Count+option2Count;
            int fourthPercentage=(option4Count*100)/total;
            int secondPercentage=100-(fourthPercentage);
            String b=secondPercentage+"%";
            String d=fourthPercentage+"%";
            percent4.setText(d);
            percent2.setText(b);
        }
        else if(isOption3&&isOption4){
            int option3Count=surveyModel.getOption3Count();
            int option4Count=surveyModel.getOption4Count();
            int total=option3Count+option4Count;
            int thirdPercentage=(option3Count*100)/total;
            int fourthPercentage=100-(thirdPercentage);
            String c=thirdPercentage+"%";
            String d=fourthPercentage+"%";
            percent3.setText(c);
            percent4.setText(d);
        }

        }

        void showSelection(int selection,final AskSurveyModel surveyModel){
            animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
            animation.setDuration(500);
            Handler handler=new Handler();

        if(selection==1){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showSelectionPercentage(surveyModel);
                    container1.startAnimation(animation);
                    changeColor(container1.getId());
                }
            });

        }
        else if(selection==2){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showSelectionPercentage(surveyModel);
                    container2.startAnimation(animation);
                    changeColor(container2.getId());
                }
            });

        }
        else if(selection==3){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showSelectionPercentage(surveyModel);
                    container3.startAnimation(animation);
                    changeColor(container3.getId());
                }
            });

        }
        else if(selection==4){
           handler.post(new Runnable() {
               @Override
               public void run() {
                   showSelectionPercentage(surveyModel);
                   container4.startAnimation(animation);
                   changeColor(container4.getId());
               }
           });

        }else if(selection==0){
            //nothing to do
            Log.i("TAG", "survey selection :- "+selection);
        }

        makeContainerUnClickable();
         changeBackGroundOfLinearLayout();
        }

        void onProfileImageClicked(final Context context,final AskSurveyModel surveyModel){
            Activity activity=(Activity)context;
            Intent intent=new Intent(context, OtherAccountActivity.class);
            intent.putExtra("profile_image", surveyModel.getAskerName());
            intent.putExtra("uid", surveyModel.getAskerId());
            intent.putExtra("user_name", surveyModel.getAskerName());
            intent.putExtra("bio", surveyModel.getAskerBio());
            context.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_down);

        }

    void onThreeDotClicked(final Context context, final AskSurveyModel surveyModel, ArrayList<Object> list,
                           HomeRecyclerViewAdapter adapter, String status, ArrayList<String> followingIdListFromLocalDatabase){

        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(context).inflate(R.layout.survey_overflow_dialog,
                null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        if(alertDialog.getWindow()!=null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.customAnimations_successfull;
        }

        TextView statusView=dialogView.findViewById(R.id.follow_text_view);
        if (status.equals(HomeRecyclerViewAdapter.UNFOLLOW)) {
            statusView.setText(HomeRecyclerViewAdapter.UNFOLLOW);
        }else {
            statusView.setText(HomeRecyclerViewAdapter.FOLLOW); }

        if(FirebaseAuth.getInstance().getCurrentUser()!=null&&
                !FirebaseAuth.getInstance().getCurrentUser().getUid().equals(surveyModel.getAskerId())){
            View view=dialogView.findViewById(R.id.delete_survey_textview);
            view.setEnabled(false);view.setClickable(false);view.setAlpha(0.3f);
        }
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(surveyModel.getAskerId())){
            View view=dialogView.findViewById(R.id.report_text_view);
            view.setEnabled(false);view.setClickable(false);view.setAlpha(0.3f);
        }if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(surveyModel.getAskerId())){
            statusView.setEnabled(false);statusView.setClickable(false);statusView.setAlpha(0.3f);
        }

        alertDialog.show();
        handleDialogItemClicked(dialogView,alertDialog,surveyModel,list,adapter,followingIdListFromLocalDatabase);

    }

    private void handleDialogItemClicked(final View view,final AlertDialog dialog,final AskSurveyModel surveyModel,
                                         final ArrayList<Object> list,final HomeRecyclerViewAdapter adapter,
                                         final ArrayList<String> followingIdListFromLocalDatabase){

        TextView reportTextView=view.findViewById(R.id.report_text_view);
        TextView followTextView=view.findViewById(R.id.follow_text_view);
        final TextView deleteSurveyView=view.findViewById(R.id.delete_survey_textview);

        reportTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onReportClicked(surveyModel.getSurveyId(),list,adapter);
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

                onFollowClicked(surveyModel, status,followingIdListFromLocalDatabase);
                dialog.dismiss();
            }
        });
        deleteSurveyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onDeleteClicked(surveyModel, list, adapter);
            }
        });
    }

    private void onReportClicked(String surveyId,ArrayList<Object> list,HomeRecyclerViewAdapter adapter){
        @SuppressLint("InflateParams") View dialogView=LayoutInflater.from(context).inflate(R.layout.survey_report_dialog,
                null,false);
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog dialog=builder.create();
        if(dialog.getWindow()!=null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().getAttributes().windowAnimations = R.style.customAnimations_bounce;
        }
        dialog.show();
        onReportItemClicked(dialogView,dialog,surveyId,list,adapter);
    }

    private void onReportItemClicked(final View view, final AlertDialog dialog,
                                     final String surveyId,final ArrayList<Object> list,final HomeRecyclerViewAdapter adapter){

        TextView spamTextView=view.findViewById(R.id.spam);
        TextView selfPromotionTextView=view.findViewById(R.id.self_promotion);
        TextView violentTextView=view.findViewById(R.id.violent);
        TextView dontLikeTextView=view.findViewById(R.id.dontlike);

        spamTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDatabase database=new LocalDatabase(context.getApplicationContext());
                database.insertReportedSurvey(surveyId);
                list.remove(getAdapterPosition());
                dialog.dismiss();
                adapter.notifyItemRemoved(getAdapterPosition());
                homeMessageListener.onSomeMessage("We'll try to not show such survey");



            }
        });
        selfPromotionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDatabase database=new LocalDatabase(context.getApplicationContext());
                database.insertReportedSurvey(surveyId);
                list.remove(getAdapterPosition());
                dialog.dismiss();
                adapter.notifyItemRemoved(getAdapterPosition());
                homeMessageListener.onSomeMessage("We'll try to not show such survey");


            }
        });
        violentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDatabase database=new LocalDatabase(context.getApplicationContext());
                database.insertReportedSurvey(surveyId);
                list.remove(getAdapterPosition());
                dialog.dismiss();
                adapter.notifyItemRemoved(getAdapterPosition());
                homeMessageListener.onSomeMessage("We'll try to not show such survey");


            }
        });
        dontLikeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDatabase database=new LocalDatabase(context.getApplicationContext());
                database.insertReportedSurvey(surveyId);
                list.remove(getAdapterPosition());
                dialog.dismiss();
                adapter.notifyItemRemoved(getAdapterPosition());
                homeMessageListener.onSomeMessage("We'll try to not show such survey");


            }
        });
    }

    private void onDeleteClicked(final AskSurveyModel surveyModel,final ArrayList<Object> list, final HomeRecyclerViewAdapter adapter){
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
                DocumentReference rootReference=FirebaseFirestore.getInstance().collection("survey").
                        document(surveyModel.getSurveyId());
                DocumentReference uploaderReference=FirebaseFirestore.getInstance().collection("user")
                        .document(surveyModel.getAskerId()).collection("survey")
                        .document(surveyModel.getSurveyId());
                WriteBatch batch=FirebaseFirestore.getInstance().batch();
                batch.delete(rootReference);
                batch.delete(uploaderReference);

                if(isNetworkAvailable()){

                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            homeMessageListener.onSomeMessage("Survey deleted");
                            Log.d("TAG","survey  successfully deleted");
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

    private void onFollowClicked(final AskSurveyModel surveyModel, int status,
                                 final ArrayList<String> followingListFromLocalDatabase){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            if(status==FOLLOW) {

                try {
                    String selfUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
                    String selfName = preferences.getString(Constants.userName, null);
                    String selfImageUrl = preferences.getString(Constants.LOW_IMAGE_URL, null);
                    String selfBio = preferences.getString(Constants.bio, null);

                    DocumentReference selfFollowingRef = FirebaseFirestore.getInstance().collection("user")
                            .document(selfUid).collection("following")
                            .document(surveyModel.getAskerId());
                    DocumentReference askerFollowerRef = FirebaseFirestore.getInstance().collection("user")
                            .document(surveyModel.getAskerId()).collection("follower")
                            .document(selfUid);
                    DocumentReference selfFollowingCountRef = FirebaseFirestore.getInstance().collection("user")
                            .document(selfUid);
                    DocumentReference askerFollowerCountRef = FirebaseFirestore.getInstance().collection("user")
                            .document(surveyModel.getAskerId());

                    Map<String, Object> selfFollowingMap = new HashMap<>();
                    Map<String, Object> askerFollowerMap = new HashMap<>();
                    Map<String, Object> selfFollowingCountMap = new HashMap<>();
                    Map<String, Object> askerFollowerCountMap = new HashMap<>();

                    selfFollowingMap.put("followingId", surveyModel.getAskerId());
                    selfFollowingMap.put("followingName", surveyModel.getAskerName());
                    selfFollowingMap.put("followingImageUrl", surveyModel.getAskerImageUrlLow());
                    selfFollowingMap.put("followingBio", surveyModel.getAskerBio());
                    selfFollowingMap.put("selfId", selfUid);

                    askerFollowerMap.put("followerId", selfUid);
                    askerFollowerMap.put("followerName", selfName);
                    askerFollowerMap.put("followerImageUrl", selfImageUrl);
                    askerFollowerMap.put("followerBio", Objects.requireNonNull(selfBio));
                    askerFollowerMap.put("selfId", surveyModel.getAskerId());

                    selfFollowingCountMap.put("followingCount", FieldValue.increment(1));
                    askerFollowerCountMap.put("followerCount", FieldValue.increment(1));

                    WriteBatch batch = FirebaseFirestore.getInstance().batch();
                    batch.set(selfFollowingRef, selfFollowingMap, SetOptions.merge());
                    batch.set(askerFollowerRef, askerFollowerMap, SetOptions.merge());
                    batch.set(selfFollowingCountRef, selfFollowingCountMap, SetOptions.merge());
                    batch.set(askerFollowerCountRef, askerFollowerCountMap, SetOptions.merge());


                    if (isNetworkAvailable()) {
                        followingListFromLocalDatabase.add(surveyModel.getAskerId());
                        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.i("TAG", "follower add successfully");
                                final String followingId = surveyModel.getAskerId();
                                String followingName = surveyModel.getAskerName();
                                String followingImageUrl = surveyModel.getAskerImageUrlLow();
                                String followingBio = surveyModel.getAskerBio();
                                Following following = new Following(followingId, followingName, followingImageUrl
                                        , followingBio);
                                final ArrayList<Following> followingsList = new ArrayList<>();
                                followingsList.add(following);
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        LocalDatabase database = new LocalDatabase(context.getApplicationContext());
                                        database.removeFollowingModel(followingId);
                                        database.insertFollowingModel(followingsList);

                                    }
                                });
                            }
                        });
                    } else {
                        homeMessageListener.onSomeMessage("No internet connection");

                    }
                }catch (AssertionError e){
                    Log.i("TAG", "Assertion error occurs in following :- "+e.getMessage());
                }
            }
            else if (status == UNFOLLOW) {

                String selfUid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                DocumentReference selfFollowingRef=FirebaseFirestore.getInstance().collection("user")
                        .document(selfUid).collection("following")
                        .document(surveyModel.getAskerId());
                DocumentReference askerFollowerRef=FirebaseFirestore.getInstance().collection("user")
                        .document(surveyModel.getAskerId()).collection("follower")
                        .document(selfUid);
                DocumentReference selfFollowingCountRef=FirebaseFirestore.getInstance().collection("user")
                        .document(selfUid);
                DocumentReference askerFollowerCountRef=FirebaseFirestore.getInstance().collection("user")
                        .document(surveyModel.getAskerId());

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
                    followingListFromLocalDatabase.remove(surveyModel.getAskerId());
                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.i("TAG","unfollow successfully");
                            LocalDatabase  database=new LocalDatabase(context.getApplicationContext());
                            database.removeFollowingModel(surveyModel.getAskerId());

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
