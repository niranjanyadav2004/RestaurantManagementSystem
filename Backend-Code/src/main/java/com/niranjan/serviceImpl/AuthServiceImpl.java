package com.niranjan.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.niranjan.DTOS.SignupRequest;
import com.niranjan.DTOS.UserDto;
import com.niranjan.entities.User;
import com.niranjan.enums.UserRole;
import com.niranjan.repos.UserRepository;
import com.niranjan.service.AuthService;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void createAdminAccount() {
		User admin = userRepository.findByUserRole(UserRole.ADMIN);
		if(admin==null) {
			User user = new User();
			user.setName("admin");
			user.setEmail("admin@gmail.com");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setUserRole(UserRole.ADMIN);
			userRepository.save(user);
		}
	}
	
	@Override
	public UserDto createUser(SignupRequest signupRequest) {
		User user = new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		
		User saveUser = userRepository.save(user);
		
		UserDto createdUserDto = new UserDto();
		createdUserDto.setId(saveUser.getId());
		createdUserDto.setName(saveUser.getName());
		createdUserDto.setEmail(saveUser.getEmail());
		createdUserDto.setUserRole(saveUser.getUserRole());
		
		
		return createdUserDto;
	}

}
