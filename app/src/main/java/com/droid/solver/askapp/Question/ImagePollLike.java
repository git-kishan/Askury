package com.droid.solver.askapp.Question;

import java.util.ArrayList;
import java.util.HashMap;

public class ImagePollLike{

    private HashMap<String,Integer> imagePollMap;
    private ArrayList<HashMap<String,Integer>> imagePollMapList;
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
