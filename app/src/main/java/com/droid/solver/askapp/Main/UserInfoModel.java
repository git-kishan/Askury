package com.droid.solver.askapp.Main;

public class UserInfoModel {
   private  String userId;
   private String userName;
   private String profilePicUrlLow;
   private String profilePicUrlHigh;
   private String bio;
   private int point;
   private String country;
   private String [] language;
   private String [] interest;
   private int followerCount;
   private int followingCount;
   private int firstTimeUser;

   public UserInfoModel (){}
   public UserInfoModel(String userId,String userName,String profilePicUrlLow,String profilePicUrlHigh,
                        String bio,int  point ,String country,String []language,String [] interest,int followerCount,
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
   }  public UserInfoModel(String userId,String userName,String profilePicUrlLow,String profilePicUrlHigh,
                           String bio,int  point ,String country,String []language,String [] interest,int followerCount,
                           int followingCount,int firstTimeUser){
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

    public String[] getInterest() {
        return interest;
    }

    public String[] getLanguage() {
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
}
