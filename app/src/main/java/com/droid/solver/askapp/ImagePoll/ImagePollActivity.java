package com.droid.solver.askapp.ImagePoll;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.droid.solver.askapp.BuildConfig;
import com.droid.solver.askapp.R;

public class ImagePollActivity extends AppCompatActivity implements View.OnClickListener,
        Toolbar.OnMenuItemClickListener,PassData {

    private Toolbar toolbar;
    private CardView rootView;
    private ImageView image1,image2;
    private TextInputLayout questionInputLayout;
    private TextInputEditText questionInputEditText;
    private ImageSelectionFragment dialogFragment;
    private static  final  int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE=54;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_poll);
        toolbar=findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.questionactivity_toolbar_menu);
        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        toolbar.setTitleMarginBottom(30);
        rootView=findViewById(R.id.root);
        questionInputLayout=findViewById(R.id.questionInputLayout);
        questionInputEditText=findViewById(R.id.questionEditText);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image1.requestFocus();
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(this);
        dialogFragment=new ImageSelectionFragment();

    }

    @Override
    public void onClick(View view) {
        Bundle bundle=new Bundle();
        switch (view.getId()){
            case R.id.image1:

                bundle.putBoolean("image1", true);
                bundle.putBoolean("image2", false);
                dialogFragment.setArguments(bundle);
                ((ImageSelectionFragment) dialogFragment).show(getSupportFragmentManager(), "image_select");
                break;
            case R.id.image2:
                bundle.putBoolean("image1", false);
                bundle.putBoolean("image2", true);
                dialogFragment.setArguments(bundle);
                ((ImageSelectionFragment) dialogFragment).show(getSupportFragmentManager(), "image_select");
                break;
            default :
                Toast.makeText(this, "navigation is clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.ask){
            Toast.makeText(ImagePollActivity.this, "ask clicked", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void passDataFromFragmentToActivity(boolean isImage1Clicked, boolean isImage2Clicked, boolean isGalleryClicked, boolean isCameraClicked) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Toast.makeText(this, "upper level", Toast.LENGTH_SHORT).show();
            checkReadExternalStoragePermission( isImage1Clicked,  isImage2Clicked,  isGalleryClicked,  isCameraClicked);
        }
    }
    private void onGalleryClicked(boolean isImage1Clicked, boolean isImage2Clicked, boolean isGalleryClicked, boolean isCameraClicked){
        Snackbar.make(rootView, "permission granted ", Toast.LENGTH_SHORT).show();
    }
    private void onCameraClicked(){

    }
    private void checkReadExternalStoragePermission(boolean isImage1Clicked, boolean isImage2Clicked, boolean isGalleryClicked, boolean isCameraClicked){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);

            }
        } else {
            onGalleryClicked(isImage1Clicked,isImage2Clicked,isGalleryClicked,isCameraClicked);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull  int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                   onGalleryClicked();
                } else {
                    Snackbar.make(rootView,"permission denied",Snackbar.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}
