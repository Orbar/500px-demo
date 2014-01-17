package com.orbar.pxdemo.Model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class FiveZeroZeroUserContactsBean implements Parcelable {
	
	final static String TAG = "FiveZeroZeroUserContactsBean";
	
	String website;
	String twitter;
	String livejournal;
	String flickr;
	String gtalk;
	String skype;
	String facebook;
	String facebookPage;
	
	// Empty Constructor
	public FiveZeroZeroUserContactsBean() {};
	

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * @return the twitter
	 */
	public String getTwitter() {
		return twitter;
	}
	/**
	 * @param twitter the twitter to set
	 */
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	/**
	 * @return the livejournal
	 */
	public String getLivejournal() {
		return livejournal;
	}
	/**
	 * @param livejournal the livejournal to set
	 */
	public void setLivejournal(String livejournal) {
		this.livejournal = livejournal;
	}
	/**
	 * @return the flickr
	 */
	public String getFlickr() {
		return flickr;
	}
	/**
	 * @param flickr the flickr to set
	 */
	public void setFlickr(String flickr) {
		this.flickr = flickr;
	}
	/**
	 * @return the gtalk
	 */
	public String getGtalk() {
		return gtalk;
	}
	/**
	 * @param gtalk the gtalk to set
	 */
	public void setGtalk(String gtalk) {
		this.gtalk = gtalk;
	}
	/**
	 * @return the skype
	 */
	public String getSkype() {
		return skype;
	}
	/**
	 * @param skype the skype to set
	 */
	public void setSkype(String skype) {
		this.skype = skype;
	}
	/**
	 * @return the facebook
	 */
	public String getFacebook() {
		return facebook;
	}
	/**
	 * @param facebook the facebook to set
	 */
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	/**
	 * @return the facebookPage
	 */
	public String getFacebookPage() {
		return facebookPage;
	}
	/**
	 * @param facebookPage the facebookPage to set
	 */
	public void setFacebookPage(String facebookPage) {
		this.facebookPage = facebookPage;
	}



	public void parseJSONObject(JSONObject contactsObject) {
		
		try {
			if (contactsObject.has("website") && !contactsObject.isNull("website")) 
				website = contactsObject.getString("website");
			if (contactsObject.has("twitter") && !contactsObject.isNull("twitter")) 
				twitter = contactsObject.getString("twitter");
			if (contactsObject.has("livejournal") && !contactsObject.isNull("livejournal")) 
				livejournal = contactsObject.getString("livejournal");
			if (contactsObject.has("flickr") && !contactsObject.isNull("flickr")) 
				flickr = contactsObject.getString("flickr");
			if (contactsObject.has("gtalk") && !contactsObject.isNull("gtalk")) 
				gtalk = contactsObject.getString("gtalk");
			if (contactsObject.has("skype") && !contactsObject.isNull("skype")) 
				skype = contactsObject.getString("skype");
			if (contactsObject.has("facebook") && !contactsObject.isNull("facebook")) 
				facebook = contactsObject.getString("facebook");
			if (contactsObject.has("facebookpage") && !contactsObject.isNull("facebookpage")) 
				facebookPage = contactsObject.getString("facebookpage");
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Log.v(TAG, "writeToParcel..."+ flags);
	    
		dest.writeString(website);
		dest.writeString(twitter);
		dest.writeString(livejournal);
		dest.writeString(flickr);
		dest.writeString(gtalk);
		dest.writeString(skype);
		dest.writeString(facebook);
		dest.writeString(facebookPage);
	        
	}
	
	// Parcelling part
	public FiveZeroZeroUserContactsBean(Parcel source){
		Log.v(TAG, "ParcelData(Parcel source): time to put back parcel data");
        
		website = source.readString();
		twitter = source.readString();
		livejournal = source.readString();
		flickr = source.readString();
		gtalk = source.readString();
		skype = source.readString();
		facebook = source.readString();
		facebookPage = source.readString();
			
	}

	
	public static final Parcelable.Creator<FiveZeroZeroUserContactsBean> CREATOR = new Parcelable.Creator<FiveZeroZeroUserContactsBean>() {
	      public FiveZeroZeroUserContactsBean createFromParcel(Parcel source) {
	            return new FiveZeroZeroUserContactsBean(source);
	      }
	      public FiveZeroZeroUserContactsBean[] newArray(int size) {
	            return new FiveZeroZeroUserContactsBean[size];
	      }
	};
}
