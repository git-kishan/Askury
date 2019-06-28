package com.droid.solver.askapp.Account;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.widget.CoordinatorLayout;
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
import com.droid.solver.askapp.Main.UidPasserListener;
import com.droid.solver.askapp.Main.UserInfoModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherAccountActivity extends AppCompatActivity implements View.OnClickListener, UidPasserListener {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CoordinatorLayout coordinatorLayout;
    private ImageView backImageButton;
    private CircleImageView profileImage;
    private EmojiTextView profileName,about;
    private TextView followNum,followingNum,pointNum;
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
        coordinatorLayout=findViewById(R.id.root_layout);
        profileImage=findViewById(R.id.circleImageView4);
        profileName=findViewById(R.id.profile_name);
        about=findViewById(R.id.about);
        followNum=findViewById(R.id.follow_count);
        followingNum=findViewById(R.id.following_count);
        pointNum=findViewById(R.id.point_count);
        viewPager=findViewById(R.id.view_pager);
        backImageButton=findViewById(R.id.back_button);
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
        backImageButton.setOnClickListener(this);
        checkConnection();
    }

    private void checkConnection(){
        if(isNetworkAvailable()){
            loadDataFromRemoteDatabase();
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
        AccountQuestionFragment accountQuestionFragment=new AccountQuestionFragment();
        AccountQuestionAnswerFragment accountQuestionAnswerFragment=new AccountQuestionAnswerFragment();
        pagerAdapter.addFragment(new AccountQuestionFragment());
        pagerAdapter.addFragment(new AccountQuestionAnswerFragment());
        viewPager.setAdapter(pagerAdapter);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.circleImageView4:
                break;
            case R.id.back_button:
                onBackPressed();
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
                                        String bio = model.getBio();
                                        int followerCount=model.getFollowerCount();
                                        int followingCount=model.getFollowingCount();
                                        int point=model.getPoint();
                                        followingCount=followingCount<0?0:followingCount;
                                        followerCount=followerCount<0?0:followerCount;
                                        point =point<0?0:point;
                                        followingNum.setText(String.valueOf(followingCount));
                                        followNum.setText(String.valueOf(followerCount));
                                        pointNum.setText(String.valueOf(point));
                                        profileName.setText(userName);
                                        about.setText(bio);

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
}
