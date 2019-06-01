package com.droid.solver.askapp.Question;

import com.droid.solver.askapp.ImagePoll.ImagePollActivity;

public class QuestionLike {
    private String  [] questionLikeId;

    public QuestionLike(String [] questionLikeId){
        this.questionLikeId=questionLikeId;
    }

    public String[] getQuestionLikeId() {
        return questionLikeId;
    }
}
class SurveyParticipated{
    private String [] surveyParticipatedId;

    public SurveyParticipated(String  [] surveyParticipatedId){
        this.surveyParticipatedId=surveyParticipatedId;
    }

    public String[] getSurveyParticipatedId() {
        return surveyParticipatedId;
    }
}
class ImagePollLike{
    private String  [] imagePollLikeId;

   public  ImagePollLike(String [] imagePollLikeId){
        this.imagePollLikeId=imagePollLikeId;
    }

    public String[] getImagePollLikeId() {
        return imagePollLikeId;
    }
}
class Follower{
    private String followerId;
    private  String followerName;
    private String followerImageUrl;
    private String followerAbout;
    public Follower(String followerId,String followerName,String followerImageUrl,String followerAbout){
        this.followerId=followerId;
        this.followerName=followerName;
        this.followerImageUrl=followerImageUrl;
        this.followerAbout=followerAbout;
    }

    public String getFollowerId() {
        return followerId;
    }

    public String getFollowerAbout() {
        return followerAbout;
    }

    public String getFollowerImageUrl() {
        return followerImageUrl;
    }

    public String getFollowerName() {
        return followerName;
    }

}
class Following{
    private String followingId;
    private String followingName;
    private String followingImageUrl;
    private  String followingAbout;

    public Following(String followingId,String followingName,String followingImageUrl,String followingAbout){
        this.followingId=followingId;
        this.followingName=followingName;
        this.followingImageUrl=followingImageUrl;
        this.followingAbout=followingAbout;
    }

    public String getFollowingId() {
        return followingId;
    }

    public String getFollowingAbout() {
        return followingAbout;
    }

    public String getFollowingImageUrl() {
        return followingImageUrl;
    }

    public String getFollowingName() {
        return followingName;
    }

}
class Community{
    private String communityId;
    private String communityImageUrl;
    private String communityName;
    private int communityMemberCount;
    Community(String communityId,String communityName,String communityImageUrl,int communityMemberCount){
        this.communityId=communityId;
        this.communityImageUrl=communityImageUrl;
        this.communityName=communityName;
        this.communityMemberCount=communityMemberCount;
    }

    public String getCommunityId() {
        return communityId;
    }

    public int getCommunityMemberCount() {
        return communityMemberCount;
    }

    public String getCommunityImageUrl() {
        return communityImageUrl;
    }

    public String getCommunityName() {
        return communityName;
    }

}
