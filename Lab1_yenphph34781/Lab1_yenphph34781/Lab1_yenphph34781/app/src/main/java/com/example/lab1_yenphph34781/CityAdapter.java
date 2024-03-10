package com.example.lab1_yenphph34781;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>{
    private final List<City> cityList;
    private Context context;

    public CityAdapter(List<City> cityList, Context context) {
        this.cityList = cityList;
        this.context = context;
    }

    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, int position) {
        City city = cityList.get(position);
        holder.textViewName.setText(city.getName());
        holder.textViewState.setText(city.getState());

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewState;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewState = itemView.findViewById(R.id.textViewState);
        }
    }
    public void updateCityList(List<City> newList) {
        cityList.clear();
        cityList.addAll(newList);
        notifyDataSetChanged();
    }
}
