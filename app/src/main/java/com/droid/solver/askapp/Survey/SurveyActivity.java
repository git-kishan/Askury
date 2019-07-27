package com.droid.solver.askapp.Survey;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.button.MaterialButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.emoji.widget.EmojiEditText;
import androidx.emoji.widget.EmojiTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.Main.MainActivity;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

public class SurveyActivity extends AppCompatActivity implements
        View.OnClickListener, View.OnFocusChangeListener, TextWatcher {

    private CoordinatorLayout coordinatorLayout;
    private TextInputLayout questionInputLayout;
    private EmojiEditText questionInputEditText;
    private AppCompatButton doneButton;
    private CardView appbarCardView;
    private ImageView addImageButton;
    private int numberOfTimesAddButtonClicked=0;
    private EmojiEditText option1EditText,option2EditText,option3EditText,option4EditText;
    private ConstraintLayout submitButtonConstraintLayout;
    private ProgressBar progressBar;
    private EmojiTextView submitTextView;
    private EmojiTextView optionATextView,optionBTextView,optionCTextView,optionDTextView;
    private boolean isOption1Available=false,isOption2Available=false,isOption3Available=false,isOption4Available=false;
    private int languageIndex;
    private FirebaseUser user;
    private FirebaseFirestore root;
    private boolean isuUploaded=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Intent intent=getIntent();
        languageIndex=intent.getIntExtra("languageIndex", 0);
        questionInputLayout=findViewById(R.id.textInputLayout);
        questionInputEditText=findViewById(R.id.input_edit_text);
        doneButton=findViewById(R.id.done_button);
        Toolbar toolbar=findViewById(R.id.toolbar);
        changeToolbarFont(toolbar);
        appbarCardView=findViewById(R.id.toolbar_card_view);
        submitButtonConstraintLayout=findViewById(R.id.child_constraint_layout);
        progressBar=findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        submitTextView=findViewById(R.id.submit_textview);
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
        submitButtonConstraintLayout.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(this);
        appbarCardView.requestFocus();
        hideSoftKeyboard(appbarCardView);
        questionInputEditText.setHint("What's your question");
        FirebaseAuth auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if(user==null){
            showSnackBar("Please sign in again...");
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SurveyActivity.this, SignInActivity.class));
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.done_button:
                hideSoftKeyboard(questionInputEditText);
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
            case R.id.add_image_button:
                numberOfTimesAddButtonClicked++;
                onAddButtonClicked();
                break;
            case R.id.child_constraint_layout:
                if(checkValidation()){
                    if(isNetworkAvailable()){
                        hideSoftKeyboard(questionInputEditText);
                        submitButtonConstraintLayout.setEnabled(false);
                        submitTextView.setText(getString(R.string.uploading));
                        progressBar.setVisibility(View.VISIBLE);
                        addImageButton.setEnabled(false);
                        questionInputLayout.setEnabled(false);
                        addImageButton.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                                R.color.chip_fader_color, null));
                        uploadToRemoteDatabase();

                    }else {
                        showSnackBar("No internet connection");
                    }
                }
                break;
                default:
                    hideSoftKeyboard(questionInputEditText);
                    onBackPressed();
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
            option1EditText.startAnimation(scaleInAnimation);
            optionATextView.startAnimation(scaleInAnimation);
        }
        else if(numberOfTimesAddButtonClicked==2){
            option2EditText.setVisibility(View.VISIBLE);
            optionBTextView.setVisibility(View.VISIBLE);
            option2EditText.startAnimation(scaleInAnimation);
            optionBTextView.startAnimation(scaleInAnimation);
        }else if(numberOfTimesAddButtonClicked==3){
            option3EditText.setVisibility(View.VISIBLE);
            optionCTextView.setVisibility(View.VISIBLE);
            option3EditText.startAnimation(scaleInAnimation);
            optionCTextView.startAnimation(scaleInAnimation);
        }else if(numberOfTimesAddButtonClicked==4){
            option4EditText.setVisibility(View.VISIBLE);
            optionDTextView.setVisibility(View.VISIBLE);
            option4EditText.startAnimation(scaleInAnimation);
            optionDTextView.startAnimation(scaleInAnimation);
        }else if(numberOfTimesAddButtonClicked>=5){
            showSnackBar("Maximum four option");
        }

    }

    private void changeToolbarFont(Toolbar toolbar){
        for(int i=0;i<toolbar.getChildCount();i++){
            View view=toolbar.getChildAt(i);
            if(view instanceof TextView ){
                TextView toolbarTitleView= (TextView) view;
                Typeface typeface= ResourcesCompat.getFont(this, R.font.aclonica);
                toolbarTitleView.setTypeface(typeface);
            }
        }
    }

    private void showSnackBar(String message){
        Snackbar snackbar=Snackbar.make(coordinatorLayout,  message, Snackbar.LENGTH_LONG);
//        snackbar.setActionTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
//        View view=snackbar.getView();
//        TextView tv =view.findViewById(R.id.snackbar_text);
//        tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
//        view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.progress_bar_overlay_color, null));
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        if(isuUploaded) {
            Intent intent = new Intent(SurveyActivity.this,
                    MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }else
        {
            super.onBackPressed();

        }
    }

    public boolean checkValidation(){
        checkOptionsEntered();
        if(questionInputEditText.getText().toString().trim().length()==0){
            showSnackBar("Enter your survey question");
            return false;
        }else if(questionInputEditText.getText().toString().trim().length()>200){
            showSnackBar("Survey question must be less than 200 character");
            return false;
        }
        return validateOptions();
    }

    private void checkOptionsEntered(){
        isOption1Available = option1EditText.getText().toString().trim().length() > 0;
        isOption2Available = option2EditText.getText().toString().trim().length() > 0;
        isOption3Available = option3EditText.getText().toString().trim().length() > 0;
        isOption4Available = option4EditText.getText().toString().trim().length() > 0;


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
       else if(!isOption1Available&&!isOption2Available&!isOption3Available){
           showSnackBar("Must be atleast two option");
           return false;

        }
        return true;
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager cmm= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(cmm!=null){
            NetworkInfo activeNetworkInfo=cmm.getActiveNetworkInfo();
            return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
        }
        return false;

    }

    private void uploadToRemoteDatabase(){

        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);

        String askerId=user.getUid();
        String askerName=preferences.getString(Constants.userName, null);
        String askerImageUrlLow=preferences.getString(Constants.LOW_IMAGE_URL, null);
        String askerBio=preferences.getString(Constants.bio, null);
        final String question=questionInputEditText.getText().toString().trim();
        long timeOfSurvey=System.currentTimeMillis();
        boolean option1=isOption1Available;
        boolean option2=isOption2Available;
        boolean option3=isOption3Available;
        boolean option4=isOption4Available;
        String option1Value=option1EditText.getText().toString();
        String option2Value=option2EditText.getText().toString();
        String option3Value=option3EditText.getText().toString();
        String option4Value=option4EditText.getText().toString();
        int option1Count=0;
        int option2Count=0;
        int option3Count=0;
        int option4Count=0;
        int languageSelectedIndex=languageIndex;

        String surveyId=root.collection("user").document(askerId).collection("survey").document().getId();

        AskSurveyModel surveyModel=new AskSurveyModel(
        askerId,askerName ,askerImageUrlLow ,askerBio , question,
                timeOfSurvey, option1, option2, option3,
                option4, option1Value, option2Value, option3Value,
                option4Value, option1Count, option2Count, option3Count, option4Count,
                languageSelectedIndex,surveyId,timeOfSurvey);
        WriteBatch writeBatch=root.batch();
        DocumentReference rootSurveyRef=root.collection("survey").document(surveyId);
        writeBatch.set(rootSurveyRef, surveyModel);
        DocumentReference userSurveyRef=root.collection("user").document(askerId).
                collection("survey").document(surveyId);
        writeBatch.set(userSurveyRef, surveyModel);

        writeBatch.commit().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                submitButtonConstraintLayout.setEnabled(true);
                submitTextView.setText(getString(R.string.submit));
                progressBar.setVisibility(View.GONE);
                addImageButton.setEnabled(true);
                questionInputLayout.setEnabled(true);
                addImageButton.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.chip_fader_color, null));
                questionInputEditText.setText("");
                option1EditText.setText("");
                option2EditText.setText("");
                option3EditText.setText("");
                option4EditText.setText("");
                showCustomSuccessfullDialog();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                submitButtonConstraintLayout.setEnabled(true);
                submitTextView.setText(getString(R.string.submit));
                progressBar.setVisibility(View.GONE);
                addImageButton.setEnabled(true);
                questionInputLayout.setEnabled(true);
                addImageButton.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.chip_fader_color, null));
                showCustomErrorDialog();



            }
        });
    }

    private void showCustomSuccessfullDialog() {
        final ViewGroup viewGroup = findViewById(R.id.root);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.uploading_success_dialog, viewGroup, false);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        TextView textView=dialogView.findViewById(R.id.message_text_view);
        textView.setText(getString(R.string.survey_accepted_successfull));
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        if(alertDialog.getWindow()!=null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.customAnimations_successfull;
        }
        alertDialog.show();

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isuUploaded=true;
                        onBackPressed();
                    }
                }, 300);
            }
        });

    }

    private void showCustomErrorDialog(){

        final ViewGroup viewGroup = findViewById(R.id.root);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.uploading_failure_dialog, viewGroup, false);
        TextView textView=dialogView.findViewById(R.id.message_text_view);
        textView.setText(getString(R.string.survey_accepted_failed));
        MaterialButton retryButton=dialogView.findViewById(R.id.retry_button);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        if(alertDialog.getWindow()!=null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.customAnimations_error;
        }
        alertDialog.show();

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if(checkValidation()){
                    if(isNetworkAvailable()){

                        submitButtonConstraintLayout.setEnabled(false);
                        submitTextView.setText(getString(R.string.uploading));
                        progressBar.setVisibility(View.VISIBLE);
                        addImageButton.setEnabled(false);
                        questionInputLayout.setEnabled(false);
                        addImageButton.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                                R.color.chip_fader_color, null));
                        uploadToRemoteDatabase();

                    }else {
                        showSnackBar("No internet connection");
                    }
                }
            }
        });
    }
}
