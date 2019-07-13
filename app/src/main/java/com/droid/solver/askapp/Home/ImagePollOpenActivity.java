package com.droid.solver.askapp.Home;

import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.droid.solver.askapp.R;
public class ImagePollOpenActivity extends AppCompatActivity {

    String [] imageUrlList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String image1Url=intent.getStringExtra("image1Url");
        String image2Url=intent.getStringExtra("image2Url");
        setContentView(R.layout.activity_image_poll_open);
        ImageView backImage=findViewById(R.id.back_image);
        imageUrlList=new String[2];
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
