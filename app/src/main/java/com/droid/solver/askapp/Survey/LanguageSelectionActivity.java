package com.droid.solver.askapp.Survey;

import android.content.Intent;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.droid.solver.askapp.R;

public class LanguageSelectionActivity extends AppCompatActivity implements View.OnClickListener, ChipGroup.OnCheckedChangeListener {

    private ChipGroup chipGroup;
    private ImageView nextImageButton,backImageButton;
    private Chip chip;
    private Chip englishChip,chineseChip,hindiChip,spanishChip,arabicChip,malayChip;
    private Chip russianChip,bengaliChip,frenchChip,portgueseChip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);
        chipGroup=findViewById(R.id.chip_group);
        nextImageButton=findViewById(R.id.next_image_button);
        backImageButton=findViewById(R.id.back_image_button);
        chipGroup.setOnCheckedChangeListener(this);
        nextImageButton.setOnClickListener(this);
        backImageButton.setOnClickListener(this);
        englishChip=findViewById(R.id.english_chip);
        chineseChip=findViewById(R.id.chinese_chip);
        hindiChip=findViewById(R.id.hindi_chip);
        spanishChip=findViewById(R.id.spannish_chip);
        arabicChip=findViewById(R.id.arabic_chip);
        malayChip=findViewById(R.id.malay_chip);
        russianChip=findViewById(R.id.russian_chip);
        bengaliChip=findViewById(R.id.bengali_chip);
        frenchChip=findViewById(R.id.french_chip);
        portgueseChip=findViewById(R.id.portuguese_chip);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.next_image_button:
                startActivity(new Intent(this, QuestionTakerActivity.class));
                break;
            case R.id.back_image_button:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onCheckedChanged(ChipGroup chipGroup, int checkedId) {
        chip=findViewById(checkedId);
        changeAllChipTextColorToBlack();
        if(chip!=null){
            nextImageButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_right_arrow_darker,null));
            chip.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.white, null));
        }else {
            changeAllChipTextColorToBlack();
            nextImageButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_right_arrow_fader,null));

        }
    }
    private void changeAllChipTextColorToBlack(){
        englishChip.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        chineseChip.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        hindiChip.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        spanishChip.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        arabicChip.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        malayChip.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        russianChip.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        bengaliChip.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        frenchChip.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        portgueseChip.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
    }
}
