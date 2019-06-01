package com.droid.solver.askapp.Main;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.R;
import com.squareup.okhttp.ConnectionPool;


public class NoInternetFragment extends Fragment implements View.OnClickListener {


    private MaterialButton retryButton;
    private SwipeRefreshLayout swipeRefershLayout;
    private Handler handler;
    private Runnable runnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_no_internet, container, false);
        retryButton=view.findViewById(R.id.retry_button);
        retryButton.setOnClickListener( this);
        swipeRefershLayout=view.findViewById(R.id.swipe_refresh);
        handler=new Handler();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.retry_button){
            swipeRefershLayout.setRefreshing(true);
            if(isNetworkAvailable()){
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

            }else {
                 runnable=new Runnable() {
                    @Override
                    public void run() {

                        swipeRefershLayout.setEnabled(false);
                        swipeRefershLayout.setRefreshing(false);
                    }
                };
                handler.postDelayed(runnable, 1500);
            }
        }
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager cmm= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo =cmm.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
    }
}
