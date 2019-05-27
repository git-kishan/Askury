package com.droid.solver.askapp.ImagePoll;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.R;


public class SuccessfullyUploadDialogFragment extends BottomSheetDialogFragment {

    private TextView textView;
    public SuccessfullyUploadDialogFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        String message=bundle.getString("message");
        View view= inflater.inflate(R.layout.fragment_successfully_upload_dialog, container, false);
        textView=view.findViewById(R.id.textView);
        if(message!=null)
        textView.setText(message);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
