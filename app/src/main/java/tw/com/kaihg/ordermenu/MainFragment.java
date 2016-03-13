package tw.com.kaihg.ordermenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tw.com.kaihg.ordermenu.services.OrderService;

/**
 * Created by huangkaihg on 2016/3/9.
 */
public class MainFragment extends Fragment implements MainViewAdapter.Callback {

    private Callback mCallback;
    private MainViewAdapter mAdapter;
    private List<FoodModel> foodList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            mCallback = (Callback) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View contentView = inflater.inflate(R.layout.fragment_main, container, false);

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListView();
        requestFoods();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);

    }

    private void requestFoods() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(OrderService.HOST).addConverterFactory(GsonConverterFactory.create()).build();
        OrderService service = retrofit.create(OrderService.class);
        service.allFoodList().enqueue(new retrofit2.Callback<FoodListModel>() {
            @Override
            public void onResponse(Call<FoodListModel> call, Response<FoodListModel> response) {
                Log.d("LOG", "onResponse " + response.body().getFoodModelList().size());
                foodList.addAll(response.body().getFoodModelList());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FoodListModel> call, Throwable t) {
                Log.d("LOG", "onFailure");
                t.printStackTrace();
            }
        });

    }

    private void initListView() {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.mainFragment_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new MainViewAdapter(foodList, getContext(), this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onItemClick(FoodModel model) {
        Log.d("LOG", "onItemClick " + model.getFoodName());
        mCallback.openFoodDetail(model);
    }

    interface Callback {
        void openFoodDetail(FoodModel foodModel);
    }
}
