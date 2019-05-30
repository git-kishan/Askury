package com.droid.solver.askapp.Main;

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

    public  final int [] languageNumber={1,2,3,4,5,6,7,8,9,10};
    public  final String [] languageString={"English","Chinese","Hindi","Spanish","Arabic",
            "Malay","Russian","Bengali","French","Portuguese"};
    private int languageChoosenNumber;
    public Constants(int number){
        languageChoosenNumber=number;
    }

    public String getLanguageChoosen() {
        return languageString[languageChoosenNumber];
    }
}
