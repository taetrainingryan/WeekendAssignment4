package com.roundarch.codetest.part3;

/**
 * Created by Ryan on 11/12/2017.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.roundarch.codetest.R;
import com.roundarch.codetest.part3.model.Result;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Result> {
    private final Context context;
    private final ArrayList<Result> values;

    public ListAdapter(Context context, ArrayList<Result> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.part3_listview_item, parent, false);

        TextView zipcode = (TextView) rowView.findViewById(R.id.tvZipcode);
        TextView county = (TextView) rowView.findViewById(R.id.tvCounty);
        TextView city = (TextView) rowView.findViewById(R.id.tvCity);

        Result result = values.get(position);
        zipcode.setText(result.getZipcode());
        county.setText(result.getCounty());
        city.setText(result.getCity());

        return rowView;
    }
}