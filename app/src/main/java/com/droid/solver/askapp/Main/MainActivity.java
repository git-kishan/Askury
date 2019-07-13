package com.droid.solver.askapp.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
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
import com.bumptech.glide.Glide;
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
import com.droid.solver.askapp.SignInActivity;
import com.facebook.login.LoginManager;
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
    public static ArrayList<String> answerLikeList;
    public static HashMap<String,Integer> imagePollLikeMap;
    public static HashMap<String,Integer> surveyParticipatedMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        fetchUserInfo();
        fetchLikeDocumentsFromRemoteDatabase();
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu_main);
        frameLayout = findViewById(R.id.fragment_container);
        messageText=findViewById(R.id.message_text);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        messageFrameLayout=findViewById(R.id.message_frame_layout);
        progressFrameLayout=findViewById(R.id.progress_frame);

        progressFrameLayout.setVisibility(View.GONE);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment(), HOME);

        changeToolbarFont(toolbar);
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
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Glide.get(MainActivity.this)
                                    .clearDiskCache();
                        }
                    });
                    LoginManager.getInstance().logOut();
                    signInAgain();

                    return true;
                }
                return false;
            }
        });
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
                showNotificationBadge(false);
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

    private void checkNotification(){

        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.i("TAG","uid :- "+uid);
             db.child("user").child(uid).child("notification")
                    .orderByChild("notifiedTime").addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     if(dataSnapshot.getChildrenCount()>0){
                         showNotificationBadge(true);
                     }else{
                         showNotificationBadge(false);
                     }
                 }
                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });


        }

    }

    private void showNotificationBadge(boolean show){

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(2);
        View notificationBadge= LayoutInflater.from(this).inflate(R.layout.notification_badge_view,menuView,false);
        if(show){
            itemView.addView(notificationBadge);
        }else {
            itemView.removeView(notificationBadge);
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
        final Set<String> set = new LinkedHashSet<>();
        String uid=user.getUid();
        Query query=FirebaseFirestore.getInstance().collection("user").document(uid)
                .collection("answerLike");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    if(task.getResult()!=null)
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
                    if(task.getResult()!=null)
                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                        ImagePollLike imagePollLike=snapshot.toObject(ImagePollLike.class);
                        tempList.addAll(imagePollLike.getImagePollMapList());
                    }
                    for(int i=0;i<tempList.size();i++){
                        imagePollLikeMap.putAll(tempList.get(i));
                    }
                    tempList.clear();
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
                    if(task.getResult()!=null)
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
                }else {
                    Log.i("TAG", "task is not successful in fetching survey participated documents");
                }
            }
        });
    }

    private void clearDatabase(){
        LocalDatabase database=new LocalDatabase(getApplicationContext());
        database.clearAllTable();
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
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null) {
                        UserInfoModel infoModel = task.getResult().toObject(UserInfoModel.class);
                        if(infoModel!=null){
                            String userName=infoModel.getUserName();
                            String profilePicUrlLow=infoModel.getProfilePicUrlLow();
                            String profilePicUrlHigh=infoModel.getProfilePicUrlHigh();
                            String bio=infoModel.getBio();
                            int point=infoModel.getPoint();
                            int followerCount=infoModel.getFollowerCount();
                            int followingCount=infoModel.getFollowingCount();
                            String gender=infoModel.getGender();
                            ArrayList<String> interest=infoModel.getInterest();

                            StringBuilder builder=new StringBuilder();
                            StringBuilder bioBuilder=new StringBuilder();
                            if(interest!=null) {
                                for (int i = 0; i < interest.size(); i++) {
                                    builder.append(interest.get(i));
                                    builder.append("@");
                                }
                                if(bio==null){
                                    for(int i=0;i<interest.size();i++){
                                        bioBuilder.append(interest.get(i));
                                        if(i!=interest.size())
                                            bioBuilder.append(" |");

                                    }
                                }
                            }
                            SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME,MODE_PRIVATE );
                            SharedPreferences.Editor editor=preferences.edit();
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
                }
            }
        });
    }

    @Override
    public String passUid() {
        return null;
    }
}
