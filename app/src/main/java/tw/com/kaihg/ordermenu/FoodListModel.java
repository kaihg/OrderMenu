package tw.com.kaihg.ordermenu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangkaihg on 2016/3/6.
 */
public class FoodListModel implements Serializable {
    @SerializedName("itemHotList")
    private List<FoodModel> foodModelList;

    public List<FoodModel> getFoodModelList() {
        return foodModelList;
    }

    public void setFoodModelList(List<FoodModel> foodModelList) {
        this.foodModelList = foodModelList;
    }
}
