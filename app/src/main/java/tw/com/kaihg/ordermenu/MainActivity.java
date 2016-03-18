package tw.com.kaihg.ordermenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import tw.com.kaihg.ordermenu.detail.FoodDetailFragment;
import tw.com.kaihg.ordermenu.foodlist.OrdersFragment;
import tw.com.kaihg.ordermenu.main.MealTabFragment;
import tw.com.kaihg.ordermenu.manager.OrderManager;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback, FoodDetailFragment.OnFragmentInteractionListener, OrdersFragment.Callback {

    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolBar);
        mToolBar.inflateMenu(R.menu.main_action_menu);
        mToolBar.setTitle(R.string.app_name);

        Fragment fragment = new MealTabFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, fragment).commit();

//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter(new MealFragmentAdapter(getSupportFragmentManager(),this));
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
//        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_action_edit:
                openOrderList();
                return true;
        }
        Log.d("LOG", "activity " + item.getTitle());
        return false;
    }

    private void openOrderList() {
        if (OrderManager.getInstance().getOrdersCount() == 0) {
            Toast.makeText(MainActivity.this, "目前沒有訂單", Toast.LENGTH_SHORT).show();
            return;
        }
        OrdersFragment fragment = OrdersFragment.newInstance(null, null);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).addToBackStack(null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public void openFoodDetail(FoodModel foodModel) {
        FoodDetailFragment foodDetailFragment = FoodDetailFragment.newInstance(foodModel);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, foodDetailFragment).addToBackStack(null).commit();
    }

    @Override
    public void addToCart(FoodModel foodModel) {
        Toast.makeText(this, "加入訂單", Toast.LENGTH_SHORT).show();
        OrderManager.getInstance().addToCart(foodModel);
    }

    @Override
    public Toolbar getToolbar() {
        return mToolBar;
    }
}
