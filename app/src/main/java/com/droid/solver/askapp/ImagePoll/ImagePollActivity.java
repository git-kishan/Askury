package com.droid.solver.askapp.ImagePoll;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.BuildConfig;
import com.droid.solver.askapp.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImagePollActivity extends AppCompatActivity implements View.OnClickListener,
        Toolbar.OnMenuItemClickListener,PassData {

    private TextView orTextVeiw;
    private CardView rootCardView;
    private Toolbar toolbar;
    private CardView rootView;
    private ImageView image1,image2;
    private TextInputLayout questionInputLayout;
    private TextInputEditText questionInputEditText;
    private ImageSelectionFragment dialogFragment;
    private static  final  int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE=170;
    private static final int PHOTO_PICKER_REQUEST_CODE=60;
    private static final int CAMERE_REQUEST_CODE=13;
    private boolean isImage1Clicked,isImage2Clicked;
    private CardView image1CardView,image2CardView;
    private TextView option1,option2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_poll);
        toolbar=findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.questionactivity_toolbar_menu);
        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        toolbar.setTitleMarginBottom(30);
        orTextVeiw=findViewById(R.id.or_text_view);
        rootCardView = findViewById(R.id.root_card_view);
        rootView=findViewById(R.id.root);
        questionInputLayout=findViewById(R.id.questionInputLayout);
        questionInputEditText=findViewById(R.id.questionEditText);
        image1CardView=findViewById(R.id.cardView11);
        image2CardView=findViewById(R.id.cardView4);
        option1=findViewById(R.id.textView2);
        option2=findViewById(R.id.textView8);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image1.requestFocus();
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(this);
        rootCardView.setOnClickListener(this);
        orTextVeiw.setOnClickListener(this);
        dialogFragment=new ImageSelectionFragment();
        orTextVeiw.requestFocus();
        hideSoftKeyboard(orTextVeiw);

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
            case R.id.root_card_view:
                rootCardView.requestFocus();
                hideSoftKeyboard(rootCardView);
                break;
            case R.id.or_text_view:
                orTextVeiw.requestFocus();
                hideSoftKeyboard(orTextVeiw);
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

    private void hideSoftKeyboard(View view){
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(inputMethodManager!=null){
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
    @Override
    public void passDataFromFragmentToActivity(boolean isImage1Clicked, boolean isImage2Clicked,
                                               boolean isGalleryClicked, boolean isCameraClicked) {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            this.isImage1Clicked=isImage1Clicked;
            this.isImage2Clicked=isImage2Clicked;

            checkReadExternalStoragePermission( isImage1Clicked,  isImage2Clicked,  isGalleryClicked,  isCameraClicked);
        }
    }
    private void onGalleryClicked(boolean isImage1Clicked, boolean isImage2Clicked,
                                  boolean isGalleryClicked, boolean isCameraClicked){
        Intent photoPickerIntent=new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        if(photoPickerIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST_CODE);
        }


    }
    private void onCameraClicked(){
        Snackbar.make(rootView, "permission granted  in camera" , Toast.LENGTH_SHORT).show();

    }
    private void checkReadExternalStoragePermission(boolean isImage1Clicked, boolean isImage2Clicked,
                                                    boolean isGalleryClicked, boolean isCameraClicked){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);

            }
        } else {
            if(isGalleryClicked){
                onGalleryClicked(isImage1Clicked,isImage2Clicked,isGalleryClicked,isCameraClicked);
            }else if(isCameraClicked){
                onCameraClicked();
            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PHOTO_PICKER_REQUEST_CODE:
                if(resultCode==RESULT_OK&&data!=null){
                    resizeSelectedImage(data);
                    }else {
                    Snackbar.make(rootView, "Error occur,select again ", Snackbar.LENGTH_LONG).setAction("retry",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onGalleryClicked(isImage1Clicked, isImage2Clicked,
                                            true, false);
                                }
                            }).show();
                }
                break;
            case CAMERE_REQUEST_CODE:
                break;
        }
    }
    private void resizeSelectedImage(@Nullable Intent data){
        final Uri imageUri = data.getData();
        Bitmap bitmap=decodeSelectedImageUri(imageUri, image1.getWidth(),image1.getHeight());
        if(bitmap!=null) {
            Bitmap b = Bitmap.createScaledBitmap(bitmap, image1.getWidth(), image1.getHeight(), false);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encodedString= Base64.encodeToString(byteArray, Base64.DEFAULT);
            //now upload both image to database
            if (isImage1Clicked) {
                image1.setImageBitmap(b);
                image1CardView.setVisibility(View.GONE);
                option1.setVisibility(View.GONE);


            } else if (isImage2Clicked) {
                image2.setImageBitmap(b);
                image2CardView.setVisibility(View.GONE);
                option2.setVisibility(View.GONE);

            }
        }
    }
    private  Bitmap decodeSelectedImageUri(Uri uri,int requiredWidth,int requiredHeight){
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            final InputStream imageStream = getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(imageStream, null, options);
            int selectedImageHeight = options.outHeight;
            int selectedImageWidth = options.outWidth;
            String imageType = options.outMimeType;
            Log.i("TAG", "bitmap height :- " + selectedImageHeight);
            Log.i("TAG", "bitmap width :- " + selectedImageWidth);
            Log.i("TAG", "bitmap mime type   :- " + imageType);

            options.inSampleSize=calculateInSampleSize(options, requiredWidth, requiredHeight);
            options.inJustDecodeBounds=false;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
        }catch (FileNotFoundException e){
            Snackbar.make(rootView, "Unknown error", Snackbar.LENGTH_SHORT).show();

        }
        return null;
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}
