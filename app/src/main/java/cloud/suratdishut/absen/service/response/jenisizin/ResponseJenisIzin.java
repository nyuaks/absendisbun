package cloud.suratdishut.absen.service.response.jenisizin;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseJenisIzin{

	@SerializedName("data")
	private List<DataItemJenisIzin> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<DataItemJenisIzin> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}