package tw.com.kaihg.ordermenu;

import android.support.annotation.IntDef;

/**
 * Created by huangkaihg on 2016/3/18.
 */
public class Enums {

    //    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MEAL_SIDE_DISH, MEAL_MAIN_MEAL, MEAL_DRINK, MEAL_DESSERT, MEAL_PACKAGE})
    public @interface MealType {
    }

    public static final int MEAL_SIDE_DISH = 0;//小菜
    public static final int MEAL_MAIN_MEAL = 1;//主餐
    public static final int MEAL_DRINK = 2;    //飲料
    public static final int MEAL_DESSERT = 3;  //甜點
    public static final int MEAL_PACKAGE = 4;   //套餐

    public static int getMealTypeSize() {
        return 5;
    }
}
