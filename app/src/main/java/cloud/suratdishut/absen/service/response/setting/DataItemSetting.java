package cloud.suratdishut.absen.service.response.setting;

import com.google.gson.annotations.SerializedName;

public class DataItemSetting {

	@SerializedName("nama")
	private String nama;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("jam_masuk")
	private String jamMasuk;

	@SerializedName("jam_pulang")
	private String jamPulang;

	@SerializedName("latitude")
	private Object latitude;

	@SerializedName("longtitude")
	private Object longtitude;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("jarak_absen")
	private int jarakAbsen;

	@SerializedName("deleted_at")
	private Object deletedAt;

	public String getNama(){
		return nama;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getJamMasuk(){
		return jamMasuk;
	}

	public String getJamPulang(){
		return jamPulang;
	}

	public Object getLatitude(){
		return latitude;
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

	public int getJarakAbsen(){
		return jarakAbsen;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}
}