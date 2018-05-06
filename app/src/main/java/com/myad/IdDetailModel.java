package com.myad;

/**
 * Created by book4 on 2018/2/1.
 */

public class IdDetailModel {
    private String id;
    private int type;//0banner1原生2开屏

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
