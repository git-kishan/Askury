package com.droid.solver.askapp.Account;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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


    private CardView rootLayout;
    private ImageView backImageButton;
    private CircleImageView profileImage;
    private MaterialButton followButton;
    private EmojiTextView profileName,about;
    private TextView followCount,followingCount,pointCount;
    private RecyclerView recyclerView;
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
        getWindow().setStatusBarColor(ResourcesCompat.getColor(getResources(), android.R.color.white, null));
        rootLayout=findViewById(R.id.root_layout);
        backImageButton = findViewById(R.id.back_image);
        profileImage=findViewById(R.id.circleImageView4);
        followButton=findViewById(R.id.follow_button);
        profileName=findViewById(R.id.profile_name);
        about=findViewById(R.id.about);
        followCount=findViewById(R.id.follow_count);
        followingCount=findViewById(R.id.following_count);
        pointCount=findViewById(R.id.point_count);
        recyclerView=findViewById(R.id.recycler_view);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        init();
        followButton.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        backImageButton.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.back_image:
                onBackPressed();
                break;
            case R.id.circleImageView4:
                break;
            case R.id.follow_button:
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
