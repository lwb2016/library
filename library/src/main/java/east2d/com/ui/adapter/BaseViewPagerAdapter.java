package east2d.com.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017/5/8.
 */

public abstract class BaseViewPagerAdapter<D,T extends BaseViewPagerAdapter.BaseViewHolder> extends PagerAdapter {

    protected Context mContext;

    protected List<D> items=new ArrayList<>();

    protected List<WeakReference<T>> mHolderList=new ArrayList<WeakReference<T>>();

    public BaseViewPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void resetData(List<D> data, boolean b) {
        if(data==null)
            return;
        items.clear();
        items.addAll(data);
        if(b){
            notifyDataSetChanged();
        }
    }

    public int getPositionByItem(D d){
        return items.indexOf(d);
    }

    protected abstract T getItemViewHolder(LayoutInflater layoutInflater, ViewGroup container);
    protected abstract void boundItemViewHolder(T baseViewHolder, D e , int position);

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        T baseViewHolder=null;
        if(mHolderList.size()>0){  //控制相关的viewholder数量0--num
            baseViewHolder=mHolderList.get(0).get();
            mHolderList.remove(0);
        }
        if(baseViewHolder==null){
            baseViewHolder=getItemViewHolder(LayoutInflater.from(mContext),container);
        }
        boundItemViewHolder(baseViewHolder,items.get(position),position);
        if(mListener!=null){
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mListener.onItemClick(v, items.get(position),position);
                }
            });
        }
        container.addView(baseViewHolder.itemView,-1,null);
        return baseViewHolder;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        T viewHolder = (T) object;
        container.removeView(viewHolder.itemView);
        mHolderList.add(new WeakReference<T>(viewHolder));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((BaseViewHolder)object).itemView;
    }

    public class BaseViewHolder{
        View itemView;
        public BaseViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }

    private OnItemClickListener<D> mListener;

    public void setListener(OnItemClickListener<D> listener) {
        mListener = listener;
    }

    public interface OnItemClickListener<E>{
        void onItemClick(View view, E e, int position);
    }



}
