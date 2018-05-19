package com.myad;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by book4 on 2018/5/18.
 */

public class PositionHelper {
    private static PositionHelper mInstance;
    private Map<String, Integer> mMap = new HashMap<>();
    private final String Tag = "positionHelp";

    public static PositionHelper getInstance(){
        if(mInstance == null){
            synchronized (PositionHelper.class){
                if(mInstance == null){
                    mInstance = new PositionHelper();
                }
            }
        }
        return mInstance;
    }

    public int getPosition(Context context){
        int result = 0;
        if(context instanceof Activity){
            String name = ((Activity) context).getLocalClassName();
            if(mMap.containsKey(name)){
                int size = mMap.get(name);
                result = size+1;
            }else{
                result = 1;
            }
            mMap.put(name, result);
            Log.d(Tag, "getPos:" + name + "," + result);
        }
        return result-1;
    }

    public void clearPosition(Context context){
        if(context instanceof Activity){
            String name = ((Activity) context).getLocalClassName();
            if(mMap.containsKey(name)){
                mMap.remove(name);
                Log.d(Tag, "clearPos:" + name);
            }
        }
    }
}
