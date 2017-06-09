package east2d.com.myapplication.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by leo on 2017/5/24.
 */

public class CusViewGroup extends ViewGroup implements NestedScrollingChild{
    private static final String TAG = "CusViewGroup";

//    Android为我们提供了VelocityTracker这个类检测速率
//            使用mVelocityTracker = VelocityTracker.obtain();来初始化
//    使用mVelocityTracker.addMovement(event);将touch事件添加进去检测。注意每个touch事件都要添加进去
//    使用mVelocityTracker.computeCurrentVelocity(1000);计算每个单位时间内滑动了多少像素，这里传入的是1000ms即1s。
//    然后使用float pxsec = mVelocityTracker.getXVelocity();获取到x轴滑动的像素值，必须在执行了上面方法只会再调用。
//            最后需要mVelocityTracker.recycle();mVelocityTracker = null;回收掉这个对象。

    private VelocityTracker mVelocityTracker=VelocityTracker.obtain();  //用于速率的检测
    private Scroller mScroller;  //滚动的计算仓库

    private int curScreen;
    private int defaultScreen = 0;
    private float mLastMotionX = 0;

    public CusViewGroup(Context context) {
        super(context);
        init(context);
    }

    public CusViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CusViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CusViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        //测量自己的宽高
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        //给子类分配宽高

        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        int height = 0;

        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;

        MarginLayoutParams cParams = null;

        // 用于计算左边两个childView的高度
        int lHeight = 0;
        // 用于计算右边两个childView的高度，最终高度取二者之间大值
        int rHeight = 0;

        // 用于计算上边两个childView的宽度
        int tWidth = 0;
        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
        int bWidth = 0;

        /**
         * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
         */
        for (int i = 0; i < cCount; i++)
        {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            // 上面两个childView
            if (i == 0 || i == 1) {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;//
            }

            if (i == 2 || i == 3)
            {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 0 || i == 2)
            {
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

            if (i == 1 || i == 3)
            {
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

        }

        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);

        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : height);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        /**
         * 遍历所有childView根据其宽和高，以及margin进行布局
         */
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0, ct = 0, cr = 0, cb = 0;

            switch (i) {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = cParams.topMargin;

                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;

            }
            cr = cl + cWidth;
            cb = cHeight + ct;
            childView.layout(cl, ct, cr, cb);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet p) {
        return new MarginLayoutParams(getContext(),p);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "dispatchTouchEvent ACTION_UP");
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x=event.getX();
        if(mVelocityTracker==null){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent ACTION_DOWN");
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent ACTION_MOVE");
                float delt=mLastMotionX-x;
                if(isCanMove(delt)){

                }

                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent ACTION_UP");
                break;

            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    private boolean isCanMove(float delt) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onInterceptTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onInterceptTouchEvent ACTION_UP");
                break;

            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.e(TAG, "requestDisallowInterceptTouchEvent ");
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
}
