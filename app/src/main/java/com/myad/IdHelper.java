package com.myad;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by book4 on 2018/1/29.
 */

public class IdHelper {

    private static IdHelper mInstance;
    private IdAllModel mData;

    public static IdHelper getInstance(){
        if(mInstance == null){
            synchronized (IdHelper.class){
                if(mInstance == null){
                    mInstance = new IdHelper();
                }
            }
        }
        return mInstance;
    }

    public IdDetailModel getAdModel(String packageName, int index){
        IdDetailModel result = null;
        if(packageName == null || packageName.length() <= 0) return result;
        if(mData != null){
            List<IdModel> dataList = mData.getList();
            if(dataList != null && dataList.size() > 0){
                for(IdModel data: dataList){
                    String name = data.getName();
                    if(name.equals(packageName)){
                        List<IdDetailModel> idList = data.getData();
                        if(idList != null && idList.size() > 0 && index >= 0 && index < idList.size()){
                            result = idList.get(index);
                        }
                        break;
                    }
                }
            }
        }
        return result;
    }

    public boolean haveData(){
        if(mData != null){
            return true;
        }else{
            getData();
            return false;
        }
    }

    private void getData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/anAndroidMonster/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IdService request = retrofit.create(IdService.class);
        Call<IdAllModel> call = request.getBlog();
        call.enqueue(new Callback<IdAllModel>() {
            @Override
            public void onResponse(Call<IdAllModel> call, Response<IdAllModel> response) {
                mData = response.body();
                Log.e("IdHelper", "接口成功");
            }

            @Override
            public void onFailure(Call<IdAllModel> call, Throwable t) {
                Log.e("IdHelper", "接口错误:"  + t.getMessage());
            }
        });
    }
}
