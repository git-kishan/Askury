package com.droid.solver.askapp.Home;


import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.R;

public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

}
