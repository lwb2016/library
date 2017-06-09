package east2d.com.myapplication.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.andview.refreshview.XRefreshView;

import java.util.ArrayList;
import java.util.List;

import east2d.com.myapplication.R;
import east2d.com.myapplication.view.FootView;
import east2d.com.myapplication.view.HeadView;
import east2d.com.ui.activity.BaseActivity;

/**
 * Created by leo on 2017/4/27.
 */

public class XRecycleViewDemoUi extends BaseActivity{
    XRefreshView mXRefreshView;
    RecyclerView mRecyclerView;

    private View header_view,footer_view;

    //private DemoRecycleAdapter mRecycleAdapter;
    private DemoTypeRecycleAdapter mRecycleAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xrecycle_ui);
        header_view= LayoutInflater.from(mContext).inflate(R.layout.refresh_header,null);
        footer_view= LayoutInflater.from(mContext).inflate(R.layout.load_footer,null);

        mRecyclerView= (RecyclerView) findViewById(R.id.rv_list);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));

        mRecycleAdapter=new DemoTypeRecycleAdapter(this,"0",null);
        //mRecycleAdapter=new DemoRecycleAdapter(this,getData());

//        mRecycleAdapter.setOnItemClickListener(new RVTypeBaseAdapter.OnItemClickListener<String>() {
//            @Override
//            public void onItemClick(View view, String s) {
//                toastShow(s+":CLICK");
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, String s) {
//                toastShow(s+":LONG");
//                return true;
//            }
//        });

        mRecyclerView.setAdapter(mRecycleAdapter);


        mXRefreshView= (XRefreshView) findViewById(R.id.xv_refresh);

        mXRefreshView.setAutoLoadMore(true);
        mXRefreshView.setAutoRefresh(false);

        //mXRefreshView.setCustomFooterView(footer_view);
        mXRefreshView.setCustomHeaderView(new HeadView(mContext));

        //mXRefreshView.setEmptyView();

        mXRefreshView.setPinnedTime(1000);
        mXRefreshView.setMoveForHorizontal(true);

        mXRefreshView.setLoadComplete(false);

        mXRefreshView.setCustomFooterView(new FootView(mContext));

        //TextView textView = new TextView(this);
        //textView.setText("EMPTY");


        //mXRefreshView.setEmptyView(textView);

        mXRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                final List<String> list = loadData(true);
                if(list==null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toastShow("已经到顶了");
                            mXRefreshView.stopRefresh(true);
                            //mXRefreshView.setPullRefreshEnable(false);
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int data = mRecycleAdapter.addHeadData(String.valueOf(num), list, true);
                            //int data = mRecycleAdapter.addHeadData(list, true);
                            mRecyclerView.scrollToPosition(data);
                            mXRefreshView.stopRefresh(true);
                        }
                    });
                }
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                final List<String> list = loadData(false);
                if(list==null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toastShow("已经到底了");
                            mXRefreshView.stopLoadMore();
                            //mXRefreshView.setLoadComplete(true);
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecycleAdapter.addFootData(String.valueOf(num2),list,true);
                            //mRecycleAdapter.addFootData(list,true);
                            //mXRefreshView.setLoadComplete(true);
                            mXRefreshView.stopLoadMore();
                        }
                    });
                }
            }
        });

        //mXRefreshView.enableEmptyView(true);
    }

    private int num=-1;

    private int num2=1;


    protected List<String> loadData(boolean isUp){
        if(isUp){
            if(num<-5){
                return null;
            }
            num--;
        }else{
            if(num2>5){
                return null;
            }
            num2++;
        }
        return getData();
    }

    protected List<String> getData(){
        ArrayList<String> list=new ArrayList<>();
        for(int i=1;i<15;i++){
            list.add("TEST"+i);
        }
        return list;
    }


}
