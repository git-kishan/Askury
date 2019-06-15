package com.droid.solver.askapp.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Main.ImagePollClickListener;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImagePollViewHolder extends RecyclerView.ViewHolder {
    EmojiTextView profileName, bio, question;
    CircleImageView profileImageView;
    TextView timeAgo, text1, text2;
    RelativeLayout view1, view2;
    ConstraintLayout constraintLayout;
    ImageView image1, image2, leftWhiteHeart, rightWhiteHeart, leftRedHeart, rightRedHeart, threeDot;
    private FirebaseUser user;
    private FirebaseFirestore firestoreRootRef;
    private ImagePollClickListener listener;
    public ImagePollViewHolder(@NonNull View itemView) {
        super(itemView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        firestoreRootRef = FirebaseFirestore.getInstance();
        profileImageView = itemView.findViewById(R.id.profile_image);
        profileName = itemView.findViewById(R.id.asker_name);
        bio = itemView.findViewById(R.id.about_textview);
        question = itemView.findViewById(R.id.question_textview);
        timeAgo = itemView.findViewById(R.id.time_ago_textview);
        threeDot = itemView.findViewById(R.id.imageView4);
        rightRedHeart = itemView.findViewById(R.id.imageView11);
        leftRedHeart = itemView.findViewById(R.id.imageView12);
        rightWhiteHeart = itemView.findViewById(R.id.imageView9);
        leftWhiteHeart = itemView.findViewById(R.id.imageView7);
        text1 = itemView.findViewById(R.id.text1);
        text2 = itemView.findViewById(R.id.text2);
        view1 = itemView.findViewById(R.id.view1);
        view2 = itemView.findViewById(R.id.view2);
        image1 = itemView.findViewById(R.id.imageView5);
        image2 = itemView.findViewById(R.id.imageView6);
        constraintLayout = itemView.findViewById(R.id.constraintLayout8);
        constraintLayout.setVisibility(View.GONE);
        leftRedHeart.setVisibility(View.GONE);
        rightRedHeart.setVisibility(View.GONE);
        leftWhiteHeart.setVisibility(View.GONE);
        rightWhiteHeart.setVisibility(View.GONE);
    }

    void onImage1SingleClicked(final Context context, String imageUrl) {

        listener= (ImagePollClickListener) context;
        listener.onImagePollImageClicked(imageUrl,image1);

    }

    void onImage2SingleClicked(final Context context, String imageUrl) {

        listener= (ImagePollClickListener) context;
        listener.onImagePollImageClicked(imageUrl, image2);

    }

    void onImage1LongClicked(final Context context, final AskImagePollModel imagePollModel) {

        DocumentReference rootImagePollRef = firestoreRootRef.collection("imagePoll").document(imagePollModel.getImagePollId());
        DocumentReference askerImagePollRef = firestoreRootRef.collection("user").document(imagePollModel.getAskerId())
                .collection("imagePoll").document(imagePollModel.getImagePollId());
        DocumentReference userImagePollLikeRef = firestoreRootRef.collection("user").document(user.getUid()).
                collection("imagePollLike").document("like");

        Map<String, Object> rootImagePollMap = new HashMap<>();
        rootImagePollMap.put("image1LikeNo", FieldValue.increment(1));

        Map<String,Object> imagePollLikeMap=new HashMap<>();
        imagePollLikeMap.put(imagePollModel.getImagePollId(), 1);

        Map<String, Object> userImagePollLikeMap = new HashMap<>();
        userImagePollLikeMap.put("imagePollMapList", FieldValue.arrayUnion(imagePollLikeMap));

        WriteBatch batch = firestoreRootRef.batch();
        batch.update(rootImagePollRef, rootImagePollMap);
        batch.update(askerImagePollRef, rootImagePollMap);
        batch.set(userImagePollLikeRef, userImagePollLikeMap, SetOptions.merge());

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    LocalDatabase localDatabase=new LocalDatabase(context.getApplicationContext());
                    HashMap<String,Integer> map=new HashMap<>();
                    map.put(imagePollModel.getImagePollId(), 1);
                    localDatabase.insertImagePollLikeModel(map);
                    Log.i("TAG", "image poll batch write successfull");
                }else {
                    Log.i("TAG", "image poll batch write fails");
                }
            }
        });

        Log.i("TAG", "image 1 like no :- "+imagePollModel.getImage1LikeNo());
        Log.i("TAG", "image 2 like no - "+imagePollModel.getImage2LikeNo());
        constraintLayout.setVisibility(View.GONE);
        leftRedHeart.setVisibility(View.GONE);
        rightRedHeart.setVisibility(View.GONE);
        leftWhiteHeart.setVisibility(View.GONE);
        rightWhiteHeart.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.heart_bouncing_animation_scalein);
        Animation animation2 = AnimationUtils.loadAnimation(context, R.anim.heart_bouncing_animation_scaleout);
        final Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.heart_fade_out);
        leftWhiteHeart.startAnimation(animation);
        constraintLayout.animate().translationY(40f).setDuration(0).start();
        leftWhiteHeart.setVisibility(View.VISIBLE);
        final int totalLikeOfBothImages = imagePollModel.getImage1LikeNo() + imagePollModel.getImage2LikeNo() + 1;//till now in addition to current user

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                leftWhiteHeart.startAnimation(fadeOut);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        leftWhiteHeart.setVisibility(View.GONE);
                        constraintLayout.setVisibility(View.VISIBLE);
                        constraintLayout.animate().translationY(0f).setDuration(300).start();
                        Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int firstPercentage =((imagePollModel.getImage1LikeNo()+1)*100/totalLikeOfBothImages);//white view1
                                int secondPercentage = 100 - firstPercentage;//black white
                                double view1Width = (double) (constraintLayout.getWidth()) * firstPercentage / 100;
                                ViewGroup.LayoutParams layoutParams = view1.getLayoutParams();
                                layoutParams.width = (int) view1Width;
                                if(firstPercentage==100) {
                                    view2.setVisibility(View.GONE);
                                    Log.i("TAG", "first percentage match parent");
                                }
                                view1.setLayoutParams(layoutParams);
                                ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(view2.getWidth(),
                                        view2.getHeight());
                                ((RelativeLayout.LayoutParams) params).setMarginStart((int) view1Width);
                                text2.setLayoutParams(params);
                                String mfirstPercentage = String.format(context.getString(R.string.first_percentage), firstPercentage);
                                String msecondPercentage = String.format(context.getString(R.string.second_percentage), secondPercentage);

                                if (firstPercentage < 20)
                                    text1.setVisibility(View.GONE);
                                if (secondPercentage < 20)
                                    text2.setVisibility(View.GONE);

                                text1.setText(mfirstPercentage + "%");
                                text2.setText(msecondPercentage + "%");
                                Animation redHeartFadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.red_heart_fade_in);
                                leftRedHeart.setVisibility(View.VISIBLE);
                                leftRedHeart.startAnimation(redHeartFadeInAnimation);
                            }
                        }, 0);

                    }
                }, 300);
            }
        }, 1000);

        image1.setLongClickable(false);
        image2.setLongClickable(false);


    }

    void onImage2LongClicked(final Context context, final AskImagePollModel imagePollModel) {

        DocumentReference rootImagePollRef = firestoreRootRef.collection("imagePoll").document(imagePollModel.getImagePollId());
        DocumentReference askerImagePollRef = firestoreRootRef.collection("user").document(imagePollModel.getAskerId())
                .collection("imagePoll").document(imagePollModel.getImagePollId());
        DocumentReference userImagePollLikeRef = firestoreRootRef.collection("user").document(user.getUid()).
                collection("imagePollLike").document("like");

        Map<String, Object> rootImagePollMap = new HashMap<>();
        rootImagePollMap.put("image2LikeNo", FieldValue.increment(1));

        Map<String,Object> imagePollLikeMap=new HashMap<>();
        imagePollLikeMap.put(imagePollModel.getImagePollId(), 2);

        Map<String, Object> userImagePollLikeMap = new HashMap<>();
        userImagePollLikeMap.put("imagePollMapList", FieldValue.arrayUnion(imagePollLikeMap));

        WriteBatch batch = firestoreRootRef.batch();
        batch.update(rootImagePollRef, rootImagePollMap);
        batch.update(askerImagePollRef, rootImagePollMap);
        batch.update(userImagePollLikeRef, userImagePollLikeMap);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    LocalDatabase localDatabase=new LocalDatabase(context.getApplicationContext());
                    HashMap<String,Integer> map=new HashMap<>();
                    map.put(imagePollModel.getImagePollId(), 2);
                    localDatabase.insertImagePollLikeModel(map);
                    Log.i("TAG", "batch operation of image poll successfull");
                }else {
                    Log.i("TAG", "batch operation of image poll fails");
                }
            }
        });

