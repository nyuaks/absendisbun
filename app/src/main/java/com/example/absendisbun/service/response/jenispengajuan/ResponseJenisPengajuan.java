package com.example.absendisbun.service.response.jenispengajuan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseJenisPengajuan{

	@SerializedName("data")
	private List<DataJenisPengajuan> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<DataJenisPengajuan> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}