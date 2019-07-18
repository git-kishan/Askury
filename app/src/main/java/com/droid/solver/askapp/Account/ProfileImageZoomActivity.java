package com.droid.solver.askapp.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileImageZoomActivity extends AppCompatActivity {

    private CircleImageView profileImageView;
    private ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image_zoom);
        Intent intent=getIntent();
        String uid=intent.getStringExtra("uid");
        String url= Constants.PROFILE_PICTURE+"/"+uid+Constants.THUMBNAIL;
        StorageReference reference= FirebaseStorage.getInstance().getReference();
        supportPostponeEnterTransition();
        profileImageView=findViewById(R.id.profile_image);
        backButton=findViewById(R.id.back_button);
    }
}
