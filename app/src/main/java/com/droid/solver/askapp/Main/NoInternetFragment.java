package com.droid.solver.askapp.Main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v4.net.ConnectivityManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.R;
import com.squareup.okhttp.ConnectionPool;


public class NoInternetFragment extends Fragment implements View.OnClickListener {


    MaterialButton retryButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_no_internet, container, false);
        retryButton=view.findViewById(R.id.retry_button);
        retryButton.setOnClickListener( this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.retry_button){
            if(isNetworkAvailable()){
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

            }
        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager cmm= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo =cmm.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
    }
}
