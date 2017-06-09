package east2d.com.myapplication.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by leo on 2017/5/23.
 */

public class TopNestScrollView extends NestedScrollView {
    View topView;


    public TopNestScrollView(Context context) {
        super(context);
    }

    public TopNestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopNestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int top=0;

    private void addTopViewListener(){
        setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                top = topView.getTop();
            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
