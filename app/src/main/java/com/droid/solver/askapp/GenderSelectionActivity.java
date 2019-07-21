package com.droid.solver.askapp;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.Main.Constants;

public class GenderSelectionActivity extends AppCompatActivity implements View .OnClickListener{

    private CardView maleCardView,femaleCardView;
    private TextView maleText,femaleText;
    private int selection=0;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_selection);
        maleCardView=findViewById(R.id.male_card_view);
        femaleCardView=findViewById(R.id.female_card_view);
        maleText=findViewById(R.id.male_text_view);
        femaleText=findViewById(R.id.female_text_view);
        ImageView nextButton=findViewById(R.id.next_button);
        TextView hiUser=findViewById(R.id.textView29);
        preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        String userName=preferences.getString(Constants.userName, null);
        String heading;
        if(userName!=null){
            heading=String.format(getString(R.string.hi_user), userName);
            hiUser.setText(heading);
        }else {
            heading=String.format(getString(R.string.hi_user),"");
            hiUser.setText(heading);
        }
        nextButton.setOnClickListener(this);
        maleCardView.setOnClickListener(this);
        femaleCardView.setOnClickListener(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);

    }

    @Override
    public void onClick(View view){
         final int MALE=1;
         final int FEMALE=2;

        switch (view.getId()){
            case R.id.male_card_view:
                maleCardView.setAlpha(1);
                maleText.setAlpha(1);
                femaleCardView.setAlpha(0.8f);
                femaleText.setAlpha(0.8f);
                selection = MALE;
                maleCardView.animate().scaleX(0.8f).scaleY(0.8f).setDuration(300).start();
                femaleCardView.animate().scaleY(1f).scaleX(1f).setDuration(400).start();
                break;
            case R.id.female_card_view:
                maleCardView.setAlpha(0.8f);
                maleText.setAlpha(0.8f);
                femaleCardView.setAlpha(1);
                femaleText.setAlpha(1);
                selection = FEMALE;
                maleCardView.animate().scaleX(1f).scaleY(1f).setDuration(400).start();
                femaleCardView.animate().scaleY(0.8f).scaleX(0.8f).setDuration(400).start();
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
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                }else if(selection==FEMALE){
                    editor.putBoolean(Constants.GENDER_SELECTION, true);
                    editor.putString(Constants.GENDER, Constants.FEMALE);
                    editor.apply();
                    startActivity(new Intent(GenderSelectionActivity.this,InterestActivity.class));
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                }
        }
    }
}
