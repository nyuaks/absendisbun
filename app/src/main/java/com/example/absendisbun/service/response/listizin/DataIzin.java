package com.example.absendisbun.service.response.listizin;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataIzin {

	@SerializedName("jenis_absensi_id")
	private int jenisAbsensiId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("pegawai_id")
	private int pegawaiId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("url_file")
	private String urlFile;

	@SerializedName("jumlah_hari")
	private int jumlahHari;

	@SerializedName("id")
	private int id;

	@SerializedName("jenis_absensi")
	private JenisAbsensi jenisAbsensi;

	@SerializedName("jenis_pengajuan")
	private JenisPengajuan jenisPengajuan;

	@SerializedName("media")
	private List<Object> media;

	@SerializedName("deleted_at")
	private Object deletedAt;

	@SerializedName("tertanggal")
	private String tertanggal;

	@SerializedName("status")
	private int status;

	public int getJenisAbsensiId(){
		return jenisAbsensiId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getPegawaiId(){
		return pegawaiId;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getUrlFile(){
		return urlFile;
	}

	public int getJumlahHari(){
		return jumlahHari;
	}

	public int getId(){
		return id;
	}

	public JenisAbsensi getJenisAbsensi(){
		return jenisAbsensi;
	}
	public JenisPengajuan getJenisPengajuan(){
		return jenisPengajuan;
	}

	public List<Object> getMedia(){
		return media;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	public String getTertanggal(){
		return tertanggal;
	}

	public int getStatus(){
		return status;
	}
}