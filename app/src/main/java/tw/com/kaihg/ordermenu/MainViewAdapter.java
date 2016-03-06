package tw.com.kaihg.ordermenu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by huangkaihg on 2016/3/5.
 */
public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.FoodViewHolder> {

    private List<FoodModel> modelList;

    public MainViewAdapter(List<FoodModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new FoodViewHolder(inflater.inflate(R.layout.adapter_food_item, parent, false));
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        // TODO get image
        holder.titleText.setText(modelList.get(position).getFoodName());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImage;
        TextView titleText;

        public FoodViewHolder(View itemView) {
            super(itemView);
            foodImage = (ImageView) itemView.findViewById(R.id.foodItem_image);
            titleText = (TextView) itemView.findViewById(R.id.foodItem_title);
        }
    }
}
