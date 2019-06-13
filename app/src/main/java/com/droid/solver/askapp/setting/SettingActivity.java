package com.droid.solver.askapp.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static int INTEREST_REQUEST_CODE=965;
    public static String SETTING_ACTIVITY="SettingActivity";
    private CircleImageView profileImage;
    private ImageView backImage;
    private TextView profileName,about,setting,addInterest;
    private TextView interestTextView;
    private TextInputLayout userNameInputLayout,aboutInputLayout;
    private TextInputEditText userNameInputEditText,aboutInputEditText;
    private MaterialButton editButton,updateButton;
    private ChipGroup chipGroup;
    private LinearLayout languageLayout,notificationLayout,privacyPolicyLayout,reportErrorLayout;
    private SwitchCompat notificationSwitch;
    private ImageView languageImageButton,privacyPolicyImageButton,reportErrorImageButton;
    private TextView languageText;
    private ArrayList<String> savedInterestList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        profileImage=findViewById(R.id.circleImageView4);
        backImage=findViewById(R.id.back_image);
        userNameInputLayout=findViewById(R.id.user_name_input_layout);
        userNameInputEditText=findViewById(R.id.user_name_input_edit_text);
        aboutInputLayout=findViewById(R.id.about_input_layout);
        aboutInputEditText=findViewById(R.id.about_input_edit_text);
        editButton=findViewById(R.id.edit_button);
        updateButton=findViewById(R.id.update_button);
        updateButton.setEnabled(false);
        chipGroup=findViewById(R.id.chipGroup);
        profileName=findViewById(R.id.profile_name);
        about=findViewById(R.id.about);
        setting=findViewById(R.id.textView37);
        addInterest=findViewById(R.id.textView31);
        interestTextView=findViewById(R.id.textView38);
        languageLayout=findViewById(R.id.linearLayout7);
        notificationLayout=findViewById(R.id.linearLayout8);
        privacyPolicyLayout=findViewById(R.id.linearLayout9);
        reportErrorLayout=findViewById(R.id.linearLayout10);
        notificationSwitch=findViewById(R.id.notification_switch);
        languageImageButton=findViewById(R.id.language_image);
        privacyPolicyImageButton=findViewById(R.id.privacy_policy_image);
        reportErrorImageButton=findViewById(R.id.report_error_image);
        languageText=findViewById(R.id.language_text);
        userNameInputEditText.setText("Sahil sekhawat");
        aboutInputEditText.setText("Author | Writer | Dancer | Actor");
        updateButton.setVisibility(View.GONE);
        userNameInputLayout.setEnabled(false);
        aboutInputLayout.setEnabled(false);
        chipGroup.setEnabled(false);
        addInterest.setVisibility(View.GONE);

        languageLayout.setOnClickListener(this);languageImageButton.setOnClickListener(this);
        notificationLayout.setOnClickListener(this);notificationSwitch.setOnCheckedChangeListener(this);
        privacyPolicyLayout.setOnClickListener(this);privacyPolicyImageButton.setOnClickListener(this);
        reportErrorLayout.setOnClickListener(this);reportErrorImageButton.setOnClickListener(this);

        editButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        backImage.setOnClickListener(this);
        addInterest.setOnClickListener(this);
        setting.requestFocus();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        hideSoftKeyboard(setting);
        savedInterestList=new ArrayList<>();
        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        String interestString=preferences.getString(Constants.INTEREST, null);
        if(interestString!=null) {
            String[] interestArr = interestString.split("@");
            savedInterestList = (ArrayList<String>) Arrays.asList(interestArr);
        }
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
            case R.id.linearLayout7:
                 Toast.makeText(this, "language ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearLayout8:
                notificationSwitch.performClick();
                break;
            case R.id.linearLayout9:
                Toast.makeText(this, "privacy policy", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearLayout10:
                Toast.makeText(this, "report Error", Toast.LENGTH_SHORT).show();

                break;


        }
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

            String url= ProfileImageActivity.PROFILE_PICTURE+"/"+ FirebaseAuth.getInstance().getCurrentUser().
                    getUid()+ProfileImageActivity.SMALL_THUMBNAIL;
            StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);
            GlideApp.with(this).load(reference)
                    .placeholder(R.drawable.round_account)
                    .error(R.drawable.round_account)
                    .into(profileImage);
            e.printStackTrace();
        }catch (NullPointerException e){
            //handle sign in again
        }
    }

    private void generateChipDynamically(ArrayList<String> list){
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
        editButton.setAlpha(0.0f);
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

    private void onUpdateButtonClicked(){
        editButton.setAlpha(1.0f);
        userNameInputLayout.setEnabled(false);
        aboutInputLayout.setEnabled(false);
        editButton.setEnabled(true);
        interestTextView.setAlpha(0.2f);//0.2
        updateButton.setEnabled(false);
        updateButton.setVisibility(View.GONE);
        addInterest.setVisibility(View.GONE);
        chipGroup.removeAllViews();
        makeChipTextFader();
    }

    @Override
    public void onBackPressed() {
        if(updateButton.isEnabled()){
            updateButton.performClick();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        if(b){
           Toast.makeText(this, "switch on", Toast.LENGTH_SHORT).show();
            editor.putBoolean(Constants.NOTIFICATION,true );

        }else {
            Toast.makeText(this, "switch off", Toast.LENGTH_SHORT).show();

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
                    ArrayList<String> interestList = data.getStringArrayListExtra("interestList");
                    generateChipDynamically(interestList);
                    makeChipTextBlack();
                    Log.i("TAG", "interest list :- "+interestList.size());
                }
            }else if(resultCode==RESULT_CANCELED){
                Toast.makeText(this, "Not selected ", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
