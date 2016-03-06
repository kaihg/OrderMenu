package tw.com.kaihg.ordermenu;

import java.io.Serializable;

/**
 * Created by huangkaihg on 2016/3/5.
 */
public class FoodModel implements Serializable {

    private String foodName;
    private int price;
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
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
