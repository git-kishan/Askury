package com.droid.solver.askapp.Question;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.ImagePoll.SuccessfullyUploadDialogFragment;
import com.droid.solver.askapp.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import steelkiwi.com.library.DotsLoaderView;

public class QuestionActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,
        CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private Toolbar toolbar;
    private DotsLoaderView dotsLoaderView;
    private FrameLayout overlayFrameLayout;
    private CircleImageView circularImageView;
    private TextView userNameAsked;
    private TextInputLayout questionInputLayout;
    private TextInputEditText questionInputEditText;
    private SwitchCompat anonymousSwitch;
    private CardView cardView ;
    private ConstraintLayout rootView;
    private ImageView imageView;
    private static final int PHOTO_PICKER_REQUEST_CODE=60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        toolbar=findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.questionactivity_toolbar_menu);
        toolbar.setNavigationOnClickListener(this);
        dotsLoaderView=findViewById(R.id.dotsLoaderView);
        overlayFrameLayout=findViewById(R.id.overlay_frame_layout);
        circularImageView=findViewById(R.id.profile_image);
        userNameAsked=findViewById(R.id.userNameAsked);
        questionInputLayout=findViewById(R.id.questionInputLayout);
        questionInputEditText=findViewById(R.id.questionEditText);
        anonymousSwitch=findViewById(R.id.anonymous);
        cardView=findViewById(R.id.cardView5);
        imageView=findViewById(R.id.image_view);
        rootView=findViewById(R.id.root);
        imageView.setOnClickListener(this);
        anonymousSwitch.setOnCheckedChangeListener(this);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(final MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.ask){
            Toast.makeText(this, "ask is clicked", Toast.LENGTH_SHORT).show();
            menuItem.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_next_dark, null));
            overlayFrameLayout.setVisibility(View.VISIBLE);
            dotsLoaderView.show();

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dotsLoaderView.hide();
                  overlayFrameLayout.setVisibility(View.GONE);
                    SuccessfullyUploadDialogFragment imageSuccessfullyUploadDialogFragment=new SuccessfullyUploadDialogFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("message", "Question uploaded successfully");
                    imageSuccessfullyUploadDialogFragment.setArguments(bundle);
                    imageSuccessfullyUploadDialogFragment.show(getSupportFragmentManager(), "question_dialog");


                }
            }, 3000);
        }
        return true;

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        if(isChecked){
            Toast.makeText(this, "anonymous", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.image_view){

            Intent photoPickerIntent=new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            if(photoPickerIntent.resolveActivity(getPackageManager())!=null){
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST_CODE);
            }else {
                Snackbar.make(rootView, "No camera app present", Snackbar.LENGTH_SHORT).show();
            }
        }else {
            onBackPressed();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PHOTO_PICKER_REQUEST_CODE&&resultCode==RESULT_OK){
            resizeImageSelectedFromGallery(data);

        }
    }
    private void resizeImageSelectedFromGallery(@Nullable Intent data){
        final Uri imageUri = data.getData();
        Bitmap bitmap=decodeSelectedImageUri(imageUri,400 ,200);
        if(bitmap!=null) {
            Bitmap compressedBitmap = Bitmap.createScaledBitmap(bitmap, 400, 200, false);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encodedString= Base64.encodeToString(byteArray, Base64.DEFAULT);
            imageView.setImageBitmap(compressedBitmap);



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
