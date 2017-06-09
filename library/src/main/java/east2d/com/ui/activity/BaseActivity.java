package east2d.com.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * activity的基类
 * Created by leo on 2017/6/8.
 */

public class BaseActivity extends AppCompatActivity {

    protected Context mContext=BaseActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //提示弹窗
    /**
     * @param msg
     */
    protected void toastShow(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    protected void toastShow(int msgRes){
        Toast.makeText(mContext,msgRes,Toast.LENGTH_SHORT).show();
    }

    private FrameLayout root_view;

    /**
     * 获取activity中最外层的Framelayout(这个view包含statusBar)
     * @return
     */
    private FrameLayout getRootView(){
        if(root_view==null){
            root_view = (FrameLayout) findViewById(Window.ID_ANDROID_CONTENT);
        }
        return root_view;
    }
}
