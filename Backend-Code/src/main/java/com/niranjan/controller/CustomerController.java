package com.niranjan.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niranjan.DTOS.CategoryDto;
import com.niranjan.DTOS.ProductDTO;
import com.niranjan.DTOS.ReservationDto;
import com.niranjan.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> list =  customerService.getAllCategories();
		if(list==null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/categories/{title}")
	public ResponseEntity<List<CategoryDto>> getCategoriesByName(@PathVariable String title){
		List<CategoryDto> list =  customerService.getCategoriesByName(title);
		if(list==null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(list);
	}
	
	
	//	Product Operations
	
	@GetMapping("/{categoryId}/products")
	public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable Long categoryId){
		List<ProductDTO> list =  customerService.getProductByCategory(categoryId);
		if(list==null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{categoryId}/product/{title}")
	public ResponseEntity<List<ProductDTO>> getAllProductsByCategoryAndTitle(@PathVariable Long categoryId,@PathVariable String title){
		List<ProductDTO> list =  customerService.getAllProductsByCategoryAndTitle(categoryId,title);
		if(list==null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(list);
	}
	
	//Reservation Operations
	
	@PostMapping("/reservation")
	public ResponseEntity<?> postReservation(@RequestBody ReservationDto reservationDto) throws IOException{
		ReservationDto postReservation = customerService.postReservation(reservationDto);
		if(postReservation==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong...!!");
		return ResponseEntity.status(HttpStatus.CREATED).body(postReservation);
	}
	
	@GetMapping("/reservations/{customerId}")
	public ResponseEntity<List<ReservationDto>> getReservationByUser(@PathVariable Long customerId){
		List<ReservationDto> list =  customerService.getReservationByUser(customerId);
		if(list==null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(list);
		
	}
	
	
}
