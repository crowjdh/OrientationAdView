package com.yooiistudios.orientationadview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.IntDef;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Dongheyon Jeong in News-Android-L from Yooii Studios Co., LTD. on 15. 2. 16.
 *
 * OrientationAdView
 *  화면 회전에 따라 다른 광고를 캐싱하는 클래스
 */
@SuppressLint("ViewConstructor")
public class OrientationAdView extends FrameLayout {

    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {}

    private OneShotAdView mPortraitAd;
    private OneShotAdView mLandscapeAd;
    private String mPortraitAdUnit;
    private String mLandscapeAdUnit;
    private boolean mIsShowing;

    public OrientationAdView(Context context, String portraitAdUnit, String landscapeAdUnit) {
        super(context);
        mPortraitAdUnit = portraitAdUnit;
        mLandscapeAdUnit = landscapeAdUnit;
        init();
    }

    private void init() {
        initAdViews();
        show();
    }

    private void initAdViews() {
        createAdViews();
        addAllAdViews();
    }

    private void createAdViews() {
        mPortraitAd = new OneShotAdView(getContext(), mPortraitAdUnit);
        mLandscapeAd = new OneShotAdView(getContext(), mLandscapeAdUnit);
    }

    private void addAllAdViews() {
        addView(mPortraitAd, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mLandscapeAd, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isShowing()) {
            show();
        }
    }

    private void showAdBasedOnOrientation() {
        if (isPortrait()) {
            showPortraitAdView();
        } else {
            showLandscapeAdView();
        }
    }

    private void showPortraitAdView() {
        if (isShowing()) {
            mPortraitAd.show();
        }
        mLandscapeAd.hide();
    }

    private void showLandscapeAdView() {
        mPortraitAd.hide();
        if (isShowing()) {
            mLandscapeAd.show();
        }
    }

    public void show() {
        mIsShowing = true;
        showAdBasedOnOrientation();
    }

    public void hide() {
        mIsShowing = false;
        mPortraitAd.hide();
        mLandscapeAd.hide();
    }

    public boolean isShowing() {
        return mIsShowing;
    }

//    @Override
//    public void setVisibility(@Visibility int visibility) {
//        if (mPortraitAd != null) {
//            mPortraitAd.setVisibility(visibility);
//        }
//        if (mLandscapeAd != null) {
//            mLandscapeAd.setVisibility(visibility);
//        }
//    }

    public void pause() {
        pausePortraitAd();
        pauseLandscapeAd();
    }

    public void resume() {
        resumePortraitAd();
        resumeLandscapeAd();
    }

    private void pausePortraitAd() {
        if (mPortraitAd != null) {
            mPortraitAd.pause();
        }
    }

    private void pauseLandscapeAd() {
        if (mLandscapeAd != null) {
            mLandscapeAd.pause();
        }
    }

    private void resumePortraitAd() {
        if (mPortraitAd != null){
            mPortraitAd.resume();
        }
    }

    private void resumeLandscapeAd() {
        if (mLandscapeAd != null){
            mLandscapeAd.resume();
        }
    }

    protected boolean isPortrait() {
        return getOrientation() == Configuration.ORIENTATION_PORTRAIT;
    }

    private int getOrientation() {
        return getContext().getResources().getConfiguration().orientation;
    }
}
