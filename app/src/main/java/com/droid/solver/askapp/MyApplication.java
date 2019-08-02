package com.droid.solver.askapp;

import android.app.Application;
import android.util.Log;

import androidx.core.provider.FontRequest;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.text.FontRequestEmojiCompatConfig;

import com.facebook.FacebookActivity;
import com.facebook.FacebookSdk;
import com.google.firebase.FirebaseApp;

public class MyApplication extends Application{

    public MyApplication(){
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG", "application class");
        FontRequest fontRequest = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs);
        EmojiCompat.Config config ;
        config=new FontRequestEmojiCompatConfig(this, fontRequest)
                .setReplaceAll(true)
                .setEmojiSpanIndicatorEnabled(false)
                .registerInitCallback(new EmojiCompat.InitCallback() {
                    @Override
                    public void onInitialized() {
                        Log.i("TAG", "emoji initiiazed");
                        super.onInitialized();
                    }

                    @Override
                    public void onFailed(@androidx.annotation.Nullable Throwable throwable) {
                        Log.i("TAG", "emoji initilization failed");
                        super.onFailed(throwable);
                    }
                });
        EmojiCompat.init(config);
        FirebaseApp.initializeApp(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());

    }

}
