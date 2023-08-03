package cloud.suratdishut.absen.activity;

import androidx.appcompat.app.AppCompatActivity;
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

import cloud.suratdishut.absen.R;
import cloud.suratdishut.absen.adapter.ListIzinAdapter;
import cloud.suratdishut.absen.config.Const;
import cloud.suratdishut.absen.manager.PrefManager;
import cloud.suratdishut.absen.service.ApiClient;
import cloud.suratdishut.absen.service.ApiInterface;
import cloud.suratdishut.absen.service.response.listizin.ResponseListIzin;
import cloud.suratdishut.absen.utils.ConverterData;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerizinanActivity extends AppCompatActivity {
    private PrefManager prf;
    private ApiInterface apiInterface;
    private ListIzinAdapter listIzinAdapter;
    private RecyclerView rvRekapIzin;
    private TextView namaKaryawan, nip, jam, tanggal;
    private Button btnAjukanIzin;

    BroadcastReceiver broadcastReceiver;
    private final SimpleDateFormat sdfWatchTime = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat sdfWatchDate = new SimpleDateFormat("dd MMMM YYYY");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perizinan);
        ConverterData cvtData = new ConverterData();

        namaKaryawan = findViewById(R.id.tvUserName);
        nip = findViewById(R.id.tvNip);
        jam = findViewById(R.id.tvRealTime);
        tanggal = findViewById(R.id.tvDate);
        btnAjukanIzin = findViewById(R.id.btn_ajukan_cuti);

        String currentDateTimeString = java.text.DateFormat.getDateInstance().format(new Date());
        tanggal.setText(cvtData.convertDateFormat2(currentDateTimeString));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        prf = new PrefManager(this);
        namaKaryawan.setText(prf.getString(Const.MY_NAME));
        nip.setText(prf.getString(Const.MY_NIP));

        rvRekapIzin = findViewById(R.id.rvListIzin);
        rvRekapIzin.setHasFixedSize(true);
        rvRekapIzin.setLayoutManager(new LinearLayoutManager(this));
        listIzinAdapter = new ListIzinAdapter(this);

        btnAjukanIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerizinanActivity.this, CreatePerizinanActivity.class);
                startActivity(intent);
            }
        });

        getListIzin();
    }

    private void getListIzin() {
        Call<ResponseListIzin> api = apiInterface.getListIzin("Bearer "+prf.getString(Const.TOKEN));
        api.enqueue(new Callback<ResponseListIzin>() {
            @Override
            public void onResponse(Call<ResponseListIzin> call, Response<ResponseListIzin> response) {
                if (response.isSuccessful()){
                    if(response.body() != null && response.body().getData().size() > 0){
                        listIzinAdapter.setDataIzinList(response.body().getData());
                        rvRekapIzin.setAdapter(listIzinAdapter);
                    }
                }else{
                    new SweetAlertDialog(PerizinanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Peringatan")
                            .setContentText("Error: "+response.message())
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseListIzin> call, Throwable t) {
                new SweetAlertDialog(PerizinanActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Peringatan")
                        .setContentText("Gagal memuat data rekap absensi!")
                        .show();
            }
        });
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
}