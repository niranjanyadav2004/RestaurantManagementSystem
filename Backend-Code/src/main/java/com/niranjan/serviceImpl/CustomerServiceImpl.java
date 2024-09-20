package com.niranjan.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niranjan.DTOS.CategoryDto;
import com.niranjan.DTOS.ProductDTO;
import com.niranjan.DTOS.ReservationDto;
import com.niranjan.entities.Category;
import com.niranjan.entities.Product;
import com.niranjan.entities.Reservation;
import com.niranjan.entities.User;
import com.niranjan.enums.ReservationStatus;
import com.niranjan.repos.CategoryRepository;
import com.niranjan.repos.ProductRepository;
import com.niranjan.repos.ReservationRepository;
import com.niranjan.repos.UserRepository;
import com.niranjan.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
    private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

	@Override
	public List<CategoryDto> getCategoriesByName(String title) {
		return categoryRepository.findAllByNameContaining(title).stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getProductByCategory(Long categoryId) {
		return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDTO).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getAllProductsByCategoryAndTitle(Long categoryId, String title) {
		return productRepository.findAllByCategoryIdAndNameContaining(categoryId, title).stream().map(Product::getProductDTO).collect(Collectors.toList());
	}

	@Override
	public ReservationDto postReservation(ReservationDto reservationDto) {
		Optional<User> optional = userRepository.findById(reservationDto.getCustomerId());
		if(optional.isPresent()) {
			Reservation reservation = new Reservation();
			reservation.setTableType(reservationDto.getTableType());
			reservation.setDateTime(reservationDto.getDateTime());
			reservation.setDescription(reservationDto.getDescription());
			reservation.setUser(optional.get());
			reservation.setReservationStatus(ReservationStatus.PENDING);
			
			Reservation postedReservation = reservationRepository.save(reservation);
			
			ReservationDto postedReservationDto = new ReservationDto();
			postedReservationDto.setId(postedReservation.getId());
			
			return postedReservationDto;
		}
		return null;
	}

	@Override
	public List<ReservationDto> getReservationByUser(Long customerId) {
		return reservationRepository.findAllByUserId(customerId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
	}

}
