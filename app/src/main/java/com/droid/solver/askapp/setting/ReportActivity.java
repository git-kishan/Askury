package com.droid.solver.askapp.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.droid.solver.askapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText textInputEditText;
    private MaterialButton button;
    private CardView cardView;
    ConstraintLayout rootLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        rootLayout=findViewById(R.id.root);
        textInputEditText=findViewById(R.id.textInputEditText);
        Toolbar toolbar = findViewById(R.id.toolbar);
        cardView=findViewById(R.id.card);
        button=findViewById(R.id.button);
        button.setEnabled(false);
        changeToolbarFont(toolbar);
        hideSoftKeyboard();
        toolbar.setNavigationOnClickListener(this);
        textInputEditText.addTextChangedListener(textWatcher);
        button.setOnClickListener(this);



    }
    private void hideSoftKeyboard(){
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(inputMethodManager!=null){
            inputMethodManager.hideSoftInputFromWindow(textInputEditText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);
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

    public void onClick(View view){
        if(view.getId()==R.id.button){
            if(textInputEditText.getText()==null||textInputEditText.getText().toString().length()==0){
                Snackbar.make(rootLayout, "Please write your problem.", Snackbar.LENGTH_LONG).show();
                return;
            }
            cardView.setVisibility(View.VISIBLE);
            uploadReportToRemoteDatabase();
        }else {
            onBackPressed();
        }
    }

    TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence!=null&&charSequence.toString().length()>0){
                button.setEnabled(true);
            }else {
                button.setEnabled(false);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void uploadReportToRemoteDatabase(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            String uid=user.getUid();
            if(textInputEditText.getText()!=null) {
                String report = textInputEditText.getText().toString();
                reference.child("user").child("report").child(uid).setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            cardView.setVisibility(View.GONE);
                            textInputEditText.setText("");
                            Snackbar.make(rootLayout, "Problem reported successfully ,thank you.", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        cardView.setVisibility(View.GONE);
                        Snackbar.make(rootLayout, "Error not reported,try again later.", Snackbar.LENGTH_LONG).show();
                    }
                });

            }

        }
    }
}


