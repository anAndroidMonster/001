package com.myad;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import java.util.List;

/**
 * Created by book4 on 2018/1/28.
 */

public class AdLayout extends RelativeLayout {
    private Context mContext;
    private int mIndex = -1;
    private Handler mHandler;
    private NativeExpressADView nativeExpressADView;
    private final String Tag = "adLayout";
    private AbsListView mlv;
    private RecyclerView mRv;
    private boolean mHaveParent;
    private int mPosition = -1;
    private boolean isGetAd;
    private View mVItem;
    private int mRecycleTime;

    public AdLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mHandler = new Handler();
        getInParam();

    }

    private void getMyParent(){
        if(mHaveParent){
            return;
        }else{
            mHaveParent = true;
        }
        ViewParent parent = null;
        ViewParent itemView = null;
        for(int i=0; i<10; i++){
            if(parent == null) {
                parent = getParent();
                itemView = parent;
            }else{
                itemView = parent;
                parent = parent.getParent();
            }
            if(parent == null){
                break;
            }else {
                Log.d(Tag, "父亲" + parent.getClass().getSimpleName());
                if(parent instanceof AbsListView){
                    mlv = (AbsListView) parent;
                    mVItem = (View) itemView;
                    if(mPosition == 0){
                        mlv.setRecyclerListener(new AbsListView.RecyclerListener() {
                            @Override
                            public void onMovedToScrapHeap(View view) {
                                if(view == mVItem){
                                    mRecycleTime += 1;
                                    if(mRecycleTime%2 == 1){
                                        removeAllViews();
                                    }else{
                                        initData();
                                    }
                                }
                            }
                        });
                    }
                }else if(parent instanceof RecyclerView){
                    mRv = (RecyclerView) parent;
                }
            }
        }
    }

    private void getInParam(){
        String tag = getTag().toString();
        if(tag != null && tag.length() > 0){
            try{
                mIndex = Integer.parseInt(tag);
            }catch (NumberFormatException ex){
                ex.printStackTrace();
            }
        }
    }

    private void initData(){
        if(mPosition == 0){
            Log.d(Tag, "直接请求" + mPosition);
            mHandler.post(mRunnable);
        }else{
            Log.d(Tag, "不请求" + mPosition);
        }
    }

    private void addAd(){
        String packageName = mContext.getPackageName();
        IdDetailModel adModel = IdHelper.getInstance().getAdModel(packageName, mIndex);
        if(adModel == null) {
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
    protected void onWindowVisibilityChanged(int visibility) {//用于界面不可见
        super.onWindowVisibilityChanged(visibility);
        if (visibility == View.VISIBLE){
            Log.d(Tag, "可见");
            if(mPosition < 0){
                mPosition = PositionHelper.getInstance().getPosition(mContext);
            }
            getMyParent();

            initData();
        }
    }

    @Override
    protected void onDetachedFromWindow() {//用于界面销毁
        PositionHelper.getInstance().clearPosition(mContext);
        super.onDetachedFromWindow();
        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (nativeExpressADView != null) {
            nativeExpressADView.destroy();
        }
        Log.d(Tag, "销毁");
    }

    private void getNativeAd(String appId, String adId){
        if(isGetAd){
            return;
        }
        synchronized (AdLayout.class){
            if(isGetAd){
                return;
            }else{
                isGetAd = true;
            }
        }
        Log.d(Tag, "刷新ad");
        NativeExpressAD nativeExpressAD = new NativeExpressAD(mContext, new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT), appId, adId, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(AdError adError) {
                isGetAd = false;
            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                isGetAd = false;
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
                            removeAllViews();
                            Log.d(Tag, "添加广告");
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
