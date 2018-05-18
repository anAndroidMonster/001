package com.myad;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import java.util.List;
import java.util.Random;

/**
 * Created by book4 on 2018/1/28.
 */

public class AdLayout extends RelativeLayout {
    private Context mContext;
    private int mIndex = -1;
    private int mNum = -1;
    private Handler mHandler;
    private NativeExpressADView nativeExpressADView;

    public AdLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mHandler = new Handler();
        getInParam();
        initData();
    }

    private void getInParam(){
        String tag = getTag().toString();
        if(tag != null && tag.length() > 0){
            try{
                int temp = Integer.parseInt(tag);
                mIndex = temp % 10;
                if(temp > 10){
                    mNum = temp / 10;
                }
            }catch (NumberFormatException ex){
                ex.printStackTrace();
            }
        }
    }

    private void initData(){
        if(mNum > 1){
            Random random = new Random();
            int temp = random.nextInt(mNum);
            if(temp == 0){
                mHandler.post(mRunnable);
            }
        }else {
            mHandler.post(mRunnable);
        }
    }

    private void addAd(){
        String packageName = mContext.getPackageName();
        IdDetailModel adModel = IdHelper.getInstance().getAdModel(packageName, mIndex);
        if(adModel == null) {
            AdLayout.this.setVisibility(GONE);
            return;
        }
        if(mContext instanceof Activity) {
            if(adModel.getType() == 0){
                getNativeAd(adModel.getAid(), adModel.getBid());
            }
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(IdHelper.getInstance().haveData()){
                addAd();
            }else{
                if(mHandler != null) {
                    mHandler.postDelayed(mRunnable, 1000 * 3);
                }
            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (nativeExpressADView != null) {
            nativeExpressADView.destroy();
        }
        setVisibility(GONE);
    }

    private void getNativeAd(String appId, String adId){
        NativeExpressAD nativeExpressAD = new NativeExpressAD(mContext, new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT), appId, adId, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(AdError adError) {
                if(mHandler != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            AdLayout.this.setVisibility(GONE);
                        }
                    });
                }
            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                if (nativeExpressADView != null) {
                    nativeExpressADView.destroy();
                }
                // 3.返回数据后，SDK会返回可以用于展示NativeExpressADView列表
                nativeExpressADView = list.get(0);
                nativeExpressADView.render();
                if(mHandler != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            addView(nativeExpressADView);
                        }
                    });
                }
            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                if(mHandler != null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            AdLayout.this.setVisibility(GONE);
                        }
                    });
                }
            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

            }
        });
        nativeExpressAD.setVideoOption(new VideoOption.Builder()
                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI)
                .setAutoPlayMuted(true)
                .build());
        nativeExpressAD.loadAD(1);
    }
}
