package com.droid.solver.askapp.Home;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.droid.solver.askapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagePollOpenFragment extends Fragment {


    private View toolbar,bottomNavigation;
    private ImageView backImage;
    private String imageUrl;
    private ImageView imageView;

    public ImagePollOpenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_image_poll_open, container, false);
        Bundle bundle=getArguments();
        imageUrl=bundle.getString("imageUrl");
        String transitionName=bundle.getString("imageTransitionName");
        imageView=view.findViewById(R.id.image_view);

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            if(transitionName!=null)
            imageView.setTransitionName(transitionName);

        }
        toolbar=getActivity().findViewById(R.id.toolbar_card_view);
        bottomNavigation=getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigation.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        Picasso.get().load(imageUrl).noFade().into(imageView,new Callback(){
            @Override
            public void onSuccess() {
                getActivity().supportStartPostponedEnterTransition();
            }

            @Override
            public void onError(Exception e) {
                getActivity().supportStartPostponedEnterTransition();
            }
        });
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        backImage=view.findViewById(R.id.back_image);
        PhotoViewAttacher attacher=new PhotoViewAttacher(imageView);
        attacher.update();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }


    @Override
    public void onDestroy() {
        bottomNavigation.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        super.onDestroy();
    }
}
