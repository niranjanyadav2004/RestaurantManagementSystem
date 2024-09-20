package com.niranjan.DTOS;

import com.niranjan.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
	
	private String jwt;
	
	private UserRole userRole;
	
	private Long userId;

}
