package com.niranjan.service;

import java.util.List;

import com.niranjan.DTOS.CategoryDto;
import com.niranjan.DTOS.ProductDTO;
import com.niranjan.DTOS.ReservationDto;

public interface CustomerService {

	List<CategoryDto> getAllCategories();

	List<CategoryDto> getCategoriesByName(String title);

	List<ProductDTO> getProductByCategory(Long categoryId);

	List<ProductDTO> getAllProductsByCategoryAndTitle(Long categoryId, String title);

	ReservationDto postReservation(ReservationDto reservationDto);

	List<ReservationDto> getReservationByUser(Long customerId);

}
