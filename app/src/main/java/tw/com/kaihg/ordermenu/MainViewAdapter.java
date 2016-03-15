package tw.com.kaihg.ordermenu;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private Resources mRes;

    public MainViewAdapter(List<FoodModel> modelList, Context context, Callback callback) {
        this.modelList = modelList;
        mPicasso = Picasso.with(context);
        this.callback = callback;
        mRes = context.getResources();
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_food_list_item, parent, false);
        FoodViewHolder holder = new FoodViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        holder.titleText.setText(modelList.get(position).getFoodName());
        holder.priceText.setText(mRes.getString(R.string.food_price_dollar, modelList.get(position).getPrice()));
        mPicasso.load(modelList.get(position).getImageUrl()).placeholder(R.drawable.default_food).into(holder.foodImage);
        holder.totalView.setTag(position);
        holder.totalView.setOnClickListener(this);
        holder.addToCartButton.setTag(position);
        holder.addToCartButton.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public void onClick(View v) {
        FoodModel model = modelList.get((Integer) v.getTag());
        if (v.getId() == R.id.foodItem_addToCartButton) {
            callback.addToCart(model);
            return;
        }

        callback.onItemClick(model);
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        View totalView;
        ImageView foodImage;
        TextView titleText;
        TextView priceText;
        ImageButton addToCartButton;

        public FoodViewHolder(View itemView) {
            super(itemView);
            totalView = itemView;
            foodImage = (ImageView) itemView.findViewById(R.id.foodItem_image);
            titleText = (TextView) itemView.findViewById(R.id.foodItem_title);
            priceText = (TextView) itemView.findViewById(R.id.foodItem_price);
            addToCartButton = (ImageButton) itemView.findViewById(R.id.foodItem_addToCartButton);
        }
    }

    interface Callback {
        void onItemClick(FoodModel model);

        void addToCart(FoodModel model);
    }
}
