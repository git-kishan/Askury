package com.droid.solver.askapp.Survey;

import android.content.Intent;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.R;

public class LanguageSelectionActivity extends AppCompatActivity implements View.OnClickListener, ChipGroup.OnCheckedChangeListener {

    private ChipGroup chipGroup;
    private ImageView nextImageButton,backImageButton;
    private Chip chip;
    private Chip englishChip,chineseChip,hindiChip,spanishChip,arabicChip,malayChip;
    private Chip russianChip,bengaliChip,frenchChip,portgueseChip;
    private CardView rootView;

    public  final int [] languageNumber={0,1,2,3,4,5,6,7,8,9};
    public  final String [] languageString={"English","Chinese","Hindi","Spanish","Arabic",
            "Malay","Russian","Bengali","French","Portuguese"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);
        chipGroup=findViewById(R.id.chip_group);
        nextImageButton=findViewById(R.id.next_image_button);
        backImageButton=findViewById(R.id.back_image_button);
        rootView=findViewById(R.id.root_card_view);
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
                if(chip==null){
                    showSnackBar("Select your survey language");
                    return;
                }
                String data=chip.getText().toString().trim();
                int languageIndex=-1;
                for(int i=0;i<languageString.length;i++){
                    if(languageString[i].trim().equals(data)){
                        languageIndex=i;
                        break;
                    }
                }
                if(languageIndex==-1){
                    showSnackBar("select your survey language");
                    return;
                }
//                Toast.makeText(this, "text selection :- "+languageString[languageIndex], Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LanguageSelectionActivity.this,QuestionTakerActivity.class);
                intent.putExtra("languageIndex", languageIndex);
                startActivity(intent);
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
    private void showSnackBar(String message){
        Snackbar snackbar=Snackbar.make(rootView,  message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
        View view=snackbar.getView();
        TextView tv =view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.progress_bar_overlay_color, null));
        snackbar.show();
    }
}
