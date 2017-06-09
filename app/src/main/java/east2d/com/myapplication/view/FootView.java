package east2d.com.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.callback.IFooterCallBack;

import east2d.com.myapplication.R;

/**
 * Created by leo on 2017/4/27.
 */

public class FootView extends FrameLayout implements IFooterCallBack {
    TextView mTextView;
    public FootView(Context context) {
        super(context);
        initView(context);
    }

    public FootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        View view= LayoutInflater.from(context).inflate(R.layout.load_footer,null);
        mTextView= (TextView) view.findViewById(R.id.tv_load);
        addView(view);
    }

    @Override
    public void callWhenNotAutoLoadMore(final XRefreshView xRefreshView) {
        mTextView.setText("点击加载");
        mTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                xRefreshView.notifyLoadMore();
            }
        });
    }

    @Override
    public void onStateReady() {
        mTextView.setText("准备加载");
    }

    @Override
    public void onStateRefreshing() {
        mTextView.setText("开始加载");
    }

    @Override
    public void onReleaseToLoadMore() {
        mTextView.setText("正在加载中。。");
    }

    @Override
    public void onStateFinish(boolean hidefooter) {
        mTextView.setText("加载完成1"+hidefooter);
    }

    @Override
    public void onStateComplete() {
        mTextView.setText("加载完成2");
    }

    @Override
    public void show(boolean show) {
        if(show){
            setVisibility(VISIBLE);
        }else{
            setVisibility(GONE);
        }
    }

    @Override
    public boolean isShowing() {
        return getVisibility()!=GONE;
    }

    @Override
    public int getFooterHeight() {
        return getHeight();
    }
}
