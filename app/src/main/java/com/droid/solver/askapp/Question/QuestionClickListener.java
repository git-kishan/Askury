package com.droid.solver.askapp.Question;

import androidx.annotation.Keep;

@Keep
public interface QuestionClickListener {
    void onCardItemClicked(int position);
    void onShareImageClicked(int position);
    void onProfileImageClicked(int position);
}