//        Log.i("TAG", "image 1 like no :- "+imagePollModel.getImage1LikeNo());
//        Log.i("TAG", "image 2 like no - "+imagePollModel.getImage2LikeNo());

        constraintLayout.setVisibility(View.GONE);
        leftRedHeart.setVisibility(View.GONE);
        rightRedHeart.setVisibility(View.GONE);
        leftWhiteHeart.setVisibility(View.GONE);
        rightWhiteHeart.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.heart_bouncing_animation_scalein);
        Animation animation2 = AnimationUtils.loadAnimation(context, R.anim.heart_bouncing_animation_scaleout);
        final Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.heart_fade_out);
        rightWhiteHeart.startAnimation(animation);
        constraintLayout.animate().translationY(40f).setDuration(0).start();
        rightWhiteHeart.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        final int totalLikeOfBothImages = imagePollModel.getImage1LikeNo() + imagePollModel.getImage2LikeNo() + 1;//till now in addition to current user
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rightWhiteHeart.startAnimation(fadeOut);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rightWhiteHeart.setVisibility(View.GONE);
                        constraintLayout.setVisibility(View.VISIBLE);
                        constraintLayout.animate().translationY(0f).setDuration(400).start();
                        Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int firstPercentage = ((imagePollModel.getImage1LikeNo()*100)/totalLikeOfBothImages);//white view1
                                int secondPercentage = 100 - firstPercentage;//black white
