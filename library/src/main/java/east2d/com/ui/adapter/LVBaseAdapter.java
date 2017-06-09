package east2d.com.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * listview和gridview的基础类
 * Created by leo on 2017/3/2.
 */

public abstract class LVBaseAdapter<T,V extends LVBaseAdapter.LVBaseViewHolder> extends BaseAdapter {

    protected List<T> datas;

    protected LayoutInflater mInflater;
    protected Context mContext;

    public LVBaseAdapter(Context context, List<T> initDatas) {
        mContext=context;
        mInflater= LayoutInflater.from(context);
        this.datas=new ArrayList<>();
        if(initDatas!=null){
            this.datas.addAll(initDatas);
        }
    }

    @Override
    public int getCount() {
        return datas==null?0:datas.size();
    }

    @Override
    public T getItem(int i) {
        return datas==null?null:datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        V holder=null;
        if(view==null){
            holder=getItemHolder(mInflater,viewGroup,i);
            view=holder.getItemView();
            view.setTag(holder);
        }else{
            holder=(V) view.getTag();
        }
        bindData(holder,i,getItem(i));
        return view;
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
            notifyDataSetChanged();
        }
    }

    /**
     * 添加数据并刷新
     * @param list
     */
    public void addDatas(List<T> list, boolean notify){
        if(list==null)
            return;
        datas.addAll(list);
        if(notify){
            notifyDataSetChanged();
        }
    }
    /**
     * 添加数据并刷新
     * @param data
     */
    public void addData(T data, boolean notify){
        if(data==null)
            return;
        datas.add(data);
        if(notify){
            notifyDataSetChanged();
        }
    }

    public List<T> getDatas() {
        return datas;
    }

    /**
     * 清空数据
     * @param notify
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
        if(mContext!=null){
            mContext=null;
        }
        if(mInflater!=null){
            mInflater=null;
        }
        if(datas!=null){
            datas.clear();
            datas=null;
        }
    }


    public abstract V getItemHolder(LayoutInflater inflater, ViewGroup parent, int position);

    public abstract void bindData(V holder,int position,T itemData);

    public static class LVBaseViewHolder {
        private View itemView;

        public View getItemView() {
            return itemView;
        }

        public LVBaseViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }

}
