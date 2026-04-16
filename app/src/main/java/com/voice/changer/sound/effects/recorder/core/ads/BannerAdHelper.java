package com.voice.changer.sound.effects.recorder.core.ads;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.voice.changer.sound.effects.recorder.core.constants.AdConstants;

public final class BannerAdHelper {
    private BannerAdHelper() {
    }

    public static void loadBanner(Activity activity, FrameLayout container) {
        if (!ConsentManager.canRequestAds(activity)) {
            container.setVisibility(View.GONE);
            return;
        }

        AdView adView = new AdView(activity);
        adView.setAdUnitId(AdConstants.BANNER_ID);

        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int adWidth = (int) (metrics.widthPixels / metrics.density);
        AdSize adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
        adView.setAdSize(adSize);

        container.removeAllViews();
        container.addView(adView);
        adView.loadAd(new AdRequest.Builder().build());
    }
}
