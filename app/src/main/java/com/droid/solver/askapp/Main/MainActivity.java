package com.droid.solver.askapp.Main;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.droid.solver.askapp.Account.AccountFragment;
import com.droid.solver.askapp.Community.CommunityFragment;
import com.droid.solver.askapp.Home.HomeFragment;
import com.droid.solver.askapp.Question.QuestionFragment;
import com.droid.solver.askapp.R;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String HOME="home";
    private static final String QUESTION="ic_question";
    private static final String COMMUNITY="community";
    private static final String ACCOUNT="ic_account";
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Toolbar toolbar;
    private CardView toolbarCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        toolbarCardView=findViewById(R.id.toolbar_card_view);
        frameLayout=findViewById(R.id.fragment_container);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment(),HOME);
        changeToolbarFont(toolbar, this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment,tempFragment;
        switch (menuItem.getItemId()){

            case R.id.home:
                tempFragment=getSupportFragmentManager().findFragmentByTag(HOME);
                if(tempFragment!=null&&tempFragment.isVisible()){
                    break;
                }
                fragment=new HomeFragment();
                loadFragment(fragment,HOME);
                return true;
                case R.id.question:
                    tempFragment=getSupportFragmentManager().findFragmentByTag(QUESTION);
                    if(tempFragment!=null&&tempFragment.isVisible()){
                        break;
                    }
                    fragment=new QuestionFragment();
                    loadFragment(fragment,QUESTION);
                return true;
            case R.id.community:
                tempFragment=getSupportFragmentManager().findFragmentByTag(COMMUNITY);
                if(tempFragment!=null&&tempFragment.isVisible()){
                    break;
                }
                fragment=new CommunityFragment();
                loadFragment(fragment,COMMUNITY);
                return true;
            case R.id.account:
                tempFragment=getSupportFragmentManager().findFragmentByTag(ACCOUNT);
                if(tempFragment!=null&&tempFragment.isVisible()){
                    break;
                }
                fragment=new AccountFragment();
                loadFragment(fragment,ACCOUNT);
                return true;

        }
        return true;
    }
    private void loadFragment(Fragment fragment,String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment,tag);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment=getSupportFragmentManager().findFragmentByTag(HOME);
        if(fragment!=null&&fragment.isVisible()){
            super.onBackPressed();
        }else {
            loadFragment(new HomeFragment(), HOME);
            bottomNavigationView.setSelectedItemId(R.id.home);
            bottomNavigationView.setSelected(true);
        }
    }



    public static void changeToolbarFont(Toolbar toolbar, Activity context) {
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

    public static void applyFont(TextView tv, Activity context) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "aclonica.ttf"));
    }
}
