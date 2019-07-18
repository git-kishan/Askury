package com.droid.solver.askapp.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.emoji.widget.EmojiTextView;
import androidx.fragment.app.Fragment;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.cardview.widget.CardView;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private ViewPager viewPager;
    private CircleImageView profileImage;
    private CardView toolbarCardViewActivity;
    private TextView followerCount,followingCount,pointCount;
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
        TextView followerTextView=view.findViewById(R.id.follower_text_view);
        TextView followingTextView=view.findViewById(R.id.following_text_view);
        TextView pointTextView=view.findViewById(R.id.points_text_view);
        CardView typeImageCard=view.findViewById(R.id.type_image_card);
        ImageView typeImageView=view.findViewById(R.id.type_image);
        toolbarCardViewActivity.setVisibility(View.GONE);
        viewPager=view.findViewById(R.id.view_pager);
        SharedPreferences preferences=getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        String userName=preferences.getString(Constants.userName, "");
        String muserBio=preferences.getString(Constants.bio, "");
        String gender=preferences.getString(Constants.GENDER, null);
        ImageView expandedImageView=view.findViewById(R.id.expanded);
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
        expandedImageView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        loadProfilePicFromFile();
        super.onResume();
    }

    private void setTabLayout(View view){
        TabLayout tabLayout=view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(getActivity()!=null) {
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
            }
        }
            Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_questions_black, null));
            Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_qa_black, null));

    }
    private void setViewPager(){
        AccountFragmentPagerAdapter pagerAdapter=new AccountFragmentPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new AccountQuestionFragment());
        pagerAdapter.addFragment(new AccountQuestionAnswerFragment());
        viewPager.setAdapter(pagerAdapter);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        followingCount.setText(String.valueOf(0));
        followerCount.setText(String.valueOf(0));
        pointCount.setText(String.valueOf(0));
        loadCount();

    }

    @Override
    public void onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(getActivity()!=null){
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
            }
        }
        toolbarCardViewActivity.setVisibility(View.VISIBLE);
        super.onDestroy();
    }
    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.setting_image:
                if(getActivity()!=null) {
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                    getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }

                break;
            case R.id.profile_image:
                startActivity(new Intent(getActivity(),ProfileImageActivity.class));
                break;
            case (R.id.follower_text_view):
                loadFollowers();
                break;
            case R.id.following_text_view:
                loadFollowings();
                break;
            case R.id.points_text_view:
                break;
            case R.id.expanded:
                if(getActivity()!=null) {
                    startActivity(new Intent(getActivity(), ExpandedViewPagerActivity.class));
                    getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }

                break;
        }
    }

    private void loadProfilePicFromFile(){
        if(getActivity()!=null) {
            SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
            String path = preferences.getString(Constants.LOW_PROFILE_PIC_PATH, null);
            File file = new File(path, "profile_pic_high_resolution");
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                profileImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    String url = ProfileImageActivity.PROFILE_PICTURE + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
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

    private void loadCount(){
        if(getActivity()!=null) {
            SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
            int mfollowerCount=preferences.getInt(Constants.followerCount, 0);
            int mfollowingCount=preferences.getInt(Constants.followingCount, 0);
            int mpointCount=preferences.getInt(Constants.point, 0);
            followerCount.setText(String.valueOf(mfollowerCount));
            followingCount.setText(String.valueOf(mfollowingCount));
            pointCount.setText(String.valueOf(mpointCount));


        }

    }

    private void loadFollowers(){
        if(getActivity()!=null) {
            SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
            int count=preferences.getInt(Constants.followerCount, 0);
            if(count>0){
                Intent intent=new Intent(getActivity(),FollowActivity.class);
                intent.putExtra("title", "followers");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }else {
                Toast.makeText(getActivity(), "No followers", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadFollowings(){
        if(getActivity()!=null) {
            SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
            int count=preferences.getInt(Constants.followingCount, 0);
            if(count>0){
                Intent intent=new Intent(getActivity(),FollowActivity.class);
                intent.putExtra("title", "followings");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }else {
                Toast.makeText(getActivity(), "No followings", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
