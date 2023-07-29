package com.example.absendisbun.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.absendisbun.R;
import com.example.absendisbun.config.Const;
import com.example.absendisbun.manager.PrefManager;
import com.example.absendisbun.service.ApiClient;
import com.example.absendisbun.service.ApiInterface;
import com.example.absendisbun.service.response.setting.ResponseSetting;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilActivity extends AppCompatActivity {

    private TextView namaKaryawan, nip,tvTimeIn,tvTimeOut,tvNik,tvStatus;
    private final SimpleDateFormat sdfWatchTime = new SimpleDateFormat("HH:mm");
    private LinearLayout btnLogout;
    private PrefManager prf;
    private ApiInterface apiInterface;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        prf = new PrefManager(this);

        setContentView(R.layout.activity_profil);
        namaKaryawan = findViewById(R.id.tvUserName);
        nip = findViewById(R.id.tvNip);
        tvTimeIn = findViewById(R.id.tvTimeIn);
        tvTimeOut = findViewById(R.id.tvTimeOut);
        tvNik = findViewById(R.id.tvNik);
        tvStatus = findViewById(R.id.tvStatus);
        btnLogout = findViewById(R.id.logout);


        nip.setText (prf.getString(Const.MY_NIP));
        namaKaryawan.setText(prf.getString(Const.MY_NAME));
        tvNik.setText(prf.getString(Const.MY_NIK));
        tvStatus.setText(prf.getString(Const.MY_STATUS));
        getSetting();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void getSetting() {
        Call<ResponseSetting> api = apiInterface.getSetting("Bearer "+prf.getString(Const.TOKEN));
        api.enqueue(new Callback<ResponseSetting>() {
            @Override
            public void onResponse(Call<ResponseSetting> call, Response<ResponseSetting> response) {
                if (response.isSuccessful()){
                    if(response.body() != null) {
                        tvTimeIn.setText("Masuk: " +response.body().getData().getJamMasuk());
                        tvTimeOut.setText("Pulang: " +response.body().getData().getJamPulang());
                    }
                    else {
                        new SweetAlertDialog(ProfilActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Peringatan")
                                .setContentText("Error: " + response.message())
                                .show();
                    }
                }else {
                    new SweetAlertDialog(ProfilActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Peringatan")
                            .setContentText("Error: " + response.message())
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSetting> call, Throwable t) {

            }
        });
    }

    private void logout(){
        prf.remove("MY_EMAIL");
        prf.remove("JUMLAH_CUTI");
        prf.remove("MY_STATUS");
        prf.remove("MY_NIP");
        prf.remove("MY_NIK");
        prf.remove("MY_NAME");
        prf.remove("TOKEN");
        Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}