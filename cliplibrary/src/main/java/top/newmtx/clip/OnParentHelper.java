package top.newmtx.clip;

import android.view.MotionEvent;

/**
 * 监听
 * Created by leo on 2017/6/2.
 */

public interface OnParentHelper {
    void setInterceptBeforeTouch(boolean intercept);
    boolean onInterceptClipTouchEvent(MotionEvent ev);
}
