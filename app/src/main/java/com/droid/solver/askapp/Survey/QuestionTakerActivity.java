package com.droid.solver.askapp.Survey;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.droid.solver.askapp.R;

public class QuestionTakerActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher {

    private TextInputLayout questionInputLayout;
    private TextInputEditText questionInputEditText;
    private AppCompatButton doneButton;
    private CardView appbarCardView;
    private ImageView backImageButton,addImageButton;
    private int numberOfTimesAddButtonClicked=0;
    private EditText options1EditText,option2EditText,option3EditText,option4EditText;
    private ConstraintLayout submitButtonConstraintLayout;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_taker);
        questionInputLayout=findViewById(R.id.textInputLayout);
        questionInputEditText=findViewById(R.id.input_edit_text);
        doneButton=findViewById(R.id.done_button);
        appbarCardView=findViewById(R.id.toolbar_card_view);
        submitButtonConstraintLayout=findViewById(R.id.child_constraint_layout);
        progressBar=findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        backImageButton=findViewById(R.id.back_image_button);
        addImageButton=findViewById(R.id.add_image_button);
        options1EditText =findViewById(R.id.option1);
        option2EditText=findViewById(R.id.option2);
        option3EditText=findViewById(R.id.option3);
        option4EditText=findViewById(R.id.option4);
        options1EditText.setVisibility(View.GONE);
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
                questionInputEditText.clearFocus();
                questionInputLayout.clearFocus();
                appbarCardView.requestFocus();
                hideSoftKeyboard(appbarCardView);
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
                submitButtonConstraintLayout.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus&&questionInputEditText.getText().toString().length()==0) {
            questionInputEditText.setHint("What's your question");
            questionInputEditText.setGravity(Gravity.LEFT|Gravity.TOP);
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
        if(numberOfTimesAddButtonClicked==1){
            options1EditText.setVisibility(View.VISIBLE);
        }
        else if(numberOfTimesAddButtonClicked==2){
            option2EditText.setVisibility(View.VISIBLE);
        }else if(numberOfTimesAddButtonClicked==3){
            option3EditText.setVisibility(View.VISIBLE);
        }else if(numberOfTimesAddButtonClicked==4){
            option4EditText.setVisibility(View.VISIBLE);
        }else if(numberOfTimesAddButtonClicked>=5){
            Toast.makeText(this, "option limit exceed", Toast.LENGTH_SHORT).show();
        }

    }
}
