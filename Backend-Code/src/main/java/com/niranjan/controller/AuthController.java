package com.niranjan.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niranjan.DTOS.AuthenticationRequest;
import com.niranjan.DTOS.AuthenticationResponse;
import com.niranjan.DTOS.SignupRequest;
import com.niranjan.DTOS.UserDto;
import com.niranjan.entities.User;
import com.niranjan.repos.UserRepository;
import com.niranjan.service.AuthService;
import com.niranjan.serviceImpl.UserDetailsServiceImpl;
import com.niranjan.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private final AuthService authService;
	
	@Autowired
	private final UserDetailsServiceImpl userService;
	
	@Autowired
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	private final JwtUtil jwtUtil;
	
	@Autowired
	private final UserRepository userRepository;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
		UserDto user = authService.createUser(signupRequest);
		
		if(user==null) {
			return new ResponseEntity<>("User not created..!!", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,HttpServletResponse response) throws IOException {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid username or password...!!");
		}
		catch (DisabledException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,"User is not active");
			return null;
		}
		
		 UserDetails username = userService.loadUserByUsername(authenticationRequest.getEmail());
		final String jwt = jwtUtil.generateToken(username);
		Optional<User> optional = userRepository.findFirstByEmail(username.getUsername());
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		if(optional.isPresent()) {
			authenticationResponse.setJwt(jwt);
			authenticationResponse.setUserRole(optional.get().getUserRole());
			authenticationResponse.setUserId(optional.get().getId());
		}
		return authenticationResponse;
		
	}

	
}
