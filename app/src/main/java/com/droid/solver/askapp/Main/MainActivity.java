package com.droid.solver.askapp.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.FontRequestEmojiCompatConfig;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.provider.FontRequest;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.droid.solver.askapp.Account.AccountFragment;
import com.droid.solver.askapp.Community.CommunityFragment;
import com.droid.solver.askapp.Home.HomeFragment;
import com.droid.solver.askapp.Home.ImagePollOpenFragment;
import com.droid.solver.askapp.Question.AnswerLike;
import com.droid.solver.askapp.Question.ImagePollLike;
import com.droid.solver.askapp.Question.QuestionFragment;
import com.droid.solver.askapp.Question.SurveyParticipated;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,ProfilePicSaver,ImagePollClickListener {

    public static boolean isDataLoadedFromRemoteInQuestionFragment=false;
    private static final String HOME = "home";
    private static final String QUESTION = "ic_question";
    private static final String COMMUNITY = "community";
    private static final String ACCOUNT = "ic_account";
    private static final String NO_INTERNET = "no_internet";
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Toolbar toolbar;
    private FirebaseFirestore firestoreRoot;
    private FirebaseUser user;
    private String uid;
    private ProgressBar progressBar;
    private FrameLayout progressFrameLayout;
    public static ArrayList<String> answerLikeList;
    public static HashMap<String,Integer> imagePollLikeMap;
    public static HashMap<String,Integer> surveyParticipatedMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEmojiFont();
        setContentView(R.layout.activity_main);
        firestoreRoot = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            progressFrameLayout.setVisibility(View.VISIBLE);
            clearDatabase();

        }
        answerLikeList=new ArrayList<>();
        imagePollLikeMap=new HashMap<>();
        surveyParticipatedMap=new HashMap<>();
        uid = user.getUid();
        fetchLikeDocumentsFromRemoteDatabase();
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu_main);
        frameLayout = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        progressBar=findViewById(R.id.progress_bar);
        progressFrameLayout=findViewById(R.id.progress_frame);
        progressFrameLayout.setVisibility(View.GONE);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment(), HOME);
        changeToolbarFont(toolbar, this);
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        String lowProfilePicPath = preferences.getString(Constants.LOW_PROFILE_PIC_PATH, null);
        String highProfilePath = preferences.getString(Constants.HIGH_PROFILE_PIC_PATH, null);
        if (lowProfilePicPath == null && highProfilePath == null) {
            getProfilePicUrlFromRemoteDatabase();
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.log_out){
                    progressFrameLayout.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().signOut();
                    SharedPreferences sharedPreferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    clearDatabase();
                    return true;
                }
                return false;
            }
        });

    }
    private void initEmojiFont(){
        FontRequest fontRequest = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs);
        EmojiCompat.Config config ;
        config=new FontRequestEmojiCompatConfig(this, fontRequest)
                .setReplaceAll(true)
                .setEmojiSpanIndicatorEnabled(false)
                .registerInitCallback(new EmojiCompat.InitCallback() {
                    @Override
                    public void onInitialized() {
                        Log.i("TAG", "emoji initiiazed");
                        super.onInitialized();
                    }

                    @Override
                    public void onFailed(@android.support.annotation.Nullable Throwable throwable) {
                        Log.i("TAG", "emoji initilization failed");
                        super.onFailed(throwable);
                    }
                });
        EmojiCompat.init(config);



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


    public  void changeToolbarFont(Toolbar toolbar, Activity context) {
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

    public  void applyFont(TextView tv, Activity context) {
        tv.setTypeface(ResourcesCompat.getFont(this, R.font.aclonica));
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
    }

    private void fetchLikeDocumentsFromRemoteDatabase(){
        fetchSurveyParticipatedDocuments();
        fetchImagePollLikeDocuments();
        fetchAnswerLikeDocuments();
        Log.i("TAG","Fetch like documents from remote database called");
    }

    private void fetchAnswerLikeDocuments(){
        final Set<String> set = new LinkedHashSet<>();
        String uid=user.getUid();
        Query query=FirebaseFirestore.getInstance().collection("user").document(uid)
                .collection("answerLike");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot snapshots : task.getResult()) {

                        AnswerLike answerLike = snapshots.toObject(AnswerLike.class);
                        if (answerLike.getAnswerLikeId() != null)
                            answerLikeList.addAll(answerLike.getAnswerLikeId());
                    }
                }
                    else {
                        Log.i("TAG", "error in fetching answer like documents ");
                        //task is not successfull
                    }

                if(answerLikeList!=null) {
                    set.addAll(answerLikeList);
                    answerLikeList.clear();
                    answerLikeList.addAll(set);
                    LocalDatabase database = new LocalDatabase(getApplicationContext());
                    database.clearAnswerLikeModel();
                    database.insertAnswerLikeModel(answerLikeList);
                }
            }
        });
    }

    private void fetchImagePollLikeDocuments(){
        final ArrayList<HashMap<String,Integer>> tempList=new ArrayList<>();
        Query query=FirebaseFirestore.getInstance().collection("user").document(uid)
                .collection("imagePollLike");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                        ImagePollLike imagePollLike=snapshot.toObject(ImagePollLike.class);
                        tempList.addAll(imagePollLike.getImagePollMapList());
                    }
                    for(int i=0;i<tempList.size();i++){
//                        Log.i("TAG", "key :object :- "+tempList.get(i));
                        imagePollLikeMap.putAll(tempList.get(i));
                    }
                    tempList.clear();
