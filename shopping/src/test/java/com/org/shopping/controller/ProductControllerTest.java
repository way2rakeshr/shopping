
package com.org.shopping.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.org.shopping.dto.ProductDto;
import com.org.shopping.dto.ProductListDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.service.ProductService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductControllerTest {

	@InjectMocks
	ProductController productController;

	@Mock
	ProductService productService;

	List<ProductListDto> productList = new ArrayList<>();
	ProductDto productDto = new ProductDto();
	UserResponse response = new UserResponse("Product added Successfully", HttpStatus.CREATED.value());

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		productList = Stream.of(new ProductListDto("P001", "Titan", "Ladies Watch", 5000.0, 5.0),
						        new ProductListDto("P002", "Allen Solly", "Mens Wear", 2300.0,4.0))
				      .collect(Collectors.toList());
    }
	
	/**
	 * @author Hasina
	 * @param retriving product details
	 * @return list of product details
	 */
	@Test
	public void test_findAllProducts_valid_success() {
		when(productService.findAllProducts(Mockito.any())).thenReturn(productList);
		ResponseEntity<List<ProductListDto>> actual = productController.findAllProducts(Mockito.any());
		assertEquals(HttpStatus.OK, actual.getStatusCode());
		verify(productService, times(1)).findAllProducts(Mockito.any());
	}
	
	@Test
	public void test_searchProduct_valid_success() {
		when(productService.searchProduct(Mockito.any(), Mockito.any())).thenReturn(productList);
		ResponseEntity<List<ProductListDto>> actual = productController.searchProduct(Mockito.any(), Mockito.any());
		assertEquals(HttpStatus.OK, actual.getStatusCode());
		verify(productService, times(1)).searchProduct(Mockito.any(), Mockito.any());
	}
	
	@Test
	public void test_addProduct_valid_success() {
		when(productService.addProduct(Mockito.any())).thenReturn(response);
		ResponseEntity<UserResponse> actual = productController.addProduct(productDto);
		assertEquals(HttpStatus.CREATED, actual.getStatusCode());
		verify(productService, times(1)).addProduct(Mockito.any());
	}
	
	@Test
	public void test_ratingProduct_valid_success() {
		when(productService.ratingProduct(Mockito.anyLong(), Mockito.anyInt())).thenReturn(response);
		ResponseEntity<UserResponse> actual = productController.ratingProduct(12L,5);
		assertEquals(HttpStatus.CREATED, actual.getStatusCode());
		verify(productService, times(1)).ratingProduct(Mockito.anyLong(), Mockito.anyInt());
	}	
	
}
