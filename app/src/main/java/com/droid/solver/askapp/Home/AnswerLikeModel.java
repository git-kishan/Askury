package com.droid.solver.askapp.Home;

public class AnswerLikeModel {
    private String likerId;
    private String likerName;
    private String likerImageUrl;
    private String likerBio;

    public AnswerLikeModel(String likerId,String likerName,String likerImageUrl,String likeBio){
        this.likerId=likerId;
        this.likerName=likerName;
        this.likerImageUrl=likerImageUrl;
        this.likerBio=likeBio;
    }

    public String getLikerImageUrl() {
        return likerImageUrl;
    }

    public String getLikerBio() {
        return likerBio;
    }

    public String getLikerId() {
        return likerId;
    }

    public String getLikerName() {
        return likerName;
    }

}
