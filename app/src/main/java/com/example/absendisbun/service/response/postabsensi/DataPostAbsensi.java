package com.example.absendisbun.service.response.postabsensi;

import com.google.gson.annotations.SerializedName;

public class DataPostAbsensi {

	@SerializedName("jenis_absensi_id")
	private int jenisAbsensiId;

	@SerializedName("keterangan")
	private Object keterangan;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("late")
	private boolean late;

	@SerializedName("pegawai_id")
	private int pegawaiId;

	@SerializedName("latitude")
	private Object latitude;

	@SerializedName("time_in")
	private Object timeIn;

	@SerializedName("longtitude")
	private Object longtitude;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("deleted_at")
	private Object deletedAt;

	@SerializedName("time_out")
	private String timeOut;

	public int getJenisAbsensiId(){
		return jenisAbsensiId;
	}

	public Object getKeterangan(){
		return keterangan;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public boolean isLate(){
		return late;
	}

	public int getPegawaiId(){
		return pegawaiId;
	}

	public Object getLatitude(){
		return latitude;
	}

	public Object getTimeIn(){
		return timeIn;
	}

	public Object getLongtitude(){
		return longtitude;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	public String getTimeOut(){
		return timeOut;
	}
}