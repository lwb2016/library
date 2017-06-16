package top.newmtx.clip;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by leo on 2017/6/2.
 */

public class ClipLineaerLayout extends LinearLayout {
    private View firstChild;

    public ClipLineaerLayout(Context context) {
        this(context,null);
    }

    public ClipLineaerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int max=0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i("ClipLineaerLayout","onLayout");
        int count = getChildCount();
        int amountWidth=0;
        for(int i=0;i<count;i++){
            View childAt = getChildAt(i);
            if(i==0){
                firstChild = getChildAt(0);
            }
            amountWidth+=childAt.getWidth();
            Log.i("ClipLineaerLayout","onLayout"+i+" firstChild.width"+amountWidth);
        }
        max = amountWidth - getWidth();
    }


    public void onRelayout(int width){
        if(firstChild!=null){
            ViewGroup.LayoutParams layoutParams = firstChild.getLayoutParams();
            if(layoutParams.width!=width){
                layoutParams.width=width;
                firstChild.setLayoutParams(layoutParams);
            }
        }
    }

    private float lastPoint=0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("ClipLineaerLayout","dispatchTouchEvent");
        float x = ev.getX();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("ClipLineaerLayout","dispatchTouchEvent down");
                lastPoint=x;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("ClipLineaerLayout","dispatchTouchEvent move:"+getScrollX()+" width"+getWidth());
                int scrollX = getScrollX();
                float v = scrollX + (lastPoint - x);
                if(v<0&&scrollX>0){
                    v=0;
                }else if(v>max&&scrollX<max){
                    v=max;
                }else if(v>=0&&v<=max){
                    //v=v;
                }else{
                    v=scrollX;
                }
                scrollTo((int) v,0);
                lastPoint=x;
                return true;
            case MotionEvent.ACTION_UP:
                Log.i("ClipLineaerLayout","dispatchTouchEvent up");
                if(getScrollX()>max/2){
                    scrollTo(max,0);
                }else{
                    scrollTo(0,0);
                }
                return true;
                //break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("ClipLineaerLayout","onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("ClipLineaerLayout","onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
