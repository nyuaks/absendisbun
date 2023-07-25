package com.example.absendisbun.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absendisbun.R;
import com.example.absendisbun.config.Const;
import com.example.absendisbun.service.response.listabsensi.AbsensiItem;
import com.example.absendisbun.service.response.listabsensi.DataListAbsensi;
import com.example.absendisbun.utils.ConverterData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ListAbsensiAdapter extends RecyclerView.Adapter<ListAbsensiAdapter.ViewHolder> {
    private List<AbsensiItem> dataListAbsensis;
    private Activity activity;

    public ListAbsensiAdapter(Activity activity){
        this.dataListAbsensis = new ArrayList<>();
        this.activity = activity;
    }

    public void setDataListAbsensis(List<AbsensiItem> dataListAbsensis) {
        this.dataListAbsensis = dataListAbsensis;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListAbsensiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rekap_kehadiran, parent, false);
        return new ListAbsensiAdapter.ViewHolder(itemrow);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAbsensiAdapter.ViewHolder holder, int position) {
        String timeIn = "-";
        String timeOut = "-";
        String created = "-";
        ConverterData cvtData = new ConverterData();

        if (dataListAbsensis.get(position).getTimeIn()!=null)
        {
            timeIn = cvtData.convertTimeFormat1(dataListAbsensis.get(position).getTimeIn());
        }
        if (dataListAbsensis.get(position).getTimeOut()!=null)
        {
            timeOut = cvtData.convertTimeFormat1(dataListAbsensis.get(position).getTimeOut());
        }
        created = cvtData.convertDateFormat2(dataListAbsensis.get(position).getCreatedAt());

        holder.createAt.setText(created);
        holder.timeIn.setText(timeIn);
        holder.timeOut.setText(timeOut);
        holder.jenisAbsen.setText(dataListAbsensis.get(position).getJenisAbsensi().getNama());
        holder.jenisAbsen.setText(dataListAbsensis.get(position).getLate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(activity, DetailKandidatActivity.class);
//                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Math.max(dataListAbsensis.size(), 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView createAt, timeIn, timeOut, jenisAbsen,tvKeterangan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            createAt = itemView.findViewById(R.id.created);
            timeIn = itemView.findViewById(R.id.tvTimeIn);
            timeOut = itemView.findViewById(R.id.tvTimeOut);
            jenisAbsen = itemView.findViewById(R.id.tvKeterangan);
        }
    }
}
