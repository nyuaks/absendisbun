package com.example.absendisbun.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.absendisbun.R;
import com.example.absendisbun.adapter.ListAbsensiAdapter;
import com.example.absendisbun.config.Const;
import com.example.absendisbun.manager.PrefManager;
import com.example.absendisbun.service.ApiClient;
import com.example.absendisbun.service.ApiInterface;
import com.example.absendisbun.service.response.listabsensi.ResponseListAbsensi;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private PrefManager prf;
    private ApiInterface apiInterface;
    private ListAbsensiAdapter listAbsensiAdapter;
    private RecyclerView rvRekapAbsensi;
    private TextView namaKaryawan, nip, jam, tanggal;
    private CardView cvRekapanAbsen,cvPerizinan,cvAbsensi;

    private Button btnLogin;
    BroadcastReceiver broadcastReceiver;
    private final SimpleDateFormat sdfWatchTime = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat sdfWatchDate = new SimpleDateFormat("dd MMMM YYYY");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimeZone tz = TimeZone.getTimeZone("Asia/Makassar");
        sdfWatchTime.setTimeZone(tz);
        sdfWatchDate.setTimeZone(tz);
        namaKaryawan = findViewById(R.id.tvUserName);
        nip = findViewById(R.id.tvNip);
        jam = findViewById(R.id.tvRealTime);
        tanggal = findViewById(R.id.tvDate);
        cvAbsensi = findViewById(R.id.cardViewAbsensi);
        cvPerizinan = findViewById(R.id.cardViewPerizinan);
        cvRekapanAbsen = findViewById(R.id.cardViewRekapAbsen);
        btnLogin = findViewById(R.id.button_login);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        prf = new PrefManager(this);
        namaKaryawan.setText(prf.getString(Const.MY_NAME));
        nip.setText(prf.getString(Const.MY_NIP));

        String currentDateTimeString = java.text.DateFormat.getDateInstance().format(new Date());
        tanggal.setText(currentDateTimeString);

        rvRekapAbsensi = findViewById(R.id.rvListAbsen);
        rvRekapAbsensi.setHasFixedSize(true);
        rvRekapAbsensi.setLayoutManager(new LinearLayoutManager(this));
        listAbsensiAdapter = new ListAbsensiAdapter(this);

        cvRekapanAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RekapanAbsenActivity.class);
                startActivity(intent);
            }
        });
        cvAbsensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AbsensiActivity.class);
                startActivity(intent);
            }
        });

        cvPerizinan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PerizinanActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });

        getListAbsensi();
    }

    @Override
    public void onStart() {
        super.onStart();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
                    jam.setText(sdfWatchTime.format(new Date()));
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
    }

    private void getListAbsensi() {
        Call<ResponseListAbsensi> api = apiInterface.getListAbsensi("Bearer "+prf.getString(Const.TOKEN));
        api.enqueue(new Callback<ResponseListAbsensi>() {
            @Override
            public void onResponse(Call<ResponseListAbsensi> call, Response<ResponseListAbsensi> response) {
                if (response.isSuccessful()){
                    if(response.body() != null && response.body().getData().getAbsensi().size() > 0){
                        listAbsensiAdapter.setDataListAbsensis(response.body().getData().getAbsensi());
                        rvRekapAbsensi.setAdapter(listAbsensiAdapter);
                    }
                }else{
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Peringatan")
                            .setContentText("Error: "+response.message())
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseListAbsensi> call, Throwable t) {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Peringatan")
                        .setContentText("Gagal memuat data rekap absensi!")
                        .show();
            }
        });
    }
}