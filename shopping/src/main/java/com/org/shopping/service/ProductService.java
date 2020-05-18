package com.org.shopping.service;

import java.util.List;

import com.org.shopping.dto.ProductDto;
import com.org.shopping.dto.ProductListDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.entity.Product;

public interface ProductService {

	public List<ProductListDto> findAllProducts(Long userId);

	public List<ProductListDto> searchProduct(String productName, Long userId);

	public UserResponse addProduct(ProductDto prodcutDto);

	UserResponse ratingProduct(Long productId, int rating);	
	
}
