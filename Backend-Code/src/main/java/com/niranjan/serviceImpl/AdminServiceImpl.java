package com.niranjan.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niranjan.DTOS.CategoryDto;
import com.niranjan.DTOS.ProductDTO;
import com.niranjan.DTOS.ReservationDto;
import com.niranjan.entities.Category;
import com.niranjan.entities.Product;
import com.niranjan.entities.Reservation;
import com.niranjan.enums.ReservationStatus;
import com.niranjan.repos.CategoryRepository;
import com.niranjan.repos.ProductRepository;
import com.niranjan.repos.ReservationRepository;
import com.niranjan.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	@Autowired
     private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public CategoryDto postCategory(CategoryDto categoryDto) throws IOException {
		Category category = new Category();
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setImg(categoryDto.getImg().getBytes());
		
		Category createdcategory = categoryRepository.save(category);
		
		CategoryDto createdDto = new CategoryDto();
		createdDto.setId(createdcategory.getId());
		
		
		return createdDto;
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

	@Override
	public List<CategoryDto> getAllCategoriesByTitle(String title) {
		return categoryRepository.findAllByNameContaining(title).stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

	
	//Product Operations
	
	@Override
	public ProductDTO postProduct(Long categoryId, ProductDTO productDTO) throws IOException {
		Optional<Category> optional = categoryRepository.findById(categoryId);
		if(optional.isPresent()) {
			Product product = new Product();
			BeanUtils.copyProperties(productDTO, product);
			product.setImg(productDTO.getImg().getBytes());
			product.setCategory(optional.get());
			Product CreatedProduct = productRepository.save(product);
			
			ProductDTO createdDto = new ProductDTO();
			createdDto.setId(CreatedProduct.getId());
			
			return createdDto;
		}
		return null;
	}

	@Override
	public List<ProductDTO> getAllProductsByCategory(Long categoryId) {
		return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDTO).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getAllProductsByCategoryAndTitle(Long categoryId, String title) {
		return productRepository.findAllByCategoryIdAndNameContaining(categoryId,title).stream().map(Product::getProductDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteProduct(Long productId) {
		Optional<Product> optional = productRepository.findById(productId);
		if(optional.isPresent()) {
			  productRepository.deleteById(productId);
		}
		else {
			throw new IllegalArgumentException("Product with id : "+ productId + " not found");
		}
	}

	@Override
	public ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws IOException {
		Optional<Product> optional = productRepository.findById(productId);
		if(optional.isPresent()) {
			Product product = optional.get();
			product.setName(productDTO.getName());
			product.setDescription(productDTO.getDescription());
			product.setPrice(productDTO.getPrice());
			if(productDTO.getImg()!=null) {
				product.setImg(productDTO.getImg().getBytes());
			}
			
			Product updatedProduct = productRepository.save(product);
			ProductDTO updatedProductDTO = new ProductDTO();
			updatedProductDTO.setId(updatedProduct.getId());
			
			return updatedProductDTO;
		}
		
		return null;
	}

	@Override
	public ProductDTO getProductById(Long productId) {
		Optional<Product> optional = productRepository.findById(productId);
		if(optional.isPresent()) {
			Product product = optional.get();
			return product.getProductDTO();
		}
		return null;
	}

	@Override
	public List<ReservationDto> getReservations() {
		return reservationRepository.findAll().stream().map(Reservation::getReservationDto).collect(Collectors.toList());
	}

	@Override
	public ReservationDto changeReservationStatus(Long reservationId, String status) {
		Optional<Reservation> optional = reservationRepository.findById(reservationId);
		if(optional.isPresent()) {
			Reservation reservation = optional.get();
			if(Objects.equals(status, "Approve")) {
			     reservation.setReservationStatus(ReservationStatus.APPROVED);
			}
			else {
				 reservation.setReservationStatus(ReservationStatus.DISAPPROVED);
			}
			
			Reservation updatedReservation = reservationRepository.save(reservation);
			ReservationDto updatedReservationDto = new ReservationDto();
			updatedReservationDto.setId(updatedReservation.getId());
			
			return updatedReservationDto;
		}
		return null;
	}	
	
}
