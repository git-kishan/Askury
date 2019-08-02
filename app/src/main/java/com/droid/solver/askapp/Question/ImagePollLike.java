package com.droid.solver.askapp.Question;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.HashMap;

@Keep
public class ImagePollLike{

    public HashMap<String,Integer> imagePollMap;
    public ArrayList<HashMap<String,Integer>> imagePollMapList;
    //key will be imagePollId
    // value will be optionSelected , 1 for first one and 2 for second one

    public ImagePollLike(){}

    public  ImagePollLike(HashMap<String,Integer> imagePollMap){
       this.imagePollMap=imagePollMap;
    }

    public ImagePollLike(ArrayList<HashMap<String,Integer>> imagePollMapList){
        this.imagePollMapList=imagePollMapList;
    }

    public ArrayList<HashMap<String, Integer>> getImagePollMapList() {
        return imagePollMapList;
    }

    public HashMap<String, Integer> getImagePollMap() {
        return imagePollMap;
    }

}
