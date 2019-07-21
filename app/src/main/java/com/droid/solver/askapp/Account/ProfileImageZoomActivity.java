package com.droid.solver.askapp.Account;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ProfileImageZoomActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image_zoom);
        Intent intent=getIntent();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String uid=intent.getStringExtra("uid");
        String url= Constants.PROFILE_PICTURE+"/"+uid+Constants.THUMBNAIL;
        StorageReference reference= FirebaseStorage.getInstance().getReference();
        final ImageView profileImageView=findViewById(R.id.profile_image);
        ImageView backButton=findViewById(R.id.back_button);
        Glide.with(this)
                .load(reference.child(url))
                .placeholder(R.drawable.ic_placeholder_large)
                .error(R.drawable.ic_placeholder_large)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        PhotoViewAttacher attacher=new PhotoViewAttacher(profileImageView);
                        attacher.update();
                        return false;
                    }
                })
                .into(profileImageView);
        backButton.setOnClickListener( this);

    }
    public void onClick(View view){
        onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);
    }
}
