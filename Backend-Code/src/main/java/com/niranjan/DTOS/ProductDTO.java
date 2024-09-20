package com.niranjan.DTOS;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductDTO {

	private Long id;
	
	private String name;
	
	private String price;
	
	private String description;
	
	private byte[] returnedImg;
	
	private MultipartFile img;
	
	private Long categoryId;
	
	private String categoryName;
}
