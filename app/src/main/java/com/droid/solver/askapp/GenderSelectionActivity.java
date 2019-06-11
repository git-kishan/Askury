package com.droid.solver.askapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class GenderSelectionActivity extends AppCompatActivity implements View .OnClickListener{

    private CardView maleCardView,femaleCardView;
    private TextView maleText,femaleText;
    private final int MALE=1;
    private final int FEMALE=2;
    private int selection=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_selection);
        maleCardView=findViewById(R.id.male_card_view);
        femaleCardView=findViewById(R.id.female_card_view);
        maleText=findViewById(R.id.male_text_view);
        femaleText=findViewById(R.id.female_text_view);
        maleCardView.setOnClickListener(this);
        femaleCardView.setOnClickListener(this);
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

        }
    }
}
