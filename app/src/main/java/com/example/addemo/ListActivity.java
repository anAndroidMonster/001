package com.example.addemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.myad.AdLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by book4 on 2018/5/18.
 */

public class ListActivity extends Activity {
    private ListView mLvData;
    private List<String> mDataList = new ArrayList<>();
    private MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        initData();
    }

    private void initView(){
        mLvData = findViewById(R.id.lv_data);
        mAdapter = new MyAdapter();
        mLvData.setAdapter(mAdapter);
    }

    private void initData(){
        for(int i=0; i<30; i++){
            mDataList.add("编号" + i);
        }
        mAdapter.notifyDataSetChanged();
    }

    class MyAdapter extends BaseAdapter{

        private LayoutInflater mInflater;

        public MyAdapter(){
            mInflater = LayoutInflater.from(ListActivity.this);
        }

        @Override
        public int getCount() {
            return mDataList == null? 0: mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_list, parent, false);

                holder.tvData = convertView.findViewById(R.id.tv_data);
                holder.layAd = convertView.findViewById(R.id.lay_ad);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            String data = mDataList.get(position);
            if(data != null) {
                holder.tvData.setText(data);
            }

            return convertView;
        }

        class ViewHolder{
            TextView tvData;
            AdLayout layAd;
        }
    }
}
