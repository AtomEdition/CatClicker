package com.AtomEdition.CatClicker.ad;

import android.app.Activity;
import android.content.Context;
import com.AtomEdition.KittyClicker.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by FruityDevil on 11.01.2016.
 */
public class AdService {
    private static AdService instance;
    private InterstitialAd interstitialAd;

    private AdService(){
    }

    public static AdService getInstance(){
        if (instance == null) {
            instance = new AdService();
        }
        return instance;
    }

    public void showBanner(Activity activity){

        AdView adView = (AdView) activity.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
    }

    public void loadInterstitial(Context context){
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId("ca-app-pub-9550981282535152/6376277427");
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void displayInterstitial(){
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }
}
