package com.example.market.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.market.GraphActivity;
import com.example.market.MarketActivity;
import com.example.market.R;
import com.example.market.models.model_coin_list;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CoinListAdapter extends ArrayAdapter<model_coin_list>{

    private final ArrayList<model_coin_list> dataSet;
    private final MarketActivity activity;

    // View lookup cache
    private static class ViewHolder {
        private TextView coin_name;
        private TextView coin_symbol;
        private TextView coin_price;
        private TextView change_24h;
        private ImageView coin_icon;
    }

    public CoinListAdapter(ArrayList<model_coin_list> data, MarketActivity activity) {
        super(activity, R.layout.list_coin_item, data);
        this.dataSet = data;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            ViewHolder viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_coin_item, parent, false);

            viewHolder.coin_icon = convertView.findViewById(R.id.imv_coin_icon);
            viewHolder.coin_name = convertView.findViewById(R.id.tv_coin_name);
            viewHolder.coin_symbol = convertView.findViewById(R.id.tv_coin_symbol);
            viewHolder.coin_price = convertView.findViewById(R.id.tv_coin_price);
            viewHolder.change_24h =  convertView.findViewById(R.id.tv_price_change);

            model_coin_list list_item = dataSet.get(position);
            viewHolder.coin_name.setText(list_item.getCoin_name());
            viewHolder.coin_symbol.setText(list_item.getCoin_symbol());
            viewHolder.coin_price.setText(String.valueOf(list_item.getCoin_price()));
            viewHolder.change_24h.setText(String.valueOf(list_item.getChange_24h()));

            Glide.with(activity).load(list_item.getIcon()).into(viewHolder.coin_icon);
            simulateRandom(viewHolder,list_item);

            convertView.setOnClickListener(v-> {
                Intent intent = new Intent(activity, GraphActivity.class);
                intent.putExtra("coin_symbol", list_item.getCoin_symbol());
                activity.startActivity(intent);
            });
        }
        return convertView;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void simulateRandom(ViewHolder viewHolder, model_coin_list list_item){

        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(() -> {
            double old_price = list_item.getCoin_price();
            Random r = new Random();
            double rangeMin = -5.00;
            double rangeMax = 5.00;
            double percent_fluctuation = rangeMin + (rangeMax - rangeMin) * r.nextDouble();

            double new_price = old_price+(old_price*percent_fluctuation/100);
            viewHolder.coin_price.setText(String.format("%.4f", new_price));
            list_item.setCoin_price(new_price);

            viewHolder.change_24h.setText(String.format("%.2f", percent_fluctuation)+"%");

            if(percent_fluctuation>0)
                viewHolder.change_24h.setBackgroundColor(
                        ContextCompat.getColor(activity,R.color.colorGreen));
            else if(percent_fluctuation<0)
                viewHolder.change_24h.setBackgroundColor(
                        ContextCompat.getColor(activity,R.color.colorRed));
            else
                viewHolder.change_24h.setBackgroundColor(
                        ContextCompat.getColor(activity,R.color.colorGrayLight));

        }, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public int getViewTypeCount() {
        if(dataSet.size()==0){
            return 1;
        }
        return dataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
