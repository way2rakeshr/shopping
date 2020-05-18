package com.org.shopping.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.org.shopping.constant.AppConstant;
import com.org.shopping.dto.ErrorResponse;

@ControllerAdvice
public class Global extends ResponseEntityExceptionHandler {

//	@Override
//	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//		
//		Map<String, Object> body = new LinkedHashMap<>();
//		body.put("timestamp", new Date());
//		body.put("status", status.value());
//
//		// Get all errors for field valid
//		
//		 List<Object> detail = new ArrayList<>();
//	        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
//	            detail.add(error.getDefaultMessage());
//	 
//	        }
//	        ErrorResponse er= new 
//		return new ResponseEntity<>(body, headers, status);
//	}
public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers,HttpStatus status,WebRequest request){
	Map<String, Object > body=new LinkedHashMap<>();	
	body.put("timeStamp", new Date());
	body.put("status", 750);
List<Object> ob=new ArrayList<Object>();
for (ObjectError error : ex.getBindingResult().getAllErrors()) {
   ob.add(error.getDefaultMessage());

}
	body.put("error", ob);
	
	return new ResponseEntity<Object>(body,headers,status);
	
}
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ErrorStatus> loginresponse(LoginException loginException) {
		ErrorStatus error = new ErrorStatus();
		error.setStatusmessage(loginException.getMessage());
		error.setStatuscode(AppConstant.LOGIN_UNSUCCESS);
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(EmailException.class)
	public ResponseEntity<ErrorStatus> emailresponse(EmailException emailException) {
		ErrorStatus error = new ErrorStatus();
		error.setStatusmessage(emailException.getMessage());
		error.setStatuscode(AppConstant.INVALID_MAIL);
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ErrorStatus> prductresponse(ProductException productException) {
		ErrorStatus error = new ErrorStatus();
		error.setStatusmessage(productException.getMessage());
		error.setStatuscode(AppConstant.PRODUCT_NOT_EXIST);
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UserException.class)
	ResponseEntity<ErrorStatus> showresponse(UserException userException) {
		ErrorStatus er = new ErrorStatus();
		er.setStatusmessage(userException.getMessage());
		er.setStatuscode(AppConstant.USER_NOT_EXIST);
		return new ResponseEntity<>(er, HttpStatus.FORBIDDEN);
	}

}