package com.droid.solver.askapp.Survey;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.ImagePoll.SuccessfullyUploadDialogFragment;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.prefs.Preferences;

public class QuestionTakerActivity extends AppCompatActivity implements
        View.OnClickListener, View.OnFocusChangeListener, TextWatcher {

    private CoordinatorLayout coordinatorLayout;
    private TextInputLayout questionInputLayout;
    private TextInputEditText questionInputEditText;
    private AppCompatButton doneButton;
    private CardView appbarCardView;
    private ImageView backImageButton,addImageButton;
    private int numberOfTimesAddButtonClicked=0;
    private EditText option1EditText,option2EditText,option3EditText,option4EditText;
    private ConstraintLayout submitButtonConstraintLayout;
    private ProgressBar progressBar;
    TextView submitTextView;
    private TextView optionATextView,optionBTextView,optionCTextView,optionDTextView;
    private boolean isOption1Available=false,isOption2Available=false,isOption3Available=false,isOption4Available=false;
    private int languageIndex;
    public  final String [] languageString={"English","Chinese","Hindi","Spanish","Arabic",
            "Malay","Russian","Bengali","French","Portuguese"};
    private String [] optionToBeUploaded;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_taker);
        Intent intent=getIntent();
        languageIndex=intent.getIntExtra("languageIndex", 0);
        questionInputLayout=findViewById(R.id.textInputLayout);
        questionInputEditText=findViewById(R.id.input_edit_text);
        doneButton=findViewById(R.id.done_button);
        appbarCardView=findViewById(R.id.toolbar_card_view);
        submitButtonConstraintLayout=findViewById(R.id.child_constraint_layout);
        progressBar=findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        submitTextView=findViewById(R.id.submit_textview);
        backImageButton=findViewById(R.id.back_image_button);
        addImageButton=findViewById(R.id.add_image_button);
        coordinatorLayout=findViewById(R.id.root);
        optionATextView=findViewById(R.id.textView20);
        optionBTextView=findViewById(R.id.textView21);
        optionCTextView=findViewById(R.id.textView22);
        optionDTextView=findViewById(R.id.textView23);
        optionATextView.setVisibility(View.GONE);
        optionBTextView.setVisibility(View.GONE);
        optionCTextView.setVisibility(View.GONE);
        optionDTextView.setVisibility(View.GONE);
        option1EditText =findViewById(R.id.option1);
        option2EditText=findViewById(R.id.option2);
        option3EditText=findViewById(R.id.option3);
        option4EditText=findViewById(R.id.option4);
        option1EditText.setVisibility(View.GONE);
        option2EditText.setVisibility(View.GONE);
        option3EditText.setVisibility(View.GONE);
        option4EditText.setVisibility(View.GONE);
        doneButton.setOnClickListener(this);
        appbarCardView.setOnClickListener(this);
        questionInputEditText.setOnFocusChangeListener(this);
        questionInputEditText.addTextChangedListener(this);
        addImageButton.setOnClickListener(this);
        backImageButton.setOnClickListener(this);
        submitButtonConstraintLayout.setOnClickListener(this);
        appbarCardView.requestFocus();
        hideSoftKeyboard(appbarCardView);
        questionInputEditText.setHint("What's your question");
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if(user==null){
            showSnackBar("Please sign in again...");
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(QuestionTakerActivity.this, SignInActivity.class));
                    finish();
                }
            }, 1000);
        }
        root=FirebaseFirestore.getInstance();


    }
    private void hideSoftKeyboard(View view){

        InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm!=null)
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.done_button:
                if(questionInputEditText.hasFocus())
                  questionInputEditText.clearFocus();
                if(questionInputLayout.hasFocus())
                  questionInputLayout.clearFocus();
                doneButton.requestFocus();
                hideSoftKeyboard(doneButton);
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doneButton.setTextColor(ResourcesCompat.getColor(getResources(),android.R.color.black, null));
                        doneButton.setBackgroundTintList(ResourcesCompat.getColorStateList(getResources(),
                                R.color.chip_fader_color, null));
                    }
                }, 300);
            break;
            case R.id.toolbar_card_view:
                questionInputEditText.clearFocus();
                questionInputLayout.clearFocus();
                appbarCardView.requestFocus();
                hideSoftKeyboard(appbarCardView);
                break;
            case R.id.back_image_button:
                onBackPressed();
                break;
            case R.id.add_image_button:
                numberOfTimesAddButtonClicked++;
                onAddButtonClicked();
                break;
            case R.id.child_constraint_layout:
                if(checkValidation()){
                    if(isNetworkAvailable()){

                        submitButtonConstraintLayout.setEnabled(false);
                        submitTextView.setText("Uploading ...");
                        progressBar.setVisibility(View.VISIBLE);
                        addImageButton.setEnabled(false);
                        questionInputLayout.setEnabled(false);
                        addImageButton.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                                R.color.chip_fader_color, null));
                        backImageButton.setEnabled(false);
                        uploadToRemoteDatabase();

                    }else {
                        showSnackBar("No internet connection");
                    }
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus&&questionInputEditText.getText().toString().length()==0) {
            questionInputEditText.setHint("What's your question");
            questionInputEditText.setGravity(Gravity.START|Gravity.TOP);
            InputMethodManager  inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if(inputMethodManager!=null){
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int length=charSequence.toString().length();
        if(length==0){
            doneButton.setBackgroundTintList(ResourcesCompat.
                    getColorStateList(getResources(), R.color.progress_bar_overlay_color, null));
            doneButton.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black
                    ,null));
        }else if(length>0){
            doneButton.setBackgroundTintList(ResourcesCompat
                    .getColorStateList(getResources(), R.color.colorAccent, null));
            doneButton.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.white,
                    null));

        }
    }

    @Override
    public void afterTextChanged(Editable editable) {


    }
    private void onAddButtonClicked(){
        Animation scaleInAnimation=AnimationUtils.loadAnimation(this, R.anim.status_scale_in);
        if(numberOfTimesAddButtonClicked==1){
            option1EditText.setVisibility(View.VISIBLE);
            optionATextView.setVisibility(View.VISIBLE);
            optionATextView.startAnimation(scaleInAnimation);
        }
        else if(numberOfTimesAddButtonClicked==2){
            option2EditText.setVisibility(View.VISIBLE);
            optionBTextView.setVisibility(View.VISIBLE);
            optionBTextView.startAnimation(scaleInAnimation);
        }else if(numberOfTimesAddButtonClicked==3){
            option3EditText.setVisibility(View.VISIBLE);
            optionCTextView.setVisibility(View.VISIBLE);
            optionCTextView.startAnimation(scaleInAnimation);
        }else if(numberOfTimesAddButtonClicked==4){
            option4EditText.setVisibility(View.VISIBLE);
            optionDTextView.setVisibility(View.VISIBLE);
            optionDTextView.startAnimation(scaleInAnimation);
        }else if(numberOfTimesAddButtonClicked>=5){
            showSnackBar("Maximum four option");
        }

    }
    private void showSnackBar(String message){
        Snackbar snackbar=Snackbar.make(coordinatorLayout,  message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
        View view=snackbar.getView();
        TextView tv =view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.progress_bar_overlay_color, null));
        snackbar.show();
    }

    public boolean checkValidation(){
        checkOptionsEntered();
        if(questionInputEditText.getText().toString().trim().length()==0){
            showSnackBar("Enter your survey question");
            return false;
        }
        if(!validateOptions()){
            return false;
        }
        setOptionToBeUploaded();
        return true;
    }
    private void checkOptionsEntered(){
        if(option1EditText.getText().toString().trim().length()>0){
            isOption1Available = true;
        }else {
            isOption1Available=false;
        }
        if(option2EditText.getText().toString().trim().length()>0){
            isOption2Available=true;
        }else {
            isOption2Available=false;
        }
        if(option3EditText.getText().toString().trim().length()>0){
            isOption3Available=true;
        }else{
            isOption3Available=false;
        }
        if(option4EditText.getText().toString().trim().length()>0){
            isOption4Available=true;
        }else {
            isOption4Available=false;
        }


    }// tell about about is entered or not in edit text
    private boolean validateOptions() {

        if(!isOption1Available&&!isOption2Available&!isOption3Available&&!isOption4Available){
            showSnackBar("Must be atleast two option");
            return false;
        }
       else if(isOption1Available&&!isOption2Available&!isOption3Available&&!isOption4Available){
           showSnackBar("Must be atleast two option");
           return false;
       }
       else  if(!isOption1Available&&isOption2Available&!isOption3Available&&!isOption4Available){
           showSnackBar("Must be atleast two option");
           return false;

        }
       else  if(!isOption1Available&&!isOption2Available&isOption3Available&&!isOption4Available){
           showSnackBar("Must be atleast two option");
           return false;
        }
       else if(!isOption1Available&&!isOption2Available&!isOption3Available&&isOption4Available){
           showSnackBar("Must be atleast two option");
           return false;

        }
        return true;
    }
    private void setOptionToBeUploaded(){
        if(isOption1Available&&isOption2Available&&isOption3Available&&isOption4Available){
            optionToBeUploaded=new String[]{option1EditText.getText().toString(),option2EditText.getText().toString(),
                    option3EditText.getText().toString(),option4EditText.getText().toString()
            };

        }else if(isOption1Available&&isOption2Available&&isOption3Available){

            optionToBeUploaded=new String[]{option1EditText.getText().toString(),option2EditText.getText().toString(),
                    option3EditText.getText().toString()};

        }else if(isOption2Available&&isOption3Available&&isOption4Available){
            optionToBeUploaded=new String[]{option2EditText.getText().toString(),option3EditText.getText().toString(),
                    option4EditText.getText().toString()};

        }else if(isOption3Available&&isOption4Available&&isOption1Available){

            optionToBeUploaded=new String[]{option3EditText.getText().toString(),option4EditText.getText().toString(),
                    option1EditText.getText().toString()};
        }else if(isOption4Available&&isOption1Available&&isOption2Available){

            optionToBeUploaded=new String[]{option4EditText.getText().toString(),option1EditText.getText().toString(),
                    option2EditText.getText().toString()};
        }else if(isOption1Available&&isOption2Available){

            optionToBeUploaded=new String[]{option1EditText.getText().toString(),option2EditText.getText().toString()};
        }else if(isOption1Available&&isOption3Available){

            optionToBeUploaded=new String[]{option1EditText.getText().toString(),option3EditText.getText().toString()};
        }else if(isOption1Available&&isOption4Available){
            optionToBeUploaded=new String[]{option1EditText.getText().toString(),option4EditText.getText().toString()};

        }else if(isOption2Available&&isOption3Available){
            optionToBeUploaded=new String[]{option2EditText.getText().toString(),option3EditText.getText().toString()};

        }else if(isOption2Available&&isOption4Available){
            optionToBeUploaded=new String[]{option2EditText.getText().toString(),option4EditText.getText().toString()};

        }else if(isOption3Available&&isOption4Available){
            optionToBeUploaded=new String[]{option3EditText.getText().toString(),option4EditText.getText().toString()};

        }
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager cmm= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=cmm.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
    }
    private void uploadToRemoteDatabase(){

        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);

        String askerUid=user.getUid();
        String askerName=preferences.getString(Constants.userName, null);
        String askerImageUrl=preferences.getString(Constants.LOW_IMAGE_URL, null);
        final String question=questionInputEditText.getText().toString();
        long timeOfSurvey=System.currentTimeMillis();
        int maximumTimeOfSurvey=15;
        boolean option1=isOption1Available;boolean option2=isOption2Available;
        final boolean option3=isOption3Available;boolean option4=isOption4Available;
        int option1Count=0;int option2Count=0;
        int option3Count=0;int option4Count=0;
        int languageSelectedIndex=languageIndex;

        String surveyId=root.collection("user").document(askerUid).collection("survey").document().getId();

        AskSurveyModel surveyModel=new AskSurveyModel(askerUid, askerName, askerImageUrl,
                question, timeOfSurvey, maximumTimeOfSurvey, option1,
                option2, option3, option4,optionToBeUploaded[0],optionToBeUploaded[1],optionToBeUploaded[2]
                ,optionToBeUploaded[3], option1Count, option2Count,
                option3Count, option4Count, languageSelectedIndex,surveyId);

        root.collection("user").document(askerUid).collection("survey").document(surveyId).set(surveyModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        submitButtonConstraintLayout.setEnabled(true);
                        submitTextView.setText("Submit");
                        progressBar.setVisibility(View.GONE);
                        addImageButton.setEnabled(true);
                        questionInputLayout.setEnabled(true);
                        addImageButton.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                                R.color.chip_fader_color, null));
                        backImageButton.setEnabled(true);
                        questionInputEditText.setText("");

                        option1EditText.setText("");
                        option2EditText.setText("");
                        option3EditText.setText("");
                        option4EditText.setText("");

                        SuccessfullyUploadDialogFragment imageSuccessfullyUploadDialogFragment=new SuccessfullyUploadDialogFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("message", "Survey uploaded successfully");
                        imageSuccessfullyUploadDialogFragment.setArguments(bundle);
                        imageSuccessfullyUploadDialogFragment.show(getSupportFragmentManager(), "question_dialog");


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                submitButtonConstraintLayout.setEnabled(true);
                submitTextView.setText("Submit");
                progressBar.setVisibility(View.GONE);
                addImageButton.setEnabled(true);
                questionInputLayout.setEnabled(true);
                addImageButton.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.chip_fader_color, null));
                backImageButton.setEnabled(true);

                SuccessfullyUploadDialogFragment imageSuccessfullyUploadDialogFragment=new SuccessfullyUploadDialogFragment();
                Bundle bundle=new Bundle();
                bundle.putString("message", "Survey uploading failed,try again");
                imageSuccessfullyUploadDialogFragment.setArguments(bundle);
                imageSuccessfullyUploadDialogFragment.show(getSupportFragmentManager(), "question_dialog");

            }
        });
        root.collection("survey").document(surveyId).set(surveyModel);


    }

}
