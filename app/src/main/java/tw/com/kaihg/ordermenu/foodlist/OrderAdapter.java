package tw.com.kaihg.ordermenu.foodlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tw.com.kaihg.ordermenu.FoodModel;
import tw.com.kaihg.ordermenu.R;

/**
 * Created by huangkaihg on 2016/3/15.
 */
class OrderAdapter extends BaseAdapter implements View.OnClickListener{

    private List<FoodModel> modelList;
    private Picasso picasso;
    private OrderAdapter.Callback callback;
    public OrderAdapter(List<FoodModel> modelList, Context context, Callback callback) {
        this.modelList = modelList;
        picasso = Picasso.with(context);
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public FoodModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        bindView(holder, position);

        return convertView;
    }

    private void bindView(ViewHolder holder, int position) {
        FoodModel model = modelList.get(position);
        picasso.load(model.getImageUrl()).placeholder(R.drawable.default_food).into(holder.foodImage);
        holder.titleText.setText(model.getFoodName());
        holder.priceText.setText("$" + model.getPrice());
        holder.removeButton.setTag(position);
        holder.removeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FoodModel model = modelList.get((Integer) v.getTag());
        if (v.getId() == R.id.foodItem_removeButton) {
            callback.removeItem(model);
            return;
        }
    }

    private class ViewHolder {
        View totalView;
        ImageView foodImage;
        TextView titleText;
        TextView priceText;
        View removeButton;

        public ViewHolder(View itemView) {
            totalView = itemView;
            foodImage = (ImageView) itemView.findViewById(R.id.foodItem_image);
            titleText = (TextView) itemView.findViewById(R.id.foodItem_title);
            priceText = (TextView) itemView.findViewById(R.id.foodItem_price);
            removeButton = itemView.findViewById(R.id.foodItem_removeButton);
        }
    }

    interface Callback {
        void removeItem(FoodModel model);
    }
}
