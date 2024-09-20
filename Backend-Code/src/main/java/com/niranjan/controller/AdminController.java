package com.niranjan.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niranjan.DTOS.CategoryDto;
import com.niranjan.DTOS.ProductDTO;
import com.niranjan.DTOS.ReservationDto;
import com.niranjan.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin()
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/category")
	public ResponseEntity<CategoryDto> postCategory(@ModelAttribute CategoryDto categoryDto) throws IOException{
		CategoryDto postCategory = adminService.postCategory(categoryDto);
		if(postCategory==null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(postCategory);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> list =  adminService.getAllCategories();
		if(list==null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/categories/{title}")
	public ResponseEntity<List<CategoryDto>> getAllCategoriesByTitle(@PathVariable String title){
		List<CategoryDto> list =  adminService.getAllCategoriesByTitle(title);
		if(list==null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(list);
	}
	
	
	//Product Operations
	
	
	@PostMapping("/{categoryId}/product")
	public ResponseEntity<?> postProduct(@PathVariable Long categoryId ,@ModelAttribute ProductDTO productDTO) throws IOException{
		ProductDTO postProduct = adminService.postProduct(categoryId, productDTO);
		if(postProduct==null) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong...!!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(postProduct);
	}
	
	@GetMapping("/{categoryId}/products")
	public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(@PathVariable Long categoryId){
		List<ProductDTO> list =  adminService.getAllProductsByCategory(categoryId);
		if(list==null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{categoryId}/product/{title}")
	public ResponseEntity<List<ProductDTO>> getAllProductsByCategoryAndTitle(@PathVariable Long categoryId,@PathVariable String title){
		List<ProductDTO> list =  adminService.getAllProductsByCategoryAndTitle(categoryId,title);
		if(list==null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId){
		ProductDTO productById = adminService.getProductById(productId);
		if(productById == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productById);
	}
	
	@PutMapping("/product/{productId}")
	public ResponseEntity<?> UpdateProduct(@PathVariable Long productId ,@ModelAttribute ProductDTO productDTO) throws IOException{
		ProductDTO product = adminService.updateProduct(productId, productDTO);
		if(product==null) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong...!!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}
	
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
		adminService.deleteProduct(productId);
		return ResponseEntity.noContent().build();
	}
	
	
	//Reservations Operations
	
	@GetMapping("/reservations")
	public ResponseEntity<List<ReservationDto>> getReservations(){
		List<ReservationDto> list =  adminService.getReservations();
		if(list==null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/reservation/{reservationId}/{status}")
	public ResponseEntity<ReservationDto> changeReservationStatus(@PathVariable Long reservationId,@PathVariable String status){
		ReservationDto updatedReservationDto =  adminService.changeReservationStatus(reservationId,status);
		if(updatedReservationDto==null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(updatedReservationDto);
	}
	
	

}
