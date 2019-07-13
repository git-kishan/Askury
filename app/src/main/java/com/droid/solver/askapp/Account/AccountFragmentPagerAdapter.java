package com.droid.solver.askapp.Account;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;

public class AccountFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList;

     AccountFragmentPagerAdapter(FragmentManager fm) {
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

