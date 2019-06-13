package com.droid.solver.askapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.Main.Constants;
import com.facebook.share.model.SharePhoto;

public class GenderSelectionActivity extends AppCompatActivity implements View .OnClickListener{

    private CardView maleCardView,femaleCardView;
    private TextView maleText,femaleText;
    private final int MALE=1;
    private final int FEMALE=2;
    private int selection=0;
    private SharedPreferences preferences;
    private ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_selection);
        maleCardView=findViewById(R.id.male_card_view);
        femaleCardView=findViewById(R.id.female_card_view);
        maleText=findViewById(R.id.male_text_view);
        femaleText=findViewById(R.id.female_text_view);
        nextButton=findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);
        maleCardView.setOnClickListener(this);
        femaleCardView.setOnClickListener(this);
        preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.male_card_view:
                maleCardView.setAlpha(1);
                maleText.setAlpha(1);
                femaleCardView.setAlpha(0.8f);
                femaleText.setAlpha(0.8f);
                selection = MALE;
                break;
            case R.id.female_card_view:
                maleCardView.setAlpha(0.8f);
                maleText.setAlpha(0.8f);
                femaleCardView.setAlpha(1);
                femaleText.setAlpha(1);
                selection = FEMALE;
                break;
            case R.id.next_button:
                SharedPreferences.Editor editor=preferences.edit();
                if(selection==0){
                    Toast.makeText(this, "Select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selection==MALE){
                    editor.putBoolean(Constants.GENDER_SELECTION, true);
                    editor.putString(Constants.GENDER, Constants.MALE);
                    editor.apply();
                    startActivity(new Intent(GenderSelectionActivity.this,InterestActivity.class));

                }else if(selection==FEMALE){
                    editor.putBoolean(Constants.GENDER_SELECTION, true);
                    editor.putString(Constants.GENDER, Constants.FEMALE);
                    editor.apply();
                    startActivity(new Intent(GenderSelectionActivity.this,InterestActivity.class));

                }


        }
    }
}
