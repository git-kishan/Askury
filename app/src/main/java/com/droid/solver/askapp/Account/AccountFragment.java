package com.droid.solver.askapp.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.setting.SettingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.protobuf.StringValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CircleImageView profileImage;
    private CardView toolbarCardViewActivity;
    private TextView followerCount,followingCount,pointCount;
    private TextView followerTextView,followingTextView,pointTextView;
    public AccountFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account, container, false);
        if(getActivity()!=null)
        toolbarCardViewActivity=getActivity().findViewById(R.id.toolbar_card_view);
        profileImage=view.findViewById(R.id.profile_image);
        ImageView settingimage = view.findViewById(R.id.setting_image);
        EmojiTextView profileName=view.findViewById(R.id.user_name_text_view);
        EmojiTextView userBio=view.findViewById(R.id.about_user);
        followerCount=view.findViewById(R.id.follower_count_number);
        followingCount=view.findViewById(R.id.following_count_number);
        pointCount=view.findViewById(R.id.point_count_number);
        followerTextView=view.findViewById(R.id.follower_text_view);
        followingTextView=view.findViewById(R.id.following_text_view);
        pointTextView=view.findViewById(R.id.points_text_view);
        CardView typeImageCard=view.findViewById(R.id.type_image_card);
        ImageView typeImageView=view.findViewById(R.id.type_image);
        toolbarCardViewActivity.setVisibility(View.GONE);
        viewPager=view.findViewById(R.id.view_pager);
        SharedPreferences preferences=getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        String userName=preferences.getString(Constants.userName, "");
        String muserBio=preferences.getString(Constants.bio, "");
        String gender=preferences.getString(Constants.GENDER, null);
        loadDataFromLocalDatabase();
        if(gender!=null){
            if(gender.equals(Constants.MALE)){
                typeImageCard.setVisibility(View.VISIBLE);
                typeImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_masculine, null));
            }else if(gender.equals(Constants.FEMALE)){
                typeImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_femenine, null));
                typeImageCard.setVisibility(View.VISIBLE);

            }
        }
        profileName.setText(userName);
        userBio.setText(muserBio);
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

    @Override
    public void onResume() {
        loadProfilePicFromFile();
        super.onResume();
    }

    private void setTabLayout(View view){
        tabLayout=view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab1=tabLayout.newTab();
        TabLayout.Tab tab2=tabLayout.newTab();
        TabLayout.Tab tab3=tabLayout.newTab();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(getActivity()!=null) {
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
            }
        }
        try {
            tabLayout.getTabAt(0).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_questions_black, null));
            tabLayout.getTabAt(1).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_qa_black, null));
        }catch (NullPointerException e){

        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        followingCount.setText(String.valueOf(0));
        followerCount.setText(String.valueOf(0));
        pointCount.setText(String.valueOf(0));

    }
    private void setViewPager(){
        AccountFragmentPagerAdapter pagerAdapter=new AccountFragmentPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new AccountQuestionFragment());
        pagerAdapter.addFragment(new AccountQuestionAnswerFragment());
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
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.profile_image:
                getActivity().startActivity(new Intent(getActivity(),ProfileImageActivity.class));
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

    private void loadProfilePicFromFile(){
        SharedPreferences preferences=getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        String path=preferences.getString(Constants.LOW_PROFILE_PIC_PATH, null);
        File file=new File(path,"profile_pic_high_resolution");
        try {
            Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(file));
            profileImage.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                String url = ProfileImageActivity.PROFILE_PICTURE +"/"+FirebaseAuth.getInstance().getCurrentUser().getUid()
                        + ProfileImageActivity.THUMBNAIL;
                StorageReference reference = FirebaseStorage.getInstance().getReference().child(url);
                GlideApp.with(getActivity()).load(reference)
                        .error(R.drawable.round_account)
                        .placeholder(R.drawable.round_account)
                        .into(profileImage);
                e.printStackTrace();
            }
        }
    }

    private void loadDataFromLocalDatabase() {
        if (getActivity() != null) {
            SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
            int mfollowingCount = preferences.getInt(Constants.followingCount, 0);
            int mfollowerCount = preferences.getInt(Constants.followerCount, 0);
            int mpoint = preferences.getInt(Constants.point, 0);

            followerCount.setText(String.valueOf(mfollowerCount));
            followingCount.setText(String.valueOf(mfollowingCount));
            pointCount.setText(String.valueOf(mpoint));

        }
    }


}
