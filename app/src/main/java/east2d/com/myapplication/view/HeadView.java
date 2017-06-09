package east2d.com.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.callback.IFooterCallBack;
import com.andview.refreshview.callback.IHeaderCallBack;

import east2d.com.myapplication.R;

/**
 * Created by leo on 2017/4/27.
 */

public class HeadView extends FrameLayout implements IHeaderCallBack {
    TextView mTextView;
    public HeadView(Context context) {
        super(context);
        initView(context);
    }

    public HeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        View view= LayoutInflater.from(context).inflate(R.layout.load_footer,null);
        mTextView= (TextView) view.findViewById(R.id.tv_load);
        addView(view);
    }

    @Override
    public void onStateNormal() {
        mTextView.setText("正常状态");
    }

    @Override
    public void onStateReady() {
        mTextView.setText("准备加载");
    }

    @Override
    public void onStateRefreshing() {
        mTextView.setText("正在加载中。。。");
    }

    @Override
    public void onStateFinish(boolean success) {
        mTextView.setText("加载完成");
    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY, int deltaY) {

    }

    @Override
    public void setRefreshTime(long lastRefreshTime) {
        mTextView.append("   "+lastRefreshTime);
    }

    @Override
    public void hide() {
        setVisibility(GONE);
    }

    @Override
    public void show() {
        setVisibility(VISIBLE);
    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}
