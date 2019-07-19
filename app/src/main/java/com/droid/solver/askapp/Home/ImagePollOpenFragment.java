package com.droid.solver.askapp.Home;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.droid.solver.askapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagePollOpenFragment extends Fragment {

    private View toolbar,bottomNavigation;
    private ImageView backImage;
    private String imageUrl;

    public ImagePollOpenFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_image_poll_open, container, false);
        if(getActivity()!=null)
        getActivity().supportStartPostponedEnterTransition();
        Bundle bundle=getArguments();
        String transitionName=null;
        if(bundle!=null) {
            imageUrl = bundle.getString("imageUrl");
            transitionName = bundle.getString("imageTransitionName");
        }
        final CardView cardView=view.findViewById(R.id.progress_card);
        ImageView imageView=view.findViewById(R.id.image_view);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            if(transitionName!=null)
            imageView.setTransitionName(transitionName);

        }
        toolbar=getActivity().findViewById(R.id.toolbar_card_view);
        bottomNavigation=getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigation.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        getActivity().supportStartPostponedEnterTransition();
        Picasso.get().load(imageUrl)
                .noFade()
                .fit()
                .into(imageView,new Callback(){
                    @Override
                    public void onSuccess() {
                        if(getActivity()!=null)
                        getActivity().supportStartPostponedEnterTransition();
                        cardView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        if(getActivity()!=null)
                        getActivity().supportStartPostponedEnterTransition();
                        ConstraintLayout root=view.findViewById(R.id.root);
                        cardView.setVisibility(View.GONE);
                        Snackbar.make(root, "Error occured in loading image ", Snackbar.LENGTH_LONG).show();
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
                if(getActivity()!=null){
                    getActivity().onBackPressed();

                }
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
