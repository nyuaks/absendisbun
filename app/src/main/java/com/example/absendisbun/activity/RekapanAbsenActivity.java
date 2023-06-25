package com.example.absendisbun.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.absendisbun.R;
import com.example.absendisbun.adapter.ListAbsensiAdapter;
import com.example.absendisbun.config.Const;
import com.example.absendisbun.manager.PrefManager;
import com.example.absendisbun.service.ApiClient;
import com.example.absendisbun.service.ApiInterface;
import com.example.absendisbun.service.response.listabsensi.ResponseListAbsensi;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapanAbsenActivity extends AppCompatActivity {

    private PrefManager prf;
    private ApiInterface apiInterface;
    private ListAbsensiAdapter listAbsensiAdapter;
    private RecyclerView rvRekapAbsensi;
    private TextView tvTotalMasuk,tvTotalPulang,tvtvTotalIzin,tvtvTotalCuti,tvtvTotalDinas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekapan_absen);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        prf = new PrefManager(this);
        tvTotalMasuk = findViewById(R.id.tvTotalMasuk);
        tvTotalPulang = findViewById(R.id.tvTotalPulang);
        tvtvTotalCuti = findViewById(R.id.tvTotalCuti);
        tvtvTotalIzin = findViewById(R.id.tvTotalIzin);
        tvtvTotalDinas = findViewById(R.id.tvTotalDinas);

        rvRekapAbsensi = findViewById(R.id.rvListAbsen);
        rvRekapAbsensi.setHasFixedSize(true);
        rvRekapAbsensi.setLayoutManager(new LinearLayoutManager(this));
        listAbsensiAdapter = new ListAbsensiAdapter(this);

        getListAbsensi();

    }
    private void getListAbsensi() {
        Call<ResponseListAbsensi> api = apiInterface.getListAbsensi("Bearer "+prf.getString(Const.TOKEN));
        api.enqueue(new Callback<ResponseListAbsensi>() {
            @Override
            public void onResponse(Call<ResponseListAbsensi> call, Response<ResponseListAbsensi> response) {
                if (response.isSuccessful()){
                    if(response.body() != null && response.body().getData().getAbsensi().size() > 0){
                        tvTotalMasuk.setText(String.valueOf(response.body().getData().getTotalMasuk()));
                        tvTotalPulang.setText(String.valueOf(response.body().getData().getTotalPulang()));
                        tvtvTotalIzin.setText(String.valueOf(response.body().getData().getTotalIzin()));
                        tvtvTotalCuti.setText(String.valueOf(response.body().getData().getTotalCuti()));
                        tvtvTotalDinas.setText(String.valueOf(response.body().getData().getTotalDinas()));
                        listAbsensiAdapter.setDataListAbsensis(response.body().getData().getAbsensi());
                        rvRekapAbsensi.setAdapter(listAbsensiAdapter);
                    }
                }else{
                    new SweetAlertDialog(RekapanAbsenActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Peringatan")
                            .setContentText("Error: "+response.message())
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseListAbsensi> call, Throwable t) {
                new SweetAlertDialog(RekapanAbsenActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Peringatan")
                        .setContentText("Gagal memuat data rekap absensi!")
                        .show();
            }
        });
    }
}