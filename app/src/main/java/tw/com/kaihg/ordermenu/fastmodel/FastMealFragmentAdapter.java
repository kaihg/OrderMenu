package tw.com.kaihg.ordermenu.fastmodel;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tw.com.kaihg.ordermenu.Enums;
import tw.com.kaihg.ordermenu.R;

/**
 * Created by NB on 2016/3/21.
 */
public class FastMealFragmentAdapter extends FragmentPagerAdapter {

    private String[] titleArray;

    public FastMealFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        titleArray = context.getResources().getStringArray(R.array.meal_title);
    }

    @Override
    public Fragment getItem(int position) {
        return new FastMainFragment();
    }

    @Override
    public int getCount() {
        return Enums.getMealTypeSize();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }
}
