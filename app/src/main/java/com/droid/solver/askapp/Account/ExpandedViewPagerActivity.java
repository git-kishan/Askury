package com.droid.solver.askapp.Account;

import android.graphics.Typeface;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.droid.solver.askapp.Main.UidPasserListener;
import com.droid.solver.askapp.R;

public class ExpandedViewPagerActivity extends AppCompatActivity implements View.OnClickListener,
        UidPasserListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_view_pager);
        viewPager=findViewById(R.id.view_pager);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_black);
        toolbar.setNavigationOnClickListener(this);
        setViewPager();
        setTabLayout();
        changeToolbarFont(toolbar);

    }

    private void setTabLayout(){
        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab1=tabLayout.newTab();
        TabLayout.Tab tab2=tabLayout.newTab();
        try {
            tabLayout.getTabAt(0).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_questions_black, null));
            tabLayout.getTabAt(1).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_qa_black, null));
        }
        catch (NullPointerException e){

        }
    }
    private void changeToolbarFont(Toolbar toolbar){
        for(int i=0;i<toolbar.getChildCount();i++){
            View view=toolbar.getChildAt(i);

            if(view instanceof TextView ){
                TextView textView= (TextView) view;
                if(textView.getText().toString().equals(toolbar.getTitle().toString())){
                    Typeface typeface=ResourcesCompat.getFont(this, R.font.aclonica);
                    textView.setTypeface(typeface);
                }

            }
        }
    }

    private void setViewPager(){
        AccountFragmentPagerAdapter pagerAdapter=new AccountFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new AccountQuestionFragment());
        pagerAdapter.addFragment(new AccountQuestionAnswerFragment());
        viewPager.setAdapter(pagerAdapter);
    }
    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    @Override
    public String passUid() {
        return null;
    }
}
