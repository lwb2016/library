package top.newmtx.clip;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by leo on 2017/6/2.
 */

public class ClipListView extends ListView implements OnExpendParentView{
    public ClipListView(Context context) {
        super(context);
    }

    public ClipListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClipListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ClipHelper mClipHelper=new ClipHelper();

    @Override
    public void setChild(OnExpendChildView child) {
        mClipHelper.setChild(child);
    }

    public void setInterceptBeforeTouch(boolean intercept) {
        mClipHelper.setInterceptBeforeTouch(intercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!mClipHelper.isOnTouchItem(ev)) {
            boolean b = mClipHelper.onInterceptClipTouchEvent(ev);
            if(b)
                return true;
        }
//        boolean b = mClipHelper.onInterceptClipTouchEvent(ev);
//        if(b)
//            return true;
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        //if(!mClipHelper.isOnTouchItem(ev)) {
//            boolean b = mClipHelper.onInterceptClipTouchEvent(ev);
//            if(b)
//                return true;
//        //}
//        return super.onInterceptTouchEvent(ev);
//    }
}
