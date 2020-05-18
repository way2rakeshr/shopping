package com.org.shopping.service;

import com.org.shopping.dto.LoginDto;
import com.org.shopping.dto.ResponseDto;
import com.org.shopping.dto.UserDto;
import com.org.shopping.dto.UserResponse;

public interface UserService {

	public UserResponse registerUser(UserDto userDto);
	
	public ResponseDto loginUser(LoginDto loginDto);
	
}
