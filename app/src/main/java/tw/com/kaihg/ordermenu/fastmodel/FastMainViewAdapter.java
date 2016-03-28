package tw.com.kaihg.ordermenu.fastmodel;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tw.com.kaihg.ordermenu.FoodModel;
import tw.com.kaihg.ordermenu.R;

/**
 * Created by NB on 2016/3/21.
 */
public class FastMainViewAdapter extends RecyclerView.Adapter<FastMainViewAdapter.FoodViewHolder> implements View.OnClickListener{
    private List<FoodModel> modelList;
    private FastMainViewAdapter.Callback callback;
    private Resources mRes;

    public FastMainViewAdapter(List<FoodModel> modelList, Context context, Callback callback) {
        this.modelList = modelList;
        this.callback = callback;
        mRes = context.getResources();
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_fastorder_item, parent, false);
        FoodViewHolder holder = new FoodViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        holder.titleText.setText(modelList.get(position).getFoodName());
        holder.priceText.setText(mRes.getString(R.string.food_price_dollar, modelList.get(position).getPrice()));
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
        callback.onItemClick_addToCart(model);
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        View totalView;
        TextView titleText;
        TextView priceText;

        public FoodViewHolder(View itemView) {
            super(itemView);
            totalView = itemView;
            titleText = (TextView) itemView.findViewById(R.id.foodItem_title);
            priceText = (TextView) itemView.findViewById(R.id.foodItem_price);
        }
    }

    public interface Callback {
        void onItemClick_addToCart(FoodModel model);
    }
}
