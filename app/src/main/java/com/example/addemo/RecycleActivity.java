package com.example.addemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by book4 on 2018/5/19.
 */

public class RecycleActivity extends Activity {
    private RecyclerView mRvData;
    private List<String> mDataList = new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        initView();
        initData();
    }

    private void initView(){
        mRvData = findViewById(R.id.lv_data);
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter();
        mRvData.setAdapter(mAdapter);
    }

    private void initData(){
        for(int i=0; i<50; i++){
            mDataList.add("编号" + i);
        }
        mAdapter.notifyDataSetChanged();
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = new MyHolder(LayoutInflater.from(RecycleActivity.this).inflate(R.layout.item_list, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            String data = mDataList.get(position);
            holder.tvData.setText(data);
        }

        @Override
        public int getItemCount() {
            return mDataList == null? 0: mDataList.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{
            public TextView tvData;

            public MyHolder(View itemView) {
                super(itemView);
                tvData = itemView.findViewById(R.id.tv_data);
            }
        }
    }
}
