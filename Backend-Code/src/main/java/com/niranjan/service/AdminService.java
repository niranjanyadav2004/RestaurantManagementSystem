package com.niranjan.service;

import java.io.IOException;
import java.util.List;

import com.niranjan.DTOS.CategoryDto;
import com.niranjan.DTOS.ProductDTO;
import com.niranjan.DTOS.ReservationDto;

public interface AdminService {
    CategoryDto postCategory(CategoryDto categoryDto) throws IOException;

	List<CategoryDto> getAllCategories();

	List<CategoryDto> getAllCategoriesByTitle(String title);
	
	ProductDTO postProduct(Long categoryId,ProductDTO productDTO) throws IOException;

	List<ProductDTO> getAllProductsByCategory(Long categoryId);

	List<ProductDTO> getAllProductsByCategoryAndTitle(Long categoryId, String title);

	void deleteProduct(Long productId);

	ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws IOException;
	
	ProductDTO getProductById(Long productId);

	List<ReservationDto> getReservations();

	ReservationDto changeReservationStatus(Long reservationId, String status);
}
