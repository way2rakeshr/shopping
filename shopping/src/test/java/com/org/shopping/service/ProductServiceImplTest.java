package com.org.shopping.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import com.org.shopping.constant.AppConstant;
import com.org.shopping.dto.ProductDto;
import com.org.shopping.dto.ProductListDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.entity.Product;
import com.org.shopping.entity.User;
import com.org.shopping.exception.ProductException;
import com.org.shopping.exception.UserException;
import com.org.shopping.repository.ProductRepository;
import com.org.shopping.repository.UserRepository;
import com.org.shopping.serviceImplement.ProductServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductServiceImplTest {

	@InjectMocks
	ProductServiceImpl productService;

	@Mock
	ProductRepository productRepository;

	@Mock
	UserRepository userRepository;

	List<Product> productList = new ArrayList<>();
	List<ProductDto> productDtoList = new ArrayList<>();
	User user = new User();
	Product product = new Product();
	ProductDto productDto = new ProductDto();

	@Before
	public void setup() {

		productList = Stream
				.of(new Product(1L, "Titan", "P001", "Ladies Watch", 5000.00, "High", 2, 5.0),
						new Product(2L, "Allen Solly", "P002", "Mens Wear", 2300.00, "Low", 5, 3.0))
				.collect(Collectors.toList());

		productDtoList = Stream
				.of(new ProductDto("P001", "Titan", "Ladies Watch", 5000, 3, "High"),
						new ProductDto("P002", "Allen Solly Suit", "Mens Wear", 2000.00, 5, "High"),
						new ProductDto("P003", "Titan", "Ladies Watch", 3, 3, "Low"),
						new ProductDto("P004", "Allen Solly Shirt", "Mens Wear", 2300.00, 5, "Low"))
				.collect(Collectors.toList());
		user.setUserId(1L);
		user.setUserName("John");
		user.setUserType(AppConstant.PRIME_USER);

		product.setProductCode("P001");
		product.setProductName("Titan");
		product.setPriority(AppConstant.HIGH);

		productDto.setProductName("Laptop");
		productDto.setPriority("High");
		productDto.setQuantity(5);
		productDto.setDescription("HP Laptop");
		productDto.setPrice(55000);
		productDto.setProductCode("P001");
	}

	/**
	 * @author Hasina
	 * @param find all product details based on prime users
	 */
	@Test
	public void test_findAllProducts_primeUsers_valid_success() {
		when(productRepository.findAll()).thenReturn(productList);
		when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		assertEquals(product.getProductName(), productDto.getProductName());
		List<ProductListDto> actual = productService.findAllProducts(user.getUserId());
		verify(productRepository, times(1)).findAll();
		verify(userRepository, times(1)).findById(user.getUserId());
		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	public void test_searchProduct_PrimeUsers_valid_success() {
		when(productRepository.findAll()).thenReturn(productList);
		when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
		ProductListDto productDto = new ProductListDto();
		BeanUtils.copyProperties(product, productDto);
		assertEquals(product.getProductName(), productDto.getProductName());
		List<ProductListDto> actual = productService.searchProduct("Allen", user.getUserId());
		verify(productRepository, times(1)).findAll();
		verify(userRepository, times(1)).findById(user.getUserId());
		assertNotNull(actual);
		assertEquals(1, actual.size());
	}

	@Test
	public void test_searchProduct_nonPrimeUsers_valid_success() {
		User user1 = new User();
		user1.setUserId(1L);
		user1.setUserName("John");
		user1.setUserType("Normal");
		when(productRepository.findAll()).thenReturn(productList);
		when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user1));
		ProductListDto productDto = new ProductListDto();
		BeanUtils.copyProperties(product, productDto);
		assertEquals(product.getProductName(), productDto.getProductName());
		List<ProductListDto> actual = productService.searchProduct("Titan", user.getUserId());
		verify(productRepository, times(1)).findAll();
		verify(userRepository, times(1)).findById(user.getUserId());
		assertNotNull(actual);
		assertEquals(1, actual.size());
	}

	@Test(expected = ProductException.class)
	public void test_searchProduct_productnotFound_exception() {
		String name = "";
		when(productService.searchProduct(name, user.getUserId()))
				.thenThrow(new ProductException(AppConstant.PRODUCT_NAME));
		productService.searchProduct(name, user.getUserId());
	}

	@Test(expected = ProductException.class)
	public void test_searchProduct_productName_exception() {
		User user1 = new User();
		user1.setUserId(1L);
		user1.setUserName("John");
		user1.setUserType("Normal");
		when(productRepository.findAll()).thenReturn(productList);
		when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user1));
		List<ProductListDto> actual = productService.searchProduct("Dell", user.getUserId());
		when(productService.searchProduct(Mockito.anyString(), user.getUserId()))
				.thenThrow(new ProductException(AppConstant.PRODUCT_NOT_FOUND));
		productService.searchProduct(Mockito.anyString(), user.getUserId());
	}

	@Test(expected = UserException.class)
	public void test_searchProducts_userId_exception() {
		when(userRepository.findById(12L)).thenThrow(new UserException(AppConstant.USER_NOT_EXISTS));
		List<ProductListDto> actual = productService.searchProduct(product.getProductName(), 12L);
		verify(userRepository, times(1)).findById(12L);
	}

	/**
	 * @author Hasina
	 * @param findall product details based on non prime users
	 */
	@Test
	public void test_findAllProducts_notPrimeUsers_success() {
		User newuser = new User();
		newuser.setUserName("John");
		newuser.setUserType("Normal");
		when(productRepository.findAll()).thenReturn(productList);
		when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(newuser));
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		assertEquals(product.getProductName(), productDto.getProductName());
		List<ProductListDto> actual = productService.findAllProducts(user.getUserId());
		verify(productRepository, times(1)).findAll();
		verify(userRepository, times(1)).findById(user.getUserId());
		assertNotNull(actual);
		assertEquals(1, actual.size());
	}

	@Test
	public void test_ratingProduct_valid_success() {

		when(productRepository.findById((long) 1)).thenReturn(Optional.of(product));
		UserResponse ratingProduct = productService.ratingProduct((long) 1, 4);
		assertEquals(AppConstant.USER_REVIEW, ratingProduct.getStatusCode());
	}

	@Test
	public void test_ratingProductselse() {

		when(productRepository.findById((long) 1)).thenReturn(Optional.of(product));
		UserResponse ratingProduct = productService.ratingProduct((long) 5, 4);
		assertEquals(AppConstant.USER_REVIEW, ratingProduct.getStatusCode());
	}

	/**
	 * @author Hasina
	 * @param throw exception if user code not found
	 */
	@Test(expected = UserException.class)
	public void test_findAllProducts_exception() {
		when(userRepository.findById(12L)).thenThrow(new UserException(AppConstant.USER_NOT_EXISTS));
		List<ProductListDto> actual = productService.findAllProducts(user.getUserId());
		verify(userRepository, times(1)).findById(user.getUserId());
	}

	@Test
	public void test_addProduct_valid_success() {
		when(productRepository.findByProductName(product.getProductName())).thenReturn(Optional.of(product));
		UserResponse response = productService.addProduct(productDto);
		assertNotNull(response);
		assertEquals("Laptop added successfully!", response.getMessage());
	}

	@Test(expected = UserException.class)
	public void test_addProduct_exception() {
		ProductDto product = new ProductDto();
		product.setProductName("Apple");
		product.setProductCode("M002");
		product.setDescription("abc");
		product.setPrice(0.0);
		product.setQuantity(0);
		product.setPriority("Low");
		when(productService.addProduct(product)).thenThrow(new UserException(AppConstant.DETAILS_REQUIRED));
		productService.addProduct(product);
	}

	@Test(expected = ProductException.class)
	public void test_addProduct_product_exception() {
		when(productRepository.findByProductName(productDto.getProductName()))
				.thenThrow(new ProductException(AppConstant.PRODUCT_EXISTS));
		UserResponse actual = productService.addProduct(productDto);
		verify(productRepository, times(1)).findByProductName(productDto.getProductName());
	}

}
