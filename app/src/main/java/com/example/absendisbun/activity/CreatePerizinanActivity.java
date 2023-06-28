package com.example.absendisbun.activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absendisbun.R;
import com.example.absendisbun.adapter.SpinnerJenisIzinAdapter;
import com.example.absendisbun.config.Const;
import com.example.absendisbun.manager.PrefManager;
import com.example.absendisbun.service.ApiClient;
import com.example.absendisbun.service.ApiInterface;
import com.example.absendisbun.service.response.jenisizin.DataItemJenisIzin;
import com.example.absendisbun.service.response.jenisizin.ResponseJenisIzin;
import com.example.absendisbun.service.response.postizin.ResponsePostIzin;
import com.example.absendisbun.utils.ConverterData;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePerizinanActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private static final int PICK_PDF_FILE = 2;

    private static final int REQUEST_FILE_IZIN = 1;
    private Uri uriFileIzin = null;
    private String pathFileIzin;
    private EditText etTanggal, etJumlahHari;
    private TextView tvNamaFileIzin,namaKaryawan, nip,jam,tanggal;
    private Button btnTgl, btnFileIzin, btnSubmitIzin;
    private DatePickerDialog dpd;
    private ApiInterface apiInterface;
    private PrefManager prf;
    List<DataItemJenisIzin> jenisIzinList;
    private Spinner spinJenisIzin;
    private Integer jenisIzinId;
    BroadcastReceiver broadcastReceiver;
    private final SimpleDateFormat sdfWatchTime = new SimpleDateFormat("HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_perizinan);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        prf = new PrefManager(this);

        namaKaryawan = findViewById(R.id.tvUserName);
        nip = findViewById(R.id.tvNip);
        jam = findViewById(R.id.tvRealTime);
        tanggal = findViewById(R.id.tvDate);
        tvNamaFileIzin = findViewById(R.id.tv_upload_file);
        etTanggal = findViewById(R.id.tertanggal);
        etJumlahHari = findViewById(R.id.jumlah_hari);
        spinJenisIzin = findViewById(R.id.sp_jenis_izin);
        btnFileIzin = findViewById(R.id.btn_file_izin);
        btnSubmitIzin = findViewById(R.id.btn_submit_izin);
        btnTgl = findViewById(R.id.btn_tgl);

        nip.setText(prf.getString(Const.MY_NIP));
        namaKaryawan.setText(prf.getString(Const.MY_NAME));

        ConverterData cvtData = new ConverterData();
        String currentDateTimeString = java.text.DateFormat.getDateInstance().format(new Date());
        tanggal.setText(cvtData.convertDateFormat2(currentDateTimeString));

        btnTgl.setOnClickListener(v->{
            Calendar tanggal = Calendar.getInstance();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                tanggal.setTime(sdf.parse(etTanggal.getText().toString()));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            dpd=new DatePickerDialog(
                    CreatePerizinanActivity.this, this, tanggal.get(Calendar.YEAR),
                    tanggal.get(Calendar.MONTH),
                    tanggal.get(Calendar.DAY_OF_MONTH));
            dpd.setTitle("Pilih Tanggal Lahir");
            dpd.show();
        });
        btnFileIzin.setOnClickListener(view -> {
            pickFile(REQUEST_FILE_IZIN);
//            openFile(PICK_PDF_FILE);
        });
        btnSubmitIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajukanIzin();
            }
        });

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (!report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "GPS Access Granted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    }
                }).check();

        if (!permission()) {
            requestPermissionDialog();
        }

        getJenisIzin();
    }

    private void ajukanIzin() {
        RequestBody tertanggal = RequestBody.create(MultipartBody.FORM, etTanggal.getEditableText().toString());
        RequestBody jumlahHari = RequestBody.create(MultipartBody.FORM, etJumlahHari.getEditableText().toString());
        RequestBody jenisIzin = RequestBody.create(MultipartBody.FORM, String.valueOf(jenisIzinId));
        MultipartBody.Part fileIzin=null;

        File dataFileIzin = new File(pathFileIzin);
        if ((dataFileIzin.exists())){
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),dataFileIzin);
            fileIzin = MultipartBody.Part.createFormData("media",dataFileIzin.getName(),requestFile);
        }
        Call<ResponsePostIzin> api = apiInterface.postIzin("Bearer "+prf.getString(Const.TOKEN),tertanggal,jumlahHari,jenisIzin,fileIzin);
        api.enqueue(new Callback<ResponsePostIzin>() {
            @Override
            public void onResponse(Call<ResponsePostIzin> call, Response<ResponsePostIzin> response) {
                if (response.isSuccessful()){
                    if(response.body() != null && response.body().getData() !=null){
                        new SweetAlertDialog(CreatePerizinanActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Pesan Aplikasi")
                                .setContentText("Pengajuan Izin Telah Berhasil di Upload. Silahkan Tunggu Konfirmasi Admin")
                                .setConfirmText("Tutup")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        Intent intent = new Intent(CreatePerizinanActivity.this, PerizinanActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .show();

                    }
                }else{
                    new SweetAlertDialog(CreatePerizinanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Peringatan")
                            .setContentText("Error: "+response.message())
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePostIzin> call, Throwable t) {
                new SweetAlertDialog(CreatePerizinanActivity.this, SweetAlertDialog.ERROR_TYPE)
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

    private void getJenisIzin() {
        Call<ResponseJenisIzin> api = apiInterface.getJenisIzin("Bearer "+prf.getString(Const.TOKEN));
        api.enqueue(new Callback<ResponseJenisIzin>() {
            @Override
            public void onResponse(Call<ResponseJenisIzin> call, Response<ResponseJenisIzin> response) {
                if (response.isSuccessful()){
                    if(response.body() != null && response.body().getData().size() > 0){
                        SpinnerJenisIzinAdapter spinnerJenisIzinAdapter = new SpinnerJenisIzinAdapter(getApplicationContext(),
                                android.R.layout.simple_spinner_item, response.body().getData());
                        spinnerJenisIzinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        jenisIzinList = response.body().getData();
                        spinJenisIzin.setAdapter(spinnerJenisIzinAdapter);
                        spinJenisIzin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                jenisIzinId = jenisIzinList.get(position).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }else{
                    new SweetAlertDialog(CreatePerizinanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Peringatan")
                            .setContentText("Error: "+response.message())
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseJenisIzin> call, Throwable t) {
                new SweetAlertDialog(CreatePerizinanActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Peringatan")
                        .setContentText("Gagal memuat data rekap absensi!")
                        .show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String sDay="00"+dayOfMonth;
        String sMonth="00"+(month+1);
        String tanggal= sDay.substring(sDay.length()-2)+"-"+sMonth.substring(sMonth.length()-2)+"-"+year;
        etTanggal.setText(tanggal);
    }

    private void pickFile(int request){
        Intent pickDocument = new Intent(Intent.ACTION_GET_CONTENT);
        pickDocument.setType("application/pdf");
        pickDocument.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(pickDocument,request);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data!=null){
                Uri uriFileOri = data.getData();
//                getDuplicateFile(uriFileIzin);

                uriFileIzin = duplicateFileToInternalStorage(uriFileOri);

                pathFileIzin = getRealPathFromUri(uriFileIzin);

                tvNamaFileIzin.setText(pathFileIzin);
                Log.i("fileIzin",""+pathFileIzin);
            }
        }
        else if (resultCode == RESULT_CANCELED){
            new SweetAlertDialog(CreatePerizinanActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Pesan Aplikasi")
                    .setContentText("Maaf Pemilihan File Dibatalkan")
                    .setConfirmText("Tutup")
                    .setConfirmClickListener(sDialog -> {
                        sDialog.dismissWithAnimation();
                    })
                    .show();
        }

    }

    private Uri duplicateFileToInternalStorage(Uri originalFileUri) {
        try {
            // Mendapatkan InputStream dari dokumen PDF asli
            InputStream inputStream = getContentResolver().openInputStream(originalFileUri);

            // Membuat file baru di direktori temporary aplikasi
            File tempDir = getApplicationContext().getCacheDir();
            File tempFile = new File(tempDir, "duplicate_pdf.pdf");

            // Membuka OutputStream ke file baru
            OutputStream outputStream = new FileOutputStream(tempFile);

            // Membaca dan menulis byte dokumen PDF dari InputStream ke OutputStream
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            // Menutup InputStream dan OutputStream
            inputStream.close();
            outputStream.close();

            // Mengembalikan URI dari dokumen PDF duplikat
            return Uri.fromFile(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

//    public String getRealPathFromUri(final Uri uri) {
//        // DocumentProvider
//        if (DocumentsContract.isDocumentUri(this, uri)) {
//            // ExternalStorageProvider
//            if (isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                if ("primary".equalsIgnoreCase(type)) {
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }
//            }
//            // DownloadsProvider
//            else if (isDownloadsDocument(uri)) {
//                String id = DocumentsContract.getDocumentId(uri);
//                final String contentUri = id.replace("raw:","");
//                return contentUri;
////                final Uri contentUri = ContentUris.withAppendedId(
////                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
////                return getDataColumn(this, contentUri, null, null);
//            }
//            // MediaProvider
//            else if (isMediaDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                Uri contentUri = null;
//                if ("image".equals(type)) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//
//                final String selection = "_id=?";
//                final String[] selectionArgs = new String[]{
//                        split[1]
//                };
//
//                return getDataColumn(this, contentUri, selection, selectionArgs);
//            }
//        }
//        // MediaStore (and general)
//        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//
//            // Return the remote address
//            if (isGooglePhotosUri(uri))
//                return uri.getLastPathSegment();
//
//            return getDataColumn(this, uri, null, null);
//        }
//        // File
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//
//        return null;
//    }
//    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
//        Cursor cursor = null;
//        final String column = "_data";
//        final String[] projection = {
//                column
//        };
//
//        try {
//            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
//                    null);
//            if (cursor != null && cursor.moveToFirst()) {
//                final int index = cursor.getColumnIndexOrThrow(column);
//                return cursor.getString(index);
//            }
//        } finally {
//            if (cursor != null)
//                cursor.close();
//        }
//        return null;
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_FILE_IZIN:
                if (grantResults.length > 0) {
                    boolean storage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (storage && read) {
                        //next activity
                    } else {
                        //show msg kai permission allow nahi havai
                    }
                }
                break;
        }
    }

    public void requestPermissionDialog() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
//                startActivity(intent);
            } catch (Exception e) {
                Intent obj = new Intent();
                obj.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                startActivity(obj);

            }
        } else {
            ActivityCompat.requestPermissions(CreatePerizinanActivity.this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, REQUEST_FILE_IZIN);
        }
    }
    public boolean permission() {
        if (SDK_INT >= Build.VERSION_CODES.R) { // R is Android 11
            return Environment.isExternalStorageManager();
        } else {
            int write = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED
                    && read == PackageManager.PERMISSION_GRANTED;
        }
    }

    public String getRealPathFromUri(final Uri uri) {
        // DocumentProvider
        if (SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(this, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(this, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(this, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.example.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.example.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.example.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}