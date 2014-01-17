package com.orbar.pxdemo.APIHelper;

public class FiveZeroZeroUsersAPIBuilder {

	private static String API_BASE_POINT = "https://api.500px.com/v1";
		
	// API ENDPOINTS
	private static String API_USERS_BASE = "/users?";
	
	
	private String APICall;
	
	
	public String getApiBasePoint() {
		return API_BASE_POINT;
	}
	
	/**
	 * @return the apiCall
	 */
	public String getMyAccount() {
		APICall = API_USERS_BASE;
		
		APICall = APICall.replace(" ", "%20");
		
		return APICall;
	}
	
}
