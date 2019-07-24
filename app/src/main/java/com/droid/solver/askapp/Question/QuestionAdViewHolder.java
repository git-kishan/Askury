package com.droid.solver.askapp.Question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.model.LazyHeaderFactory;
import com.droid.solver.askapp.R;
import com.facebook.ads.AdIconView;
import com.facebook.ads.NativeAdLayout;

import java.net.PortUnreachableException;

public class QuestionAdViewHolder extends RecyclerView .ViewHolder{
     public NativeAdLayout nativeAdLayout;
     public LinearLayout adView;
     public TextView nativeAdTitle;
     public TextView nativeAdSocialContext;
     @SuppressWarnings("deprecation")
     public AdIconView nativeAdIconView;
     public TextView sponsoredLabel;
     public Button nativeAdCallToAction;
    public QuestionAdViewHolder(@NonNull View view, Context context) {
        super(view);
        nativeAdLayout = view.findViewById(R.id.native_banner_ad_container);
        LayoutInflater inflater= LayoutInflater.from(context);
        adView=(LinearLayout) inflater.inflate(R.layout.question_banner_native_ad,nativeAdLayout,false);
        nativeAdLayout.addView(adView);
        nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);




    }
}
