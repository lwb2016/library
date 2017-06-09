package east2d.com.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by leo on 2017/5/27.
 */

public class ScrollViewGroup extends ViewGroup {
    public ScrollViewGroup(Context context) {
        super(context);
    }

    public ScrollViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScrollViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        int height = 0;

        int itemHeight=offset;

        MarginLayoutParams cParams = null;

        int count=getChildCount();

        int left=0;
        int top=0;
        int right=0;
        int bottom=0;

        int mHeight=0;

        for(int i=0;i<count;i++){
            View childView = getChildAt(i);
            width = childView.getMeasuredWidth();
            height = childView.getMeasuredHeight();
            cParams= (MarginLayoutParams) childView.getLayoutParams();
            mHeight=cParams.topMargin+height+cParams.bottomMargin;

            left=cParams.leftMargin;

//            if(i==0){
//                top=cParams.topMargin;
//            }else{
//            }
            top=itemHeight+cParams.topMargin;
            itemHeight+=mHeight;  //跳转至下一个断点

            right=left+width+cParams.rightMargin;
            int measuredHeight = getMeasuredHeight();
            if(i==count-1){
                if(measuredHeight>itemHeight){
                    bottom=top+height+(measuredHeight-itemHeight);
                }else{
                    bottom=measuredHeight-cParams.bottomMargin;
                }
            }else{
                bottom=top+height;
            }
            childView.layout(left,top,right,bottom);
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet p) {
        return new MarginLayoutParams(getContext(),p);
    }

    private int offset=0;

    private int cur=0;

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

        Log.i("onMeasure","onMeasure:"+widthMeasureSpec+";"+heightMeasureSpec+";"+sizeWidth+";"+sizeHeight);
        // 检测控件大小是否符合要求
        if(widthMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.UNSPECIFIED) {
            throw new IllegalArgumentException("不合法的MeasureSpec mode");
        }

        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        int height = 0;

        int curWidth=0;

        int maxWidth = 0;
        int rheight = 0;

        MarginLayoutParams cParams = null;

        int cCount = getChildCount();
        for(int i=0;i<cCount;i++){
            View childView = getChildAt(i);
            width = childView.getMeasuredWidth();
            height = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            curWidth=width+cParams.leftMargin+cParams.rightMargin;
            if(i==0){
                cur=cParams.topMargin+height+cParams.bottomMargin;
            }
            if(curWidth>maxWidth){
                maxWidth=curWidth;
            }
            rheight += height + cParams.topMargin + cParams.bottomMargin;

            //确定大小的
            final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(cParams.width, MeasureSpec.EXACTLY);
            final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(cParams.height, MeasureSpec.EXACTLY);

            Log.i("onMeasure","onMeasure :"+i+";"+maxWidth+":"+rheight);
//
//            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }

        int rWidth = (widthMode == MeasureSpec.EXACTLY) ? sizeWidth : maxWidth;
        int rHeight = (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : rheight;

        yOffset=rheight-rHeight;

        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */
        setMeasuredDimension(rWidth, rHeight+300);
    }

    private float lastY=0;
    private int yOffset=0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        float y = ev.getY();
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                lastY=y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float off = y - lastY;
//                if(offset+off+cur>0){
//                    offset += off;
//                    invalidate();
//                }else{
//                    offset=-cur;
//                    invalidate();
//                }
//                lastY=y;
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    private int shade=0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float y = ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("MotionEvent","MotionEvent.ACTION_DOWN:"+y);
                lastY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                float off = lastY - y;
//                if(offset-off+cur>0){
//                    Log.i("MotionEvent","MotionEvent.ACTION_MOVE:invalidate"+offset);
//                    offset += off;
//                }else{
//                    Log.i("MotionEvent","MotionEvent.ACTION_MOVE:"+y);
//                    offset=-cur;
//                }
                offset +=off;
                if(offset>cur){
                    offset=cur;
                }else if(offset<-shade){
                    offset=-shade;
                }
                scrollTo(0,offset);
                invalidate();
                lastY=y;
                break;
            case MotionEvent.ACTION_UP:
                Log.i("MotionEvent","MotionEvent.ACTION_UP:"+y);
                break;
        }
        return true;
    }
}
