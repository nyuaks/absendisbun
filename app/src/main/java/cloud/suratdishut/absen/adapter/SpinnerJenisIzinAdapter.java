package cloud.suratdishut.absen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cloud.suratdishut.absen.service.response.jenisizin.DataItemJenisIzin;

import java.util.List;

public class SpinnerJenisIzinAdapter extends ArrayAdapter<DataItemJenisIzin> {
    private Context context;
    private List<DataItemJenisIzin> values;
    public List<DataItemJenisIzin> getValues() {
        return values;
    }

    public SpinnerJenisIzinAdapter(@NonNull Context context, int resource, @NonNull List<DataItemJenisIzin> objects) {
        super(context, resource, objects);
        values=objects;
        this.context=context;
    }

    @Override
    public int getCount(){
        return getValues().size();
    }

    @Override
    public DataItemJenisIzin getItem(int position){
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
