package com.example.absendisbun.service.response.setting;

import com.google.gson.annotations.SerializedName;

public class ResponseSetting{

	@SerializedName("data")
	private DataItemSetting data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public DataItemSetting getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}