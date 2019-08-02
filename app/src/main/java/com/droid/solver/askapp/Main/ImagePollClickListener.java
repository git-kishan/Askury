package com.droid.solver.askapp.Main;

import android.view.View;

import androidx.annotation.Keep;

@Keep
public interface ImagePollClickListener {
    void onImagePollImageClicked(String imageUrl,View view);
}
