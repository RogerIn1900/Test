package com.example.test2.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test2.R;
import com.example.test2.data.WeatherDataOfDay;

import java.util.List;

public class WeatherOfDayAdapter
        extends RecyclerView.Adapter<WeatherOfDayAdapter.ViewHolder>{

    private List<WeatherDataOfDay> itemList;

    public WeatherOfDayAdapter(List<WeatherDataOfDay> itemList) {
        this.itemList = itemList;
    }

    // ViewHolder 类，用于缓存视图
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView forecastDayLabel,forecastDateLabel,forecastConditionLabel,
                forecastHighTemp,forecastLowTemp;
        ImageView forecastIcon;
        LinearLayout weatherOfDayItem;


        public ViewHolder(View itemView) {
            super(itemView);
            forecastDayLabel = itemView.findViewById(R.id.forecastDayLabel);
            forecastDateLabel = itemView.findViewById(R.id.forecastDateLabel);
            forecastConditionLabel = itemView.findViewById(R.id.forecastConditionLabel);
            forecastHighTemp = itemView.findViewById(R.id.forecastHighTemp);
            forecastLowTemp = itemView.findViewById(R.id.forecastLowTemp);
            forecastIcon = itemView.findViewById(R.id.forecastIcon);
            weatherOfDayItem = itemView.findViewById(R.id.weatherOfDayItem);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherOfDayAdapter.ViewHolder holder, int position) {
        WeatherDataOfDay item = itemList.get(position);
        holder.forecastDayLabel.setText(item.getDayLabel());
        holder.forecastDateLabel.setText(item.getDateLabel());
        holder.forecastIcon.setContentDescription(item.getCondition());
        holder.forecastConditionLabel.setText(item.getCondition());
        holder.forecastHighTemp.setText(item.getHighTemp());
        holder.forecastLowTemp.setText(item.getLowTemp());

        holder.weatherOfDayItem.setOnLongClickListener(v ->{
            item.getAction().run();
            return true;
        }
        );
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast_row,parent,false);
        return new ViewHolder(view);
    }
}
