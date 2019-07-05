package com.droid.solver.askapp.Home;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.droid.solver.askapp.R;

public class LoadingViewHolderVertically extends RecyclerView.ViewHolder {
    ProgressBar progressBar;
    public LoadingViewHolderVertically(@NonNull View itemView) {
        super(itemView);
        progressBar=itemView.findViewById(R.id.progress_bar);
    }
}
