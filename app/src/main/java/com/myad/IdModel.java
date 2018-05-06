package com.myad;

import java.util.List;

/**
 * Created by book4 on 2018/1/29.
 */

public class IdModel {
    private String name;
    private String first;
    private List<IdDetailModel> second;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public List<IdDetailModel> getSecond() {
        return second;
    }

    public void setSecond(List<IdDetailModel> second) {
        this.second = second;
    }
}
