package com.droid.solver.askapp.Survey;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.solver.askapp.R;

public class SurveyOptionProviderRecyclerViewHolder extends RecyclerView.ViewHolder {
    private EditText editText;
    private TextView textView;
    private ImageView editImage,tickImage;

    public SurveyOptionProviderRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        editImage=itemView.findViewById(R.id.edit_image);
        tickImage=itemView.findViewById(R.id.tick_image);
        editText=itemView.findViewById(R.id.edit_text);
        textView=itemView.findViewById(R.id.textView);

    }

}
