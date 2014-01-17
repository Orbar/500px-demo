package com.orbar.pxdemo.AsyncTasks;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import com.fivehundredpx.api.PxApi;
import com.orbar.pxdemo.R;
import com.orbar.pxdemo.APIHelper.FiveZeroZeroImageAPIBuilder;
import com.orbar.pxdemo.Model.MyAccessToken;

public abstract class FivePXBaseAsyncTask extends AsyncTask<Void, Void, JSONObject> {

	String TAG = this.getClass().getName();
	
	Context mContext;
	protected FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder; 
	
	protected PxApi api;
	
	protected FivePXBaseAsyncTask (Context mContext, FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder) {
		
		this.mContext = mContext;
		this.mFiveZeroZeroImageAPIBuilder = mFiveZeroZeroImageAPIBuilder;
	}
	
	@Override
	protected JSONObject doInBackground(Void... params) {
		
		api = getAPI();
		
		return callAPI(); 
		
	}
	
	
	@Override
    protected void onPostExecute(JSONObject resObj) {
    	try {
			
    		if (resObj != null) {
    			parseReturn (resObj);
    			
    			updateView ();
    		} else {
    			if (hasErrToast())
    				ShowErrToast();
    		}
			
    		if (hasPostProcessing()) {
    			postProcess();
    		}
    		
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	

	protected PxApi getAPI() {
		return new PxApi(MyAccessToken.getMyToken().getmAccessToken(), mContext.getString(R.string.px_consumer_key),
					mContext.getString(R.string.px_consumer_secret));
	}
	
	
	
	protected abstract JSONObject callAPI();
	
	protected abstract void parseReturn(JSONObject resObj) throws JSONException;
	
	protected abstract void updateView();
	
	protected void ShowErrToast() { /* Do nothing by default */ }
	
	protected void postProcess() { /* Do nothing by default */ }
	
	
	// HOOKS 
	protected boolean LoginRequired() {return false;}
	protected boolean hasErrToast() {return false;}
	protected boolean hasPostProcessing() {return false;}
	
	
}