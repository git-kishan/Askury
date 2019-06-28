package com.droid.solver.askapp.Account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherAccountActivity extends AppCompatActivity implements View.OnClickListener {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CardView rootLayout;
    private CircleImageView profileImage;
    private EmojiTextView profileName,about;
    private TextView followCount,followingCount,pointCount;
    private String imageUrl,uid,userName,bio;
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_account);
        Intent intent=getIntent();
        imageUrl=intent.getStringExtra("profile_image");
        uid=intent.getStringExtra("uid");
        userName=intent.getStringExtra("user_name");
        bio=intent.getStringExtra("bio");
        rootLayout=findViewById(R.id.root_layout);
        profileImage=findViewById(R.id.circleImageView4);
        profileName=findViewById(R.id.profile_name);
        about=findViewById(R.id.about);
        followCount=findViewById(R.id.follow_count);
        followingCount=findViewById(R.id.following_count);
        pointCount=findViewById(R.id.point_count);
        viewPager=findViewById(R.id.view_pager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window=getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.WHITE);
        }
        setViewPager();
        setTabLayout();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        init();
        profileImage.setOnClickListener(this);
        if(user==null){
            Snackbar.make(rootLayout, "Sign in again ...",Snackbar.LENGTH_SHORT).show();
           Handler handler=new Handler();
           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   startActivity(new Intent(OtherAccountActivity.this, SignInActivity.class)) ;
                   finish();
               }
           }, 300);
        }



    }

    private void setTabLayout(){
        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab1=tabLayout.newTab();
        TabLayout.Tab tab2=tabLayout.newTab();
        try {
            tabLayout.getTabAt(0).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_questions_black, null));
            tabLayout.getTabAt(1).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_qa_black, null));
        }catch (NullPointerException e){

        }
    }
    private void setViewPager(){
        AccountFragmentPagerAdapter pagerAdapter=new AccountFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new AccountQuestionFragment());
        pagerAdapter.addFragment(new AccountQuestionAnswerFragment());
        viewPager.setAdapter(pagerAdapter);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.circleImageView4:
                break;
        }
    }

    private void init(){
        imageUrl=imageUrl==null?"":imageUrl;
        uid=uid==null?"":uid;
        userName=userName==null?"":userName;
        bio=bio==null?"":bio;
        String url = Constants.PROFILE_PICTURE +"/"+uid + ProfileImageActivity.THUMBNAIL;
        StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);

        GlideApp.with(this)
                .load(reference)
                .placeholder(ResourcesCompat.getDrawable(getResources(), R.drawable.round_account, null))
                .error(ResourcesCompat.getDrawable(getResources(), R.drawable.round_account, null))
                .into(profileImage);
        profileName.setText(userName);
        about.setText(bio);

    }
}
