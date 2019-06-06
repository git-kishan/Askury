package com.droid.solver.askapp.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.facebook.login.widget.LoginButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImagePollViewHolder extends RecyclerView.ViewHolder {
    EmojiTextView profileName,bio,question;
    CircleImageView profileImageView;
    TextView timeAgo,text1,text2;
    RelativeLayout view1,view2;
    ConstraintLayout constraintLayout;
    ImageView image1,image2,leftWhiteHeart,rightWhiteHeart,leftRedHeart,rightRedHeart,threeDot;
    public ImagePollViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImageView=itemView.findViewById(R.id.profile_image);
        profileName=itemView.findViewById(R.id.asker_name);
        bio=itemView.findViewById(R.id.about_textview);
        question=itemView.findViewById(R.id.question_textview);
        timeAgo=itemView.findViewById(R.id.time_ago_textview);
        threeDot=itemView.findViewById(R.id.imageView4);
        rightRedHeart=itemView.findViewById(R.id.imageView11);
        leftRedHeart=itemView.findViewById(R.id.imageView12);
        rightWhiteHeart=itemView.findViewById(R.id.imageView9);
        leftWhiteHeart=itemView.findViewById(R.id.imageView7);
        text1=itemView.findViewById(R.id.text1);
        text2=itemView.findViewById(R.id.text2);
        view1=itemView.findViewById(R.id.view1);
        view2=itemView.findViewById(R.id.view2);
        image1=itemView.findViewById(R.id.imageView5);
        image2=itemView.findViewById(R.id.imageView6);
        constraintLayout=itemView.findViewById(R.id.constraintLayout8);
        constraintLayout.setVisibility(View.GONE);
        leftRedHeart.setVisibility(View.GONE);
        rightRedHeart.setVisibility(View.GONE);
        leftWhiteHeart.setVisibility(View.GONE);
        rightWhiteHeart.setVisibility(View.GONE);
    }
     void onImage1SingleClicked(final Context context,String image1Url,String image2Url){
         Intent intent=new Intent(context, ImagePollOpenActivity.class);
         intent.putExtra("image1Url",image1Url );
         intent.putExtra("image2Url", image2Url);
         context.startActivity(intent);


    }
     void onImage2SingleClicked(final Context context,String image1Url,String image2Url){
         Intent intent=new Intent(context, ImagePollOpenActivity.class);
         intent.putExtra("image1Url", image1Url);
         intent.putExtra("image2Url", image2Url);
         context.startActivity(intent);

    }
    void onImage1LongClicked(final Context context,int image1LikeNo,int image2LikeNo,String imagePollId){
                    constraintLayout.setVisibility(View.GONE);
                    leftRedHeart.setVisibility(View.GONE);
                    rightRedHeart.setVisibility(View.GONE);
                    leftWhiteHeart.setVisibility(View.GONE);
                    rightWhiteHeart.setVisibility(View.GONE);

                    Animation animation= AnimationUtils.loadAnimation(context, R.anim.heart_bouncing_animation_scalein);
                    Animation animation2=AnimationUtils.loadAnimation(context,R.anim.heart_bouncing_animation_scaleout);
                    final Animation fadeOut=AnimationUtils.loadAnimation(context, R.anim.heart_fade_out);
                    leftWhiteHeart.startAnimation(animation);
                    constraintLayout.animate().translationY(40f).setDuration(0).start();
                    leftWhiteHeart.setVisibility(View.VISIBLE);
                    final Handler handler=new Handler();

         handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            leftWhiteHeart.startAnimation(fadeOut);
                            Handler handler1=new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    leftWhiteHeart.setVisibility(View.GONE);
                                    constraintLayout.setVisibility(View.VISIBLE);
                                    constraintLayout.animate().translationY(0f).setDuration(400).start();
                                    Handler handler2=new Handler();
                                    handler2.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            int firstPercentage=80;//white view1
                                            int second=100-firstPercentage;//black white
                                            double view1Width=(double)(constraintLayout.getWidth())*firstPercentage/100;
                                            ViewGroup.LayoutParams layoutParams=view1.getLayoutParams();
                                            layoutParams.width=(int)view1Width;
                                            view1.setLayoutParams(layoutParams);
//                                            int view2Width=(constraintLayout.getWidth())*second/100;
                                            ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(view2.getWidth(),
                                                    view2.getHeight());
                                            ((RelativeLayout.LayoutParams) params).setMarginStart((int)view1Width);
                                            text2.setLayoutParams(params);

                                            text1.setText(firstPercentage+"%");
                                            text2.setText(second+"%");

                                            Animation redHeartFadeInAnimation=AnimationUtils.loadAnimation(context,R.anim.red_heart_fade_in);
                                            leftRedHeart.setVisibility(View.VISIBLE);
                                            leftRedHeart.startAnimation(redHeartFadeInAnimation);
                                        }
                                    }, 0);

                                }
                            }, 500);
                        }
                    }, 1000);


    }

    void onImage2LongClicked(final Context context, final int image1LikeNo, final int image2LikeNo, String imagePollId){
        constraintLayout.setVisibility(View.GONE);
        leftRedHeart.setVisibility(View.GONE);
        rightRedHeart.setVisibility(View.GONE);
        leftWhiteHeart.setVisibility(View.GONE);
        rightWhiteHeart.setVisibility(View.GONE);

        Animation animation= AnimationUtils.loadAnimation(context, R.anim.heart_bouncing_animation_scalein);
        Animation animation2=AnimationUtils.loadAnimation(context,R.anim.heart_bouncing_animation_scaleout);
        final Animation fadeOut=AnimationUtils.loadAnimation(context, R.anim.heart_fade_out);
        rightWhiteHeart.startAnimation(animation);
        constraintLayout.animate().translationY(40f).setDuration(0).start();
        rightWhiteHeart.setVisibility(View.VISIBLE);
        final Handler handler=new Handler();
        final int totalLikeOfBothImages=image1LikeNo+image2LikeNo+1;//till now in addition to current user
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rightWhiteHeart.startAnimation(fadeOut);
                Handler handler1=new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rightWhiteHeart.setVisibility(View.GONE);
                        constraintLayout.setVisibility(View.VISIBLE);
                        constraintLayout.animate().translationY(0f).setDuration(400).start();
                        Handler handler2=new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int secondPercentage=20;//((image2LikeNo+1)/totalLikeOfBothImages)*100;//white view1
                                int firstPercentage=80;//100-secondPercentage;//black white
                                Log.i("TAG", "first percentage :- "+firstPercentage);
                                Log.i("TAG", "second percentage :- "+secondPercentage);
                                double view1Width=(double)(constraintLayout.getWidth())*firstPercentage/100;
                                Log.i("TAG", "constraint layout width :- "+view1Width);
                                ViewGroup.LayoutParams layoutParams=view1.getLayoutParams();
                                layoutParams.width=(int)view1Width;
                                view1.setLayoutParams(layoutParams);
//                                int view2Width=(constraintLayout.getWidth())*secondPercentage/100;
                                ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(view2.getWidth(),
                                        view2.getHeight());
                                ((RelativeLayout.LayoutParams) params).setMarginStart((int)view1Width);
                                text2.setLayoutParams(params);

                                if(firstPercentage<20)
                                    text1.setVisibility(View.GONE);
                                if(secondPercentage<20)
                                    text2.setVisibility(View.GONE);
                                text1.setText(firstPercentage+"%");
                                text2.setText(secondPercentage+"%");

                                Animation redHeartFadeInAnimation=AnimationUtils.loadAnimation(context,R.anim.red_heart_fade_in);
                                rightRedHeart.setVisibility(View.VISIBLE);
                                rightRedHeart.startAnimation(redHeartFadeInAnimation);
                            }
                        }, 0);

                    }
                }, 500);
            }
        }, 1000);


    }

    void onThreeDotClicked(Context context){

        View dialogView = LayoutInflater.from(context).inflate(R.layout.image_poll_overflow_dialog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().getAttributes().windowAnimations=R.style.customAnimations_successfull;
        alertDialog.show();
    }
}
