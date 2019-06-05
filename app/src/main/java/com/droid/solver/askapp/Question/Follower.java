package com.droid.solver.askapp.Question;

public class Follower{
    private String followerId;
    private  String followerName;
    private String followerImageUrl;
    private String followerBio;
    public Follower(String followerId,String followerName,String followerImageUrl,String followerBio){
        this.followerId=followerId;
        this.followerName=followerName;
        this.followerImageUrl=followerImageUrl;
        this.followerBio=followerBio;
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

}
