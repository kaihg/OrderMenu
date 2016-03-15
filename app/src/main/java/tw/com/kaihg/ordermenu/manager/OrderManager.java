package tw.com.kaihg.ordermenu.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import tw.com.kaihg.ordermenu.FoodModel;

/**
 * Created by huangkaihg on 2016/3/14.
 */
public class OrderManager {
    private static final String SHARED_PREF_KEY = "SHARED_PREF_KEY";
    private static final String SHARED_PREF_ORDER_LIST = "SHARED_PREF_ORDER_LIST";
    private static OrderManager mInstance;

    private SharedPreferences mPref;
    private Gson mGson;

    public static OrderManager getInstance() {
        if (mInstance == null) {
            mInstance = new OrderManager();
        }
        return mInstance;
    }

    private OrderManager() {
        mGson = new Gson();
    }

    public void init(Context context) {
        mPref = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
    }

    public List<FoodModel> getOrderList() {
        String listJson = mPref.getString(SHARED_PREF_ORDER_LIST, "[]");
        return mGson.fromJson(listJson, new TypeToken<List<FoodModel>>() {
        }.getType());
    }

    public void addToCart(FoodModel food) {
        List<FoodModel> list = getOrderList();
        list.add(food);
        mPref.edit().putString(SHARED_PREF_ORDER_LIST, mGson.toJson(list)).apply();
    }

    public void removeItem(FoodModel food) {
        List<FoodModel> list = getOrderList();
        list.add(food);
        mPref.edit().putString(SHARED_PREF_ORDER_LIST, mGson.toJson(list)).apply();

    }
    public int getOrdersCount() {
        return getOrderList().size();
    }

    public void clearOrders() {
        mPref.edit().putString(SHARED_PREF_ORDER_LIST, null).apply();
    }
}
