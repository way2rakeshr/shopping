package com.org.shopping.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.org.shopping.constant.AppConstant;
import com.org.shopping.dto.LoginDto;
import com.org.shopping.dto.ResponseDto;
import com.org.shopping.dto.UserDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.entity.User;
import com.org.shopping.entity.UserProduct;
import com.org.shopping.exception.LoginException;
import com.org.shopping.exception.UserException;
import com.org.shopping.repository.UserRepository;
import com.org.shopping.serviceImplement.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	UserRepository userRepository;

	UserDto userdto = new UserDto();
	User user = new User();
	LoginDto loginDto = new LoginDto();
	List<UserProduct> userproduct = new ArrayList<UserProduct>();
	ResponseDto responseDto = new ResponseDto();

	UserResponse response = new UserResponse("You have successfully login...", AppConstant.LOGIN_SUCCESS);

	@Before
	public void init() {
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
		user.setUserId(1L);
		user.setUserProduct(userproduct);

		loginDto.setEmail("way2rakesh@gmail.com");
		loginDto.setPassword("123456789");

		responseDto.setUserName("Hari");
		responseDto.setUserType("Prime");
	}

	/**
	 * @author Rakesh
	 * @param create a new user
	 * @return the Success message with corresponding status code
	 */
	@Test
	public void saveRegister() {
		when(userRepository.findByEmail("way2rakeshr@gmail.com")).thenReturn(Optional.of(user));
		UserResponse registerUser = userServiceImpl.registerUser(userdto);
		assertNotNull(registerUser);
	}

	/**
	 * @author Rakesh
	 * @param create a new user
	 * @return throw exception if same email
	 */
	@Test(expected = UserException.class)
	public void saveRegisterthrow() {
		when(userRepository.findByEmail("way2rakesh@gmail.com")).thenReturn(Optional.of(user));
		UserResponse registerUser = userServiceImpl.registerUser(userdto);
	}
	
	@Test
	public void test_loginUser() throws LoginException {
		when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(user));
		ResponseDto result = userServiceImpl.loginUser(loginDto);
		Assert.assertNotNull(result);
		assertEquals(710, result.getStatusCode());
		assertEquals("rakesh", result.getUserName());
	}

	
	@Test(expected = LoginException.class)
	public void test_invalidloginUser_exception() throws LoginException {
		User users = new User();
		users.setUserId(1L);
		when(userRepository.findByEmailAndPassword("reg@gmail.com", "reg12")).thenReturn(Optional.of(users));		
		userServiceImpl.loginUser(loginDto);
		
	}
	
	@Test(expected = LoginException.class)
	public void test_loginUser_empty_exception() throws LoginException {
		User users = new User();
		users.setUserId(1L);
		when(userRepository.findByEmailAndPassword("reg@gmail.com", "reg12")).thenReturn(Optional.of(users));		
		userServiceImpl.loginUser(loginDto);
		
	}
	
	@Test(expected = LoginException.class)
	public void test_loginUser_email_empty_error() {
		LoginDto dto = new LoginDto();
		dto.setEmail("");
		dto.setPassword("");
		when(userServiceImpl.loginUser(dto)).thenReturn(responseDto);
		userServiceImpl.loginUser(dto);
	}
	
	/*
	 * @Test(expected = UserException.class) public void saveRegisterthrowUser() {
	 * 
	 * when(userRepository.findByEmail("way2rakeshr@gmail.com")).thenReturn(Optional
	 * .of(user)); UserResponse registerUser =
	 * userServiceImpl.registerUser(userdto);
	 * 
	 * }
	 */
}
