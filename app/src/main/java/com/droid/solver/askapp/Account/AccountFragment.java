package com.droid.solver.askapp.Account;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountFragment extends Fragment implements View.OnClickListener {

    private ViewPager viewPager;
    private CardView toolbarCardViewActivity;
    private TabLayout tabLayout;
    private ImageView settingimage;
    private CircleImageView profileImage;
    private EmojiTextView profileName,professionTextView;
    private TextView followerCount,followingCount,pointCount,accountStatusTextView;
    private TextView followerTextView,followingTextView,pointTextView;
    private String PUBLIC_ACCOUNT_STATUS="Public";
    private String PRIVATE_ACCOUNT_STATUS="Private";
    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account, container, false);
        toolbarCardViewActivity=getActivity().findViewById(R.id.toolbar_card_view);
        profileImage=view.findViewById(R.id.profile_image);
        settingimage = view.findViewById(R.id.setting_image);
        profileName=view.findViewById(R.id.user_name_text_view);
        accountStatusTextView=view.findViewById(R.id.public_private_text_view);
        accountStatusTextView.setText(PUBLIC_ACCOUNT_STATUS);
        professionTextView=view.findViewById(R.id.about_user);
        followerCount=view.findViewById(R.id.follower_count_number);
        followingCount=view.findViewById(R.id.following_count_number);
        pointCount=view.findViewById(R.id.point_count_number);
        followerTextView=view.findViewById(R.id.follower_text_view);
        followingTextView=view.findViewById(R.id.following_text_view);
        pointTextView=view.findViewById(R.id.points_text_view);
        toolbarCardViewActivity.setVisibility(View.GONE);
        viewPager=view.findViewById(R.id.view_pager);
        setViewPager();
        setTabLayout(view);
        settingimage.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        followerTextView.setOnClickListener(this);
        followingTextView.setOnClickListener(this);
        pointTextView.setOnClickListener(this);
        followerCount.setOnClickListener(this);
        followingCount.setOnClickListener(this);
        pointCount.setOnClickListener(this);

        return view;
    }

    private void setTabLayout(View view){
        tabLayout=view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab1=tabLayout.newTab();
        TabLayout.Tab tab2=tabLayout.newTab();
        TabLayout.Tab tab3=tabLayout.newTab();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
        }
        tabLayout.getTabAt(0).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_questions_black, null));
        tabLayout.getTabAt(1).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_qa_black, null));
        tabLayout.getTabAt(2).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_like_black, null));
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }
    private void setViewPager(){
        AccountFragmentPagerAdapter pagerAdapter=new AccountFragmentPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new AccountQuestionFragment());
        pagerAdapter.addFragment(new AccountQuestionAnswerFragment());
        pagerAdapter.addFragment(new AccountLikeFragment());
        viewPager.setAdapter(pagerAdapter);
    }
    @Override
    public void onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        }
        toolbarCardViewActivity.setVisibility(View.VISIBLE);
        super.onDestroy();
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.setting_image:
                final  Animation animationRightRotate=AnimationUtils.loadAnimation(getActivity(),R.anim.setting_rotate_right);
                final Animation animationLeftRotate=AnimationUtils.loadAnimation(getActivity(), R.anim.setting_rotate_left);
                final Animation statusScaleIn=AnimationUtils.loadAnimation(getActivity(), R.anim.status_scale_in);
                final Animation statusScaleOut=AnimationUtils.loadAnimation(getActivity(), R.anim.status_scale_out);
                view.startAnimation(animationRightRotate);
                accountStatusTextView.startAnimation(statusScaleOut);
                Handler settingImageHandler=new Handler();
                settingImageHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.startAnimation(animationLeftRotate);
                        accountStatusTextView.startAnimation(statusScaleIn);
                        if(accountStatusTextView.getText().equals(PUBLIC_ACCOUNT_STATUS)){
                            accountStatusTextView.setText(PRIVATE_ACCOUNT_STATUS);
                        }else if(accountStatusTextView.getText().equals(PRIVATE_ACCOUNT_STATUS)){
                            accountStatusTextView.setText(PUBLIC_ACCOUNT_STATUS);
                        }

                    }
                }, 300);
                break;
            case R.id.profile_image:
                break;
            case (R.id.follower_text_view):
                Toast.makeText(getActivity(), "follower text view is clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.follower_count_number:
                Toast.makeText(getActivity(), "follower text view is clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.following_text_view:
                Toast.makeText(getActivity(), "following text view  is clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.following_count_number:
                Toast.makeText(getActivity(), "following text view  is clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.points_text_view:
                Toast.makeText(getActivity(), "point text view  is clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.point_count_number:
                Toast.makeText(getActivity(), "point text view  is clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
