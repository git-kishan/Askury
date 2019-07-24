package com.droid.solver.askapp.Account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.droid.solver.askapp.R;
import com.facebook.ads.AdIconView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAdLayout;

public class FbNativeAdViewHolder extends RecyclerView.ViewHolder {
    public NativeAdLayout nativeAdLayout;
    public LinearLayout adView;
    public AdIconView nativeAdIcon;
    public TextView nativeAdTitle;
    public MediaView nativeAdMedia;
    public TextView nativeAdSocialContext;
    public TextView nativeAdBody;
    public TextView sponsoredLabel;
    public Button nativeAdCallToAction;
    public FbNativeAdViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        nativeAdLayout=itemView.findViewById(R.id.native_ad_container);
        LayoutInflater inflater=LayoutInflater.from(context);
        adView= (LinearLayout) inflater.inflate(R.layout.fb_native_ad_item_layout, nativeAdLayout,false);
        nativeAdLayout.addView(adView);
        nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        nativeAdBody = adView.findViewById(R.id.native_ad_body);
        sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

    }
}
