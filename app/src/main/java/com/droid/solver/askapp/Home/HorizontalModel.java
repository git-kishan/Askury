package com.droid.solver.askapp.Home;

import com.droid.solver.askapp.Community.CommunityModel;

import java.util.ArrayList;

public class HorizontalModel {

    ArrayList<Object> list;
    HorizontalModel(){
        list=new ArrayList<>();

    }

    public ArrayList<Object> getList() {
        return list;
    }

    public void addObject(Object object) {
        list.add(object);
    }
}
