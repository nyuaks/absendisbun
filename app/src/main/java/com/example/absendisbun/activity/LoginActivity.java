package com.example.absendisbun.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.absendisbun.R;
import com.example.absendisbun.config.Const;
import com.example.absendisbun.manager.PrefManager;
import com.example.absendisbun.service.ApiClient;
import com.example.absendisbun.service.ApiInterface;
import com.example.absendisbun.service.response.login.ResponseLogin;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edEmail, edPassword;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        btnLogin = findViewById(R.id.button_login);
        edEmail = findViewById(R.id.email);
        edPassword = findViewById(R.id.password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letGoLogin();
//                start
//                Activity();
            }
        });
    }

    private void letGoLogin() {
        PrefManager prf = new PrefManager(LoginActivity.this);
        if (!prf.getString(Const.TOKEN).isEmpty()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            String email = edEmail.getEditableText().toString();
            String password = edPassword.getEditableText().toString();

            Call<ResponseLogin> api = apiInterface.getLogin(email, password);
            api.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getData() != null && response.body().isSuccess()) {
                            Log.i("DATA USER", response.body().getData().toString());
                            PrefManager prefManager = new PrefManager(getApplicationContext());
                            prefManager.setString(Const.TOKEN, response.body().getData().getToken());
                            prefManager.setString(Const.MY_NAME, response.body().getData().getUser().getName());
                            prefManager.setString(Const.MY_EMAIL, response.body().getData().getUser().getEmail());
                            prefManager.setString(Const.MY_NIP, response.body().getData().getUser().getPegawai().getNip());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Peringatan")
                                    .setContentText("User Tidak Terdaftar \n (Cek kembali Emai dan Password)")
                                    .show();
                        }
                    } else {
                        try {
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Peringatan")
                                    .setContentText("Error: " + response.errorBody().string())
                                    .show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Peringatan")
                            .setContentText("GAGAL Login!")
                            .show();
                }
            });
        }
    }

    public void startActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}