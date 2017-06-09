package east2d.com.myapplication.clip;

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
        boolean b = mClipHelper.onInterceptClipTouchEvent(ev);
        if(b)
            return true;
        return super.dispatchTouchEvent(ev);
    }

//    private OnExpendChildView lastExpendView;
//
//    @Override
//    public void setChild(OnExpendChildView child) {
//        if(lastExpendView!=null&&child!=lastExpendView){
//            lastExpendView.doExpend(false);
//        }
//        lastExpendView=child;
//    }
//
//    private boolean isIntecept=false;
//
//    private boolean shouldCheckChildBeforeTouch=false;
//
//    public void setShouldCheckChildBeforeTouch(boolean shouldCheckChildBeforeTouch) {
//        this.shouldCheckChildBeforeTouch = shouldCheckChildBeforeTouch;
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if(shouldCheckChildBeforeTouch){
//            switch (ev.getAction()){
//                case MotionEvent.ACTION_DOWN:
//                    if(lastExpendView!=null&&lastExpendView.isExpend()){
//                        lastExpendView.doExpend(false);
//                        lastExpendView=null;
//                        isIntecept=true;
//                    }
//                    break;
//                case MotionEvent.ACTION_UP:
//                    if(isIntecept){
//                        isIntecept=false;
//                        return true;
//                    }
//                    break;
//            }
//            if(isIntecept)
//                return true;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}
