package com.example.absendisbun.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.absendisbun.service.response.jenisizin.DataItemJenisIzin;
import com.example.absendisbun.service.response.jenispengajuan.DataJenisPengajuan;

import java.util.List;

public class SpinnerJenisPengajuanAdapter extends ArrayAdapter<DataJenisPengajuan> {
    private Context context;
    private List<DataJenisPengajuan> values;
    public List<DataJenisPengajuan> getValues() {
        return values;
    }

    public SpinnerJenisPengajuanAdapter(@NonNull Context context, int resource, @NonNull List<DataJenisPengajuan> objects) {
        super(context, resource, objects);
        values=objects;
        this.context=context;
    }

    @Override
    public int getCount(){
        return getValues().size();
    }

    @Override
    public DataJenisPengajuan getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return values.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getNama());

        return label;
    }
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getNama());

        return label;
    }
}
