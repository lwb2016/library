package com.oacg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * listview和gridview的基础类
 * Created by leo on 2017/3/2.
 */

public abstract class RVTypeBaseAdapter<E,T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext;
    protected Map<E,List<T>> datas;
    protected Map<Integer,E> tags;

    protected LayoutInflater mInflater;

    public RVTypeBaseAdapter(Context context,E tag,List<T> list) {
        mContext=context;
        mInflater= LayoutInflater.from(context);
        this.datas=new HashMap<>();
        this.tags=new HashMap<>();
        min=0;
        max=0;
        if(tag==null||list==null||list.isEmpty()){
            max++;
            this.tags.put(max,tag);
            this.datas.put(tag,list);
        }
    }


    protected int min=0;
    protected int max=0;
    protected int count=5;

    public int getCount() {
        return count;
    }

    /**
     * tag已存在的情况下不做添加
     * @param tag
     * @param list
     * @param notify
     */
    public int addHeadData(E tag,List<T> list, boolean notify){
        if(list==null||list.isEmpty()||datas.containsKey(tag))
            return 0;
        min--;
        tags.put(min,tag);
        datas.put(tag,new ArrayList<T>(list));
        if(notify){
            notifyDataSetChanged();
        }
        return list.size()+getCount()+1;
    }

    public void addFootData(E tag,List<T> list, boolean notify){
        if(list==null||list.isEmpty()||datas.containsKey(tag))
            return;
        max++;
        tags.put(max,tag);
        datas.put(tag,new ArrayList<T>(list));
        if(notify){
            notifyDataSetChanged();
        }
    }

    public int getLoadingTime(){
        return max-min;
    }

    /**
     * 清空数据
     * @param notify 是否需要刷新
     */
    public void clearData(boolean notify){
        datas.clear();
        if(notify){
            notifyDataSetChanged();
        }
    }

    /**
     * 清除相应的引用，析构内部成员变量
     */
    public void onDestroy(){
        if(datas!=null){
            datas.clear();
            datas=null;
        }
        if(mInflater!=null){
            mInflater=null;
        }
    }

    private int OPTION_TYPE=3;
    private int ITEM_TYPE=2;


    public abstract RecyclerView.ViewHolder getItemHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    public abstract void bindData(RecyclerView.ViewHolder holder,int position,T itemData);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==OPTION_TYPE){
            return getOptionHolder(mInflater,parent,viewType);
        }else{
            return getItemHolder(mInflater,parent,viewType);
        }

    }

    protected abstract RecyclerView.ViewHolder getOptionHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public int getItemViewType(int position) {
        if(isOption(position)){
            return OPTION_TYPE;
        }
        return ITEM_TYPE;
    }


    private boolean isOption(int position){
        int first=min;
        int sum=0;
        while(true){
            sum+=getTagDatasNumByOrder(first);
            if(sum>position&&sum<=position+getCount())
                return true;
            if(sum>position+getCount()||first>=max){
                return false;
            }
            first++;
        }
    }

    public E getTag(int position){
        int first=min;
        int sum=0;
        while(true){
            sum+=getTagDatasNumByOrder(first);
            if(sum>position)
                return tags.get(first);
            if(first>=max)
                return null;
            first++;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final T item = getItem(position);
        bindData(holder,position,item);
//        if(mOnItemClickListener!=null&&!isOption(position)){
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickListener.onItemClick(v,item);
//                }
//            });
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    return mOnItemClickListener.onItemLongClick(v,item);
//                }
//            });
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,getTag(position).toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        int sum=0;
        if(max-min>0){
            int first=min;
            while (true){
                sum+=getTagDatasNumByOrder(first);
                if(first>=max){
                    return sum;
                }
                first++;
            }
        }else{
            return 0;
        }
    }

    public int getTagDatasNumByOrder(int order){
        List<T> tagDatasByOrder = getTagDatasByOrder(order);
        if(tagDatasByOrder==null){
            return 0;
        }else{
            return tagDatasByOrder.size()+getCount();
        }
    }

    public List<T> getTagDatasByOrder(int order){
        E e = tags.get(order);
        if(e!=null){
            return datas.get(e);
        }
        return null;
    }

    public T getItem(int position){
        int first=min;
        int sum=0;
        if(isOption(position))
            return null;
        while (true){
            List<T> ts = getTagDatasByOrder(first);

            if(ts!=null){
                int num=ts.size();
                if(sum+num>position){
                    Log.i("TEST_DEMO","sum:"+sum+";data_size:"+ts.size()+";position"+position+";data:"+ts.get(position-sum));
                    return ts.get(position-sum);
                }
                sum+=(num+getCount());
            }
        }
    }

    private OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<E>{
        void onItemClick(View view, E e);
        boolean onItemLongClick(View view, E e);
    }

}
