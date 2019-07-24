package com.droid.solver.askapp.Home;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.droid.solver.askapp.R;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class HomeFragmentAdViewHolder extends RecyclerView .ViewHolder{
    private UnifiedNativeAdView adView;

    UnifiedNativeAdView getAdView(){
        return adView;
    }
    public HomeFragmentAdViewHolder(@NonNull View view) {
        super(view);
        adView = view.findViewById(R.id.ad_view);

        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((MediaView) adView.findViewById(R.id.ad_media)).setImageScaleType(ImageView.ScaleType.CENTER_CROP);

    }
}
