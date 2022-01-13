package com.example.android.earthquake;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

public class Earthquake implements Adapter {
    public String city;
    public double magni;
    public String date;
    public Long timeinmili;
    public Earthquake(double magni,String city,String date,Long timeinmili)
    {
        this.city=city;
        this.magni=magni;
        this.date=date;
        this.timeinmili=timeinmili;
    }
    public String getCity()
    {
        return this.city;
    }
    public double getMagni()
    {
        return this.magni;
    }
    public String getUrl()
    {
        return this.date;
    }
    public Long getTimeinmili(){ return this.timeinmili;}
    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
