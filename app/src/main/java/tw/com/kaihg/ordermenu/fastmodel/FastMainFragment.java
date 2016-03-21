package tw.com.kaihg.ordermenu.fastmodel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tw.com.kaihg.ordermenu.BaseFragment;
import tw.com.kaihg.ordermenu.DividerItemDecoration;
import tw.com.kaihg.ordermenu.Enums;
import tw.com.kaihg.ordermenu.FoodListModel;
import tw.com.kaihg.ordermenu.FoodModel;
import tw.com.kaihg.ordermenu.R;
import tw.com.kaihg.ordermenu.main.MealTabFragment;
import tw.com.kaihg.ordermenu.services.OrderService;

/**
 * Created by NB on 2016/3/21.
 */
public class FastMainFragment extends BaseFragment implements FastMainViewAdapter.Callback{

    private Callback mCallback;
    private FastMainViewAdapter mAdapter;
    private List<FoodModel> foodList = new ArrayList<>();
    private View mProgress;
    private Toolbar mToolbar;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            mCallback = (Callback) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View contentView = inflater.inflate(R.layout.fragment_main, container, false);
        mToolbar = mCallback.getToolbar();
        mProgress = contentView.findViewById(R.id.progressBar);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListView();
        requestFoods();

    }

    private void showLoading(boolean isShowLoading) {
        mProgress.setVisibility(isShowLoading ? View.VISIBLE : View.GONE);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.mainFragment_recyclerView);
        recyclerView.setVisibility(isShowLoading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_fast_action_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("LOG", "main onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.common_order:
                openCommonOrder();//普通點餐
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void openCommonOrder() {
        Fragment fragment = new MealTabFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_content, fragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        bar.setTitle(R.string.app_name);
        bar.setDisplayHomeAsUpEnabled(false);

    }

    private void requestFoods() {
        if (foodList.size() != 0) {
            return;
        }

        showLoading(true);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(OrderService.HOST).addConverterFactory(GsonConverterFactory.create()).build();
        OrderService service = retrofit.create(OrderService.class);
        service.allFoodList().enqueue(new retrofit2.Callback<FoodListModel>() {
            @Override
            public void onResponse(Call<FoodListModel> call, Response<FoodListModel> response) {
                Log.d("LOG", "onResponse " + response.body().getFoodModelList().size());
                showLoading(false);
                List<FoodModel> models = response.body().getFoodModelList();
                addFakeType(models);

                for (FoodModel model :
                        models) {
                    if (model.getMealType() == Enums.MEAL_MAIN_MEAL) {
                        foodList.add(model);
                    }
                }
//                foodList.addAll(models);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FoodListModel> call, Throwable t) {
                Log.d("LOG", "onFailure");
                t.printStackTrace();
            }

            private void addFakeType(List<FoodModel> models) {
                Random random = new Random();
                for (FoodModel model : models) {
                    model.setMealType(random.nextInt(4));
                }
            }
        });
    }




    private void initListView() {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.mainFragment_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new FastMainViewAdapter(foodList, getContext(), this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onItemClick_addToCart(FoodModel model) {
        Log.d("LOG", "onItemClick " + model.getFoodName());
        mCallback.onItemClick_addToCart(model);
    }

    @Override
    public String getTitle() {
        return getString(R.string.side_dish);
    }

    public interface Callback {
        void onItemClick_addToCart(FoodModel foodModel);

        Toolbar getToolbar();
    }
}
