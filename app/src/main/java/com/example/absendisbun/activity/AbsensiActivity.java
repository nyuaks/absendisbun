package com.example.absendisbun.activity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absendisbun.R;
import com.example.absendisbun.config.Const;
import com.example.absendisbun.manager.PrefManager;
import com.example.absendisbun.service.ApiClient;
import com.example.absendisbun.service.ApiInterface;
import com.example.absendisbun.service.response.postabsensi.ResponsePostAbsensi;
import com.example.absendisbun.service.response.setting.ResponseSetting;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsensiActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {


    private TextView tvTimeIn,tvTimeOut,tvUserName,tvNip;
    private ImageView imFoto;
    private Button btnAbsen,btnBeranda;
    private FusedLocationProviderClient client;
    private static final int pic_id = 123;
    Double lang, lat;

    private static final int Image_Capture_Code = 1;
    private Uri uriFoto=null;

    File imgFile;
    MultipartBody.Part bodyImagePath;

    private PrefManager prf;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);

        tvTimeIn = findViewById(R.id.tvTimeIn);
        tvTimeOut = findViewById(R.id.tvTimeOut);
        imFoto = findViewById(R.id.iv_foto_absen);
        btnAbsen = findViewById(R.id.button_absen);
        btnBeranda = findViewById(R.id.button_main);
        tvUserName = findViewById(R.id.tvUserName);
        tvNip = findViewById(R.id.tvNip);

        prf = new PrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        client = LocationServices.getFusedLocationProviderClient(this);
        Dexter.withActivity(this)
                // below line is use to request the number of permissions which are required in our app.
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                // after adding permissions we are calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).check();
        clikActionImg();
        tvNip.setText (prf.getString(Const.MY_NIP));
        tvUserName.setText(prf.getString(Const.MY_NAME));
        getSetting();


        btnBeranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(AbsensiActivity.this, MainActivity.class);
                startActivity(inten);
            }
        });

    }

    public void clikActionImg() {
        //OPEN CAMERA
        btnAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPermission();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,Image_Capture_Code);
            }
        });
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(123)
    private void openPermission() {
        String[]perms = {ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        //get long/lat
        if (EasyPermissions.hasPermissions(this,perms)){
            client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location !=null){
                        lat = location.getLatitude();
                        lang = location.getLongitude();
                    }else{
                        new SweetAlertDialog(AbsensiActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Peringatan")
                                .setContentText("Aktifkan Lokasi!")
                                .show();
                    }
                }
            });
        } else {
            EasyPermissions.requestPermissions(this, "We need Permission",
                    123, perms);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Capture_Code && resultCode == Activity.RESULT_OK) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                Uri image = data.getData();
                Uri tempUri = getImageUri(getApplicationContext(),bp);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));

                imgFile = new File(String.valueOf(finalFile));

                imFoto.setImageBitmap(bp);
                postAbsen();
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    private void postAbsen() {
        if(lat != null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/from-data"), imgFile);
            RequestBody latitude = RequestBody.create(MultipartBody.FORM, String.valueOf(lat));
            RequestBody longitude = RequestBody.create(MultipartBody.FORM, String.valueOf(lang));
            bodyImagePath = MultipartBody.Part.createFormData("media", imgFile.getName(), requestFile);

            Call<ResponsePostAbsensi> api = apiInterface.postAbsensi("Bearer "+prf.getString(Const.TOKEN), latitude,longitude,bodyImagePath);

            api.enqueue(new Callback<ResponsePostAbsensi>() {
                @Override
                public void onResponse(Call<ResponsePostAbsensi> call, Response<ResponsePostAbsensi> response) {
                    if (response.isSuccessful()){
                        new SweetAlertDialog(AbsensiActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Berhasil")
                                .setContentText(response.message())
                                .show();
                    }else{
                        try {
                            new SweetAlertDialog(AbsensiActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Peringatan")
                                    .setContentText("Error: "+response.errorBody().string())
                                    .show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponsePostAbsensi> call, Throwable t) {

                }
            });
        }else{
            new SweetAlertDialog(AbsensiActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Peringatan")
                    .setContentText("Lokasi tidak ditemukan!")
                    .show();
        }


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
                        new SweetAlertDialog(AbsensiActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Peringatan")
                                .setContentText("Error: " + response.message())
                                .show();
                    }
                }else {
                    new SweetAlertDialog(AbsensiActivity.this, SweetAlertDialog.ERROR_TYPE)
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

    public Uri getImageUri(Context context,Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}