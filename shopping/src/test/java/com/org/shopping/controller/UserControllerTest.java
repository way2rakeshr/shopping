
package com.org.shopping.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.security.auth.login.LoginException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.org.shopping.constant.AppConstant;
import com.org.shopping.dto.LoginDto;
import com.org.shopping.dto.ResponseDto;
import com.org.shopping.dto.UserDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	UserDto userdto = new UserDto();
	ResponseDto responseDto = new ResponseDto();
	UserResponse response = new UserResponse("user registered Successfully", HttpStatus.CREATED.value());
	LoginDto loginDto = new LoginDto();

	@Before
	public void init() {

		responseDto.setUserName("John");
		responseDto.setStatusCode(985);
		responseDto.setMessage(AppConstant.LOGIN_SUCCESSFUL);

		userdto.setAddress("peenya");
		userdto.setEmail("way2rakesh@gmail.com");
		userdto.setPassword("123456789");
		userdto.setPhoneNo(123456789);
		userdto.setUserName("rakesh");
		userdto.setUserType("normal");

	}

	/**
	 * @author Rakesh
	 * @param create a new user
	 * @return the Success message with corresponding status code
	 */

	@Test
	public void saveAccountTest() {

		when(userService.registerUser(userdto)).thenReturn(response);
		ResponseEntity<UserResponse> response = userController.registerUser(userdto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	/**
	 * @author nagasudha
	 * @param validate email and password
	 * @return the Success message with corresponding status code
	 * @throws LoginException
	 */
	@Test
	public void test_userLogin_success() throws LoginException {
		when(userService.loginUser(loginDto)).thenReturn(responseDto);
		ResponseEntity<ResponseDto> expected = userController.userLogin(loginDto);
		assertEquals(HttpStatus.OK, expected.getStatusCode());
		verify(userService, times(1)).loginUser(loginDto);
	}

}
