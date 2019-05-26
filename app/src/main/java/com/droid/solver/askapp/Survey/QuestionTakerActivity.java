package com.droid.solver.askapp.Survey;

import android.app.Activity;
import android.graphics.Paint;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.droid.solver.askapp.R;

public class QuestionTakerActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher {

    private TextInputLayout questionInputLayout;
    private TextInputEditText questionInputEditText;
    private AppCompatButton doneButton;
    private CardView appbarCardView;
    private ImageView backImageButton,addImageButton;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_taker);
        questionInputLayout=findViewById(R.id.textInputLayout);
        questionInputEditText=findViewById(R.id.input_edit_text);
        doneButton=findViewById(R.id.done_button);
        appbarCardView=findViewById(R.id.cardView);
        backImageButton=findViewById(R.id.back_image_button);
        addImageButton=findViewById(R.id.add_image_button);
        recyclerView=findViewById(R.id.recycler_view);
        doneButton.setOnClickListener(this);
        appbarCardView.setOnClickListener(this);
        questionInputEditText.setOnFocusChangeListener(this);
        questionInputEditText.addTextChangedListener(this);
        addImageButton.setOnClickListener(this);
        backImageButton.setOnClickListener(this);
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
            case R.id.cardView:
                questionInputEditText.clearFocus();
                questionInputLayout.clearFocus();
                appbarCardView.requestFocus();
                hideSoftKeyboard(appbarCardView);
                break;
            case R.id.back_image_button:
                onBackPressed();
                break;
            case R.id.add_image_button:
                Toast.makeText(this, "add clicked", Toast.LENGTH_SHORT).show();
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
}
