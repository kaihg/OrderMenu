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
class OrderAdapter extends BaseAdapter {

    private List<FoodModel> modelList;
    private Picasso picasso;

    public OrderAdapter(List<FoodModel> modelList, Context context) {
        this.modelList = modelList;
        picasso = Picasso.with(context);
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

        bindView(holder, getItem(position));

        return convertView;
    }

    private void bindView(ViewHolder holder, FoodModel item) {
        picasso.load(item.getImageUrl()).placeholder(R.drawable.default_food).into(holder.foodImage);

        holder.titleText.setText(item.getFoodName());
        holder.priceText.setText("$" + item.getPrice());
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

}
