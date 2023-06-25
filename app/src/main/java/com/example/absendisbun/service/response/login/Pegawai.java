package com.example.absendisbun.service.response.login;

import com.google.gson.annotations.SerializedName;

public class Pegawai{

	@SerializedName("status_pns")
	private String statusPns;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("jabatan_id")
	private int jabatanId;

	@SerializedName("agama_id")
	private int agamaId;

	@SerializedName("deleted_at")
	private Object deletedAt;

	@SerializedName("nik")
	private String nik;

	@SerializedName("nama")
	private String nama;

	@SerializedName("nip")
	private String nip;

	@SerializedName("tempat_lahir")
	private String tempatLahir;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("users_id")
	private int usersId;

	@SerializedName("id")
	private int id;

	@SerializedName("bidang_id")
	private int bidangId;

	@SerializedName("tanggal_lahir")
	private String tanggalLahir;

	public String getStatusPns(){
		return statusPns;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getJabatanId(){
		return jabatanId;
	}

	public int getAgamaId(){
		return agamaId;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	public String getNik(){
		return nik;
	}

	public String getNama(){
		return nama;
	}

	public String getNip(){
		return nip;
	}

	public String getTempatLahir(){
		return tempatLahir;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getUsersId(){
		return usersId;
	}

	public int getId(){
		return id;
	}

	public int getBidangId(){
		return bidangId;
	}

	public String getTanggalLahir(){
		return tanggalLahir;
	}
}