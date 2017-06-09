package east2d.com.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by leo on 2017/5/10.
 */

public class ChildFrameLayout extends FrameLayout {
    private Float radius=0f;

    public ChildFrameLayout(Context context) {
        super(context);
    }

    public ChildFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHeight();
    }

    /**
     * 获取当前的角度
     * @return
     */
    public Float getRadius() {
        return radius;
    }

    /**
     * 设置角度
     * @param radius
     */
    public void setRadius(Float radius) {
        radius=(radius%360+360)%360;   //保持角度的范围在0-360之间
        this.radius = radius;
    }

    /**
     * 增加角度
     * @param radius
     */
    public void addRadius(Float radius){
        setRadius(this.radius+radius);
    }
}
