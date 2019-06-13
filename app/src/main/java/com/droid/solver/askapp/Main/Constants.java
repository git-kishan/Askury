package com.droid.solver.askapp.Main;

import android.support.v4.content.res.ResourcesCompat;

import com.droid.solver.askapp.R;

public class Constants {
    public static final String PREFERENCE_NAME="user_preferences";
    public static final String PROFILE_PIC="profile_pic";
    public static final String LOW_PROFILE_PIC_PATH="low_profile_pic_path";
    public static final String HIGH_PROFILE_PIC_PATH="HIGH_profile_pic_path";
    public static final String LOW_IMAGE_URL="LOW_IMAGE_URL";

    public static final String userId="userId";
    public static final String userName="userName";
    public static final String profilePicHighUrl="profilePicHighUrl";
    public static final String profilePicLowByte="profilePicLowByte";
    public static final String profilePicLowUrl="profilePicLowUrl";
    public static final String bio="bio";

    public  final int [] languageNumber={1,2,3,4,5,6,7,8,9,10};
    public  final String [] languageString={"English","Chinese","Hindi","Spanish","Arabic",
            "Malay","Russian","Bengali","French","Portuguese"};
    public int languageChoosenNumber;

    public static final String NOTIFICATION="notification";
    public static final String INTEREST="interest";
    public static final String GENDER="gender";
    public static final String MALE="male";
    public static final String FEMALE="female";

    //language constants
    public static final String ENGLISH="English";
    public static final String CHINESE="Chinese";
    public static final String HINDI="Hindi";
    public static final String SPANISH="Spanish";
    public static final String ARABIC="Arabic";
    public static final String MALAY="Malay";
    public static final String RUSSIAN="Russian";
    public static final String BENGALI="Bengali";
    public static final String FRENCH="French";
    public static final String PORTUGUESE="Portuguese";


    public static final String LANGUAGE_SELECTION="language_selection";
    public static final String GENDER_SELECTION="gender_selection";
    public static final String INTEREST_SELECTION="interest_selection";

    public Constants(int number){
        languageChoosenNumber=number;
    }

    public String getLanguageChoosen() {
        return languageString[languageChoosenNumber];
    }
}
