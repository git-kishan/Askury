package com.droid.solver.askapp.ImagePoll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.droid.solver.askapp.R;


public class ImageSelectionFragment extends BottomSheetDialogFragment implements View.OnClickListener {


    private LinearLayout galleryLayout,cameraLayout;
    private boolean isImage1Clicked,isImage2Clicked;
    private PassData passData;

    public ImageSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        isImage1Clicked=bundle.getBoolean("image1");
        isImage2Clicked=bundle.getBoolean("image2");
        View view= inflater.inflate(R.layout.fragment_image_selection, container, false);
        galleryLayout=view.findViewById(R.id.galleryLayout);
        cameraLayout=view.findViewById(R.id.cameraLayout);
        passData= (PassData) getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        galleryLayout.setOnClickListener(this);
        cameraLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.galleryLayout:
                passData.passDataFromFragmentToActivity(isImage1Clicked,
                        isImage2Clicked, true, false);
                getDialog().cancel();
                break;
            case R.id.cameraLayout:
                passData.passDataFromFragmentToActivity(isImage1Clicked,
                        isImage2Clicked, false, true);
                getDialog().cancel();
                break;
        }
    }

}
