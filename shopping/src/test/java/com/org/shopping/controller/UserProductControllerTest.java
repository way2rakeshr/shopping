package com.org.shopping.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.org.shopping.constant.AppConstant;
import com.org.shopping.dto.BuyProductDto;
import com.org.shopping.dto.BuyResponseDto;
import com.org.shopping.dto.UserProductDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.service.UserProductService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserProductControllerTest {

	  @InjectMocks
	    UserProductController userProductController;
	    @Mock
	    UserProductService userProductService;	 

	    UserResponse response = new UserResponse(AppConstant.SUCCESSSFULLY_SAVED_DATA, AppConstant.REGISTER_SUCCESS);
	    List<UserProductDto> list = new ArrayList<UserProductDto>();	 
	    BuyProductDto buyproductDto = new BuyProductDto();
	    BuyResponseDto buyresponsedto = new BuyResponseDto(1L, "Successfully Purchased", 725);

	    void init() {	 
            buyproductDto.setProductId(1L);
            buyproductDto.setUserId(1L);
            buyproductDto.setQuantity(1);
	    }	 

	    @Test
	    public void buytest() {
	        when(userProductService.buyProduct(1, 1, 1)).thenReturn(buyresponsedto);
	        ResponseEntity<BuyResponseDto> buyProduct = userProductController.buyProduct(buyproductDto);
	        assertEquals(HttpStatus.CREATED, buyProduct.getStatusCode());
	    }
	    
	    @Test
	    public void getUserProduct() {
	        when(userProductService.getUserProducts((long) 1)).thenReturn(list);
	        ResponseEntity<List<UserProductDto>> user = userProductController.getUserProducts(1L);
	        assertEquals(HttpStatus.ACCEPTED, user.getStatusCode());
	    }
	
}
