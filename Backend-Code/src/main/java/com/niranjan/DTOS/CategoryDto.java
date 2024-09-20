package com.niranjan.DTOS;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CategoryDto {

	private Long id;
	
	private String name;
	
	private String description;
	
	private MultipartFile img;

	private byte[] returnedImg;
	
	
	
}
