package com.org.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.shopping.dto.LoginDto;
import com.org.shopping.dto.ResponseDto;
import com.org.shopping.dto.UserDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @author Rakesh
	 * @param create a new user
	 * @return the Success message with corresponding status code
	 */
	@PostMapping
	public ResponseEntity<UserResponse> registerUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.CREATED);
	}

	/**
	 * @author Hasina
	 * @param validate email and password
	 * @return the Success message with corresponding status code
	 */
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> userLogin(@RequestBody LoginDto loginDto) {
		return new ResponseEntity<>(userService.loginUser(loginDto), HttpStatus.OK);
	}

}
