package com.droid.solver.askapp.Account;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.droid.solver.askapp.R;

import java.util.ArrayList;

public class AccountFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragmentArrayList;
    private int[] imageResId = {
            R.drawable.ic_questions_black,
            R.drawable.ic_questions_black,
            R.drawable.ic_questions_black };
    public AccountFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentArrayList=new ArrayList<>();

    }

     void addFragment(Fragment fragment){
        fragmentArrayList.add(fragment);
    }
    @Override
    public Fragment getItem(int i) {
        return fragmentArrayList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }



}

