package com.voice.changer.sound.effects.recorder.core.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

public final class ConsentManager {
    private ConsentManager() {
    }

    public static void gatherConsent(Activity activity, Runnable onComplete) {
        ConsentInformation consentInformation = UserMessagingPlatform.getConsentInformation(activity);
        ConsentRequestParameters params = new ConsentRequestParameters.Builder().build();
        consentInformation.requestConsentInfoUpdate(
                activity,
                params,
                () -> {
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity, loadAndShowError -> {
                        if (loadAndShowError != null) {
                            Log.w("ConsentManager", "Consent form error: " + loadAndShowError.getMessage());
                        }
                        initAdMobIfAllowed(activity);
                        if (onComplete != null) {
                            onComplete.run();
                        }
                    });
                },
                requestConsentError -> {
                    Log.w("ConsentManager", "Consent update error: " + requestConsentError.getMessage());
                    initAdMobIfAllowed(activity);
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
        );
    }

    private static void initAdMobIfAllowed(Context context) {
        if (canRequestAds(context)) {
            MobileAds.initialize(context, initStatus -> InterstitialAdManager.getInstance().loadAd(context));
        }
    }

    public static boolean canRequestAds(Context context) {
        return UserMessagingPlatform.getConsentInformation(context).canRequestAds();
    }

    public static void showPrivacyOptionsForm(Activity activity) {
        ConsentInformation consentInformation = UserMessagingPlatform.getConsentInformation(activity);
        if (consentInformation.getPrivacyOptionsRequirementStatus()
                == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED) {
            UserMessagingPlatform.showPrivacyOptionsForm(activity, formError -> {
                if (formError != null) {
                    Log.w("ConsentManager", "Privacy form error: " + formError.getMessage());
                }
            });
        }
    }
}
