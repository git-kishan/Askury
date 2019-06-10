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
    private String image1Url,image2Url;
    private ViewPager viewPager;
    String [] imageUrlList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        image1Url=intent.getStringExtra("image1Url");
        image2Url=intent.getStringExtra("image2Url");
        setContentView(R.layout.activity_image_poll_open);
        backImage=findViewById(R.id.back_image);
        imageUrlList=new String[2];
        viewPager=findViewById(R.id.view_pager);

        imageUrlList[0]=image1Url;
        imageUrlList[1]=image2Url;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewPager viewPager=findViewById(R.id.view_pager);
        ImagePollViewPagerAdapter pagerAdapter=new ImagePollViewPagerAdapter(this, imageUrlList);
        viewPager.setAdapter(pagerAdapter);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

}
