package com.droid.solver.askapp.ImagePoll;


import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.droid.solver.askapp.R;


public class SuccessfullyUploadDialogFragment extends BottomSheetDialogFragment {

    public SuccessfullyUploadDialogFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        String message=null;
        if(bundle!=null){
            message=bundle.getString("message");
        }
        View view= inflater.inflate(R.layout.fragment_successfully_upload_dialog, container, false);
        TextView textView=view.findViewById(R.id.textView);
        if(message!=null)
        textView.setText(message);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
