package com.niranjan.DTOS;

import lombok.Data;

@Data
public class AuthenticationRequest {

	private String email;
	
	private String password;
	
}
