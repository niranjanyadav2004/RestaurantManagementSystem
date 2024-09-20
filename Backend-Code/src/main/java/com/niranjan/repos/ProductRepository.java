package com.niranjan.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niranjan.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllByCategoryId(Long categoryId);

	List<Product> findAllByCategoryIdAndNameContaining(Long categoryId, String title);

}
