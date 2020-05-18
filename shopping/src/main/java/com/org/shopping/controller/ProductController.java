package com.org.shopping.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.shopping.dto.ProductDto;
import com.org.shopping.dto.ProductListDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.entity.Product;
import com.org.shopping.service.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

	@Autowired
	ProductService productservice;

	/**
	 * @author Rakesh
	 * @param rating product for the product that user purchased
	 * @return rating details
	 */
	@GetMapping("/{productId}")
	public ResponseEntity<UserResponse> ratingProduct(Long productId, int rating) {
		return new ResponseEntity<>(productservice.ratingProduct(productId, rating), HttpStatus.CREATED);
	}

	/**
	 * @author Hasina
	 * @param retriving product details from database
	 * @return list of product details
	 */
	@GetMapping
	public ResponseEntity<List<ProductListDto>> findAllProducts(@RequestParam("userId") Long userId) {
		return new ResponseEntity<>(productservice.findAllProducts(userId), HttpStatus.OK);
	}

	/**
	 * @author Hasina
	 * @param retriving product details based on user partial search of the product
	 * @return list of product details
	 */

	@GetMapping("/search")
	public ResponseEntity<List<ProductListDto>> searchProduct(@RequestParam("productName") String productName,
			@RequestParam("userId") Long userId) {
		return new ResponseEntity<>(productservice.searchProduct(productName, userId), HttpStatus.OK);
	}

	/**
	 * @author Hasina
	 * @param add product when admin login into the portal
	 * @return add new product details
	 */
	@PostMapping
	public ResponseEntity<UserResponse> addProduct(@Valid @RequestBody ProductDto productDto) {
		return new ResponseEntity<>(productservice.addProduct(productDto), HttpStatus.CREATED);
	}

}
