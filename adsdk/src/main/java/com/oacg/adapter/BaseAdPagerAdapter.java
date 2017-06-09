package com.oacg.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdPagerAdapter<T,E,I extends BaseAdPagerAdapter.ItemViewHolder,A extends BaseAdPagerAdapter.ADViewHolder> extends PagerAdapter{

	protected List<WeakReference<BaseViewHolder>> mHolderList=new ArrayList<WeakReference<BaseViewHolder>>();
	protected List<WeakReference<BaseViewHolder>> mAdHolderList=new ArrayList<WeakReference<BaseViewHolder>>();

	protected Context mContext;

    protected LayoutInflater layoutInflater;

    protected List<T> items;
	protected List<E> ads;

	protected int num=10;

	protected boolean isAllowAd=true;

	public BaseAdPagerAdapter(Context context, List<T> items, List<E> ads) {
		mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
        this.items=new ArrayList<T>();
		if(items!=null){
			this.items.addAll(items);
		}
		this.ads=new ArrayList<E>();
		if(ads!=null){
			this.ads.addAll(ads);
		}
	}

	public void resetData(List<T> items, List<E> ads){
		if(items!=null){
			this.items.clear();
			this.items.addAll(items);
		}
		if(ads!=null){
			this.ads.clear();
			this.ads.addAll(ads);
		}
		notifyDataSetChanged();
	}

	public void addData(List<T> items, List<E> ads){
		if(items!=null){
			this.items.addAll(items);
		}
		if(ads!=null){
			this.ads.addAll(ads);
		}
		notifyDataSetChanged();
	}

	public void clear(){
		this.items.clear();
		this.ads.clear();
		this.mHolderList.clear();
		this.mAdHolderList.clear();
	}

	public void setNum(int num){
		if(num<1){
			num=1;
		}
		this.num=num+1;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
        if(isAllowAd){
            int adnum=items.size()/(num-1);
            if(adnum>ads.size()){
                adnum=ads.size();
            }
            if(adnum<0){
                adnum=0;
            }
            return items.size()+adnum;
        }
        return items.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==((BaseViewHolder)arg1).itemView;
	}

    @Override
	public Object instantiateItem(ViewGroup container,final int position) {
		// TODO Auto-generated method stub
		BaseViewHolder baseViewHolder=null;
		//判断已经出现的广告位的数量
        int adBefore=getAdDataPosition(position);

		if(isAllowAd&&(position+1)%num==0&&adBefore<ads.size()){  //为广告位置
            if(mAdHolderList.size()>0){  //控制相关的viewholder数量0--num
                baseViewHolder=mAdHolderList.get(0).get();
                mAdHolderList.remove(0);
            }
			if(baseViewHolder==null){
				baseViewHolder=getADViewHolder(layoutInflater,container);
			}
            final int pos= adBefore;
            boundADViewHolder((A)baseViewHolder,ads.get(pos),pos,position);
			if(mListener!=null){
				baseViewHolder.itemView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mListener.onAdItemClick(v, ads.get(pos),pos,position);
					}
				});
			}
		}else{                            //正常位置
            if(mHolderList.size()>0){  //控制相关的viewholder数量0--num
                baseViewHolder=mHolderList.get(0).get();
                mHolderList.remove(0);
            }
			if(baseViewHolder==null){
				baseViewHolder=getItemViewHolder(layoutInflater,container);
			}
            final int pos = position - adBefore;
            boundItemViewHolder((I)baseViewHolder,items.get(pos),pos,position);
			if(mListener!=null){
				baseViewHolder.itemView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mListener.onItemClick(v, items.get(pos),pos,position);
					}
				});
			}
		}
		container.addView(baseViewHolder.itemView,-1,null);
		return baseViewHolder;
	}

	protected abstract A getADViewHolder(LayoutInflater layoutInflater,ViewGroup container);

	protected abstract I getItemViewHolder(LayoutInflater layoutInflater, ViewGroup container);

	protected abstract void boundItemViewHolder(I baseViewHolder, T e, int itemPostion,int position);

    protected abstract void boundADViewHolder(A baseViewHolder, E e, int adPostion,int position);

    @Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		BaseViewHolder viewHolder = (BaseViewHolder) object;
        container.removeView(viewHolder.itemView);
        if(viewHolder instanceof ADViewHolder){
            mAdHolderList.add(new WeakReference<BaseViewHolder>(viewHolder));
        }else if(viewHolder instanceof ItemViewHolder){
            mHolderList.add(new WeakReference<BaseViewHolder>(viewHolder));
        }
	}
	
	private static class BaseViewHolder{
		public View itemView;

		public BaseViewHolder(View itemView) {
			super();
			this.itemView = itemView;
		}

	}

	public static class ADViewHolder extends BaseViewHolder{

		public ADViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
		}
	}

	public static class ItemViewHolder extends BaseViewHolder{

		public ItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
		}
	}

	private OnItemClickListener<E, T> mListener;

	public void setAllowAd(boolean allowAd) {
		isAllowAd = allowAd;
	}

	public void setListener(OnItemClickListener<E, T> listener) {
		mListener = listener;
	}

	public interface OnItemClickListener<E,T>{
		void onItemClick(View view, T t, int adPostion, int allPosition);
		void onAdItemClick(View view, E t, int itemPostion, int allPosition);
	}
	
	public int getItemDataPosition(int pos){
        return pos-getAdDataPosition(pos);
	}
	
	public int getAdDataPosition(int pos){
		int adBefore=0;
        if(isAllowAd){
            adBefore=pos/num;
            if(adBefore>ads.size()){
                adBefore=ads.size();
            }
        }
        return adBefore;
	}
	
	public boolean isAd(int position){
		if(isAllowAd&&(position+1)%num==0){
			return true;
		}
		return false;
	}

	public List<T> getItems() {
		return items;
	}

	public List<E> getAds() {
		return ads;
	}
	
	
}
