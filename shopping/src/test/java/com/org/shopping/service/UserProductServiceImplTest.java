package com.org.shopping.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.org.shopping.dto.BuyResponseDto;
import com.org.shopping.dto.UserDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.entity.Product;
import com.org.shopping.entity.User;
import com.org.shopping.entity.UserProduct;
import com.org.shopping.repository.ProductRepository;
import com.org.shopping.repository.UserProductRepository;
import com.org.shopping.repository.UserRepository;
import com.org.shopping.serviceImplement.UserProductServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserProductServiceImplTest {

	@InjectMocks
	UserProductServiceImpl userProductService;

	@Mock
	UserRepository userRepository;
	@Mock
	ProductRepository productRepository;
	@Mock
	UserProductRepository userProductRepository;
	Product product = new Product();
	User user = new User();
	UserDto userdto = new UserDto();
	UserProduct userProduct = new UserProduct();
	ArrayList<UserProduct> liste = new ArrayList<UserProduct>();
	BuyResponseDto buyresponsedto = new BuyResponseDto(1L, "Successfully Purchased", 725);

	@Before
	public void init() {

		product.setDescription("these is unique");
		product.setId((long) 1);
		product.setPrice(100);
		product.setPriority("High");
		product.setProductCode("p100");
		product.setProductName("honeybee");
		product.setQuantity(10);
		product.setRating(4);
		userdto.setAddress("peenya");
		userdto.setEmail("way2rakesh@gmail.com");
		userdto.setPassword("123456789");
		userdto.setPhoneNo(123456789);

		userdto.setUserName("rakesh");
		userdto.setUserType("normal");

		user.setAddress(userdto.getAddress());
		user.setCreatedDate(LocalDate.now().toString());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		user.setPhoneNo(userdto.getPhoneNo());
		user.setUserName(userdto.getUserName());
		user.setUserStatus("true");
		user.setUserType(userdto.getUserType());
		user.setUserId((long) 1);
		user.setUserProduct(liste);
		userProduct.setPrice(100);
		userProduct.setProduct(product);
		userProduct.setPurchaseddate(LocalTime.now().toString());
		userProduct.setQuantity(2);
		userProduct.setUser(user);
		userProduct.setUserProductId((long) 1);
		liste.add(userProduct);

	}

	@Test
	public void buyProduct() {
		when(userRepository.findById((long) 1)).thenReturn(Optional.of(user));
		when(productRepository.findById((long) 1)).thenReturn((Optional.of(product)));
		when(userProductRepository.save(userProduct)).thenReturn(userProduct);
		BuyResponseDto buyResponse = userProductService.buyProduct(1, 1, 12);
		assertNotNull(buyResponse);

	}

	@Test
	public void buyProductelse() {
		when(userRepository.findById((long) 1)).thenReturn(Optional.of(user));
		when(productRepository.findById((long) 2)).thenReturn((Optional.of(product)));
		when(userProductRepository.save(userProduct)).thenReturn(userProduct);
		BuyResponseDto buyResponse = userProductService.buyProduct(1, 1, 12);
		assertNotNull(buyResponse);

	}

	@Test
	public void getUserProducts() {

		when(userRepository.findById((long) 1)).thenReturn(Optional.of(user));
		userProductService.getUserProducts((long) 1);
	}
}
