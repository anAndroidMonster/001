package com.myad;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by book4 on 2018/5/18.
 */

public class PositionHelper {
    private static PositionHelper mInstance;
    private Map<String, List<Integer>> mMap = new HashMap<>();
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
                List<Integer> list = mMap.get(name);
                result = list.get(list.size() - 1) + 1;
                list.add(result);
            }else{
                List<Integer> list = new ArrayList<>();
                result = 0;
                list.add(result);
                mMap.put(name, list);
            }
            Log.d(Tag, "新增" + result);
            return result;
        }else{
            return result;
        }
    }

    public void removePosition(Context context, int position){
        if(context instanceof Activity){
            String name = ((Activity) context).getLocalClassName();
            if(mMap.containsKey(name)){
                List<Integer> list = mMap.get(name);
                if(list.contains(position)){
                    list.remove(Integer.valueOf(position));
                    Log.d(Tag, "删除" + position);
                }
            }
        }
    }
}
