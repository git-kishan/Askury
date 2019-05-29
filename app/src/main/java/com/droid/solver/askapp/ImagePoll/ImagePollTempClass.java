package com.droid.solver.askapp.ImagePoll;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.R;

public class ImagePollTempClass {
//    public class CommunityFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
//
//
//        ImageView imageFirst,imageSecond;
//        ImageView firstHeart,secondHeart;
//        ImageView smallFirstHeart;
//        ConstraintLayout constraintLayout;
//        RelativeLayout view1,view2;
//        TextView textView1,textView2;
//        public CommunityFragment() {
//        }
//
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View view= inflater.inflate(R.layout.fragment_community, container, false);
//            imageFirst=view.findViewById(R.id.imageView5);
//            imageSecond=view.findViewById(R.id.imageView6);
//            firstHeart=view.findViewById(R.id.imageView7);
//            secondHeart=view.findViewById(R.id.imageView9);
//            smallFirstHeart=view.findViewById(R.id.imageView12);
//            constraintLayout=view.findViewById(R.id.constraintLayout8);
//            view1=view.findViewById(R.id.view1);
//            view2=view.findViewById(R.id.view2);
//            textView1=view.findViewById(R.id.text1);
//            textView2=view.findViewById(R.id.text2);
//            return view;
//        }
//
//        @Override
//        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//            super.onActivityCreated(savedInstanceState);
//            imageFirst.setOnClickListener(this);
//            imageSecond.setOnClickListener(this);
//            imageFirst.setOnLongClickListener(this);
//            imageSecond.setOnLongClickListener(this);
//            constraintLayout.setVisibility(View.GONE);
//
//        }
//
//        @Override
//        public void onClick(View view) {
//
//            switch (view.getId()){
//                case R.id.imageView5:
//                    Toast.makeText(getActivity(), "first image is clicked", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.imageView6:
//                    Toast.makeText(getActivity(), "second image is clicked", Toast.LENGTH_SHORT).show();
//
//                    break;
//            }
//        }
//
//
//        @Override
//        public boolean onLongClick(final View view) {
//
//            switch (view.getId()){
//                case R.id.imageView5:
//                    Toast.makeText(getActivity(), "first image long clicked", Toast.LENGTH_SHORT).show();
//                    Animation animation= AnimationUtils.loadAnimation(getActivity(), R.anim.heart_bouncing_animation_scalein);
//                    Animation animation2=AnimationUtils.loadAnimation(getActivity(),R.anim.heart_bouncing_animation_scaleout);
//                    final Animation fadeOut=AnimationUtils.loadAnimation(getActivity(), R.anim.heart_fade_out);
//                    firstHeart.startAnimation(animation);
//                    constraintLayout.animate().translationY(40f).setDuration(0).start();
//                    firstHeart.setVisibility(View.VISIBLE);
//                    final Handler handler=new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            firstHeart.startAnimation(fadeOut);
//                            Handler handler1=new Handler();
//                            handler1.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    firstHeart.setVisibility(View.GONE);
//                                    constraintLayout.setVisibility(View.VISIBLE);
//                                    constraintLayout.animate().translationY(0f).setDuration(300).start();
//                                    Handler handler2=new Handler();
//                                    handler2.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            int firstPercentage=30;//white view1
//                                            int second=100-firstPercentage;//black white
//                                            double view1Width=(double)(constraintLayout.getWidth())*firstPercentage/100;
//                                            ViewGroup.LayoutParams layoutParams=view1.getLayoutParams();
//                                            layoutParams.width=(int)view1Width;
//                                            view1.setLayoutParams(layoutParams);
//                                            int view2Width=(constraintLayout.getWidth())*second/100;
//                                            ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(view2.getWidth(),
//                                                    view2.getHeight());
//                                            ((RelativeLayout.LayoutParams) params).setMarginStart((int)view1Width);
//                                            textView2.setLayoutParams(params);
//
//                                            textView1.setText(firstPercentage+"%");
//                                            textView2.setText(second+"%");
//
//
//                                            smallFirstHeart.setVisibility(View.VISIBLE);
//
//                                            smallFirstHeart.animate().setInterpolator(new AccelerateDecelerateInterpolator())
//                                                    .setDuration(300).setStartDelay(400).start();
//                                        }
//                                    }, 0);
//
//                                }
//                            }, 500);
//                        }
//                    }, 1000);
////                firstHeart.startAnimation(animation2);
////                firstHeart.setVisibility(View.GONE);
//
//                    break;
//                case R.id.imageView6:
//                    Toast.makeText(getActivity(), "second image long clicked", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "first image long clicked", Toast.LENGTH_SHORT).show();
//                    Animation animation1= AnimationUtils.loadAnimation(getActivity(), R.anim.heart_bouncing_animation_scalein);
//                    Animation animation3=AnimationUtils.loadAnimation(getActivity(),R.anim.heart_bouncing_animation_scaleout);
//                    secondHeart.startAnimation(animation1);
//                    secondHeart.setVisibility(View.VISIBLE);
////                secondHeart.startAnimation(animation3);
////                secondHeart.setVisibility(View.GONE);
//
//                    break;
//            }
//
//
//            return true;
//        }
//    }

}
