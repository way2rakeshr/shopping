package com.org.shopping.service;

import java.util.List;

import com.org.shopping.dto.BuyResponseDto;
import com.org.shopping.dto.UserProductDto;
import com.org.shopping.dto.UserResponse;

public interface UserProductService {
	
    BuyResponseDto buyProduct(long userId,long productId ,int quantity) ;
    List<UserProductDto> getUserProducts(Long userid);
    UserResponse ratingProduct(Long id, double rating);
}
