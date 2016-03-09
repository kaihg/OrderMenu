package tw.com.kaihg.ordermenu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import tw.com.kaihg.ordermenu.services.OrderService;

/**
 * Created by huangkaihg on 2016/3/5.
 */
public class FoodModel implements Serializable {

    @SerializedName("itemTitle")
    private String foodName;
    @SerializedName("itemPrice")
    private int price;
    @SerializedName("imageUrl")
    private String imageUrl;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return OrderService.HOST + imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
