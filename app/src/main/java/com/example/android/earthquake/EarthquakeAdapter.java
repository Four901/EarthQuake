package com.example.android.earthquake;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> objects) {
        super(context, 0, objects);
    }
    @Override



    public View getView(int position, View ConvertView, ViewGroup parent)
    {
        View listitemview = ConvertView;
        if(listitemview==null)
        {
            listitemview= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        TextView txt1=(TextView)listitemview.findViewById(R.id.primary_location);
        TextView txt2=(TextView)listitemview.findViewById(R.id.location_offset);
        TextView vw=(TextView)listitemview.findViewById(R.id.magnitude);
        TextView tim=(TextView)listitemview.findViewById(R.id.time);

        Date dateObject = new Date(getItem(position).getTimeinmili());
        String formattedTime = formatTime(dateObject);
        String formattedDate = formatDate(dateObject);
        vw.setText(String.valueOf(getItem(position).getMagni()));
        //  txt1.setText(formattedTime);
        txt1.setText(getItem(position).getCity());
        txt2.setText(String.valueOf(formattedDate));
        tim.setText(String.valueOf(getItem(position).getTimeinmili()));
        return listitemview;

    }


    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }


    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}
