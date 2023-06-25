package com.example.absendisbun.service.response.listabsensi;

import com.google.gson.annotations.SerializedName;

public class AbsensiItem{

	@SerializedName("jenis_absensi_id")
	private int jenisAbsensiId;

	@SerializedName("keterangan")
	private Object keterangan;

	@SerializedName("pegawai_id")
	private int pegawaiId;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("longtitude")
	private double longtitude;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("jenis_absensi")
	private JenisAbsensi jenisAbsensi;

	@SerializedName("deleted_at")
	private Object deletedAt;

	@SerializedName("time_out")
	private String timeOut;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("late")
	private boolean late;

	@SerializedName("time_in")
	private String timeIn;

	@SerializedName("id")
	private int id;

	public int getJenisAbsensiId(){
		return jenisAbsensiId;
	}

	public Object getKeterangan(){
		return keterangan;
	}

	public int getPegawaiId(){
		return pegawaiId;
	}

	public double getLatitude(){
		return latitude;
	}

	public double getLongtitude(){
		return longtitude;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public JenisAbsensi getJenisAbsensi(){
		return jenisAbsensi;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	public String getTimeOut(){
		return timeOut;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public boolean isLate(){
		return late;
	}

	public String getTimeIn(){
		return timeIn;
	}

	public int getId(){
		return id;
	}
}