package com.droid.solver.askapp.Question;

import androidx.annotation.Keep;

@Keep
public class Follower {
    public String followerId;
    public  String followerName;
    public String followerImageUrl;
    public String followerBio;
    public String selfId;

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
