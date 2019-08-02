package com.droid.solver.askapp.Main;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.droid.solver.askapp.Account.AccountFragment;
import com.droid.solver.askapp.Notification.NotificationFragment;
import com.droid.solver.askapp.Home.HomeFragment;
import com.droid.solver.askapp.Home.HomeMessageListener;
import com.droid.solver.askapp.Home.ImagePollOpenFragment;
import com.droid.solver.askapp.Question.AnswerLike;
import com.droid.solver.askapp.Question.ImagePollLike;
import com.droid.solver.askapp.Question.QuestionFragment;
import com.droid.solver.askapp.Question.SurveyParticipated;
import com.droid.solver.askapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,ImagePollClickListener , HomeMessageListener,UidPasserListener{

    public static boolean isFirstTimeQuestionLoaded=true;
    public static boolean isFirstTimeHomeLoaded=true;
    public static DocumentSnapshot homeLastQuestionDocumentSnapshot=null;
    public static DocumentSnapshot homeLastImagePollDocumentSnapshot=null;
    public static DocumentSnapshot homeLastSurveyDocumentSnapshot=null;
    public static DocumentSnapshot questionLastDocumentSnapshot=null;
    public static ArrayList<String> answerLikeList;
    private static final String HOME = "home";
    private static final String QUESTION = "ic_question";
    private static final String NOTIFICATION = "notification";
    private static final String ACCOUNT = "ic_account";
    private static final String NO_INTERNET = "no_internet";
    public BottomNavigationView bottomNavigationView;
    private FrameLayout messageFrameLayout;
    public FrameLayout frameLayout;
    private TextView messageText;
    public Toolbar toolbar;
    private FirebaseFirestore firestoreRoot;
    private FirebaseUser user;
    private String uid;
    private FrameLayout progressFrameLayout;
    private BottomNavigationItemView itemView;
    private View notificationBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, getString(R.string.final_admob_app_id));
        AudienceNetworkAds.initialize(this);
        firestoreRoot = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "User not recognized", Toast.LENGTH_LONG).show();
            progressFrameLayout.setVisibility(View.VISIBLE);
            clearDatabase();
        }
        uid = user.getUid();
        fetchUserInfo();
        fetchLikeDocumentsFromRemoteDatabase();
        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.fragment_container);
        messageText=findViewById(R.id.message_text);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        messageFrameLayout=findViewById(R.id.message_frame_layout);
        progressFrameLayout=findViewById(R.id.progress_frame);
        progressFrameLayout.setVisibility(View.GONE);
        loadFragment(new HomeFragment(), HOME);
        changeToolbarFont(toolbar);
        answerLikeList=new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        String lowProfilePicPath = preferences.getString(Constants.LOW_PROFILE_PIC_PATH, null);
        String highProfilePath = preferences.getString(Constants.HIGH_PROFILE_PIC_PATH, null);
        if (lowProfilePicPath == null && highProfilePath == null) {
            getProfilePicUrlFromRemoteDatabase();
        }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
                itemView = (BottomNavigationItemView) menuView.getChildAt(2);
                notificationBadge= LayoutInflater.from(MainActivity.this).inflate(R.layout.notification_badge_view,menuView,false);
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        checkNotification();
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
            case R.id.notification:
                tempFragment = getSupportFragmentManager().findFragmentByTag(NOTIFICATION);
                if (tempFragment != null && tempFragment.isVisible()) {
                    break;
                }
                fragment = new NotificationFragment();
                itemView.removeView(notificationBadge);
                loadFragment(fragment, NOTIFICATION);
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
        Log.i("TAG", "onBackPressed()");
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HOME);
            if (fragment != null && fragment.isVisible()) {
                super.onBackPressed();
            } else {
                bottomNavigationView.setSelectedItemId(R.id.home);
            }
    }

    private void checkNotification(){

        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.i("TAG","uid :- "+uid);
             db.child("user").child(uid).child("notification")
                    .orderByChild("notifiedTime").addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     try{
                     if(dataSnapshot.getChildrenCount()>0){
                         if(itemView.getParent()!=null){
                             itemView.addView(notificationBadge);
                         }
                     }else{
                         if(itemView.getParent()!=null){
                             itemView.removeView(notificationBadge);
                         }
                     }

                     }catch (IllegalStateException e){
                         Log.i("TAG", "illegal state exception in main page");
                     }catch (NullPointerException e){
                         Log.i("TAG", "NullPointerexception in main page");
                     }catch (Exception e){
                         Log.i("TAG", "Exception in main page");
                     }
                 }
                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });
        }
    }

    public  void changeToolbarFont(Toolbar toolbar) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    applyFont(tv);
                    break;
                }
            }
        }
    }

    public  void applyFont(TextView tv) {
        tv.setTypeface(ResourcesCompat.getFont(this, R.font.aclonica));
    }

    private void getProfilePicUrlFromRemoteDatabase() {//download image and return bitmap from image
        firestoreRoot.collection("user").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException e) {
                try {
                    if (e != null) {
                        Log.i("TAG", "realtime listener profile pic failed");
                        return;
                    }
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        String lowProfilePicUrl = documentSnapshot.getString("profilePicUrlLow");
                        String userName = documentSnapshot.getString("userName");
                        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(Constants.userName, userName);
                        editor.putString(Constants.LOW_IMAGE_URL, lowProfilePicUrl);
                        editor.apply();
                    }
                }catch (NullPointerException j){
                    Log.i("TAG","NullPointerException occurs in getting profile ,"+j.getMessage());
                }catch (Exception j){
                    Log.i("TAG","Exception occurs in getting profile ,"+j.getMessage());
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cmm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(cmm!=null) {
            NetworkInfo activeNetworkInfo = cmm.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private void fetchLikeDocumentsFromRemoteDatabase(){
        fetchSurveyParticipatedDocuments();
        fetchImagePollLikeDocuments();
        fetchAnswerLikeDocuments();
    }

    private void fetchAnswerLikeDocuments(){
        try {
            final Set<String> set = new LinkedHashSet<>();
            String uid = user.getUid();
            Query query = FirebaseFirestore.getInstance().collection("user").document(uid)
                    .collection("answerLike");
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {

                            if (task.isSuccessful()) {
                                if (task.getResult() != null)
                                    for (QueryDocumentSnapshot snapshots : task.getResult()) {

                                        AnswerLike answerLike = snapshots.toObject(AnswerLike.class);
                                        if(answerLike.getAnswerLikeId()!=null)
                                           answerLikeList = answerLike.getAnswerLikeId();
                                    }
                                if (answerLikeList != null && answerLikeList.size() > 0) {
                                    set.addAll(answerLikeList);
                                    answerLikeList.clear();
                                    answerLikeList.addAll(set);
                                    LocalDatabase database = new LocalDatabase(getApplicationContext());
                                    database.clearAnswerLikeModel();
                                    database.insertAnswerLikeModel(answerLikeList);
                                    set.clear();
                                }
                            } else {
                                Log.i("TAG", "error in fetching answer like documents ");
                            }
                        }
                    });
                }
            });
        }catch(NullPointerException e){
            Log.i("TAG", "NullException occurs in fetching answer like list "+e.getMessage());
        }catch (Exception e){
            Log.i("TAG", "Exception occurs in fetching answer like list "+e.getMessage());
        }
    }

    private void fetchImagePollLikeDocuments(){
        try {
            final HashMap<String, Integer> imagePollLikeMap = new HashMap<>();
            final ArrayList<HashMap<String, Integer>> tempList = new ArrayList<>();
            Query query = FirebaseFirestore.getInstance().collection("user").document(uid)
                    .collection("imagePollLike");
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (task.getResult() != null)
                                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                            ImagePollLike imagePollLike = snapshot.toObject(ImagePollLike.class);
                                            if(imagePollLike.getImagePollMapList()!=null)
                                               tempList.addAll(imagePollLike.getImagePollMapList());
                                        }
                                    for (int i = 0; i < tempList.size(); i++) {
                                        imagePollLikeMap.putAll(tempList.get(i));
                                    }
                                    tempList.clear();
                                    LocalDatabase localDatabase = new LocalDatabase(getApplicationContext());
                                    localDatabase.clearImagePollLikeModel();
                                    if(imagePollLikeMap.size()>0) {
                                        localDatabase.insertImagePollLikeModel(imagePollLikeMap);
                                        imagePollLikeMap.clear();
                                    }
                                } catch (NullPointerException e) {
                                    Log.i("TAG", "NullpointerException occurs in retrieving image poll like map ," + e.getMessage());
                                }
                            }
                        });


                    } else {
                        Log.i("TAG", "error in fetching image poll like");
                    }
                }
            });
        }catch(NullPointerException e){
            Log.i("TAG", "NullpointerException occurs in retrieving image poll like map ," + e.getMessage());

        }catch(Exception e){
            Log.i("TAG", "NullpointerException occurs in retrieving image poll like map ," + e.getMessage());

        }
    }

    private void fetchSurveyParticipatedDocuments(){
        try {
            final HashMap<String, Integer> surveyParticipatedMap = new HashMap<>();
            final ArrayList<HashMap<String, Integer>> tempList = new ArrayList<>();
            Query query = FirebaseFirestore.getInstance().collection("user").document(uid)
                    .collection("surveyParticipated");
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                if (task.getResult() != null)
                                    for (QueryDocumentSnapshot snapshots : task.getResult()) {
                                        SurveyParticipated surveyParticipated = snapshots.toObject(SurveyParticipated.class);
                                           if(surveyParticipated.getSurveyMapList()!=null)
                                              tempList.addAll(surveyParticipated.getSurveyMapList());
                                    }
                                for (int i = 0; i < tempList.size(); i++) {
                                    surveyParticipatedMap.putAll(tempList.get(i));
                                }
                                tempList.clear();
                                LocalDatabase localDatabase = new LocalDatabase(getApplicationContext());
                                localDatabase.clearSurveyParticipatedModel();
                                if(surveyParticipatedMap.size()>0) {
                                    localDatabase.insertSurveyParticipatedModel(surveyParticipatedMap);
                                    surveyParticipatedMap.clear();
                                }
                            }
                        });

                    } else {
                        Log.i("TAG", "task is not successful in fetching survey participated documents");
                    }
                }
            });
        }catch (NullPointerException e){
            Log.i("TAG","NullPointerException occurs in fetching survey participated list");
        }catch (Exception e){
            Log.i("TAG", "Exception occurs in fetching survey participated list");
        }
    }

    private void clearDatabase(){
        LocalDatabase database=new LocalDatabase(getApplicationContext());
        database.clearAllTable();

    }

