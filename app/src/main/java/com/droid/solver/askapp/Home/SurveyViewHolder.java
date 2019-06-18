package com.droid.solver.askapp.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.Account.OtherAccountActivity;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.Survey.AskSurveyModel;
import com.google.android.gms.common.api.Batch;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.protobuf.StringValue;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SurveyViewHolder extends RecyclerView.ViewHolder {
    EmojiTextView profileName,bio,question;
    CircleImageView profileImage;
    ImageView threeDot;
    LinearLayout container1,container2,container3,container4;
    EmojiTextView option1TextView,option2TextView,option3TextView,option4TextView;
    EmojiTextView percent1,percent2,percent3,percent4;
    View view1,view2,view3,view4;
    Context context;
    Animation animation;
    private FirebaseUser user;
    private FirebaseFirestore firestoreRootRef;
    public SurveyViewHolder(@NonNull View itemView,Context context) {
        super(itemView);
        this.context=context;
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
        animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
        animation.setDuration(500);
        container1.startAnimation(animation);
        changeBackGroundOfLinearLayout();
        changeColor(container1.getId());
        makeContainerUnClickable();
        updateSelectionInRemoteDatabase(1, surveyModel);
    }
    void onContainer2Clicked(final AskSurveyModel surveyModel){
        animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
        animation.setDuration(500);
        container2.startAnimation(animation);
        changeBackGroundOfLinearLayout();
        changeColor(container2.getId());
        makeContainerUnClickable();
        updateSelectionInRemoteDatabase(2, surveyModel);
    }
    void onContainer3Clicked(final AskSurveyModel surveyModel){
        animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
        animation.setDuration(500);
        container3.startAnimation(animation);
        changeBackGroundOfLinearLayout();
        changeColor(container3.getId());
        makeContainerUnClickable();
        updateSelectionInRemoteDatabase(3, surveyModel);
    }
    void onContainer4Clicked(final AskSurveyModel surveyModel){
        animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
        animation.setDuration(500);
        container4.startAnimation(animation);
        changeBackGroundOfLinearLayout();
        changeColor(container4.getId());
        makeContainerUnClickable();
        updateSelectionInRemoteDatabase(4, surveyModel);
    }

    private void changeColor(int id){
        switch (id){
            case R.id.linearLayout3:
                view1.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_green, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                option1TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_green, null));
                option2TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option3TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option4TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout4:
                view1.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_green, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                option1TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option2TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_green, null));
                option3TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option4TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout5:
                view1.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_green, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                option1TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option2TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                option3TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_green, null));
                option4TextView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_dark_pink, null));
                break;
            case R.id.linearLayout6:
                view1.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view2.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view3.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_pink, null));
                view4.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.survey_light_green, null));
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


        public void makeContainerUnClickable(){
          container1.setClickable(false);
          container2.setClickable(false);
          container3.setClickable(false);
          container4.setClickable(false);

        }
        public void makeContainerClickable(){
            container1.setClickable(true);
            container2.setClickable(true);
            container3.setClickable(true);
            container4.setClickable(true);
        }

        // 1 for first option,2 for second option,3 for third option, 4 for fourth option
      private   void updateSelectionInRemoteDatabase(final int selection, final AskSurveyModel surveyModel){

            DocumentReference rootSurveyRef=firestoreRootRef.collection("survey").document(surveyModel.getSurveyId());
            DocumentReference askerSurveyRef=firestoreRootRef.collection("user").document(surveyModel.getAskerId())
                    .collection("survey").document(surveyModel.getSurveyId());

            DocumentReference userSurveyLikeRef=firestoreRootRef.collection("user").document(user.getUid())
                    .collection("surveyParticipated").document("participated");

            Map<String,Object > surveyMap=new HashMap<>();
            if(selection==1) {
                surveyMap.put("option1Count", FieldValue.increment(1));
                surveyModel.setOption1Count(surveyModel.getOption1Count()+1);
                showSelectionPercentage(surveyModel,selection);
            }
            else if(selection==2) {
                surveyMap.put("option2Count", FieldValue.increment(1));
                surveyModel.setOption2Count(surveyModel.getOption2Count()+1);
                showSelectionPercentage(surveyModel,selection);
            }
            else if(selection==3) {
                surveyMap.put("option3Count", FieldValue.increment(1));
                surveyModel.setOption3Count(surveyModel.getOption3Count()+1);
                showSelectionPercentage(surveyModel,selection);

            }
            else if(selection==4) {
                surveyMap.put("option4Count", FieldValue.increment(1));
                surveyModel.setOption4Count(surveyModel.getOption4Count()+1);
                showSelectionPercentage(surveyModel,selection);


            }

            final Map<String,Object> selectionMap=new HashMap<>();
            selectionMap.put(surveyModel.getSurveyId(), selection);

            final Map<String,Object> selectionLikeMap=new HashMap<>();
            selectionLikeMap.put("surveyMapList",FieldValue.arrayUnion(selectionMap));

            WriteBatch batch=firestoreRootRef.batch();
            batch.update(rootSurveyRef, surveyMap);
            batch.update(askerSurveyRef, surveyMap);
            batch.set(userSurveyLikeRef, selectionLikeMap, SetOptions.merge());

            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    selectionMap.clear();
                    selectionLikeMap.clear();
                    Log.i("TAG", "survey  batch written complete");
                    LocalDatabase localDatabase=new LocalDatabase(context.getApplicationContext());
                    HashMap<String,Integer> participatedMap=new HashMap<>();
                    participatedMap.put(surveyModel.getSurveyId(),selection);
                    localDatabase.insertSurveyParticipatedModel(participatedMap);

                }
            });



        }

        private void showSelectionPercentage(final AskSurveyModel surveyModel,int selection){

        boolean isOption1=surveyModel.isOption1();
        boolean isOption2=surveyModel.isOption2();
        boolean isOption3=surveyModel.isOption3();
        boolean isOption4=surveyModel.isOption4();

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
            percent1.setText(String.valueOf(firstPercentage));
            percent2.setText(String.valueOf(secondPercentage));
            percent3.setText(String.valueOf(thirdPercentage));
            percent4.setText(String.valueOf(fourthPercentage));

        }
        else if(isOption1&&isOption2&&isOption3){
            int option1Count=surveyModel.getOption1Count();
            int option2Count=surveyModel.getOption2Count();
            int option3Count=surveyModel.getOption3Count();
            int total=option1Count+option2Count+option3Count;
            int firstPercentage=(option1Count*100)/total;
            int secondPercentage=(option2Count*100)/total;
            int thirdPercentage=100-(firstPercentage+secondPercentage);
            percent1.setText(String.valueOf(firstPercentage));
            percent2.setText(String.valueOf(secondPercentage));
            percent3.setText(String.valueOf(thirdPercentage));
        }
        else if(isOption2&&isOption3&&isOption4){
            int option2Count=surveyModel.getOption2Count();
            int option3Count=surveyModel.getOption3Count();
            int option4Count=surveyModel.getOption4Count();
            int total=option2Count+option3Count+option4Count;
            int secondPercentage=(option2Count*100)/total;
            int thirdPercentage=(option3Count*100)/total;
            int fourthPercentage=100-(secondPercentage+thirdPercentage);
            percent2.setText(String.valueOf(secondPercentage));
            percent3.setText(String.valueOf(thirdPercentage));
            percent4.setText(String.valueOf(fourthPercentage));
        }
        else if(isOption3&&isOption4&&isOption1){
            int option1Count=surveyModel.getOption1Count();
            int option3Count=surveyModel.getOption3Count();
            int option4Count=surveyModel.getOption4Count();
            int total=option1Count+option3Count+option4Count;
            int firstPercentage=(option1Count*100)/total;
            int thirdPercentage=(option3Count*100)/total;
            int fourthPercentage=100-(firstPercentage+thirdPercentage);
            percent1.setText(String.valueOf(firstPercentage));
            percent3.setText(String.valueOf(thirdPercentage));
            percent4.setText(String.valueOf(fourthPercentage));
        }
        else if(isOption4&&isOption1&&isOption2){
            int option1Count=surveyModel.getOption1Count();
            int option2Count=surveyModel.getOption2Count();
            int option4Count=surveyModel.getOption4Count();
            int total=option1Count+option2Count+option4Count;
            int firstPercentage=(option1Count*100)/total;
            int secondPercentage=(option2Count*100)/total;
            int fourthPercentage=100-(firstPercentage+secondPercentage);
            percent1.setText(String.valueOf(firstPercentage));
            percent2.setText(String.valueOf(secondPercentage));
            percent4.setText(String.valueOf(fourthPercentage));
        }
        else if(isOption1&&isOption2){
            int option1Count=surveyModel.getOption1Count();
            int option2Count=surveyModel.getOption2Count();
            int total=option1Count+option2Count;
            int firstPercentage=(option1Count*100)/total;
            int secondPercentage=100-(firstPercentage);
            percent1.setText((String.valueOf(firstPercentage)));
            percent2.setText((String.valueOf(secondPercentage)));
        }
        else if(isOption1&&isOption3){
            int option1Count=surveyModel.getOption1Count();
            int option3Count=surveyModel.getOption3Count();
            int total=option1Count+option3Count;
            int firstPercentage=(option1Count*100)/total;
            int thirdPercentage=100-(firstPercentage);
            percent1.setText(String.valueOf(firstPercentage));
            percent3.setText(String.valueOf(thirdPercentage));
        }
        else if(isOption1&&isOption4){
            int option1Count=surveyModel.getOption1Count();
            int option4Count=surveyModel.getOption4Count();
            int total=option1Count+option4Count;
            int firstPercentage=(option1Count*100)/total;
            int fourthPercentage=100-(firstPercentage);
            percent1.setText(String.valueOf(firstPercentage));
            percent4.setText(String.valueOf(fourthPercentage));
        }
        else if(isOption2&&isOption3){
            int option2Count=surveyModel.getOption2Count();
            int option3Count=surveyModel.getOption3Count();
            int total=option2Count+option3Count;
            int thirdPercentage=(option3Count*100)/total;
            int secondPercentage=100-(thirdPercentage);
            percent3.setText(String.valueOf(thirdPercentage));
            percent2.setText(String.valueOf(secondPercentage));
        }
        else if(isOption2&&isOption4){
            int option4Count=surveyModel.getOption4Count();
            int option2Count=surveyModel.getOption2Count();
            int total=option4Count+option2Count;
            int fourthPercentage=(option4Count*100)/total;
            int secondPercentage=100-(fourthPercentage);
            percent4.setText(String.valueOf(fourthPercentage));
            percent2.setText(String.valueOf(secondPercentage));
        }
        else if(isOption3&&isOption4){
            int option3Count=surveyModel.getOption3Count();
            int option4Count=surveyModel.getOption4Count();
            int total=option3Count+option4Count;
            int thirdPercentage=(option3Count*100)/total;
            int fourthPercentage=100-(thirdPercentage);
            percent3.setText(String.valueOf(thirdPercentage));
            percent4.setText(String.valueOf(fourthPercentage));
        }

        }

        void showSelection(int selection,final AskSurveyModel surveyModel){
            animation=AnimationUtils.loadAnimation(context, R.anim.survey_rectangle_scalein);
            animation.setDuration(500);
        if(selection==1){
            showSelectionPercentage(surveyModel,selection);
            container1.startAnimation(animation);
            changeColor(container1.getId());
        }
        else if(selection==2){
            showSelectionPercentage(surveyModel,selection);
            container2.startAnimation(animation);
            changeColor(container2.getId());
        }
        else if(selection==3){
            showSelectionPercentage(surveyModel,selection);
            container3.startAnimation(animation);
            changeColor(container3.getId());
        }
        else if(selection==4){
            showSelectionPercentage(surveyModel,selection);
            container4.startAnimation(animation);
            changeColor(container4.getId());
        }else if(selection==0){
            //nothing to do
        }

            makeContainerUnClickable();
         changeBackGroundOfLinearLayout();
        }

        void onProfileImageClicked(final Context context,final AskSurveyModel surveyModel){
            Intent intent=new Intent(context, OtherAccountActivity.class);
            intent.putExtra("profile_image", surveyModel.getAskerName());
            intent.putExtra("uid", surveyModel.getAskerId());
            intent.putExtra("user_name", surveyModel.getAskerName());
            intent.putExtra("bio", surveyModel.getAskerBio());
            context.startActivity(intent);

        }

    void onThreeDotClicked(Context context){
        View dialogView = LayoutInflater.from(context).inflate(R.layout.survey_overflow_dialog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().getAttributes().windowAnimations=R.style.customAnimations_successfull;
        alertDialog.show();
        handleDialogItemClicked(dialogView,alertDialog );

    }
    private void handleDialogItemClicked(final View view,final AlertDialog dialog){
        TextView reportTextView=view.findViewById(R.id.report_text_view);
        TextView followTextView=view.findViewById(R.id.follow_text_view);
        final TextView deleteSurveyView=view.findViewById(R.id.delete_survey_textview);
        reportTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "report clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        followTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "follow clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        deleteSurveyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "delete  clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
