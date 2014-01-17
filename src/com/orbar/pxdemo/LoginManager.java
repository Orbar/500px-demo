package com.orbar.pxdemo;

import com.fivehundredpx.api.FiveHundredException;
import com.fivehundredpx.api.auth.AccessToken;
import com.orbar.pxdemo.Model.MyAccessToken;
import com.fivehundredpx.api.tasks.XAuth500pxTask;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class LoginManager implements XAuth500pxTask.Delegate{
	
	private static String TAG = "LoginManager";
	
	private static LoginManager   _instance;

	private static Activity mActivity;
	
	private static MyAccessToken mMyAccessToken;
	
	
	private static String userName;
	private static String password;
	
    private LoginManager(Activity mActivity)
    {
    	LoginManager.mActivity = mActivity;
    }

    public static LoginManager getInstance(Activity mActivity)
    {
        if (_instance == null)
        {
            _instance = new LoginManager(mActivity);
        }
        else {
        	LoginManager.mActivity = mActivity;
        }
        
        return _instance;
    }
    
    
    public boolean isLoggedIn () {
    	
    	mMyAccessToken = MyAccessToken.getMyToken();
    	
    	return (mMyAccessToken.getmAccessToken() != null);
    	
    }
    
    public AccessToken getAccessMyToken () {
    	
    	mMyAccessToken = MyAccessToken.getMyToken();
    	
    	return mMyAccessToken.getmAccessToken();
    	
    }
    
    /**
     * Try to logout of 500px by losing the credentials
     * 
     * */
    public void logout () {
    	
    	mMyAccessToken = MyAccessToken.getMyToken();
    	
    	mMyAccessToken.setmAccessToken(null);
    	
    	LoginManager.userName = "";
    	LoginManager.password = "";
    	
    	saveLoginInfo();
    }
    
    
    /**
     * Try to login to 500px using the saved username and password
     * 
     * */
    public void login() {
    	final XAuth500pxTask loginTask = new XAuth500pxTask(LoginManager.this);
    	
    	SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
    	
    	LoginManager.userName = mySharedPreferences.getString("userName", "");
    	LoginManager.password = mySharedPreferences.getString("password", "");
    	
    	Log.e("userName", LoginManager.userName);
    	Log.e("password", LoginManager.password);
    	
    	
    	if (LoginManager.userName != null && !LoginManager.userName.equalsIgnoreCase("")
    			&& LoginManager.password != null && !LoginManager.password.equalsIgnoreCase("")){
    	
	    	loginTask.execute(mActivity.getResources().getString(R.string.px_consumer_key),
	    			mActivity.getResources().getString(R.string.px_consumer_secret),
	    			LoginManager.userName,
	    			LoginManager.password);
    	} else {
    		 if (mActivity instanceof LoginListener) {
            	 ((LoginListener) mActivity).onFailedLogin(false);
             }
    	}
    }
    
    /**
     * Try to login to 500px using the passed username and password
     * 
     * @param userName
     * @param password
     */
    public void login(String userName, String password) {
    	final XAuth500pxTask loginTask = new XAuth500pxTask(LoginManager.this);
    	
    	LoginManager.userName = userName;
    	LoginManager.password = password;
    	
    	loginTask.execute(mActivity.getResources().getString(R.string.px_consumer_key),
    			mActivity.getResources().getString(R.string.px_consumer_secret),
    			userName,
    			password);
    		
    }
    
    // Called by the async task that performs the login on success
	@Override
	public void onSuccess(AccessToken result) {
		Log.d(TAG, "success");
        
		mMyAccessToken.setmAccessToken(result);
        
		saveLoginInfo();
        
		if (mActivity instanceof LoginListener) {
			((LoginListener) mActivity).onSuccessLogin();
        }
	}

	// Called by the async task that performs the login on failure
	@Override
	public void onFail(FiveHundredException e) {
		Log.d(TAG, "fail");
        
		onFail();
		
	}
	
	// Save the login info to the shared preferences
	private void saveLoginInfo() {
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
    	
		Editor editor = mySharedPreferences.edit();
    	editor.putString("userName", LoginManager.userName);
    	editor.putString("password", LoginManager.password);
    	editor.commit();
	 
	}
	
	// let the calling activity know that login has failed
	public void onFail() {
        ((Activity) mActivity).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                       // Toast.makeText(mActivity,  "Login Failed, please try again.", Toast.LENGTH_LONG).show();
                        
                     if (mActivity instanceof LoginListener) {
                    	 ((LoginListener) mActivity).onFailedLogin(true);
                     }
                }
        });

	}
	
	// Define an interface that will allow for calling classes to know when login has succeseded or failed
	interface LoginListener {
    	
    	void onSuccessLogin();
    	
    	void onFailedLogin(boolean credentialsExist);
    }

}
