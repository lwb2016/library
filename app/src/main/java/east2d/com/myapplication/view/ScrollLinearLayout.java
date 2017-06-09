package east2d.com.myapplication.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by leo on 2017/6/1.
 */

public class ScrollLinearLayout extends LinearLayout {
    public ScrollLinearLayout(Context context) {
        super(context);
    }

    public ScrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScrollLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private RecyclerView mRecyclerView;

    public void setRecycleView(RecyclerView recycleView){
        if(mRecyclerView==null&&recycleView!=null){
            mRecyclerView=recycleView;
            mRecyclerView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    Log.i("SmoothDemo","onLayoutChange top:"+top+" oldTop:"+oldTop);
                }
            });

            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                RecyclerView.LayoutManager mLayoutManager;
                private int scrollY=0;

                View childAt;
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    scrollY+=dy;
                    Log.i("SmoothDemo","start scrollY:"+scrollY+"y:"+dy);
                    if(scrollY<=minHeight){
                        Log.i("SmoothDemo","scroll scrollY:"+scrollY+"y:"+dy);
                        scrollBy(dx,dy);
                    }

//                    if(mLayoutManager==null){
//                        mLayoutManager=recyclerView.getLayoutManager();
//                    }
//                    if(childAt==null){
//                        childAt = mLayoutManager.getChildAt(0);
//                    }
//                    int bottom = childAt.getBottom();
//                    if(bottom>=0){
//                        Log.i("SmoothDemo","bottom:"+bottom+"y:"+dy);
//                        scrollBy(dx,dy);
//                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });

        }
    }

    private int minHeight,maxHeight;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(getChildCount()>0){
            minHeight = getChildAt(0).getHeight();
        }
        maxHeight=getHeight();
    }

    @Override
    public void scrollBy(int x, int y) {
        int v = getScrollY() + y;
        if(v<0){
            v=0;
        }else if(v>minHeight){
            v=-minHeight;
        }
        Log.i("SmoothDemo","getScrollY:"+v);
        scrollTo(getScrollX(),v);
        //super.scrollBy(x, y);
    }

}
