package com.droid.solver.askapp.Question;

public class Following{
    private String followingId;
    private String followingName;
    private String followingImageUrl;
    private  String followingBio;
    private String selfId;

    Following(){}

    public Following(String followingId,String followingName,String followingImageUrl,String followingBio){
        this.followingId=followingId;
        this.followingName=followingName;
        this.followingImageUrl=followingImageUrl;
        this.followingBio=followingBio;
    }

    public Following(String followingId,String followingName,String followingImageUrl,String followingBio,String selfId){
        this.followingId=followingId;
        this.followingName=followingName;
        this.followingImageUrl=followingImageUrl;
        this.followingBio=followingBio;
        this.selfId=selfId;
    }

    public String getFollowingId() {
        return followingId;
    }

    public String getFollowingBio() {
        return followingBio;
    }

    public String getFollowingImageUrl() {
        return followingImageUrl;
    }

    public String getFollowingName() {
        return followingName;
    }

    public String getSelfId() {
        return selfId;
    }
}
