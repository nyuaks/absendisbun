package com.example.absendisbun.service.response.login;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("token_type")
	private String tokenType;

	@SerializedName("user")
	private User user;

	@SerializedName("token")
	private String token;

	public String getTokenType(){
		return tokenType;
	}

	public User getUser(){
		return user;
	}

	public String getToken(){
		return token;
	}
}