//                    Log.i("TAG", "new hash map :- "+imagePollLikeMap);
                    LocalDatabase localDatabase=new LocalDatabase(getApplicationContext());
                    localDatabase.clearImagePollLikeModel();
                    localDatabase.insertImagePollLikeModel(imagePollLikeMap);

                }else {
                    Log.i("TAG", "error in fetching image poll like");
                }
            }
        }) ;
    }

    private void fetchSurveyParticipatedDocuments(){
        final ArrayList<HashMap<String,Integer>> tempList=new ArrayList<>();
        Query query=FirebaseFirestore.getInstance().collection("user").document(uid)
                .collection("surveyParticipated");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot snapshots:task.getResult()){
                        SurveyParticipated surveyParticipated=snapshots.toObject(SurveyParticipated.class);
                        tempList.addAll(surveyParticipated.getSurveyMapList());
                    }
                    for(int i=0;i<tempList.size();i++){
                        surveyParticipatedMap.putAll(tempList.get(i));
                    }
                    tempList.clear();
                    LocalDatabase localDatabase=new LocalDatabase(getApplicationContext());
                    localDatabase.clearSurveyParticipatedModel();
                    localDatabase.insertSurveyParticipatedModel(surveyParticipatedMap);
                    Log.i("TAG", "survey participated map :- "+surveyParticipatedMap);
                    Log.i("TAG", "survey participated map size :- "+tempList.size());
                }else {
                    Log.i("TAG", "task is not successful in fetching survey participated documents");
                }
            }
        });
    }

    private void clearDatabase(){
        LocalDatabase database=new LocalDatabase(getApplicationContext());
        database.clearAllTable();
        signInAgain();
    }

    private void signInAgain(){
        progressFrameLayout.setVisibility(View.GONE);
        startActivity(new Intent(this, SignInActivity.class));
        finish();

    }

    @Override
    public void onImagePollImageClicked(String imageUrl,View view) {

        ImagePollOpenFragment fragment=new ImagePollOpenFragment();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.transition));
            fragment.setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.fade));
            fragment.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.transition));
            fragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.fade));
        }

        Bundle bundle=new Bundle();
        bundle.putString("imageUrl", imageUrl);
        bundle.putString("imageTransitionName", view.getTransitionName());
        fragment.setArguments(bundle);
        getSupportFragmentManager().
                beginTransaction().
                addSharedElement(view, view.getTransitionName()).
                add(R.id.fragment_container, fragment,"image poll")
                .addToBackStack(null).commit();

    }


}
