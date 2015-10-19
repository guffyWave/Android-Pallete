package com.appxperts.androidpalleteexperiment;

import android.content.Context;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gufran on 10/19/15.
 */
public class MyAdapter extends BaseAdapter {

    List<Palette.Swatch> swatchList;
    Context context;
    long totalSwatchesPopulation;

    public MyAdapter(List<Palette.Swatch> swatchList, Context context, long totalSwatchesPopulation) {
        this.swatchList = swatchList;
        this.context = context;
        this.totalSwatchesPopulation = totalSwatchesPopulation;
    }

    @Override
    public int getCount() {
        return swatchList.size();
    }

    @Override
    public Object getItem(int i) {
        return swatchList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false);
            holder.view = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Palette.Swatch swatch = swatchList.get(position);

        holder.view.setBackgroundColor(swatch.getRgb());
        holder.view.setTextColor(swatch.getBodyTextColor());
        holder.view.setText("Population: " + ((swatch.getPopulation() * 100) / totalSwatchesPopulation)+" %");

        return convertView;
    }

//    public void sortSwatches() {
//        sort(new Comparator<Palette.Swatch>() {
//            @Override
//            public int compare(Palette.Swatch lhs, Palette.Swatch rhs) {
//                return rhs.getPopulation() - lhs.getPopulation();
//            }
//        });
//    }

    public class ViewHolder {
        TextView view;
    }
}
