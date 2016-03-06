package tw.com.kaihg.ordermenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tw.com.kaihg.ordermenu.services.OrderService;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private MainViewAdapter mAdapter;
    private List<FoodModel> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolBar);
        mToolBar.inflateMenu(R.menu.main_action_menu);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        initListView();
        requestFoods();
    }

    private void requestFoods() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(OrderService.HOST).addConverterFactory(GsonConverterFactory.create()).build();
        OrderService service = retrofit.create(OrderService.class);
        service.allFoodList().enqueue(new Callback<List<FoodModel>>() {
            @Override
            public void onResponse(Call<List<FoodModel>> call, Response<List<FoodModel>> response) {
                Log.d("LOG", "onResponse");
                foodList.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<FoodModel>> call, Throwable t) {
                Log.d("LOG", "onFailure");
                t.printStackTrace();
            }
        });

    }

    private void initListView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mainActivity_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MainViewAdapter(createFakeList());
        recyclerView.setAdapter(mAdapter);
    }

    private List<FoodModel> createFakeList() {
        foodList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            FoodModel model = new FoodModel();
            model.setFoodName(String.format("The %d food", i));
            foodList.add(model);
        }
        return foodList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_action_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
