package com.myad;

import java.util.List;

/**
 * Created by book4 on 2018/1/29.
 */

public class IdModel {
    private String name;
    private List<IdDetailModel> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IdDetailModel> getData() {
        return data;
    }

    public void setData(List<IdDetailModel> data) {
        this.data = data;
    }
}
