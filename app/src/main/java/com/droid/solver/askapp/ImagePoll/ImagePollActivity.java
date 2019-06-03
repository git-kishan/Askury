package com.droid.solver.askapp.ImagePoll;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.text.emoji.widget.EmojiEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import steelkiwi.com.library.DotsLoaderView;

public class ImagePollActivity extends AppCompatActivity implements View.OnClickListener,
        Toolbar.OnMenuItemClickListener,PassData {

    private DotsLoaderView dotsLoaderView;
    private FrameLayout overLayFrameLayout;
    private TextView orTextVeiw;
    private CardView rootCardView;
    private Toolbar toolbar;
    private CardView rootView;
    private ImageView image1,image2;
    private TextInputLayout questionInputLayout;
    private EmojiEditText questionInputEditText;
    private ImageSelectionFragment dialogFragment;
    private static  final  int PERMISSION_REQUEST_READ_CAMERA=180;
    private static  final  int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE=170;
    private static final int PHOTO_PICKER_REQUEST_CODE=60;
    private static final int CAMERE_REQUEST_CODE=13;
    private boolean isImage1Clicked,isImage2Clicked;
    private CardView image1CardView,image2CardView;
    private TextView option1,option2;
    private String currentPhotoPath;
    private byte[] image1ByteArray,image2ByteArray;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore root;
    private StorageReference rootStorageRef;
    private UploadTask uploadTask;
    private static AlertDialog errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_poll);
        toolbar=findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.questionactivity_toolbar_menu);
        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        dotsLoaderView=findViewById(R.id.dotsLoaderView);
        toolbar.setTitleMarginBottom(30);
        overLayFrameLayout=findViewById(R.id.overlay_frame_layout);
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
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if(user==null){
            showSnackBar("Please sign in again...");
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(ImagePollActivity.this, SignInActivity.class));
                    finish();
                }
            }, 1000);
        }
        root=FirebaseFirestore.getInstance();
        rootStorageRef= FirebaseStorage.getInstance().getReference();

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
                onBackPressed();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.ask){

            if(checkValidation()){

                if(isInternetAvailable()){
                        menuItem.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_next_dark, null));
                        overLayFrameLayout.setVisibility(View.VISIBLE);
                        dotsLoaderView.show();
                        uploadFirstImageToStorage(menuItem);

                }
                else {
                    showSnackBar("No internet connection available");
                }
            }

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
        }else {
            Snackbar.make(rootView, "No camera app present", Snackbar.LENGTH_SHORT).show();
        }


    }
    private void onCameraClicked(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERE_REQUEST_CODE);

            }
        }

    }

    private void checkReadExternalStoragePermission(boolean isImage1Clicked, boolean isImage2Clicked,
                                                    boolean isGalleryClicked, boolean isCameraClicked){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);

                }
            } else {
                if (isGalleryClicked) {
                    onGalleryClicked(isImage1Clicked, isImage2Clicked, isGalleryClicked, isCameraClicked);
                } else if (isCameraClicked) {
                    onCameraClicked();
                }
            }
        }else {
            if (isGalleryClicked) {
                onGalleryClicked(isImage1Clicked, isImage2Clicked, isGalleryClicked, isCameraClicked);
            } else if (isCameraClicked) {
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
                    resizeImageSelectedFromGallery(data);
                }else {

                    Snackbar snackbar=Snackbar.make(rootView, "Error occur,select again ", Snackbar.LENGTH_LONG).setAction("retry",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onGalleryClicked(isImage1Clicked, isImage2Clicked,
                                            true, false);
                                }
                            });
                    snackbar.setActionTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
                    View view=snackbar.getView();
                    TextView tv =view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
                    view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.progress_bar_overlay_color, null));
                    snackbar.show();

                }
                break;
            case CAMERE_REQUEST_CODE:
                if(resultCode==RESULT_OK) {
                    addPicInGallery();
                    resizeImageCapturedFromCamera();
                }else {

                    Snackbar snackbar=Snackbar.make(rootView, "Error occur,select again ", Snackbar.LENGTH_LONG).setAction("retry",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onGalleryClicked(isImage1Clicked, isImage2Clicked,
                                            true, false);
                                }
                            });
                    snackbar.setActionTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
                    View view=snackbar.getView();
                    TextView tv =view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
                    view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.progress_bar_overlay_color, null));
                    snackbar.show();
                }
                break;
        }
    }

    private void resizeImageSelectedFromGallery(@Nullable Intent data){
        final Uri imageUri = data.getData();
        Bitmap bitmap=decodeSelectedImageUri(imageUri, 250,300);
        if(bitmap!=null) {
            ByteArrayOutputStream uploaderStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, uploaderStream);
            byte [] uploderByteArray=uploaderStream.toByteArray();


            if (isImage1Clicked) {
                image1.setImageBitmap(bitmap);
                image1CardView.setVisibility(View.GONE);
                option1.setVisibility(View.GONE);
                image1ByteArray=uploderByteArray;
                Log.i("TAG", "image 1 selected :- "+image1ByteArray.length);

            } else if (isImage2Clicked) {
                image2.setImageBitmap(bitmap);
                image2CardView.setVisibility(View.GONE);
                option2.setVisibility(View.GONE);
                image2ByteArray=uploderByteArray;
                Log.i("TAG", "image 2 selected :- "+image2ByteArray.length);
            }
        }
    }
    private  Bitmap decodeSelectedImageUri(Uri uri,int requiredWidth,int requiredHeight){
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            final InputStream imageStream = getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(imageStream, null, options);
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

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void addPicInGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void resizeImageCapturedFromCamera() {
        int targetW = image1.getWidth();
        int targetH = image1.getHeight();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, options);
        int capturedImageWidth = options.outWidth;
        int capturedImageHeight = options.outHeight;
        int scaleFactor = Math.min(capturedImageWidth/targetW, capturedImageHeight/targetH);
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, options);
        if(bitmap!=null) {
            Bitmap uploaderBitmap=Bitmap.createScaledBitmap(bitmap, 250, 300, false);
            ByteArrayOutputStream uploaderStream=new ByteArrayOutputStream();
            uploaderBitmap.compress(Bitmap.CompressFormat.JPEG, 100, uploaderStream);
            byte [] uploderByteArray=uploaderStream.toByteArray();

            if (isImage1Clicked) {
                image1.setImageBitmap(uploaderBitmap);
                image1CardView.setVisibility(View.GONE);
                option1.setVisibility(View.GONE);
                image1ByteArray=uploderByteArray;
                Log.i("TAG", "image 1 selected :- "+image1ByteArray.length);


            } else if (isImage2Clicked) {
                image2.setImageBitmap(uploaderBitmap);
                image2CardView.setVisibility(View.GONE);
                option2.setVisibility(View.GONE);
                image2ByteArray=uploderByteArray;
                Log.i("TAG", "image 2 selected :- "+image2ByteArray.length);

            }
        }
    }
    private boolean checkValidation(){
        String data=String.valueOf(questionInputEditText.getText());
        if(data.equals("null")){
            showSnackBar("Put a valid question");
            return false;
        }if(image1ByteArray ==null){
            showSnackBar("Select first image");
            return false;
        }if(image2ByteArray ==null){
            showSnackBar("Select second image");
            return false;
        }

        return true;

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
    private boolean isInternetAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
    }

    private void uploadFirstImageToStorage(final MenuItem menuItem){

            final String uid = user.getUid();
            String currentTime = String.valueOf(System.currentTimeMillis());
            String image1Name = uid + "@" + currentTime+"image1";
            final StorageReference image1Ref = rootStorageRef.child("imagePoll").child(image1Name);
            uploadTask = image1Ref.putBytes(image1ByteArray);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    image1Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.i("TAG", "image 1 url :- "+uri.toString());

                            uploadSecondImageToStorage(uri.toString(),menuItem);
                        }
                    }) ;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    overLayFrameLayout.setVisibility(View.GONE);
                    dotsLoaderView.hide();
                    menuItem.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_next_fader, null));
                    showFailureDialog();
                    showFailureDialog();
                }
            });

    }

    private void uploadSecondImageToStorage(final String image1Url, final MenuItem menuItem){

        final String uid = user.getUid();
        String currentTime = String.valueOf(System.currentTimeMillis());
        String image2Name=uid+"@"+currentTime+"image2";
        final StorageReference image2Ref=rootStorageRef.child("imagePoll").child(image2Name);
        uploadTask = image2Ref.putBytes(image2ByteArray);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image2Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("TAG", "image 2 url :- "+uri.toString());
                        uploadImagesToRemoteDatabase(menuItem,image1Url,uri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                overLayFrameLayout.setVisibility(View.GONE);
                dotsLoaderView.hide();
                menuItem.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_next_fader, null));
                showFailureDialog();
                showFailureDialog();
            }
        });

    }

    private void uploadImagesToRemoteDatabase(final MenuItem menuItem,String imageFirstUrl,String imageSecondUrl){
        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        String askerId=user.getUid();
        String askerName=preferences.getString(Constants.userName, null);
        String askerImageUrlLow=preferences.getString(Constants.LOW_IMAGE_URL, null);
        String askerBio=preferences.getString(Constants.bio, null);
        final String question=questionInputEditText.getText().toString();
        final String image1Url=imageFirstUrl;
        String image2Url=imageSecondUrl;
        long timeOfPolling=System.currentTimeMillis();
        int image1LikeNo=0;
        int image2LikeNo=0;
        String imagePollId=root.collection("user").document(askerId).collection("imagePoll").document().getId();
        AskImagePollModel pollModel=new AskImagePollModel(
                askerId, askerName, askerImageUrlLow, askerBio, question,
                image1Url, image2Url, timeOfPolling, image1LikeNo, image2LikeNo, imagePollId);

        DocumentReference userImagePollRef=root.collection("user").document(askerId).
                collection("imagePoll").document(imagePollId);

        DocumentReference rootImagePollRef=root.collection("imagePoll").document(imagePollId);

        WriteBatch batch=root.batch();
        batch.set(userImagePollRef, pollModel);
        batch.set(rootImagePollRef, pollModel);
        batch.commit().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                overLayFrameLayout.setVisibility(View.GONE);
                dotsLoaderView.hide();
                questionInputEditText.setText("");
                menuItem.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_next_fader, null));
                image1.setImageBitmap(null);
                image2.setImageBitmap(null);
                image1CardView.setVisibility(View.VISIBLE);
                image2CardView.setVisibility(View.VISIBLE);
                showSuccessfullDialog();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                overLayFrameLayout.setVisibility(View.GONE);
                dotsLoaderView.hide();
                menuItem.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_next_fader, null));
                showFailureDialog();
                showFailureDialog();

            }
        });
    }

    private void showSuccessfullDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Image Poll loaded successfully\nThank you .");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 300);
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();

    }
    private void showFailureDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setPositiveButton("got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                errorDialog.cancel();
            }
        });
        builder.setMessage("Error occured, try again ...");
        errorDialog=builder.create();
        errorDialog.show();
    }
}
