package cloud.suratdishut.absen.service;

import cloud.suratdishut.absen.service.response.jenisizin.ResponseJenisIzin;
import cloud.suratdishut.absen.service.response.jenispengajuan.ResponseJenisPengajuan;
import cloud.suratdishut.absen.service.response.listabsensi.ResponseListAbsensi;
import cloud.suratdishut.absen.service.response.listizin.ResponseListIzin;
import cloud.suratdishut.absen.service.response.login.ResponseLogin;
import cloud.suratdishut.absen.service.response.postizin.ResponsePostIzin;
import cloud.suratdishut.absen.service.response.postabsensi.ResponsePostAbsensi;
import cloud.suratdishut.absen.service.response.setting.ResponseSetting;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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

    @Multipart
    @Headers({
            "Accept: application/json",
    })
    @POST("cekAbsensi")
    Call<ResponsePostAbsensi> postAbsensi (@Header("Authorization") String token,
                                           @Part("latitude") RequestBody latitude,
                                           @Part("longitude") RequestBody longitude,
                                           @Part MultipartBody.Part media);

    @GET("setting")
    Call<ResponseSetting> getSetting(@Header("Authorization") String token);

//
//    @FormUrlEncoded
//    @POST("hasil_votings")
//    Call<ResponseVote> postVote(
//            @Field("users_id") int warga,
//            @Field("kandidat_id") int kandidat,
//            @Field("periode_id") int periode
//    );

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

    @GET("jenisPengajuan")
    Call<ResponseJenisPengajuan> getJenisPengajuan(@Header("Authorization") String token);

    @Multipart
    @Headers({
            "Accept: application/json",
    })
    @POST("postIzinCuti")
    Call<ResponsePostIzin> postIzin(@Header("Authorization") String token,
                                    @Part("tertanggal") RequestBody tglIzin,
                                    @Part("jumlah_hari") RequestBody jumlahHari,
                                    @Part("jenis_absensi_id") RequestBody jenisIzinId,
                                    @Part MultipartBody.Part media,
                                    @Part("jenis_pengajuan_id") RequestBody jenisPengajuan
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
