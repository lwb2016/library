package east2d.com.myapplication.demo;

import android.app.LauncherActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import east2d.com.myapplication.gl.MainActivity;

import com.oacg.adbase.Constants;
import com.oacg.h5.GameFullWebUi;
import com.oacg.h5.GameTitleWebUi;
import com.oacg.service.DownloadNotificationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017/4/25.
 */

public class MainUi extends LauncherActivity{
    private Intent notificationIntent;

    private List<Intent> mIntentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> datas = getDatas();

        mIntentList=getIntents();

        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas));

        notificationIntent = new Intent(this, DownloadNotificationService.class);
        startService(notificationIntent);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected Intent intentForPosition(int position) {
        return mIntentList.get(position);
    }

    @Override
    protected void onDestroy() {
        stopService(notificationIntent);
        super.onDestroy();
    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private static final String URL="http://game.oacg.cn/game_center/v1/index.php?m=Index&a=index&channel_id=1002";

    public List<String> getDatas() {
        ArrayList<String> list=new ArrayList<>();
        list.add("开屏广告测试");//1
        list.add("H5广告测试（无标题）");//2
        list.add("H5广告测试（有标题）");//3
        list.add("H5广告测试（外部链接）");//4
        list.add("列表插入广告测试");//5
        list.add("插屏广告测试");//6
        list.add("SD路径测试");//7
        list.add("上拉加载和下拉刷新测试");//8
        list.add("自定义旋转控件测试");//9
        list.add("OPEN GL 测试");//10
        list.add("底部滚动测试");//11
        list.add("recycleview侧滑滚动测试");//12
        list.add("listview侧滑滚动测试");//13
        list.add("gridview侧滑滚动测试");//14
        list.add("viewpager测试");//15
        list.add("系统图片选择测试");//16
        list.add("viewpager滑动冲突测试");//17
        return list;
    }

    public List<Intent> getIntents() {
        List<Intent> intents=new ArrayList<>();
        intents.add(new Intent(this,SplashAdUi.class));//1

        Intent intent=new Intent(this, GameFullWebUi.class);
        intent.putExtra(Constants.IntentExtra.WEB_URL, URL);
        intents.add(intent);//2

        Intent intent2=new Intent(this, GameTitleWebUi.class);
        intent2.putExtra(Constants.IntentExtra.WEB_URL, URL);
        intents.add(intent2);//3



        Uri uri=Uri.parse(URL);
        Intent intent3=new Intent(Intent.ACTION_VIEW,uri);
        intents.add(intent3);//5

        intents.add(new Intent(this,PlugAdUi.class));//6
        intents.add(new Intent(this,SDDemoUi.class));//7
        intents.add(new Intent(this,XRecycleViewDemoUi.class));//8
        intents.add(new Intent(this,ParentActivity.class));//9
        intents.add(new Intent(this,MainActivity.class));//10
        intents.add(new Intent(this,ScrollDemo.class));//11
        intents.add(new Intent(this,SmoothDemo.class));//12
        intents.add(new Intent(this,SmoothDemo2.class));//13
        intents.add(new Intent(this,ClipDemo3.class));//14
        intents.add(new Intent(this,ClipGridDemo3.class));//15
        intents.add(new Intent(this,ViewPagerUi.class));//15
        intents.add(new Intent(this,PictureUi.class));//16
        intents.add(new Intent(this,FragmentScrollerDemo.class));//17

        return intents;
    }
}
