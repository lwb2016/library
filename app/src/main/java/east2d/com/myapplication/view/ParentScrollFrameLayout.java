package east2d.com.myapplication.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * 该viewgroup的子类必须要是ChildFrameLayout
 * Created by leo on 2017/5/10.
 */

public class ParentScrollFrameLayout extends FrameLayout {

    private int round=200;
    private  float precent=360;
    private float scale=0.5f;
    private int duration=1000;

    private ArrayList<ChildFrameLayout> mLayouts=new ArrayList<>();

    public ParentScrollFrameLayout(Context context) {
        this(context,null);
    }

    public ParentScrollFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener() {
            private int lastX=-1;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        boolean b = event.getRawX() > v.getMeasuredWidth() / 2;
                        if(!isOn){
                            if(b){
                                startAnimation(false);
                            }else{
                                startAnimation(true);
                            }
                            return true;
                        }else{
                            return true;
                        }
//                    case MotionEvent.ACTION_DOWN:
//                        lastX=event.getX();
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        float cur=event.getX();
//                        //boolean b = event.getRawX() > v.getMeasuredWidth() / 2;
////                        if(!isOn){
////                            if(b){
////                                startAnimation(false);
////                            }else{
////                                startAnimation(true);
////                            }
////                            return true;
////                        }else{
////                            return true;
////                        }
//
//                        float v1 = cur - lastX;
//                        if(v1>length) {
//                            if(!isOn){
//                                startAnimation(true);
//                            }
//                            return true;
//                        }else if(v1<-length){
//                            if(!isOn){
//                                startAnimation(false);
//                            }
//                            return true;
//                        }
//                        break;
                }
                return false;
            }
        });
    }

    public float getRound() {
        return round;
    }

    public void setRound(int round) {
        if(round<1){
            round=1;
        }
        this.round = round;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        if(scale>1){
            scale=1;
        }
        this.scale = scale;
    }

    public void initData(){
        int childCount = getChildCount();
        if(childCount>0){
            precent=360*1f/childCount;
        }else{
            return;
        }
        if(mLayouts==null||mLayouts.isEmpty()){
            for(int i=0;i<childCount;i++){
                ChildFrameLayout child = (ChildFrameLayout) getChildAt(i);
                mLayouts.add(child);
            }
        }
        int size = mLayouts.size();
        for(int i=0;i<size;i++){
            ChildFrameLayout childFrameLayout = mLayouts.get(i);
            childFrameLayout.setRadius(precent*i);
            childFrameLayout.setOnClickListener(mOnClickListener);
        }
        resetLayout(0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void resetLayout(float value){
            double maxCos=-2;
            for(ChildFrameLayout layout:mLayouts){
                Float aFloat = layout.getRadius();
                Float values = aFloat+value;
                double v = values*Math.PI/180;
                double sin = Math.sin(v);
                double cos = Math.cos(v);
                double v1 = round * sin;
                layout.setTranslationX((float) (v1));
                float x=0;
                float values2=(values%360+360)%360;
                if(values2>180){
                    x=(360-values2)/180*(1-scale);
                }else{
                    x=values2/180*(1-scale);
                }

                layout.setScaleX(1-x);
                layout.setScaleY(1-x);
                if(cos>maxCos){
                    frontView=layout;
                    layout.bringToFront();
                    maxCos=cos;
                }
            }
        setOnFrontChildClickListener();
    }

    private void setOnFrontChildClickListener(){
        for(ChildFrameLayout layout:mLayouts){
            layout.setClickable(false);
        }
        if(frontView!=null){
            frontView.setClickable(true);
        }
    }

    private OnClickListener mOnClickListener;

    public void setOnFrontChildClickListener(OnClickListener onFrontChildClickListener){
        mOnClickListener=onFrontChildClickListener;
    }
    private boolean isOn=false;
    private ChildFrameLayout frontView=null;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void startAnimation(final float radius){
        ValueAnimator animator=ValueAnimator.ofFloat(0,1f);
        animator.setDuration(getDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float values = ((float) animation.getAnimatedValue())*radius;
                Log.i("Angle_Test","values:"+values);
                resetLayout(values);
                if(mFrontViewChangeListener!=null){
                    mFrontViewChangeListener.onProgressChanging(values);
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isOn=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                for(ChildFrameLayout layout:mLayouts){
                    layout.setRadius(layout.getRadius()+radius);
                }
                if(mFrontViewChangeListener!=null){
                    mFrontViewChangeListener.onChanged(frontView,mLayouts.indexOf(frontView));
                }
                isOn=false;
                startAnimation(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public void startAnimation(boolean isLeft){
        if(mLayouts==null||mLayouts.isEmpty())
            return;
        int size = mLayouts.size();
        if(isLeft){
            startAnimation(360/size);
        }else{
            startAnimation(-360/size);
        }
    }

    private OnFrontViewChangeListener mFrontViewChangeListener;

    public void setOnFrontViewChangeListener(OnFrontViewChangeListener frontViewChangeListener) {
        mFrontViewChangeListener = frontViewChangeListener;
    }

    public interface OnFrontViewChangeListener{
        void onChanged(ChildFrameLayout layout, int index);
        void onProgressChanging(float progress);
    }

}
