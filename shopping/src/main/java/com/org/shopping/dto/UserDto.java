package com.org.shopping.dto;

public class UserDto {
	
	private String userName;
    
    private String userType;
    
    private String email;
    
    private String password;
    
    private long phoneNo; 

    private String address; 

   
    public String getUserName() {
        return userName;
    }

 

    public void setUserName(String userName) {
        this.userName = userName;
    }

 

    public String getUserType() {
        return userType;
    }

 

    public void setUserType(String userType) {
        this.userType = userType;
    }

 

    public String getEmail() {
        return email;
    }

 

    public void setEmail(String email) {
        this.email = email;
    }

 

    public String getPassword() {
        return password;
    }

 

    public void setPassword(String password) {
        this.password = password;
    }

 

    public long getPhoneNo() {
        return phoneNo;
    }

 

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

 

    public String getAddress() {
        return address;
    }

 

    public void setAddress(String address) {
        this.address = address;
    }
}