package com.droid.solver.askapp.Main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.service.autofill.ImageTransformation;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.Account.AccountFragment;
import com.droid.solver.askapp.Community.CommunityFragment;
import com.droid.solver.askapp.Home.HomeFragment;
import com.droid.solver.askapp.Question.QuestionFragment;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.facebook.appevents.internal.AppEventsLoggerUtility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,ProfilePicSaver {

    private static final String HOME = "home";
    private static final String QUESTION = "ic_question";
    private static final String COMMUNITY = "community";
    private static final String ACCOUNT = "ic_account";
    private static final String NO_INTERNET = "no_internet";
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Toolbar toolbar;
    private CardView toolbarCardView;
    private FirebaseFirestore firestoreRoot;
    private FirebaseUser user;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbarCardView = findViewById(R.id.toolbar_card_view);
        frameLayout = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        firestoreRoot = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
        uid = user.getUid();
        loadFragment(new HomeFragment(), HOME);
        changeToolbarFont(toolbar, this);
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        String lowProfilePicPath = preferences.getString(Constants.LOW_PROFILE_PIC_PATH, null);
        String highProfilePath = preferences.getString(Constants.HIGH_PROFILE_PIC_PATH, null);
        if (lowProfilePicPath == null && highProfilePath == null) {
            getProfilePicUrlFromRemoteDatabase();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment, tempFragment;
        switch (menuItem.getItemId()) {

            case R.id.home:
                tempFragment = getSupportFragmentManager().findFragmentByTag(HOME);
                if (tempFragment != null && tempFragment.isVisible()) {
                    break;
                }
                fragment = new HomeFragment();
                loadFragment(fragment, HOME);
                return true;
            case R.id.question:
                tempFragment = getSupportFragmentManager().findFragmentByTag(QUESTION);
                if (tempFragment != null && tempFragment.isVisible()) {
                    break;
                }
                fragment = new QuestionFragment();
                loadFragment(fragment, QUESTION);
                return true;
            case R.id.community:
                tempFragment = getSupportFragmentManager().findFragmentByTag(COMMUNITY);
                if (tempFragment != null && tempFragment.isVisible()) {
                    break;
                }
                fragment = new CommunityFragment();
                loadFragment(fragment, COMMUNITY);
                return true;
            case R.id.account:
                tempFragment = getSupportFragmentManager().findFragmentByTag(ACCOUNT);
                if (tempFragment != null && tempFragment.isVisible()) {
                    break;
                }
                fragment = new AccountFragment();
                loadFragment(fragment, ACCOUNT);
                return true;

        }
        return true;
    }

    private void loadFragment(Fragment fragment, String tag) {
        if(!isNetworkAvailable()){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new NoInternetFragment(),NO_INTERNET)
                    .addToBackStack(null).commit();

        }else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment, tag);
            transaction.commit();
        }

    }

    @Override
    public void onBackPressed() {
        if(isNetworkAvailable()){
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HOME);
            if (fragment != null && fragment.isVisible()) {
                super.onBackPressed();
            } else {
                loadFragment(new HomeFragment(), HOME);
                bottomNavigationView.setSelectedItemId(R.id.home);
                bottomNavigationView.setSelected(true);
            }
        }
        else {
            Fragment fragment=getSupportFragmentManager().findFragmentByTag(NO_INTERNET);
            if(fragment!=null&& fragment.isVisible()){
                finish();
            }
        }

    }


    public static void changeToolbarFont(Toolbar toolbar, Activity context) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    applyFont(tv, context);
                    break;
                }
            }
        }
    }

    public static void applyFont(TextView tv, Activity context) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "aclonica.ttf"));
    }

    @Override
    public void onResult(Bitmap bitmap, boolean lowProfilePic, boolean highProfilePic) {  //save image to file and load image from file

        if (lowProfilePic) {

            if (bitmap != null) {
                String path = saveProfilePicBitmapToFile(bitmap, lowProfilePic, highProfilePic);
                saveProfileImagePathToSharedPreferences(path, lowProfilePic, highProfilePic);

            }
        }
        if (highProfilePic) {
            if (bitmap != null) {
                String path = saveProfilePicBitmapToFile(bitmap, lowProfilePic, highProfilePic);
                saveProfileImagePathToSharedPreferences(path, lowProfilePic, highProfilePic);

            }
        }
    }

    private void getProfilePicUrlFromRemoteDatabase() {//download image and return bitmap from image
        firestoreRoot.collection("user").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.i("TAG", "realtime listener profile pic failed");
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    String lowProfilePicUrl = documentSnapshot.getString("profilePicUrlLow");
                    String highProfilePicUrl = documentSnapshot.getString("profilePicUrlHigh");
                    String userName = documentSnapshot.getString("userName");
                    SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(Constants.userName, userName);
                    editor.putString(Constants.LOW_IMAGE_URL, lowProfilePicUrl);
                    editor.apply();
                    Log.i("TAG", "user name :- " + userName);
                    if (highProfilePicUrl == null)
                        highProfilePicUrl = lowProfilePicUrl;
                    ImageUrlToByteConvertor lowerAsyncTask = new ImageUrlToByteConvertor(
                            MainActivity.this, true, false);
                    lowerAsyncTask.execute(lowProfilePicUrl);
                    ImageUrlToByteConvertor higherAsyncTask = new ImageUrlToByteConvertor(MainActivity.this,
                            false, true);
                    higherAsyncTask.execute(highProfilePicUrl);
                }
            }
        });
    }

    private String saveProfilePicBitmapToFile(Bitmap bitmap, boolean lowProfilePic, boolean highProfilePic) {
        //save profile pic to file and return image path
        File directory = getDir("image", Context.MODE_PRIVATE);
        File path = null;
        if (lowProfilePic) {
            path = new File(directory, "profile_pic_low_resolution");
        } else if (highProfilePic) {
            path = new File(directory, "profile_pic_high_resolution");
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        try {
            fileOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
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

    private boolean isNetworkAvailable() {
        ConnectivityManager cmm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cmm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//            path=sharedPreferences.getString(Constants.LOW_PROFILE_PIC_PATH, null);
//        if(highProfilePic)
//            path=sharedPreferences.getString(Constants.HIGH_PROFILE_PIC_PATH, null);
//
//        if(path!=null){
//            File file=null;
//            if(lowProfilePic)
//              file=new File(path,"profile_pic_low_resolution");
//            else if(highProfilePic)
//                file=new File(path,"profile_pic_high_resolution");
//            try {
//                Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(file));
//                if(lowProfilePic) {
////                    Log.i("TAG", "loaded bitmap width  low:- " + bitmap.getWidth());
////                    Log.i("TAG", "loaded bitmap height low :- " + bitmap.getHeight());
////                    Log.i("TAG", "loaded bitmap size low :- " + bitmap.getByteCount());
//                } if(highProfilePic) {
////                    Log.i("TAG", "loaded bitmap width high :- " + bitmap.getWidth());
////                    Log.i("TAG", "loaded bitmap height high:- " + bitmap.getHeight());
////                    Log.i("TAG", "loaded bitmap size high:- " + bitmap.getByteCount());
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }else {
//            //load profile pic url from remote database
//            getProfilePicUrlFromRemoteDatabase();
//            Log.i("TAG", "profile pic path is not found in shared preferences ");
//        }
//
//
//    }

//    private boolean isInternetConnectionAvailable(){
//        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
//        return networkInfo!=null&&networkInfo.isConnected();
//    }
//
//
//
//    private void createScaledBitmap(Bitmap bitmap){
//        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//        Bitmap bmp=Bitmap.createScaledBitmap(bitmap,300,300, false);
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte [] compressedBitmapByteArray=byteArrayOutputStream.toByteArray();
//        String encodedString= Base64.encodeToString(compressedBitmapByteArray,Base64.DEFAULT);
//
//    }


    }
}
