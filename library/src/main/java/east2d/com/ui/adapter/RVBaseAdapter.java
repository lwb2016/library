package east2d.com.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * listview和gridview的基础类
 * Created by leo on 2017/3/2.
 */

public abstract class RVBaseAdapter<T,V extends RVBaseAdapter.RVBaseViewHolder> extends RecyclerView.Adapter<V> {
    protected List<T> datas;

    protected LayoutInflater mInflater;

    protected Context mContext;

    public RVBaseAdapter(Context context, List<T> initDatas) {
        mContext=context;
        mInflater= LayoutInflater.from(context);
        this.datas=new ArrayList<>();
        if(initDatas!=null){
            this.datas.addAll(initDatas);
        }
    }


    //数据的刷新和清理

    /**
     * 重置数据并刷新
     * @param list
     * @param notify 是否需要刷新
     */
    public void resetDatas(List<T> list, boolean notify){
        if(list==null)
            return;
        datas.clear();
        datas.addAll(list);
        if(notify){
            //notifyItemRangeChanged(0,getItemCount());
            notifyDataSetChanged();
        }
    }

    /**
     * 添加数据并刷新
     * @param list
     * @param notify 是否需要刷新
     */
    public void addDatas(List<T> list, boolean notify){
        if(list==null)
            return;
        datas.addAll(list);
        if(notify){
            //notifyItemRangeChanged(0,getItemCount());
            notifyDataSetChanged();
        }
    }

    public void addHeadData(T t,boolean notify){
        if(t==null)
            return;
        datas.add(0,t);
        if(notify){
            notifyItemInserted(0);
        }
    }
    public void addFootData(T t,boolean notify){
        if(t==null)
            return;
        datas.add(datas.size()-1,t);
        if(notify){
            notifyItemInserted(getItemCount()-1);
        }
    }

    /**
     * 清空数据
     * @param notify 是否需要刷新
     */
    public void clearData(boolean notify){
        datas.clear();
        if(notify){
            notifyItemRangeChanged(0,getItemCount());
            //notifyDataSetChanged();
        }
    }

    public List<T> getDatas() {
        return datas;
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


    public abstract V getItemHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    public abstract void bindData(V holder,int position,T itemData);

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return getItemHolder(mInflater,parent,viewType);
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        final T item = getItem(position);
        bindData(holder,position,item);
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,item);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnItemClickListener.onItemLongClick(v,item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    public T getItem(int position){
        return datas==null?null:datas.get(position);
    }

    public static class RVBaseViewHolder extends RecyclerView.ViewHolder{
        public RVBaseViewHolder(View itemView) {
            super(itemView);
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

    public void clear(){
        if(datas!=null){
            datas.clear();
        }
        if(mContext!=null){
            mContext=null;
        }
    }

}
