package cloud.suratdishut.absen.service.response.postabsensi;

import com.google.gson.annotations.SerializedName;

public class ResponsePostAbsensi{

	@SerializedName("data")
	private DataPostAbsensi data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public DataPostAbsensi getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}