package com.droid.solver.askapp.Main;

import androidx.annotation.Keep;
import java.util.ArrayList;

@Keep
public class UserInfoModel {
    public   String userId;
    public String userName;
    public String profilePicUrlLow;
    public String profilePicUrlHigh;
    public String bio;
    public int point;
    public String country;
    public String language;
    public ArrayList<String> interest;
    public int followerCount;
    public int followingCount;
    public int firstTimeUser;
    public String gender;
    //following list means list of object followed by user
    //follower list means list of object who is following to user

   public UserInfoModel (){}
   public UserInfoModel(String userId,String userName,String profilePicUrlLow,String profilePicUrlHigh,
                        String bio,int  point ,String country,String language,ArrayList<String> interest,int followerCount,
                        int followingCount){
       this.userId=userId;
       this.userName=userName;
       this.profilePicUrlLow=profilePicUrlLow;
       this.profilePicUrlHigh=profilePicUrlHigh;
       this.bio =bio;
       this.point=point;
       this.country=country;
       this.language=language;
       this.interest=interest;
       this.followerCount=followerCount;
       this.followingCount=followingCount;
   }
   public UserInfoModel(String userId,String userName,int point,int firstTimeUser){
       this.userId = userId;
       this.userName=userName;
       this.point=point;
       this.firstTimeUser=firstTimeUser;
   }

   public UserInfoModel(String userId,String userName,String profilePicUrlLow,String profilePicUrlHigh,
                           String bio,int  point ,String country,String language,ArrayList<String> interest,int followerCount,
                           int followingCount,int firstTimeUser,String gender){
        this.userId=userId;
        this.userName=userName;
        this.profilePicUrlLow=profilePicUrlLow;
        this.profilePicUrlHigh=profilePicUrlHigh;
        this.bio =bio;
        this.point=point;
        this.country=country;
        this.language=language;
        this.interest=interest;
        this.followerCount=followerCount;
        this.followingCount=followingCount;
        this.firstTimeUser=firstTimeUser;
        this.gender=gender;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public int getPoint() {
        return point;
    }

    public String getBio() {
        return bio;
    }

    public String getCountry() {
        return country;
    }

    public ArrayList<String> getInterest() {
        return interest;
    }

    public String getLanguage() {
        return language;
    }

    public String getProfilePicUrlHigh() {
        return profilePicUrlHigh;
    }

    public String getProfilePicUrlLow() {
        return profilePicUrlLow;
    }

    public int getFirstTimeUser() {
        return firstTimeUser;
    }

    public String getGender() {
        return gender;
    }
}
