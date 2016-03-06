package tw.com.kaihg.ordermenu.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import tw.com.kaihg.ordermenu.FoodModel;

/**
 * Created by huangkaihg on 2016/3/5.
 */
public interface OrderService {
    public static final String HOST = "http://test.quickpick.com.tw/api/json/";

    @GET("Item_Hot_List.php?Identifier=1&page=1")
    Call<List<FoodModel>> allFoodList();

    @GET("")
    Call<List<FoodModel>> orderList();
}
