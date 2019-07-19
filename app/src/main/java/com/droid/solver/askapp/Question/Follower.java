package com.droid.solver.askapp.Question;

public class Follower{
    private String followerId;
    private  String followerName;
    private String followerImageUrl;
    private String followerBio;
    private String selfId;

    public Follower(){}
    public Follower(String followerId,String followerName,String followerImageUrl,String followerBio){
        this.followerId=followerId;
        this.followerName=followerName;
        this.followerImageUrl=followerImageUrl;
        this.followerBio=followerBio;
    }

    public Follower(String followerId,String followerName,String followerImageUrl,String followerBio,String selfId){
        this.followerId=followerId;
        this.followerName=followerName;
        this.followerImageUrl=followerImageUrl;
        this.followerBio=followerBio;
        this.selfId=selfId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public String getFollowerBio() {
        return followerBio;
    }

    public String getFollowerImageUrl() {
        return followerImageUrl;
    }

    public String getFollowerName() {
        return followerName;
    }

    public String getSelfId() {
        return selfId;
    }
}
