package com.niranjan.service;

import com.niranjan.DTOS.SignupRequest;
import com.niranjan.DTOS.UserDto;

public interface AuthService {

	UserDto createUser(SignupRequest signupRequest);
	
}
