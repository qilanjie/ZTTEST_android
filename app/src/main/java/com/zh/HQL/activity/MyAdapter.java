package com.zh.HQL.activity;


import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zh.HQL.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Order> mDatas;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, List<Order> datas) {
        mDatas = new ArrayList<Order>(datas);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addAll(List<Order> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();

    }
    public void removeAllItem(){
        mDatas.clear();
 //       mDatas.removeAll(datas);List<Order> datas
        notifyDataSetChanged();
    }
  public void   removeItem(int p)
    {
        mDatas.remove(p);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder  = (MyViewHolder) holder;
        if(position%2==0) myViewHolder.itemView.setBackgroundColor( Color.argb(0xA0,0, 0, 0));
        else myViewHolder.itemView.setBackgroundColor( Color.argb(0x80,0,0,0));
        myViewHolder.idtv.setText(mDatas.get(position).id);
        myViewHolder.name.setText(mDatas.get(position).name);
        myViewHolder.hanqilang1.setText(mDatas.get(position).hanqilang1);
        myViewHolder.hanqiliang2.setText(mDatas.get(position).hanqiliang2);
        myViewHolder.hanqiliangpj.setText(mDatas.get(position).hanqiliangpj);
        myViewHolder.shijiantv.setText(mDatas.get(position).ceShiShiJian);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View itemView = mInflater.inflate(R.layout.item_data, parent, false);
        holder = new MyViewHolder(itemView);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idtv;
        TextView name;
        TextView hanqilang1;
        TextView hanqiliang2;
        TextView hanqiliangpj;
        TextView shijiantv;

        public MyViewHolder(View arg0) {
            super(arg0);
            idtv = (TextView) arg0.findViewById(R.id.id);
            name = (TextView) arg0.findViewById(R.id.name);
            hanqilang1 = (TextView) arg0.findViewById(R.id.hanqiliang1);
            hanqiliang2 = (TextView) arg0.findViewById(R.id.hanqiliang2);
            hanqiliangpj = (TextView) arg0.findViewById(R.id.hanqiliangpj);
            shijiantv = (TextView) arg0.findViewById(R.id.shijian);
        }

    }

}