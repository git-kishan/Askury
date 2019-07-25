package com.droid.solver.askapp.Account;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import uk.co.senab.photoview.PhotoViewAttacher;
import static com.droid.solver.askapp.Main.Constants.PROFILE_PICTURE;

public class ProfileImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView tickImage;
    private ImageView imageView;
    private static final int IMAGE_PICKER_REQUEST=351;
    private static final int IMAGE_PICKER=154;
    private Uri cropImageUri=null;
    private FirebaseUser user;
    private ConstraintLayout rootView;
    private UploadTask uploadTask;
    private CardView progressCard;
    private byte [] largeBitmapByteArray=null;
    private  byte [] smallBitmapByteArray=null;
    private Bitmap thumbnail=null,smallThumbnail=null;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView=findViewById(R.id.imageView);
        ImageView editImage=findViewById(R.id.image_edit);
        ImageView backImage=findViewById(R.id.back_image);
        tickImage=findViewById(R.id.tick);
        rootView=findViewById(R.id.root);
        tickImage.setVisibility(View.GONE);
        handler = new Handler();
        progressCard=findViewById(R.id.progress_card);
        progressCard.setVisibility(View.VISIBLE);
        tickImage.setOnClickListener(this);
        backImage.setOnClickListener(this);
        editImage.setOnClickListener(this);
        loadProfilePicFromFile();
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Snackbar.make(rootView,"Please sign in again ..." ,Snackbar.LENGTH_SHORT ).show();
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(ProfileImageActivity.this, SignInActivity.class));
                    finish();
                }
            }, 1500);
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);

    }

    private void loadProfilePicFromFile(){
        Log.i("TAG", "load from file ");
        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        String path=preferences.getString(Constants.LOW_PROFILE_PIC_PATH, null);
        File file=new File(path,"profile_pic_high_resolution");
        try {
            Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(file));
            imageView.setImageBitmap(bitmap);
            progressCard.setVisibility(View.GONE);
            PhotoViewAttacher attacher=new PhotoViewAttacher(imageView);
            attacher.update();
        } catch (FileNotFoundException e) {

            if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                Log.i("TAG", "load from remote url");
                String url = PROFILE_PICTURE + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                        +Constants.THUMBNAIL;
                StorageReference reference = FirebaseStorage.getInstance().getReference().child(url);
                GlideApp.with(this).load(reference)
                        .error(R.drawable.ic_placeholder_large)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressCard.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
                                progressCard.setVisibility(View.GONE);
                                attacher.update();
                                return false;
                            }
                        })
                        .into(imageView);
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.image_edit:
              checkRuntimePermission();
              break;
          case R.id.back_image:
              onBackPressed();
              break;
          case R.id.tick:
              if(cropImageUri==null||smallBitmapByteArray==null||largeBitmapByteArray==null||smallThumbnail==null||thumbnail==null){
                  Snackbar.make(rootView, "Select profile picture", Snackbar.LENGTH_SHORT).show();
                  return;
              }else {

                  if(isNetworkAvailable()){
                      tickImage.setVisibility(View.GONE);
                      uploadThumbnailToRemoteDatabase(largeBitmapByteArray, smallBitmapByteArray,thumbnail,smallThumbnail);
                  }else {
                      tickImage.setVisibility(View.VISIBLE);
                      Snackbar.make(rootView, "No internet connection", Snackbar.LENGTH_SHORT).show();
                  }
              }
              break;
      }
    }
    private void checkRuntimePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        IMAGE_PICKER_REQUEST);


            }
        } else {
            loadImageFromGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==IMAGE_PICKER_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImageFromGallery();
            } else {
                Snackbar.make(rootView, "Pemission denied", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void loadImageFromGallery(){
        Intent photoPickerIntent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        photoPickerIntent.setType("image/*");
        if(photoPickerIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(photoPickerIntent, IMAGE_PICKER);
        }else {

            Toast.makeText(this, "No camera app present", Toast.LENGTH_SHORT).show();
        }
    }
    private void showProfileUploadedDialog(Bitmap bitmap){
        ConstraintLayout root=findViewById(R.id.root);
        View dialogView= LayoutInflater.from(this).inflate(R.layout.profile_image_change_dialog, root,false);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog dialog=builder.create();
        if(dialog.getWindow()!=null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().getAttributes().windowAnimations = R.style.customAnimations_successfull;
        }
        builder.setCancelable(false);
        ImageView profileImageView=dialogView.findViewById(R.id.imageView25);
        profileImageView.setImageBitmap(bitmap);
        MaterialButton button = dialogView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tickImage.performClick();
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==IMAGE_PICKER&&resultCode==RESULT_OK){
            if(data!=null){
                Uri uri=data.getData();
                CropImage.activity(uri)
                        .start(this);
            }
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
           final  CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                        if(result!=null)
                            cropImageUri = result.getUri();
                        if(cropImageUri!=null){
                            tickImage.setVisibility(View.GONE);
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    thumbnail=decodeSelectedImageUri(cropImageUri, 400, 500);
                                    smallThumbnail=decodeSelectedImageUri(cropImageUri, 50, 50);
                                    ByteArrayOutputStream s1=new ByteArrayOutputStream();
                                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, s1);
                                    largeBitmapByteArray=s1.toByteArray();
                                    ByteArrayOutputStream s2=new ByteArrayOutputStream();
                                    smallThumbnail.compress(Bitmap.CompressFormat.JPEG, 100,s2 );
                                    smallBitmapByteArray=s2.toByteArray();
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(thumbnail!=null) {
//                                                imageView.setImageBitmap(thumbnail);
                                                showProfileUploadedDialog(thumbnail);
                                                Log.i("TAG", "cropimage uri :- "+cropImageUri);
                                            }
                                        }
                                    });
                                }
                            });
                        }else {
                            Snackbar.make(rootView, "Error occured ,try again!", Snackbar.LENGTH_SHORT).show();
                        }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                try {
                    if(result!=null) {
                        Exception error = result.getError();
                        Log.i("TAG", "Error in cropping image :- " + error.getMessage());
                        Toast.makeText(ProfileImageActivity.this, "Error occurs,try again !", Toast.LENGTH_SHORT).show();
                    }
                }catch (NullPointerException e){
                    Log.i("TAG","error occured in getting error,"+e.getMessage());
                }catch (Exception e){
                    Log.i("TAG","error occured in getting error,"+e.getMessage());

                }

            }
        }
    }

    private  Bitmap decodeSelectedImageUri(Uri uri,final int requiredWidth,final int requiredHeight){
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
        }catch (Exception e){
            Snackbar.make(rootView, "Unknown error", Snackbar.LENGTH_SHORT).show();
        }
        return null;
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

    private void uploadThumbnailToRemoteDatabase(byte [] thumbnailByteArray, final byte [] smallThumbnailArray,
                                                 final Bitmap thumbnail, final Bitmap smallThumbnail){

        progressCard.setVisibility(View.VISIBLE);
        final String uid=user.getUid();
        String thumbnailName=uid+Constants.THUMBNAIL;
        final StorageReference thumbnailRef= FirebaseStorage.getInstance().getReference().child(PROFILE_PICTURE)
                .child(thumbnailName);
        uploadTask=thumbnailRef.putBytes(thumbnailByteArray);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploadSmallThumbnailToRemoteDatabase(smallThumbnailArray);
                uploadUrlToRemoteDatabase(thumbnail, smallThumbnail);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                Glide.get(ProfileImageActivity.this).clearDiskCache();
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tickImage.setVisibility(View.VISIBLE);
                progressCard.setVisibility(View.GONE);
                Snackbar.make(rootView, "Error occured !,try again", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadSmallThumbnailToRemoteDatabase(byte[] smallThumbnailArray){
        final String uid=user.getUid();
        String smallThumbnailName=uid+Constants.SMALL_THUMBNAIL;
        final StorageReference smallThumbnailRef= FirebaseStorage.getInstance().getReference().child(Constants.PROFILE_PICTURE)
                .child(smallThumbnailName);
        uploadTask=smallThumbnailRef.putBytes(smallThumbnailArray);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tickImage.setVisibility(View.VISIBLE);
                progressCard.setVisibility(View.GONE);
                Snackbar.make(rootView, "Error occured ! ,try again", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadUrlToRemoteDatabase(final Bitmap thumbnail, final Bitmap smallThumbnail){
        String uid=user.getUid();
        DocumentReference userRef= FirebaseFirestore.getInstance().collection("user").document(uid);

        Map<String,Object> userMap=new HashMap<>();
        String thumbnailPath=PROFILE_PICTURE+"/"+uid+Constants.THUMBNAIL;
        String smallThumbnailPath=PROFILE_PICTURE+"/"+uid+Constants.SMALL_THUMBNAIL;
        userMap.put("profilePicUrlLow",smallThumbnailPath);
        userMap.put("profilePicUrlHigh",thumbnailPath);

        WriteBatch batch=FirebaseFirestore.getInstance().batch();
        batch.set(userRef, userMap, SetOptions.merge());

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               String path= saveProfilePicBitmapToFile(thumbnail, false, true);
               saveProfileImagePathToSharedPreferences(path, false, true);
                path=saveProfilePicBitmapToFile(smallThumbnail, true, false);
                saveProfileImagePathToSharedPreferences(path, true, false);
                progressCard.setVisibility(View.GONE);
                Snackbar.make(rootView, "Profile Picture updated successfully",Snackbar.LENGTH_INDEFINITE ).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tickImage.setVisibility(View.VISIBLE);
                progressCard.setVisibility(View.GONE);
                Snackbar.make(rootView, "Profile Picture uploading failed,try again",Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(connectivityManager!=null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }
        return false;
    }

    private String saveProfilePicBitmapToFile(Bitmap bitmap, boolean lowProfilePic, boolean highProfilePic) {
        File directory = getDir("image", Context.MODE_PRIVATE);
        File path = null;
        if (lowProfilePic) {
            path = new File(directory, "profile_pic_low_resolution");
        } else if (highProfilePic) {
            path = new File(directory, "profile_pic_high_resolution");
        }
        FileOutputStream fileOutputStream = null;
        try {
            if(path!=null)
            fileOutputStream = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(ProfileImageActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        try {
            if(fileOutputStream!=null)
            fileOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(fileOutputStream!=null)
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return directory.getAbsolutePath();
    }

    private void saveProfileImagePathToSharedPreferences(String path, boolean lowProfilePic, boolean highProfilePic) {

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (lowProfilePic)
            editor.putString(Constants.LOW_PROFILE_PIC_PATH, path);
        else if (highProfilePic)
            editor.putString(Constants.HIGH_PROFILE_PIC_PATH, path);
        editor.apply();
    }

}

