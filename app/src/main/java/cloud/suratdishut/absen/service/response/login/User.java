package cloud.suratdishut.absen.service.response.login;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("email_verified_at")
	private Object emailVerifiedAt;

	@SerializedName("id")
	private int id;

	@SerializedName("pegawai")
	private Pegawai pegawai;

	@SerializedName("deleted_at")
	private Object deletedAt;

	@SerializedName("email")
	private String email;

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getName(){
		return name;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public Object getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public int getId(){
		return id;
	}

	public Pegawai getPegawai(){
		return pegawai;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	public String getEmail(){
		return email;
	}
}