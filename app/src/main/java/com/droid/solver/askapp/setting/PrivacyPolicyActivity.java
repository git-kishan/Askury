package com.droid.solver.askapp.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.droid.solver.askapp.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        final CardView cardView=findViewById(R.id.card);
        cardView.setElevation(0f);
        WebViewObserver privacyPolicyView=findViewById(R.id.policy);
        privacyPolicyView.loadUrl("file:///android_asset/PrivacyPolicy.html");
        ImageView backImageButton=findViewById(R.id.imageView21);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        privacyPolicyView.setOnScrollChangedCallback(new WebViewObserver.OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                if(t>oldt){
                    //swipe up
                    cardView.setElevation(5f);
                }
                else if(t<oldt){
                    //swipe down
                    cardView.setElevation(0f);
                }
            }
        });
        privacyPolicyView.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                proceedUrl(Uri.parse(url));
                return true;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                proceedUrl(request.getUrl());
                return true;
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);
    }
    private void proceedUrl(Uri uri){
        if (uri.toString().startsWith("mailto:")) {
            startActivity(new Intent(Intent.ACTION_SENDTO, uri));
        }
    }
}
