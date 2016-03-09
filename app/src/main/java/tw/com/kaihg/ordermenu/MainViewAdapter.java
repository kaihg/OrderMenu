package tw.com.kaihg.ordermenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by huangkaihg on 2016/3/5.
 */
public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.FoodViewHolder> implements View.OnClickListener {

    private List<FoodModel> modelList;
    private MainViewAdapter.Callback callback;
    private Picasso mPicasso;

    public MainViewAdapter(List<FoodModel> modelList, Context context, Callback callback) {
        this.modelList = modelList;
        mPicasso = Picasso.with(context);
        this.callback = callback;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_food_item, parent, false);
        FoodViewHolder holder = new FoodViewHolder(itemView);


        return holder;
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        holder.titleText.setText(modelList.get(position).getFoodName());
        mPicasso.load(modelList.get(position).getImageUrl()).into(holder.foodImage);
        holder.totalView.setTag(position);
        holder.totalView.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    @Override
    public void onClick(View v) {
        FoodModel model = modelList.get((Integer) v.getTag());
        callback.onItemClick(model);
    }



    static class FoodViewHolder extends RecyclerView.ViewHolder {

        View totalView;
        ImageView foodImage;
        TextView titleText;

        public FoodViewHolder(View itemView) {
            super(itemView);
            totalView = itemView;
            foodImage = (ImageView) itemView.findViewById(R.id.foodItem_image);
            titleText = (TextView) itemView.findViewById(R.id.foodItem_title);
        }
    }

    interface Callback {
        void onItemClick(FoodModel model);
    }
}
