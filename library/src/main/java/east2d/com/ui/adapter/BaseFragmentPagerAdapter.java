package east2d.com.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * viewpager的适配器
 * Created by leo on 2017/3/6.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    final ArrayList<Fragment> mFragments=new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(Fragment fragment){
        mFragments.add(fragment);
    }

    public void clear(){
        mFragments.clear();
    }
}
