package east2d.com.myapplication.demo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import east2d.com.myapplication.R;
import east2d.com.myapplication.fragment.BlankFragment;
import east2d.com.myapplication.fragment.ScrollFragment;
import east2d.com.ui.adapter.BaseFragmentPagerAdapter;

public class FragmentScrollerDemo extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;

    private String[] mTitleList = new String[]{"收藏","下载"};//页卡标题集合

    private BaseFragmentPagerAdapter mBaseFragmentPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_scroller_demo);

        mTabLayout= (TabLayout) findViewById(R.id.tabs);
        mViewPager= (ViewPager) findViewById(R.id.vp_fragment);

        //mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList[0]));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList[1]));


        mBaseFragmentPagerAdapter=new BaseFragmentPagerAdapter(getSupportFragmentManager());
        mBaseFragmentPagerAdapter.addFragment(new BlankFragment());
        mBaseFragmentPagerAdapter.addFragment(new ScrollFragment());

        mViewPager.setAdapter(mBaseFragmentPagerAdapter);


        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        //mTabLayout.setTabsFromPagerAdapter(mBaseFragmentPagerAdapter);//给Tabs设置适配器

        mViewPager.setCurrentItem(0);
    }
}
