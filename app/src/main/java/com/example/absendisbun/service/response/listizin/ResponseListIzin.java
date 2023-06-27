package com.example.absendisbun.service.response.listizin;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseListIzin{

	@SerializedName("data")
	private List<DataIzin> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<DataIzin> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}