package top.newmtx.clip;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ScrollView;

import top.newmtx.cliplibrary.R;

/**
 * 带侧滑功能的横向滚动ScrollView(在listview，gridview和recycleview中使用中作为根布局)
 * Created by leo on 2017/6/2.
 */

public class ClipVerticalScrollView extends ScrollView implements OnExpendChildView {

    private final int AUTO_CHECK=111;
    private final int checkTime=50;
    private boolean isAutoExpend=true;

    private Handler mHandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AUTO_CHECK:
                    removeMessages(AUTO_CHECK);
                    int scrollY = getScrollY();
                    if(lastY ==scrollY){
                        autoScroller();
                    }else{
                        lastY =scrollY;
                        sendEmptyMessageDelayed(AUTO_CHECK,checkTime);
                    }
                    break;
            }
        }
    };

    private float gap=-1;
    private int lastY =-1;
    private int maxY =-1;
    private ViewGroup child;

    public void setGap(int gap) {
        this.gap = gap;
    }

    public void setAutoExpend(boolean autoExpend) {
        isAutoExpend = autoExpend;
    }

    private void autoScroller(){
        if(maxY <=0){
            return;
        }else if(getScrollX()>getGap()){
            smoothScrollTo(0, maxY);
        }else{
            smoothScrollTo(0,0);
        }
    }

    private float getGap(){
        return gap>0?gap: maxY /2;
    }

    public ClipVerticalScrollView(Context context) {
        this(context,null);
    }

    public ClipVerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeSet(context,attrs);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private void setAttributeSet(Context context, AttributeSet attrs) {
        if(attrs==null)
            return;
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClipHorizontalScrollView);
        gap=a.getDimension(R.styleable.ClipHorizontalScrollView_auto_expend_gap,-1);
        isAutoExpend=a.getBoolean(R.styleable.ClipHorizontalScrollView_auto_expend_enable,true);
        a.recycle();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childHeight=0;
        if(getChildCount()>0){
            View childAt = getChildAt(0);
            if(childAt instanceof ViewGroup){
                child= (ViewGroup) childAt;
            }
            childHeight=childAt.getHeight();
        }
        maxY =childHeight-getHeight();
    }

    /**
     * 此处在recycleview中第一次出现使用无效，请求使用onRelayoutGrandson(View view,int width)直接调用
     * @param index
     * @param height
     */
    @Deprecated
    public void onRelayoutGrandson(int index,int height){
        if(child!=null){
            if(index<child.getChildCount()){
                View childAt = child.getChildAt(index);
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if(layoutParams!=null&&layoutParams.height!=height){
                    layoutParams.height=height;
                    childAt.setLayoutParams(layoutParams);
                }
            }
        }
    }

    public static void onRelayoutGrandson(View view,int height){
        if(view!=null){
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams!=null&&layoutParams.height!=height){
                layoutParams.height=height;
                view.setLayoutParams(layoutParams);
            }
        }
    }

    private int lisY =-1;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mOnExpendListener!=null){
            mOnExpendListener.onScrollChanged(l,t,oldl,oldt, maxY);
        }
        if(maxY >0&& lisY !=t&&t== maxY){
            lisY=t;
            ViewParent parent = getParent();
            Log.i("ClipScrollChildView",parent==null?"parent is null":"parent:"+parent.getClass().getSimpleName());
            if(parent!=null && parent instanceof OnExpendParentView){
                ((OnExpendParentView) parent).setChild(this);
            }
        }
        lisY=l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(isAutoExpend){
                    mHandler.sendEmptyMessageDelayed(AUTO_CHECK,checkTime);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    private OnScrollListener mOnExpendListener;

    public OnScrollListener getOnScrollerListener() {
        return mOnExpendListener;
    }

    public void setOnScrollerListener(OnScrollListener onScrollListener) {
        mOnExpendListener = onScrollListener;
    }

    @Override
    public boolean doExpend(boolean toExpend) {
        if(canExpend()){
            if(toExpend){
                smoothScrollTo(0,maxY);
            }else{
                smoothScrollTo(0,0);
            }
            return true;
        }
        return false;

    }

    @Override
    public boolean isExpend() {
        return getScrollY()>0;
    }

    @Override
    public boolean canExpend(){
        return maxY >0;
    }

    public interface OnScrollListener{
        void onScrollChanged(int l, int t, int oldl, int oldt, int expendWidth);
    }
}
