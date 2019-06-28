package com.droid.solver.askapp.Community;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.R;

public class CommunityFragment extends Fragment {



    public CommunityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_community, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(getActivity()!=null) {
                Window window = getActivity().getWindow();
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.setStatusBarColor(Color.WHITE);
            }
        }
        return view;
    }


}
