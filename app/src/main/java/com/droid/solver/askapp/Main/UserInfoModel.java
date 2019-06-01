package com.droid.solver.askapp.Main;

public class UserInfoModel {
   private  String userId;
   private String userName;
   private String profilePicUrlLow;
   private String profilePicUrlHigh;
   private String about;
   private int point;
   private String language;
   private String interest;
   private int followerCount;
   private int followingCount;

   public UserInfoModel(String userId,String userName,String profilePicUrlLow,String profilePicUrlHigh,
                        String about,int  point ,String language,String interest,int followerCount,
                        int followingCount){
       this.userId=userId;
       this.userName=userName;
       this.profilePicUrlLow=profilePicUrlLow;
       this.profilePicUrlHigh=profilePicUrlHigh;
       this.about=about;
       this.point=point;
       this.language=language;
       this.interest=interest;
       this.followerCount=followerCount;
       this.followingCount=followingCount;
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

    public String getAbout() {
        return about;
    }

    public String getInterest() {
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

}
