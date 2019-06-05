package com.droid.solver.askapp.Home;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.droid.solver.askapp.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagePollOpenActivity extends AppCompatActivity {

    private ImageView backImage;
    private String imageUrl;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_poll_open);
        backImage=findViewById(R.id.back_image);
        Intent intent=getIntent();
        viewPager=findViewById(R.id.view_pager);
        imageUrl=intent.getStringExtra("imageUrl");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        PhotoViewAttacher attacher=new PhotoViewAttacher(imageView);
//        attacher.update();
    }

}
