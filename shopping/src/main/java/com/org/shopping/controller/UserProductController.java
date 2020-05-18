package com.org.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.shopping.dto.BuyProductDto;
import com.org.shopping.dto.BuyResponseDto;
import com.org.shopping.dto.UserProductDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.service.UserProductService;

@RestController
@RequestMapping("/userproducts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserProductController {

	@Autowired
	UserProductService userProductService;

	/**
	 * @author Rakesh
	 * @param saving a purchased data into the database
	 * @return the Success message with corresponding status code
	 */
	@PostMapping
	public ResponseEntity<BuyResponseDto> buyProduct(@RequestBody BuyProductDto buyproductDto) {
		return new ResponseEntity<>(userProductService.buyProduct(buyproductDto.getUserId(), buyproductDto.getProductId(), buyproductDto.getQuantity()),HttpStatus.CREATED);
	}

	/**
	 * @author Rakesh
	 * @param retriving product details from database based on user purchased
	 * @return list of product details
	 */
	@GetMapping
	public ResponseEntity<List<UserProductDto>> getUserProducts(@RequestParam("userId") Long userId) {
		return new ResponseEntity<>(userProductService.getUserProducts(userId), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{receiptId}")
	public ResponseEntity<UserResponse> ratingProduct(@PathVariable Long receiptId, @RequestParam("rating") double rating) {
		return new ResponseEntity<>(userProductService.ratingProduct(receiptId, rating), HttpStatus.ACCEPTED);
	}

}
