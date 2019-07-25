package com.droid.solver.askapp.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.droid.solver.askapp.Account.ProfileImageActivity;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.InterestActivity;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static int INTEREST_REQUEST_CODE = 965;
    public static String SETTING_ACTIVITY = "SettingActivity";
    private ConstraintLayout rootLayout;
    private CircleImageView profileImage;
    private TextView addInterest;
    private TextView interestTextView;
    private TextInputLayout userNameInputLayout, aboutInputLayout;
    private TextInputEditText userNameInputEditText, aboutInputEditText;
    private MaterialButton editButton, updateButton;
    private ChipGroup chipGroup;
    private SwitchCompat notificationSwitch;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        profileImage=findViewById(R.id.circleImageView4);
        ImageView backImage=findViewById(R.id.back_image);
        rootLayout=findViewById(R.id.root_layout);
        userNameInputLayout=findViewById(R.id.user_name_input_layout);
        userNameInputEditText=findViewById(R.id.user_name_input_edit_text);
        aboutInputLayout=findViewById(R.id.about_input_layout);
        aboutInputEditText=findViewById(R.id.about_input_edit_text);
        editButton=findViewById(R.id.edit_button);
        updateButton=findViewById(R.id.update_button);
        updateButton.setEnabled(false);
        chipGroup=findViewById(R.id.chipGroup);
        TextView profileName=findViewById(R.id.profile_name);
        TextView about=findViewById(R.id.about);
        TextView setting=findViewById(R.id.textView37);
        addInterest=findViewById(R.id.textView31);
        interestTextView=findViewById(R.id.textView38);
        LinearLayout notificationLayout=findViewById(R.id.linearLayout8);
        LinearLayout privacyPolicyLayout=findViewById(R.id.linearLayout9);
        LinearLayout reportErrorLayout=findViewById(R.id.linearLayout10);
        notificationSwitch=findViewById(R.id.notification_switch);
        ImageView privacyPolicyImageButton=findViewById(R.id.privacy_policy_image);
        ImageView reportErrorImageButton=findViewById(R.id.report_error_image);
        updateButton.setVisibility(View.GONE);
        userNameInputLayout.setEnabled(false);
        aboutInputLayout.setEnabled(false);
        chipGroup.setEnabled(false);
        addInterest.setVisibility(View.GONE);

        notificationLayout.setOnClickListener(this);notificationSwitch.setOnCheckedChangeListener(this);
        privacyPolicyLayout.setOnClickListener(this);privacyPolicyImageButton.setOnClickListener(this);
        reportErrorLayout.setOnClickListener(this);reportErrorImageButton.setOnClickListener(this);

        editButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        backImage.setOnClickListener(this);
        addInterest.setOnClickListener(this);
        setting.requestFocus();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideSoftKeyboard(setting);
        List<String>savedInterestList=new ArrayList<>();
        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        String interestString=preferences.getString(Constants.INTEREST, null);
        String savedUserName=preferences.getString(Constants.userName, null);
        String savedAbout=preferences.getString(Constants.bio, null);
        boolean switchState=preferences.getBoolean(Constants.NOTIFICATION, false);
        savedUserName=savedUserName==null?"Someone":savedUserName;
        savedAbout=savedAbout==null?"Nothing about yourself is mentioned":savedAbout;
        profileName.setText(savedUserName);
        about.setText(savedAbout);
        notificationSwitch.setChecked(switchState);
        if(interestString!=null) {
            String[] interestArr = interestString.split("@");
            savedInterestList = Arrays.asList(interestArr);
        }
        userNameInputEditText.setText(savedUserName);
        aboutInputEditText.setText(savedAbout);
        generateChipDynamically(savedInterestList);
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.circleImageView4:
                startActivity(new Intent(this,ProfileImageActivity.class));
                break;
            case R.id.back_image:
                onBackPressed();
                break;
            case R.id.edit_button:
               onEditButtonClicked();
                break;
            case R.id.update_button:
             onUpdateButtonClicked();
                break;
            case R.id.textView31://add interest button
                Intent intent=new Intent(this, InterestActivity.class);
                intent.putExtra("activity", SETTING_ACTIVITY);
                startActivityForResult(intent,INTEREST_REQUEST_CODE);
                break;
            case R.id.linearLayout8:
                notificationSwitch.performClick();
                break;
            case R.id.linearLayout9:
                startActivity(new Intent(SettingActivity.this,PrivacyPolicyActivity.class));
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
            case R.id.linearLayout10:
                startActivity(new Intent(SettingActivity.this,ReportActivity.class));
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;


        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);

    }

    private void hideSoftKeyboard(View view){
        InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if(imm!=null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    @Override
    protected void onResume() {
        loadProfilePicFromFile();
        super.onResume();
    }

    private void loadProfilePicFromFile(){

        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        String path=preferences.getString(Constants.LOW_PROFILE_PIC_PATH, null);
        File file=new File(path,"profile_pic_high_resolution");
        try {
            Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(file));
            profileImage.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {

            if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                String url = Constants.PROFILE_PICTURE + "/" + FirebaseAuth.getInstance().getCurrentUser().
                        getUid() + Constants.THUMBNAIL;
                StorageReference reference = FirebaseStorage.getInstance().getReference().child(url);
                GlideApp.with(this).load(reference)
                        .placeholder(R.drawable.round_account)
                        .error(R.drawable.round_account)
                        .into(profileImage);
            }
            e.printStackTrace();


        }catch (NullPointerException e){
            //handle sign in again
        }
    }

    private void generateChipDynamically(List<String> list){
        if(list.size()>0){
        chipGroup.removeAllViews();
        for(int i=0;i<list.size();i++){
            Chip chip=new Chip(this);
            chip.setText(list.get(i));
            chip.setCheckable(false);
            chip.setCloseIconVisible(false);
            chip.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
            chip.setChipBackgroundColorResource(R.color.light_chip_background_color);
            chipGroup.addView(chip);
            }
        }
    }

    private void makeChipTextFader(){

        for(int i=0;i<chipGroup.getChildCount();i++){
            final Chip chip=(Chip)chipGroup.getChildAt(i);
            if(chip!=null) {
                chip.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
            }
        }
    }

    private void makeChipTextBlack(){

        for(int i=0;i<chipGroup.getChildCount();i++){
            final Chip chip=(Chip)chipGroup.getChildAt(i);
            if(chip!=null) {
                chip.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black_color_with_alpha, null));
            }
        }
        for(int i=0;i<chipGroup.getChildCount();i++){
            final Chip chip=(Chip)chipGroup.getChildAt(i);
            if(chip!=null) {
                chip.setCloseIconVisible(true);
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chipGroup.removeView(chip);
                    }
                });
            }
        }
    }

    private void onEditButtonClicked(){
    Handler handler=new Handler();
    handler.post(new Runnable() {
        @Override
        public void run() {
            editButton.setAlpha(1f);
            userNameInputLayout.setAlpha(0f);
            aboutInputLayout.setAlpha(0f);
            chipGroup.setAlpha(0f);
            interestTextView.setAlpha(0f);
            updateButton.setAlpha(0f);
            addInterest.setAlpha(0f);

            editButton.animate().alpha(0f).setDuration(300).start();
            userNameInputLayout.animate().alpha(1f).setDuration(300).start();
            aboutInputLayout.animate().alpha(1f).setDuration(300).start();
            updateButton.animate().alpha(1f).setDuration(300).start();
            chipGroup.animate().alpha(1f).setDuration(300).start();
            interestTextView.animate().alpha(1f).setDuration(300).start();
            addInterest.animate().alpha(1f).setDuration(300).start();

            userNameInputLayout.setEnabled(true);
            aboutInputLayout.setEnabled(true);
            editButton.setEnabled(false);
            updateButton.setEnabled(true);
            chipGroup.setEnabled(true);
            interestTextView.setAlpha(1);//0.2
            updateButton.setVisibility(View.VISIBLE);
            addInterest.setVisibility(View.VISIBLE);
            makeChipTextBlack();
        }
    });


    }

    private void onUpdateButtonClicked(){

    if(validateProfileUpdation()){
        editButton.setAlpha(0.0f);
        editButton.animate().alpha(1f).setDuration(300).start();
        userNameInputLayout.setEnabled(false);
        aboutInputLayout.setEnabled(false);
        editButton.setEnabled(true);
        interestTextView.setAlpha(0.2f);//0.2
        updateButton.setEnabled(false);
        updateButton.setVisibility(View.GONE);
        addInterest.setVisibility(View.GONE);
        makeChipTextFader();
        updateProfile();
    }

    }

    private void updateProfile(){

    final List<String> tempList=new ArrayList<>();
        for(int i=0;i<chipGroup.getChildCount();i++){
            final Chip chip=(Chip)chipGroup.getChildAt(i);
            if(chip!=null) {
                tempList.add(chip.getText().toString());
            }
        }

            if(isNetworkAvailable()){

                final Map<String,Object> userMap=new HashMap<>();
                if(userNameInputEditText.getText()!=null)
                userMap.put("userName",userNameInputEditText.getText().toString());
                if(aboutInputEditText.getText()!=null)
                userMap.put("bio", aboutInputEditText.getText().toString());
                userMap.put("interest", tempList);
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseFirestore.getInstance().collection("user")
                            .document(uid)
                            .set(userMap, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Snackbar.make(rootLayout, "Profile updated ", Snackbar.LENGTH_LONG).show();
                            StringBuilder builder=new StringBuilder();
                            for(int i=0;i<tempList.size();i++){
                                builder.append(tempList.get(i));
                                builder.append("@");
                            }
                            SharedPreferences.Editor  editor=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE).edit();
                            editor.putString(Constants.userName, userNameInputEditText.getText().toString());
                            editor.putString(Constants.bio, aboutInputEditText.getText().toString());
                            editor.putString(Constants.INTEREST,builder.toString() );
                            editor.apply();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(rootLayout, "Profile updated,try again later", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Snackbar.make(rootLayout, "Please sign in again ", Snackbar.LENGTH_SHORT).show();
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SettingActivity.this, SignInActivity.class));
                            finish();
                        }
                    }, 300);
                }

            }else {
                Snackbar.make(rootLayout, "No internet connection available", Snackbar.LENGTH_SHORT).show();
            }



    }

    private boolean  validateProfileUpdation(){

         if(userNameInputEditText.getText()!=null&&userNameInputEditText.getText().toString().length()==0){
             Snackbar.make(rootLayout, "User name can't be empty", Snackbar.LENGTH_LONG).show();
             return false;
         }

         else if(chipGroup.getChildCount()<=1) {
             Snackbar.make(rootLayout, "At least two interest must selected", Snackbar.LENGTH_LONG).show();
            return false;
         }
         return true;
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        if(b){
            editor.putBoolean(Constants.NOTIFICATION,true );
        }else {
            editor.putBoolean(Constants.NOTIFICATION, false);
        }
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==INTEREST_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                if(data!=null) {
                    List<String> newInterestList = data.getStringArrayListExtra("interestList");
                    generateChipDynamically(newInterestList);
                    makeChipTextBlack();
                    Log.i("TAG", "interest list :- "+newInterestList.size());
                }
            }else if(resultCode==RESULT_CANCELED){
                Toast.makeText(this, "Not selected ", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(manager!=null) {
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }return false;
    }
}
