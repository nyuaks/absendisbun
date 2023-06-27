package com.example.absendisbun.service.response.postizin;

import com.google.gson.annotations.SerializedName;

public class ResponsePostIzin{

	@SerializedName("data")
	private DataIzin data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public DataIzin getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}