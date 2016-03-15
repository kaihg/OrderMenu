package tw.com.kaihg.ordermenu;

import android.support.v7.widget.Toolbar;

/**
 * Created by huangkaihg on 2016/3/14.
 */
public interface ActivityCallback {
    void addToCart(FoodModel model);

    Toolbar getToolbar();
}
