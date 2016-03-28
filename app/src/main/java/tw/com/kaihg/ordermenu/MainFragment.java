package tw.com.kaihg.ordermenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import tw.com.kaihg.ordermenu.services.OrderService;

/**
 * Created by huangkaihg on 2016/3/9.
 */
public class MainFragment extends BaseFragment implements MainViewAdapter.Callback, SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private Callback mCallback;
    private MainViewAdapter mAdapter;
    private List<FoodModel> foodList = new ArrayList<>();
    private Toolbar mToolbar;
    private View mProgress;
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
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_action_menu, menu);

        final MenuItem item = menu.findItem(R.id.my_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("LOG", "main onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
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

                for (FoodModel model : models) {
                    if (model.getMealType() == Enums.MEAL_SIDE_DISH) {
                        foodList.add(model);
                    }
                }
                Log.d("MainFragment", "foodList: " + foodList);
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
        recyclerView = (RecyclerView) getView().findViewById(R.id.mainFragment_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new MainViewAdapter(foodList, getActivity(), this);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onItemClick(FoodModel model) {
        mCallback.openFoodDetail(model);
    }

    @Override
    public void addToCart(FoodModel model) {
        mCallback.addToCart(model);
    }

    @Override
    public String getTitle() {
        return getString(R.string.side_dish);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<FoodModel> filteredModelList = filter(foodList, query);
        Log.d("MainFragment","filteredModelList: "+filteredModelList);
        mAdapter.setFilter(filteredModelList);
        recyclerView.scrollToPosition(0);
        return true;
    }

    private List<FoodModel> filter(List<FoodModel> models, String query) {
        query = query.toLowerCase();

        final List<FoodModel> filteredModelList = new ArrayList<>();
        for (FoodModel model : models) {
            final String text = model.getFoodName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    interface Callback {
        void openFoodDetail(FoodModel foodModel);
        void addToCart(FoodModel foodModel);

        Toolbar getToolbar();
    }
}
