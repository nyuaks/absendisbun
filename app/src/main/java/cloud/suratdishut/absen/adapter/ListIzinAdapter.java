package cloud.suratdishut.absen.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cloud.suratdishut.absen.R;
import cloud.suratdishut.absen.service.response.listizin.DataIzin;
import cloud.suratdishut.absen.utils.ConverterData;

import java.util.ArrayList;
import java.util.List;

public class ListIzinAdapter extends RecyclerView.Adapter<ListIzinAdapter.ViewHolder> {
    private List<DataIzin> dataIzinList;
    private Activity activity;

    public ListIzinAdapter(Activity activity){
        this.dataIzinList = new ArrayList<>();
        this.activity = activity;
    }

    public void setDataIzinList(List<DataIzin> dataIzinList) {
        this.dataIzinList = dataIzinList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListIzinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_izin, parent, false);
        return new ListIzinAdapter.ViewHolder(itemrow);
    }

    @Override
    public void onBindViewHolder(@NonNull ListIzinAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ConverterData cvtData = new ConverterData();
        String tertanggal = "-";
        tertanggal = cvtData.convertDateFormat2(dataIzinList.get(position).getTertanggal());
        if (dataIzinList.get(position).getStatus()==0) {
            holder.statusIzin.setText("dalam proses");
            holder.statusIzin.setTextColor(activity.getResources().getColor(R.color.yellowPrimary));
        }else if (dataIzinList.get(position).getStatus()==1){
            holder.statusIzin.setText("disetujui");
            holder.statusIzin.setTextColor(activity.getResources().getColor(R.color.primary));
        }else if (dataIzinList.get(position).getStatus()==2){
            holder.statusIzin.setText("ditolak");
            holder.statusIzin.setTextColor(activity.getResources().getColor(R.color.redPrimary));
        }else{
            holder.statusIzin.setText("-");
        }
        holder.tertanggal.setText(tertanggal);
//        holder.jumlahHari.setText(dataIzinList.get(position).getJumlahHari());
        holder.jenisIzin.setText(dataIzinList.get(position).getJenisAbsensi().getNama());
        holder.jumlahHari.setText(String.valueOf(dataIzinList.get(position).getJumlahHari()));
        holder.btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataIzinList.get(position).getUrlFile()!=null){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(dataIzinList.get(position).getUrlFile()));
                }else {
                    Toast.makeText(activity,"Data Kosong",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        return Math.max(dataIzinList.size(), 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tertanggal, statusIzin, jumlahHari, jenisIzin;
        Button btnFile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tertanggal = itemView.findViewById(R.id.tertanggal);
            jenisIzin = itemView.findViewById(R.id.jenis_izin);
            statusIzin = itemView.findViewById(R.id.status_izin);
            jumlahHari = itemView.findViewById(R.id.jumlah_hari);
            btnFile = itemView.findViewById(R.id.btn_file);
        }
    }
}
