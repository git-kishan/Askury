package com.droid.solver.askapp.Account;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.button.MaterialButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.emoji.widget.EmojiTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.Main.UidPasserListener;
import com.droid.solver.askapp.Main.UserInfoModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;

public class OtherAccountActivity extends AppCompatActivity implements View.OnClickListener,
        UidPasserListener {


    private ViewPager viewPager;
    private CoordinatorLayout coordinatorLayout;
    private CircleImageView profileImage;
    private MaterialButton followButton;
    private static final String FOLLOW ="Follow";
    private static final String UNFOLLOW="Unfollow";
    private String STATUS=null;
    private EmojiTextView profileName,about;
    private TextView followNum,followingNum,pointNum;
    private String imageUrl,uid,userName,bio;
    private boolean notification=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_account);
        Intent intent=getIntent();

        imageUrl=intent.getStringExtra("profile_image");
        uid=intent.getStringExtra("uid");
        userName=intent.getStringExtra("user_name");
        bio=intent.getStringExtra("bio");
        notification=intent.getBooleanExtra("notification",false);

        followButton=findViewById(R.id.follow_button);
        followButton.setVisibility(View.GONE);//starting state of follow button
        coordinatorLayout=findViewById(R.id.root_layout);
        profileImage=findViewById(R.id.circleImageView4);
        profileName=findViewById(R.id.profile_name);
        about=findViewById(R.id.about);
        followNum=findViewById(R.id.follow_count);
        followingNum=findViewById(R.id.following_count);
        pointNum=findViewById(R.id.point_count);
        viewPager=findViewById(R.id.view_pager);
        ImageView backImageButton=findViewById(R.id.back_button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window=getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.WHITE);
        }
        setViewPager();
        setTabLayout();
        profileImage.setOnClickListener(this);
        backImageButton.setOnClickListener(this);
        followButton.setOnClickListener(this);
        checkUserAuth();
        checkConnection();
    }
    private void checkConnection(){
        if(isNetworkAvailable()){
            if(notification){
                removeNotificationFromRemoteDatabase();
            }
            loadDataFromRemoteDatabase();
            checkFollowerList();
        }else {
            Snackbar.make(coordinatorLayout, "No internet available", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkConnection();
                        }
                    }).setActionTextColor(ResourcesCompat.getColor(getResources(),android.R.color.holo_red_light, null))
                    .show();

        }

    }

    private void checkFollowerList(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null&&uid!=null){
            String selfUid=user.getUid();
            if(selfUid.equals(uid)){
                followButton.setVisibility(View.GONE);
                return;
            }
            LocalDatabase database=new LocalDatabase(getApplicationContext());
            ArrayList<String> followingList=database.getFollowingIdList();
            if(followingList!=null&&followingList.contains(uid)){
                STATUS=FOLLOW;
                followButton.setText(UNFOLLOW);
                followButton.setVisibility(View.VISIBLE);
            }else {
                STATUS=UNFOLLOW;
                followButton.setText(FOLLOW);
                followButton.setVisibility(View.VISIBLE);
            }
        }
    }


    private void setTabLayout(){
        TabLayout tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.newTab();
        tabLayout.newTab();
        try {
            Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_questions_black, null));
            Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_qa_black, null));
        }catch (NullPointerException e){
            Log.i("TAG","exception occurs in otherAccountActivity :- "+e.getMessage());
        }
    }
    private void setViewPager(){
        AccountFragmentPagerAdapter pagerAdapter=new AccountFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new AccountQuestionFragment());
        pagerAdapter.addFragment(new AccountQuestionAnswerFragment());
        viewPager.setAdapter(pagerAdapter);
    }
    private void checkUserAuth(){
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        init();
        if(user==null){
            Snackbar.make(coordinatorLayout, "Sign in again ...",Snackbar.LENGTH_SHORT).show();
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
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.circleImageView4:
                onProfileImageClicked();
                break;
            case R.id.back_button:
                onBackPressed();
                break;
            case R.id.follow_button:
                onFollowButtonClicked(STATUS);
                break;

        }
    }

    private void onProfileImageClicked(){
        Intent intent=new Intent(this,ProfileImageZoomActivity.class);
        intent.putExtra("uid", uid);
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,profileImage,
                Objects.requireNonNull(ViewCompat.getTransitionName(profileImage)));
        startActivity(intent,optionsCompat.toBundle());
    }
    private void onFollowButtonClicked(String status){

    }

    private void init(){
        imageUrl=imageUrl==null?"":imageUrl;
        uid=uid==null?"":uid;
        userName=userName==null?"":userName;
        bio=bio==null?"":bio;
        String url = Constants.PROFILE_PICTURE +"/"+uid + Constants.THUMBNAIL;
        StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);
            GlideApp.with(this)
                    .load(reference)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(ResourcesCompat.getDrawable(getResources(), R.drawable.round_account, null))
                    .error(ResourcesCompat.getDrawable(getResources(), R.drawable.round_account, null))
                    .into(profileImage);
            profileName.setText(userName);
            about.setText(bio);



    }

    private void loadDataFromRemoteDatabase(){
        if(uid!=null){
            FirebaseFirestore.getInstance().collection("user")
                    .document(uid).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                if(task.getResult()!=null){
                                    UserInfoModel model = task.getResult().toObject(UserInfoModel.class);
                                    if(model!=null) {
                                        String userName = model.getUserName();
                                        String mbio = model.getBio();
                                        int followerCount=model.getFollowerCount();
                                        int followingCount=model.getFollowingCount();
                                        int point=model.getPoint();
                                        followingCount=followingCount<0?0:followingCount;
                                        followerCount=followerCount<0?0:followerCount;
                                        point =point<0?0:point;
                                        mbio=mbio==null?bio:mbio;
                                        followingNum.setText(String.valueOf(followingCount));
                                        followNum.setText(String.valueOf(followerCount));
                                        pointNum.setText(String.valueOf(point));
                                        profileName.setText(userName);
                                        about.setText(mbio);

                                    }
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(coordinatorLayout, "Error occured ,try after some time", Snackbar.LENGTH_INDEFINITE).show();
                }
            });
        }else {
            Snackbar.make(coordinatorLayout, "User information not available", Snackbar.LENGTH_INDEFINITE).show();

        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(manager!=null){
            NetworkInfo info=manager.getActiveNetworkInfo();
            return info!=null&&info.isConnected();
        }
        return false;
    }
    @NonNull
    @Override
    public String passUid() {
        return uid;
    }

    private void removeNotificationFromRemoteDatabase(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null&& uid!=null){
            FirebaseDatabase.getInstance().getReference()
                    .child("user")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("notification")
                    .child(uid)
                    .setValue(null);
        }
    }

}