//    private void signInAgain(){
//        progressFrameLayout.setVisibility(View.GONE);
//        startActivity(new Intent(this, SignInActivity.class));
//        finish();
//
//    }

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

    @Override
    public void onSomeMessage(final String message) {
        messageFrameLayout.animate().alpha(0.0f).setDuration(0).start();
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                messageText.setText(message);
                messageFrameLayout.animate().alpha(1.0f).setDuration(500).start();
                messageFrameLayout.setVisibility(View.VISIBLE);
                Handler handler1=new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        messageFrameLayout.animate().alpha(0.0f).setDuration(500).start();
                    }
                }, 2500);
            }
        }, 100);


    }

    private void fetchUserInfo(){
        FirebaseFirestore.getInstance().collection("user")
                .document(uid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                try {
                    if (task.isSuccessful()) {
                        try {
                            if (task.getResult() != null) {
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        final UserInfoModel infoModel = task.getResult().toObject(UserInfoModel.class);
                                        if (infoModel != null) {
                                            String userId = infoModel.getUserId();
                                            String userName = infoModel.getUserName();
                                            String profilePicUrlLow = infoModel.getProfilePicUrlLow();
                                            String profilePicUrlHigh = infoModel.getProfilePicUrlHigh();
                                            String bio = infoModel.getBio();
                                            int point = infoModel.getPoint();
                                            int followerCount = infoModel.getFollowerCount();
                                            int followingCount = infoModel.getFollowingCount();

                                            String gender = infoModel.getGender();
                                            ArrayList<String> interest = infoModel.getInterest();

                                            StringBuilder builder = new StringBuilder();
                                            StringBuilder bioBuilder = new StringBuilder();
                                            if (interest != null) {
                                                for (int i = 0; i < interest.size(); i++) {
                                                    builder.append(interest.get(i));
                                                    builder.append("@");
                                                }
                                                if (bio == null) {
                                                    for (int i = 0; i < interest.size(); i++) {
                                                        bioBuilder.append(interest.get(i));
                                                        if (i != interest.size())
                                                            bioBuilder.append(" |");

                                                    }
                                                }
                                            }
                                            SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString(Constants.userId, userId);
                                            editor.putString(Constants.userName, userName);
                                            editor.putString(Constants.profilePicLowUrl, profilePicUrlLow);
                                            editor.putString(Constants.profilePicHighUrl, profilePicUrlHigh);
                                            editor.putInt(Constants.point, point);
                                            editor.putInt(Constants.followerCount, followerCount);
                                            editor.putInt(Constants.followingCount, followingCount);
                                            editor.putString(Constants.GENDER, gender);
                                            editor.putString(Constants.bio, bioBuilder.toString());
                                            editor.putString(Constants.INTEREST, builder.toString());
                                            editor.apply();
                                        }
                                    }
                                });


                            }
                        } catch (NullPointerException e) {
                            Log.i("TAG", "NullPointerException occurs in fetching user info mainActivity");
                        } catch (Exception e) {
                            Log.i("TAG", "Exception occurs in fetching user info mainActivity");

                        }
                    }
                }catch (NullPointerException e){
                    Log.i("TAG", "NullPointerException occurs in fetcing user info");
                }catch (Exception e){
                    Log.i("TAG", "Exception occurs in fetcing user info");
                }
            }
        });
    }

    @Override
    public String passUid() {
        return null;
    }

}
