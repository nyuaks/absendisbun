package com.example.absendisbun.service;

import com.example.absendisbun.service.response.jenisizin.ResponseJenisIzin;
import com.example.absendisbun.service.response.listabsensi.ResponseListAbsensi;
import com.example.absendisbun.service.response.listizin.ResponseListIzin;
import com.example.absendisbun.service.response.login.ResponseLogin;
import com.example.absendisbun.service.response.postizin.ResponsePostIzin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("login")
    Call<ResponseLogin> getLogin(
            @Query("email")String nik_email,
            @Query("password")String password
    );

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("absensi")
    Call<ResponseListAbsensi> getListAbsensi(@Header("Authorization") String token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("getIzinCuti")
    Call<ResponseListIzin> getListIzin(@Header("Authorization") String token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("jenisAbsensi")
    Call<ResponseJenisIzin> getJenisIzin(@Header("Authorization") String token);

    @Multipart
    @Headers({
            "Accept: application/json",
    })
    @POST("postIzinCuti")
    Call<ResponsePostIzin> postIzin(@Header("Authorization") String token,
                                    @Part("tertanggal") RequestBody tglIzin,
                                    @Part("jumlah_hari") RequestBody jumlahHari,
                                    @Part("jenis_absensi_id") RequestBody jenisIzinId,
                                    @Part MultipartBody.Part media
    );
//
//    @GET("hasil_votings")
//    Call<ResponseHasilVote> getHasilVote(@Query("penduduk_id")int warga);
//
//
//    @FormUrlEncoded
//    @POST("users")
//    Call<ResponseRegister> getRegisterApi(
//            @Field("name") String nama,
//            @Field("no_hp") String no_hp,
//            @Field("email") String email,
//            @Field("password") String password
//    );
//
//    @GET("filter")
//    Call <ResponseGetLocation> getFilterLocation(@Header("Authorization") String token,
//                                           @Query("my_lat")double lat,
//                                           @Query("my_lng")double lng,
//                                           @Query("nama_lokasi") String namaLokasi,
//                                           @Query("radius") String radius);
//
//    @GET("directions/json")
//    Call<ResponseGmaps> getDirectGmaps(@Query("origin") String origin,
//                                     @Query("destination") String dest,
//                                     @Query("key") String key);
}
