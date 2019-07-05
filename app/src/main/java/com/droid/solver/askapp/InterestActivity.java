package com.droid.solver.askapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.Main.MainActivity;
import com.droid.solver.askapp.setting.SettingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

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
    private  AlertDialog alertDialog;


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
                final ArrayList<String> interestsList=new ArrayList<>();
                    Set<String> keySet=chipMap.keySet();
                    interestsList.addAll(keySet);

                if(activity!=null&&activity.equals(SettingActivity.SETTING_ACTIVITY)){
                    Intent intent=new Intent();
                    intent.putStringArrayListExtra("interestList", interestsList);
                    setResult(RESULT_OK,intent);
                    finish();

                }else {
                    showProgressDialog();
                    final SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
                    String gender=preferences.getString(Constants.GENDER, null);
                    if(isNetworkAvailable()){
                        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Map<String,Object> userMap=new HashMap<>();
                        userMap.put("gender", gender);
                        userMap.put("interest",interestsList);
                        FirebaseFirestore.getInstance().collection("user").document(uid)
                                .set(userMap, SetOptions.merge())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        SharedPreferences.Editor editor=preferences.edit();
                                        StringBuilder builder=new StringBuilder();
                                        StringBuilder bioBuilder=new StringBuilder();
                                        for(int i=0;i<interestsList.size();i++){
                                            builder.append(interestsList.get(i));
                                            builder.append("@");
                                            bioBuilder.append(interestsList.get(i));
                                            if(i!=interestsList.size())
                                            bioBuilder.append(" |");
                                        }
                                        editor.putString(Constants.INTEREST,builder.toString());
                                        editor.putBoolean(Constants.INTEREST_SELECTION, true);
                                        editor.putString(Constants.bio, bioBuilder.toString());
                                        editor.apply();
                                        alertDialog.dismiss();
                                        startActivity(new Intent(InterestActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                alertDialog.dismiss();
                                Snackbar.make(rootLayout, "Error occured, try again !", Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        Snackbar.make(rootLayout, "No internet connection", Snackbar.LENGTH_LONG).show();
                    }

                }
            }
        }
    }

    private void showProgressDialog(){

        final ViewGroup viewGroup=findViewById(R.id.root);
        View dialogView= LayoutInflater.from(this).inflate(R.layout.interest_activity_horizontal_progress_dialog, viewGroup,false);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false);
        alertDialog=builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
    }
}
