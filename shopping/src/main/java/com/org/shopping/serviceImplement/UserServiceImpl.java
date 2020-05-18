package com.org.shopping.serviceImplement;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shopping.constant.AppConstant;
import com.org.shopping.dto.LoginDto;
import com.org.shopping.dto.ResponseDto;
import com.org.shopping.dto.UserDto;
import com.org.shopping.dto.UserResponse;
import com.org.shopping.entity.User;
import com.org.shopping.exception.LoginException;
import com.org.shopping.exception.UserException;
import com.org.shopping.repository.UserRepository;
import com.org.shopping.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * @author Rakesh
	 * @param register a new user
	 * @return the Success message with corresponding status code
	 */
	@Override
	public UserResponse registerUser(UserDto userDto) {

		Optional<User> findByEmail = userRepository.findByEmail(userDto.getEmail());
		if (userDto.getUserName().isEmpty() || userDto.getUserType().isEmpty() || userDto.getEmail().isEmpty()
				|| userDto.getPassword().isEmpty() || userDto.getAddress().isEmpty()) {
			throw new UserException(AppConstant.DETAILS_REQUIRED);
		}

		if (findByEmail.isPresent()) {
			throw new UserException(AppConstant.USER_EXIST);
		}

		User user = new User();
		user.setAddress(userDto.getAddress());
		user.setCreatedDate(LocalDate.now().toString());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setPhoneNo(userDto.getPhoneNo());
		user.setUserName(userDto.getUserName());
		user.setUserStatus("true");
		user.setUserType(userDto.getUserType());
		userRepository.save(user);
		return new UserResponse(user.getUserName()+ " "+ AppConstant.SUCCESSSFULLY_SAVED_DATA, AppConstant.REGISTER_SUCCESS);
	}

	/**
	 * @author Hasina
	 * @param validate email and password
	 * @return the Success message with corresponding status code
	 */
	public ResponseDto loginUser(LoginDto loginDto) {
		ResponseDto responseDto = new ResponseDto();
		if (loginDto.getEmail() == null || loginDto.getPassword().isEmpty()) {
			throw new LoginException(AppConstant.DETAILS_REQUIRED);
		} else {
			Optional<User> user = userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());		
			
			if (!user.isPresent()) {
				throw new LoginException(AppConstant.USER_NOT_EXISTS);
			}
			
			responseDto.setStatusCode(AppConstant.LOGIN_SUCCESS);
			responseDto.setMessage(AppConstant.LOGIN_SUCCESSFUL);
			responseDto.setUserId(user.get().getUserId());
			responseDto.setUserName(user.get().getUserName());
			responseDto.setUserType(user.get().getUserType());			
		}
		return responseDto;
	}	
}
