package com.droid.solver.askapp;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.droid.solver.askapp.setting.SettingActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class InterestActivity extends AppCompatActivity implements ChipGroup.OnCheckedChangeListener, View.OnClickListener {

    private ChipGroup chipGroupBottom;
    private ChipGroup chipGroupTop;
    private Chip chip;
    private ConstraintLayout rootLayout;
    private Map<String,Integer> chipMap;//chip text and chip id
    private ImageView nextImageButton;
    private String activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        Intent intent=getIntent();
        activity=intent.getStringExtra("activity");
        chipGroupBottom=findViewById(R.id.chipGroupBottom);
        chipGroupTop = findViewById(R.id.chipGroupTop);
        chipMap=new HashMap<>();
        rootLayout=findViewById(R.id.root);
        nextImageButton=findViewById(R.id.imageView20);
        nextImageButton.setOnClickListener(this);
        chipGroupTop.setOnCheckedChangeListener(this);
        chipGroupBottom.setOnCheckedChangeListener(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public void onCheckedChanged(final ChipGroup chipGroup, int checkedId) {
        switch (chipGroup.getId()){
            case R.id.chipGroupTop:

                break;
            case R.id.chipGroupBottom:
                chip=chipGroup.findViewById(checkedId);
                if(chip!=null) {

                    final Animation fadeIn=AnimationUtils.loadAnimation(this, R.anim.chip_scale_in);
                    final Animation fadeOut=AnimationUtils.loadAnimation(this, R.anim.chip_scale_out);
                    chipMap.put(chip.getText().toString(), checkedId);
                    chip.startAnimation(fadeOut);
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chip.setVisibility(View.GONE);
                        }
                    }, 200);
                    Log.i("TAG", "checked Id :- " + chip.getText());
                    Chip newChip = new Chip(this);
                    newChip.setTextColor(chip.getTextColors());
                    newChip.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    newChip.setText(chip.getText());
                    ChipGroup.LayoutParams params = new ChipGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    newChip.setLayoutParams(params);
                    newChip.setCloseIconVisible(true);
                    chipGroupTop.addView(newChip);
                    newChip.startAnimation(fadeIn);

                    newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           final  Chip chip=(Chip)view;
                            if(chip!=null) {
                                chip.startAnimation(fadeOut);
                                Handler handler1=new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chipGroupTop.removeView(chip);

                                    }
                                }, 300);
                                String chipText=chip.getText().toString();
                               int chipId= chipMap.get(chipText);
                               chipMap.remove(chipText);
                                Chip bottomChip=chipGroup.findViewById(chipId);
                                bottomChip.startAnimation(fadeIn);
                                bottomChip.setVisibility(View.VISIBLE);
                                bottomChip.setChecked(false);

                            }

                        }
                    });

                }
                break;
        }

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.imageView20){
            if(chipMap.isEmpty()||chipMap.size()<2){
                Snackbar.make(rootLayout, "At least two interest", Snackbar.LENGTH_SHORT).show();
                return;
            }else if (chipMap.size()>5) {
                Snackbar.make(rootLayout,"Atmost five interest", Snackbar.LENGTH_SHORT).show();
            }else {
                ArrayList<String> interestsList=new ArrayList<>();
                    Set<String> keySet=chipMap.keySet();
                    interestsList.addAll(keySet);
                if(activity.equals(SettingActivity.SETTING_ACTIVITY)){
                    Intent intent=new Intent();
                    intent.putStringArrayListExtra("interestList", interestsList);
                    setResult(RESULT_OK,intent);
                    finish();

                }
            }
        }
    }
}
