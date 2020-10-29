package com.pakaranjing.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.pakaranjing.R;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] color;


    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid,String[] color) {
        super(context, R.layout.grid_view_item, itemname);

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
        this.color = color;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.grid_view_item, null, true);

        TextView txtTitle = rowView.findViewById(R.id.category_title);
        ImageView imageView = rowView.findViewById(R.id.category_image);
        CardView inicolorv = rowView.findViewById(R.id.cardView);
        inicolorv.setCardBackgroundColor(Color.parseColor(color[position]));
        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        return rowView;

    }


    ;
}
