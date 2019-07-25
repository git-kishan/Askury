package com.droid.solver.askapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.Main.MainActivity;
import com.droid.solver.askapp.Main.UserInfoModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    CallbackManager callbackManager;
    LoginButton loginButton;
    private FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN=313;
    private ConstraintLayout rootLayout;
    private CardView progressCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        boolean genderSelection=sharedPreferences.getBoolean(Constants.GENDER_SELECTION, false);
        boolean interestSelection=sharedPreferences.getBoolean(Constants.INTEREST_SELECTION, false);
        if(genderSelection && interestSelection) {
            setContentView(R.layout.starting_name);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
           final  TextView name=findViewById(R.id.textView33);
            final Animation animation=AnimationUtils.loadAnimation(this, R.anim.askury_scale_out);
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    name.startAnimation(animation);
                }
            }, 200);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SignInActivity.this,MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    finish();

                }
            }, 700);

        }
        else {
            setContentView(R.layout.activity_sign_in);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            MaterialButton googleButton=findViewById(R.id.google_button);
            MaterialButton facebookButton=findViewById(R.id.facebook_button);
            rootLayout=findViewById(R.id.root_layout);
            progressCard=findViewById(R.id.progress_card);
            callbackManager = CallbackManager.Factory.create();
            loginButton = findViewById(R.id.login_button);
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            auth=FirebaseAuth.getInstance();
            FirebaseUser user=auth.getCurrentUser();
            if(user!=null){
                if(!genderSelection) {
                    startActivity(new Intent(SignInActivity.this, GenderSelectionActivity.class));
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    finish();
                }
                else {
                    startActivity(new Intent(SignInActivity.this, InterestActivity.class));
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    finish();
                }


            }
            callbackRegistration();
            facebookButton.setOnClickListener(this);
            googleButton.setOnClickListener(this);
        }

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.google_button:
                if(isNetworkAvailable()){
                    signInWithGoogle();
                    progressCard.setVisibility(View.VISIBLE);
                }else {
                    Snackbar.make(rootLayout, "No network available ", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.facebook_button:
                if(isNetworkAvailable()){
                    loginButton.performClick();
                    progressCard.setVisibility(View.VISIBLE);
                }else {
                    Snackbar.make(rootLayout, "No network available", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account!=null)
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Snackbar.make(rootLayout, "Error occured in sign in,try again", Snackbar.LENGTH_LONG).show();
                Log.i("TAG","Api exception occurs in sign in page" );
                progressCard.setVisibility(View.GONE);
            }catch (Exception e){
                Snackbar.make(rootLayout, "Error occured in sign in,try again", Snackbar.LENGTH_LONG).show();
                Log.i("TAG","Api exception occurs in sign in page" );
                progressCard.setVisibility(View.GONE);
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseFirestore db=FirebaseFirestore.getInstance();
                            Map<String,Object> userDetail = new HashMap<>();
                            FirebaseUser user = auth.getCurrentUser();
                            if(user!=null) {
                                String userName = user.getDisplayName();
                                final String userId = user.getUid();
                                if(userName!=null)
                                userDetail.put("userName", userName);
                                userDetail.put("userId", userId);
                                userDetail.put("point", 0);
                                db.collection("user").document(userId).set(userDetail, SetOptions.merge())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressCard.setVisibility(View.GONE);
                                                Log.i("TAG", "Sign in successfully through facebook");
                                                Snackbar.make(rootLayout, "Please wait ...", Snackbar.LENGTH_LONG).show();
                                                checkFirstTimeUser(userId);

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(rootLayout, "Error occured in sign in", Snackbar.LENGTH_SHORT).show();
                                        Log.i("TAG", "Error " + e.getLocalizedMessage());
                                    }
                                });
                            }

                        } else {
                            progressCard.setVisibility(View.GONE);
                            Snackbar.make(rootLayout, "Sign in failed,try anoter methood", Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void callbackRegistration(){
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                progressCard.setVisibility(View.GONE);
                Snackbar.make(rootLayout, "Sign in failed through facebook,try another method", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                progressCard.setVisibility(View.GONE);
                Snackbar.make(rootLayout, "Sign in failed through facebook,try another method", Snackbar.LENGTH_LONG).show();
                Log.i("TAG", "Error occured in facebook sign in :- "+error.toString());
            }
        });
    }

    private void signInWithGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            FirebaseFirestore db=FirebaseFirestore.getInstance();
                            Map<String,Object> userDetail = new HashMap<>();
                            if(user!=null) {
                                String userName = user.getDisplayName();
                                final String userId = user.getUid();
                                if(userName!=null)
                                userDetail.put("userName", userName);
                                userDetail.put("userId", userId);
                                userDetail.put("point", 0);
                                db.collection("user").document(userId).set(userDetail, SetOptions.merge())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                checkFirstTimeUser(userId);
                                                progressCard.setVisibility(View.GONE);
                                                Snackbar.make(rootLayout, "Please wait ...", Snackbar.LENGTH_LONG).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(rootLayout, "Error occured in sign in", Snackbar.LENGTH_SHORT).show();
                                        Log.i("TAG", "Error " + e.getLocalizedMessage());
                                        progressCard.setVisibility(View.GONE);
                                    }
                                });

                            }
                        } else {
                            progressCard.setVisibility(View.GONE);
                            Snackbar.make(rootLayout, "Sign in failed,try another method",Snackbar.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void initializeLikeList(String uid){
        FirebaseFirestore rootRef=FirebaseFirestore.getInstance();
        DocumentReference userLikeRef=rootRef.collection("user").document(uid).collection("answerLike")
                .document("like");

        ArrayList<String> answerLikeId=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("answerLikeId", answerLikeId);

        userLikeRef.set(map, SetOptions.merge()).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressCard.setVisibility(View.GONE);
                startActivity(new Intent(SignInActivity.this, GenderSelectionActivity.class));
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                finish();
                Log.i("TAG", "array list created in remote database");
            }
        });
    }

    private void initiliazeImagePollLikeList(final String uid){
        FirebaseFirestore rootRef=FirebaseFirestore.getInstance();
        DocumentReference userLikeRef=rootRef.collection("user").document(uid).collection("imagePollLike")
                .document("like");

        ArrayList<String> imagePollMapList=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("imagePollMapList", imagePollMapList);

        userLikeRef.set(map, SetOptions.merge()).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                initiliazeSurveyParticipatedList(uid);
                Log.i("TAG", "image poll like  list created in remote database");
            }
        });
    }

    private void initiliazeSurveyParticipatedList(final String uid){
        FirebaseFirestore rootRef=FirebaseFirestore.getInstance();
        DocumentReference userLikeRef=rootRef.collection("user").document(uid).collection("surveyParticipated")
                .document("participated");

        ArrayList<String> surveyMapList=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("surveyMapList", surveyMapList);

        userLikeRef.set(map, SetOptions.merge()).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                initializeLikeList(uid);
                Log.i("TAG", "survye  participated list created in remote database");
            }
        });
    }

    private void checkFirstTimeUser(final String uid){
        FirebaseFirestore.getInstance().collection("user").document(uid).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null){
                        UserInfoModel userInfoModel = task.getResult().toObject(UserInfoModel.class);
                    SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    if(userInfoModel!=null)
                    editor.putString(Constants.userName, userInfoModel.getUserName());


                    if (userInfoModel!=null&&userInfoModel.getInterest() == null) {
                        editor.putBoolean(Constants.INTEREST_SELECTION, false);
                        editor.putString(Constants.INTEREST, null);
                    } else {
                        if(userInfoModel!=null) {
                            ArrayList<String> interestList = userInfoModel.getInterest();
                            StringBuilder builder = new StringBuilder();
                            if (interestList != null) {
                                for (int i = 0; i < interestList.size(); i++) {
                                    builder.append(interestList.get(i));
                                    builder.append("@");
                                }
                                editor.putString(Constants.INTEREST, builder.toString());
                                editor.putBoolean(Constants.INTEREST_SELECTION, true);
                            }
                        }
                    }
                    if (userInfoModel!=null&&userInfoModel.getGender() == null) {
                        editor.putBoolean(Constants.GENDER_SELECTION, false);
                        editor.putString(Constants.GENDER, null);
                    } else {
                        if(userInfoModel!=null) {
                            editor.putString(Constants.GENDER, userInfoModel.getGender());
                            editor.putBoolean(Constants.GENDER_SELECTION, true);
                        }
                    }
                    editor.apply();
                    if (userInfoModel!=null&&userInfoModel.getFirstTimeUser() == 0) {//set firstTimeUser to 1 ,0 means first time user,1 means not first time user
                        Map<String, Object> map = new HashMap<>();
                        map.put("firstTimeUser", 1);
                        FirebaseFirestore.getInstance().collection("user").document(uid).set(map, SetOptions.merge());
                        initiliazeImagePollLikeList(uid);
                    } else {
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                        finish();
                    }
                }
            }
                else {
                    Toast.makeText(SignInActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(manager!=null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }return false;
    }

}
