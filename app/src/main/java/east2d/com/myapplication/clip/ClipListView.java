package east2d.com.myapplication.clip;

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
