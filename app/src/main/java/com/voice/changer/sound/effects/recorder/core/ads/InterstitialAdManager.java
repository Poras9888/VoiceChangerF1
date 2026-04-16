package com.voice.changer.sound.effects.recorder.core.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.voice.changer.sound.effects.recorder.core.constants.AdConstants;

public class InterstitialAdManager {
    private static InterstitialAdManager instance;
    private InterstitialAd interstitialAd;
    private long lastShownAt;
    private boolean isLoading;

    private InterstitialAdManager() {
    }

    public static synchronized InterstitialAdManager getInstance() {
        if (instance == null) {
            instance = new InterstitialAdManager();
        }
        return instance;
    }

    public void loadAd(Context context) {
        if (isLoading || interstitialAd != null || !ConsentManager.canRequestAds(context)) {
            return;
        }
        isLoading = true;
        InterstitialAd.load(context, AdConstants.INTERSTITIAL_ID, new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd ad) {
                        isLoading = false;
                        interstitialAd = ad;
                        ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                interstitialAd = null;
                                loadAd(context);
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                interstitialAd = null;
                                loadAd(context);
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        isLoading = false;
                        Log.w("InterstitialAd", "Load failed: " + loadAdError.getMessage());
                    }
                });
    }

    public void showIfReady(Activity activity) {
        if (interstitialAd == null || !ConsentManager.canRequestAds(activity)) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastShownAt < AdConstants.INTERSTITIAL_COOLDOWN_MS) {
            return;
        }
        lastShownAt = now;
        interstitialAd.show(activity);
    }
}
