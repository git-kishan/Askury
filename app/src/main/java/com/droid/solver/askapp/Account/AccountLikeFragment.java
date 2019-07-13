package com.droid.solver.askapp.Account;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountLikeFragment extends Fragment {


    public AccountLikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_like, container, false);
    }

}
