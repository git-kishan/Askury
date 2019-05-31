package com.droid.solver.askapp.Question;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
    private ImageView imageView,imageViewAdd;
    private static final int PHOTO_PICKER_REQUEST_CODE=60;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore root;
    private String imageEncodedString=null;
    private boolean isAnonymous=false;

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
        imageViewAdd=findViewById(R.id.image_view_add);
        imageViewAdd.setVisibility(View.VISIBLE);
        imageViewAdd.setOnClickListener(this);
        imageViewAdd.setVisibility(View.VISIBLE);
        rootView=findViewById(R.id.root);
        loadProfilePicFromFile();
        setUserName();
        imageView.setOnClickListener(this);
        anonymousSwitch.setOnCheckedChangeListener(this);
        toolbar.setOnMenuItemClickListener(this);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if(user==null){
            showSnackBar("Please sign in again...");
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(QuestionActivity.this,SignInActivity.class));
                    finish();
                }
            }, 1000);
            startActivity(new Intent(this, SignInActivity.class) );
            finish();
        }
        root=FirebaseFirestore.getInstance();
    }

    @Override
    public boolean onMenuItemClick(final MenuItem menuItem) {

        if(menuItem.getItemId()==R.id.ask){
            if(checkQuestionLength()){

                if(isNetworkAvailable()){
                    menuItem.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_next_dark, null));

                    overlayFrameLayout.setVisibility(View.VISIBLE);
                    dotsLoaderView.show();
                    uploadQuestionToRemoteDatabase(menuItem);

                }else {
                    noInternetConnectionMessage();
                }
            }


        }
        return true;

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        if(isChecked){
            isAnonymous=true;
            String temp=String.format(getResources().getString(R.string.user_name_asked), "somebody");
            userNameAsked.setText(temp);
        }else {
            isAnonymous=false;
            setUserName();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.image_view||view.getId()==R.id.image_view_add){
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
            imageViewAdd.setVisibility(View.GONE);
            imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            Log.i("TAG", "byte array size :- "+byteArray.length);
            if(byteArray.length>600000){
                Snackbar.make(rootView,"Image size should be less than 600 kb" , Snackbar.LENGTH_SHORT).show();
                return;
            }
            imageEncodedString= Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.i("TAG", "encode string length :- "+imageEncodedString.length());



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
    private void loadProfilePicFromFile(){
        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        String path=preferences.getString(Constants.LOW_PROFILE_PIC_PATH, null);
        File file=new File(path,"profile_pic_low_resolution");
        try {
            Bitmap bitmap=BitmapFactory.decodeStream(new FileInputStream(file));
            circularImageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void setUserName(){
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        String userName=preferences.getString(Constants.userName, null);
        if(userName!=null) {
            String temp = String.format(getResources().getString(R.string.user_name_asked), userName);
            userNameAsked.setText(temp);
        }

    }
    private boolean checkQuestionLength( ){
        String data=String.valueOf(questionInputEditText.getText());
        if(data.equals("null")){
            return false;
        }
            long length=data.length();
            if(length>2000){
                Snackbar.make(rootView,"Question length must be less than 2000", Snackbar.LENGTH_LONG).show();
                return false;
            }else {
                return true;
            }

    }

    private void uploadQuestionToRemoteDatabase(final MenuItem menuItem){
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        String askerName=preferences.getString(Constants.userName, null);
        String askerId=user.getUid();
        long timeOfAsking=System.currentTimeMillis();
        final String question=String.valueOf(questionInputEditText.getText());
        String userImageUrl=preferences.getString(Constants.LOW_IMAGE_URL, null);
        boolean isImageAttached=imageEncodedString!=null;

        String uid=user.getUid();
        AskQuestionModel model = new AskQuestionModel(askerName,
                askerId, timeOfAsking, question, userImageUrl,isImageAttached,imageEncodedString,isAnonymous );
        root.collection("user").document(uid).collection("question").add(model).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        dotsLoaderView.hide();

                    overlayFrameLayout.setVisibility(View.GONE);
                    SuccessfullyUploadDialogFragment imageSuccessfullyUploadDialogFragment=new SuccessfullyUploadDialogFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("message", "Question uploaded successfully");
                    imageSuccessfullyUploadDialogFragment.setArguments(bundle);
                    imageSuccessfullyUploadDialogFragment.show(getSupportFragmentManager(), "question_dialog");
                    questionInputEditText.setText("");
                    imageView.setImageBitmap(null);
                    imageViewAdd.setVisibility(View.VISIBLE);
                    anonymousSwitch.setChecked(false);

                    menuItem.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_next_fader, null));

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                overlayFrameLayout.setVisibility(View.GONE);
                SuccessfullyUploadDialogFragment imageSuccessfullyUploadDialogFragment=new SuccessfullyUploadDialogFragment();
                Bundle bundle=new Bundle();
                bundle.putString("message", "Failure occur ,try again...");
                imageSuccessfullyUploadDialogFragment.setArguments(bundle);
                imageSuccessfullyUploadDialogFragment.show(getSupportFragmentManager(), "question_dialog");
                anonymousSwitch.setChecked(false);
            }
        });
        root.collection("question").add(model);


    }
    private boolean isNetworkAvailable(){
        ConnectivityManager comm= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=comm.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
    }
    private void showSnackBar(String message){
        Snackbar snackbar=Snackbar.make(rootView,  message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
        View view=snackbar.getView();
        TextView tv =view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
        view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.progress_bar_overlay_color, null));
        snackbar.show();
    }

    private void noInternetConnectionMessage(){
        Snackbar.make(rootView, "No internet Connection Available",Snackbar.LENGTH_LONG).show();
    }

}
