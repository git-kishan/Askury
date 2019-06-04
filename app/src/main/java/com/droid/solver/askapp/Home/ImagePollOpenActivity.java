package com.droid.solver.askapp.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.droid.solver.askapp.R;
import com.squareup.picasso.Picasso;

public class ImagePollOpenActivity extends AppCompatActivity {

    private ImageView imageView,backImage;
    private String imageUrl;
    private String transitionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_poll_open);
        imageView=findViewById(R.id.image);
        backImage=findViewById(R.id.back_image);
        Intent intent=getIntent();
        imageUrl=intent.getStringExtra("imageUrl");
        transitionName=intent.getStringExtra("trasitionName");

        Picasso.get().load(imageUrl).
                into(imageView);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
