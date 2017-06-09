package east2d.com.myapplication.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by leo on 2017/4/27.
 */

public class VRefreshView extends LinearLayout {
    private int curScreen;
    private int defaultScreen = 0;
    private Scroller mScroller;
    private float mLastMotionX = 0;

    private VelocityTracker mVelocityTracker;


    public VRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public VRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VRefreshView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        curScreen = defaultScreen;
        mScroller = new Scroller(context);
    }
    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        int action = event.getAction();
        float x = event.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(mVelocityTracker==null){
                    mVelocityTracker = VelocityTracker.obtain();
                    mVelocityTracker.addMovement(event);
                }
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastMotionX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float delt = mLastMotionX-x;
                if(isCanMove((int)delt)){
                    if(mVelocityTracker!=null){
                        mVelocityTracker.addMovement(event);
                    }
                    mLastMotionX = x;
                    scrollBy((int)delt, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mVelocityTracker!=null){
                    mVelocityTracker.addMovement(event);
                    mVelocityTracker.computeCurrentVelocity(1000);
                    float pxsec = mVelocityTracker.getXVelocity();
                    if(pxsec>600 && curScreen >0){
                        snapToScreen(curScreen-1);
                    }else if(pxsec<-600 && curScreen<getChildCount()-1){
                        snapToScreen(curScreen+1);
                    }else{
                        //主要是用来获取该滑动到哪个界面，最终调用的是invalid调用draw方法然后draw调用computeScroll方法，然后使用scroller对象
                        snapToDestination();
                    }

                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;

            default:
                break;
        }

        return true;
    }
    private void snapToScreen(int screen){
        int whichscreen = Math.max(0, Math.min(screen, getChildCount()-1));
        if(getScrollX()!=(whichscreen*getWidth())){
            final int delat = whichscreen*getWidth() - getScrollX();
            mScroller.startScroll(getScrollX(), 0, delat, 0, Math.abs(delat)*2);
            curScreen = whichscreen;
            invalidate();
        }
    }
    private void snapToDestination(){
        int screen = (getScrollX()+getWidth()/2)/getWidth();
        snapToScreen(screen);
    }
    private boolean isCanMove(int delat){
        /*if(getScrollX()<0 && delat<0){
            return false;
        }*/
        if(getScrollX()>=(getChildCount()-1)*getWidth() && delat>0){
            return false;
        }
        return true;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            int totalHeight = 0;
            int totalwidth = 0;
            int childCount = getChildCount();
            for(int i=0; i<childCount; i++){
                View childView = getChildAt(i);
                int childwidth = childView.getMeasuredWidth();
                int childheight = childView.getMeasuredHeight();
                childView.layout(totalwidth, t, totalwidth+childwidth, b);
                totalHeight += childheight;
                totalwidth += childwidth;
            }
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }
}
