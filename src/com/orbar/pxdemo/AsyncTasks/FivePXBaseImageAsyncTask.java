package com.orbar.pxdemo.AsyncTasks;

import android.content.Context;
import com.fivehundredpx.api.PxApi;
import com.orbar.pxdemo.LoginManager;
import com.orbar.pxdemo.R;
import com.orbar.pxdemo.APIHelper.FiveZeroZeroImageAPIBuilder;
import com.orbar.pxdemo.Model.FiveZeroZeroImageBean;
import com.orbar.pxdemo.Model.MyAccessToken;

public abstract class FivePXBaseImageAsyncTask extends FivePXBaseAsyncTask {

	String TAG = this.getClass().getName();
	
	LoginManager mLoginManager;
	protected FiveZeroZeroImageBean mFiveZeroZeroImageBean;
	
	protected FivePXBaseImageAsyncTask (Context mContext, FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder, 
		LoginManager mLoginManager, FiveZeroZeroImageBean mFiveZeroZeroImageBean) {
		
		super (mContext, mFiveZeroZeroImageAPIBuilder);
		
		this.mLoginManager = mLoginManager;
		this.mFiveZeroZeroImageBean = mFiveZeroZeroImageBean;
		
	}
	
	@Override
	protected PxApi getAPI() {
		PxApi api;
		
		if (LoginRequired()) {
			api = new PxApi(mLoginManager.getAccessMyToken(), mContext.getString(R.string.px_consumer_key),
					mContext.getString(R.string.px_consumer_secret));
		} else {
			// will use the log in token if it's available, or null if it's not
			api = new PxApi(MyAccessToken.getMyToken().getmAccessToken(), mContext.getString(R.string.px_consumer_key),
					mContext.getString(R.string.px_consumer_secret));
		}
		
		return api;
	}
	
}