package com.myad;

import retrofit2.Call;
import retrofit2.http.HTTP;

/**
 * Created by book4 on 2018/1/29.
 */

public interface IdService {
    @HTTP(method = "GET", path = "001/master/app/test.xml")
    Call<IdAllModel> getBlog();
}
