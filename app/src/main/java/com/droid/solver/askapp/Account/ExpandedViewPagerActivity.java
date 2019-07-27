package com.droid.solver.askapp.Account;

import android.graphics.Typeface;

import com.google.android.material.snackbar.Snackbar;
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
import java.util.Objects;

public class ExpandedViewPagerActivity extends AppCompatActivity implements View.OnClickListener,
        UidPasserListener {

    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_view_pager);
        viewPager=findViewById(R.id.view_pager);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_black);
        toolbar.setNavigationOnClickListener(this);
        setViewPager();
        setTabLayout();
        changeToolbarFont(toolbar);

    }

    private void setTabLayout(){
        try {
            TabLayout tabLayout = findViewById(R.id.tabLayout);
            tabLayout.setupWithViewPager(viewPager);

            Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_questions_black, null));
            Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_qa_black, null));
        }catch(NullPointerException e){
            Snackbar.make(findViewById(R.id.root), "Facing some issue ,restart your app", Snackbar.LENGTH_LONG).show();
        }catch (Exception e){
            Snackbar.make(findViewById(R.id.root), "Facing some issue ,restart your app", Snackbar.LENGTH_LONG).show();
        }

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);

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
