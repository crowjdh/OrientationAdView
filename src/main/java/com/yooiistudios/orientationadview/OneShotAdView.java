package com.yooiistudios.orientationadview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * Created by Dongheyon Jeong in AdViewLoadTest from Yooii Studios Co., LTD. on 15. 2. 17.
 *
 * OneShotAdView
 *  이미 불려진 광고가 있으면 그 광고를 없에지 않고 유지하면서 visibility 만 조정해 보여주는 뷰
 */
@SuppressLint("ViewConstructor")
public class OneShotAdView extends FrameLayout {
    private AdView mInnerAdView;
    private String mAdUnitId;
    private boolean mHasLoaded;

    public OneShotAdView(Context context, String adUnitId) {
        super(context);
        mAdUnitId = adUnitId;

        initInnerAdView();
        addAdView();
    }

    private void initInnerAdView() {
        mInnerAdView = makeLoadedAdView(getContext(), mAdUnitId);
        mInnerAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mHasLoaded = true;
            }
        });
    }

    private void addAdView() {
        addView(mInnerAdView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void show() {
        setVisibility(View.VISIBLE);
        if (!mHasLoaded) {
            loadAd(mInnerAdView);
        }
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    private static AdView makeLoadedAdView(Context context, String adUnitId) {
        return makeSmartBannerAdView(context, adUnitId);
    }

    private static AdView makeSmartBannerAdView(Context context, String adUnitId) {
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(adUnitId);
        return adView;
    }

    private static AdView loadAd(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        return adView;
    }

    public void pause() {
        mInnerAdView.pause();
    }

    public void resume() {
        mInnerAdView.resume();
    }
}
