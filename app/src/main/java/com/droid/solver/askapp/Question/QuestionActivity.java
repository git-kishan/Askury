package com.droid.solver.askapp.Question;

import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;
import steelkiwi.com.library.DotsLoaderView;

public class QuestionActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,
        CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private Toolbar toolbar;
    private DotsLoaderView dotsLoaderView;
    private FrameLayout overlayFrameLayout;
    private CircleImageView circularImageView;
    private TextView userNameAsked;
    private TextInputLayout questionInputLayout;
    private TextInputEditText questionInputEditText;
    private SwitchCompat anonymousSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        toolbar=findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.questionactivity_toolbar_menu);
        toolbar.setNavigationOnClickListener(this);
        dotsLoaderView=findViewById(R.id.dotsLoaderView);
        overlayFrameLayout=findViewById(R.id.overlay_frame_layout);
        circularImageView=findViewById(R.id.profile_image);
        userNameAsked=findViewById(R.id.userNameAsked);
        questionInputLayout=findViewById(R.id.questionInputLayout);
        questionInputEditText=findViewById(R.id.questionEditText);
        anonymousSwitch=findViewById(R.id.anonymous);
        anonymousSwitch.setOnCheckedChangeListener(this);
        toolbar.setOnMenuItemClickListener(this);
        dotsLoaderView.show();
    }

    @Override
    public boolean onMenuItemClick(final MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.ask){
            Toast.makeText(this, "ask is clicked", Toast.LENGTH_SHORT).show();
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menuItem.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_next_dark, null));

                }
            }, 300);
        }
        return true;

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        if(isChecked){
            Toast.makeText(this, "anonymous", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {

            Toast.makeText(this, "cross is clicked", Toast.LENGTH_SHORT).show();
    }
}
