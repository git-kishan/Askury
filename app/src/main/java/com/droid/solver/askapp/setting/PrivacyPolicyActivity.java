package com.droid.solver.askapp.setting;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.droid.solver.askapp.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private WebView privacyPolicyView;
    private ImageView backImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        privacyPolicyView=findViewById(R.id.policy);

        backImageButton=findViewById(R.id.imageView21);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
