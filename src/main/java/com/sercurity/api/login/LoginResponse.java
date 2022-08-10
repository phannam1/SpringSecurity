package com.sercurity.api.login;

import java.util.List;

public class LoginResponse {

	public String token;
	public String username;
	public String email;
	public List<String> role;
	public String refreshToken;

	public LoginResponse(String token, String refreshToken, String username, String email, List<String> role) {
		this.token = token;
		this.refreshToken = refreshToken;
		this.username = username;
		this.email = email;
		this.role = role;
	}

}
