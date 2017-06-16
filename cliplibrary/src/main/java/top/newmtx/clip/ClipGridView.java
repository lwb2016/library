package top.newmtx.clip;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by leo on 2017/6/2.
 */

public class ClipGridView extends GridView implements OnExpendParentView{
    public ClipGridView(Context context) {
        super(context);
    }

    public ClipGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClipGridView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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

}
