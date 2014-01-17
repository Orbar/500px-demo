package com.orbar.pxdemo.Model;

import com.fivehundredpx.api.auth.AccessToken;

public class MyAccessToken {
	
	private static MyAccessToken _myToken;
	
	private AccessToken mAccessToken;
    
    private MyAccessToken() {}
    
	public static MyAccessToken getMyToken() {
		if (_myToken == null) {
			_myToken = new MyAccessToken();
			
		}
		return _myToken;
	}

	
	
	public AccessToken getmAccessToken() {
		return mAccessToken;
	}

	public void setmAccessToken(AccessToken mAccessToken) {
		this.mAccessToken = mAccessToken;
	}
}
