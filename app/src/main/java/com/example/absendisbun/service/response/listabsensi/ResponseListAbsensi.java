package com.example.absendisbun.service.response.listabsensi;

import com.google.gson.annotations.SerializedName;

public class ResponseListAbsensi{

	@SerializedName("data")
	private DataListAbsensi data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public DataListAbsensi getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}