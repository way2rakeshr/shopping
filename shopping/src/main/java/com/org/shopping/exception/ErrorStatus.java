package com.org.shopping.exception;

public class ErrorStatus {

    int statuscode;
    String statusmessage;
    
    public ErrorStatus() {
    	
    }
    
	public ErrorStatus(int statuscode, String statusmessage) {
		super();
		this.statuscode = statuscode;
		this.statusmessage = statusmessage;
	}
	public int getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}
	public String getStatusmessage() {
		return statusmessage;
	}
	public void setStatusmessage(String statusmessage) {
		this.statusmessage = statusmessage;
	}
    
}
