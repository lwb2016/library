package top.newmtx.clip;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by leo on 2017/6/2.
 */

public class ClipHelper implements OnExpendParentView,OnParentHelper{

    private OnExpendChildView lastExpendView;

    @Override
    public synchronized void setChild(OnExpendChildView child) {
        if(lastExpendView!=null&&child!=lastExpendView){
            lastExpendView.doExpend(false);
        }
        lastExpendView=child;
    }

    private boolean isIntecept=false;

    private boolean shouldCheckChildBeforeTouch=true;

    @Override
    public void setInterceptBeforeTouch(boolean intercept) {
        this.shouldCheckChildBeforeTouch = intercept;
    }

    @Override
    public boolean onInterceptClipTouchEvent(MotionEvent ev) {
        if(shouldCheckChildBeforeTouch){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(lastExpendView!=null&&lastExpendView.isExpend()){
                        lastExpendView.doExpend(false);
                        lastExpendView=null;
                        isIntecept=true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(isIntecept){
                        isIntecept=false;
                        return true;
                    }
                    break;
            }
            if(isIntecept)
                return true;
        }
        return false;
    }

    public boolean isOnTouchItem(MotionEvent event){
        if(event.getAction()!=MotionEvent.ACTION_DOWN)
            return !isIntecept;
        if(lastExpendView!=null&&lastExpendView instanceof View){
            View view = (View)lastExpendView;

            float x = event.getX();
            float y = event.getY();

            int left = view.getLeft();
            int top = view.getTop();
            int right = view.getRight();
            int bottom = view.getBottom();
            if(left<x&&x<right&&y>top&&y<bottom)
                return true;
        }
        return false;
    }
}