//                                Log.i("TAG", "first percentage :- " + firstPercentage);
//                                Log.i("TAG", "second percentage :- " + secondPercentage);
                                double view1Width = (double) (constraintLayout.getWidth()) * firstPercentage / 100;
//                                Log.i("TAG", "constraint layout width :- " + view1Width);
                                ViewGroup.LayoutParams layoutParams = view1.getLayoutParams();
                                layoutParams.width = (int) view1Width;
                                if(firstPercentage==100) {
                                    view2.setVisibility(View.GONE);
                                    Log.i("TAG", "first percentage match parent");
                                }
                                view1.setLayoutParams(layoutParams);
                                ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(view2.getWidth(),
                                        view2.getHeight());
                                ((RelativeLayout.LayoutParams) params).setMarginStart((int) view1Width);
                                text2.setLayoutParams(params);
                                if (firstPercentage < 20)
                                    text1.setVisibility(View.GONE);
                                if (secondPercentage < 20)
                                    text2.setVisibility(View.GONE);
                                String mfirstPercentage = String.format(context.getString(R.string.first_percentage), firstPercentage);
                                String msecondPercentage = String.format(context.getString(R.string.second_percentage), secondPercentage);
                                text1.setText(mfirstPercentage + "%");
                                text2.setText(msecondPercentage + "%");
                                Animation redHeartFadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.red_heart_fade_in);
                                rightRedHeart.setVisibility(View.VISIBLE);
                                rightRedHeart.startAnimation(redHeartFadeInAnimation);
                            }
                        }, 0);

                    }
                }, 500);
            }
        }, 1000);

        image1.setLongClickable(false);
        image2.setLongClickable(false);

    }

    void onThreeDotClicked(Context context) {

        View dialogView = LayoutInflater.from(context).inflate(R.layout.image_poll_overflow_dialog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.customAnimations_successfull;
        alertDialog.show();
    }


    void showLike(final Context context, final int image1LikeNo, int image2LikeNo, final int choice) {

        if(choice!=0) {
            final int totalLikeOfBothImages = image1LikeNo + image2LikeNo;//till now in addition to current user
            leftWhiteHeart.setVisibility(View.GONE);
            rightWhiteHeart.setVisibility(View.GONE);
            image1.setLongClickable(false);
            image2.setLongClickable(false);
            constraintLayout.setVisibility(View.VISIBLE);
            constraintLayout.animate().translationY(0f).setDuration(50).start();
            Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int firstPercentage = image1LikeNo * 100 / (totalLikeOfBothImages);//white view1
                    int secondPercentage = 100 - firstPercentage;//black white
                    double view1Width = (double) (constraintLayout.getWidth()) * firstPercentage / 100;
                    ViewGroup.LayoutParams layoutParams = view1.getLayoutParams();
                    layoutParams.width = (int) view1Width;
                    if (firstPercentage == 100) {
                        view2.setVisibility(View.GONE);
                        Log.i("TAG", "first percentage match parent");
                    }
                    view1.setLayoutParams(layoutParams);
                    ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(view2.getWidth(),
                            view2.getHeight());
                    ((RelativeLayout.LayoutParams) params).setMarginStart((int) view1Width);
                    text2.setLayoutParams(params);
                    if (firstPercentage < 20)
                        text1.setVisibility(View.GONE);
                    if (secondPercentage < 20)
                        text2.setVisibility(View.GONE);
                    String mfirstPercentage = String.format(context.getString(R.string.first_percentage), firstPercentage);
                    String msecondPercentage = String.format(context.getString(R.string.second_percentage), secondPercentage);
                    text1.setText(mfirstPercentage + "%");
                    text2.setText(msecondPercentage + "%");
                    if (choice == 1) {
                        leftRedHeart.setVisibility(View.VISIBLE);

                    } else if (choice == 2) {
                        rightRedHeart.setVisibility(View.VISIBLE);
                    }
                    image1.setLongClickable(false);
                    image2.setLongClickable(false);
                }
            }, 0);

        }


    }

}
