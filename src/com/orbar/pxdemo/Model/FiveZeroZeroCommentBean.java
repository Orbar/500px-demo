package com.orbar.pxdemo.Model;

import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class FiveZeroZeroCommentBean implements Parcelable {
	
	final static String TAG = "FiveZeroZeroUserBean";
	
	int id;
	int userId;
	int toUserId;
	String body;
	String createdAt;
	String prettyCreatedAt;
	int parentId;
	
	FiveZeroZeroUserBean userBean;
	
	// Empty Constructor
	public FiveZeroZeroCommentBean() {};
		
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the toUserId
	 */
	public int getToUserId() {
		return toUserId;
	}

	/**
	 * @param toUserId the toUserId to set
	 */
	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the preetyCreatedAt
	 */
	public String getPrettyCreatedAt() {
		
		return prettyCreatedAt;
		
	}
	
	/**
	 * @param prettyCreatedAt the prettyCreatedAt to set
	 */
	public void setPrettyCreatedAt(String CreatedAt) {
		
		//2012-02-08T19:00:13-05:00
		
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.ENGLISH).parse(getCreatedAt());
			PrettyTime p = new PrettyTime();
			
			this.prettyCreatedAt = p.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			this.prettyCreatedAt = CreatedAt;
		}
		
	}

	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the userBean
	 */
	public FiveZeroZeroUserBean getUserBean() {
		return userBean;
	}

	/**
	 * @param userBean the userBean to set
	 */
	public void setUserBean(FiveZeroZeroUserBean userBean) {
		this.userBean = userBean;
	}

	public void parseJSONObject(JSONObject userObject) {
		
		userBean = new FiveZeroZeroUserBean();
		
		try {
			if (userObject.has("id") && !userObject.isNull("id")) 
				id = userObject.getInt("id");
			if (userObject.has("user_id") && !userObject.isNull("user_id")) 
				userId = userObject.getInt("user_id");
			if (userObject.has("to_whom_user_id") && !userObject.isNull("to_whom_user_id")) 
				toUserId = userObject.getInt("to_whom_user_id");
			if (userObject.has("body") && !userObject.isNull("body")) 
				body = userObject.getString("body");
			if (userObject.has("created_at") && !userObject.isNull("created_at")) {
				createdAt = userObject.getString("created_at");
				setPrettyCreatedAt(createdAt);
			}
			if (userObject.has("parent_id") && !userObject.isNull("parent_id")) 
				parentId = userObject.getInt("parent_id");
			if (userObject.has("user") && !userObject.isNull("user"))
				userBean.parseJSONObject(userObject.getJSONObject("user"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Log.v(TAG, "writeToParcel..."+ flags);
	    
		dest.writeInt(id);
		dest.writeInt(userId);
		dest.writeInt(userId);
		dest.writeString(body);
		dest.writeString(createdAt);
	    dest.writeInt(parentId);
	    
	    dest.writeParcelable(userBean, flags);   
	}
	
	// Parcelling part
	public FiveZeroZeroCommentBean(Parcel source){
		Log.v(TAG, "ParcelData(Parcel source): time to put back parcel data");
        
		id = source.readInt();
		userId = source.readInt();
		toUserId = source.readInt();
		body = source.readString();
		createdAt = source.readString();
		parentId = source.readInt();
		
		userBean = (FiveZeroZeroUserBean) source.readParcelable(FiveZeroZeroUserBean.class.getClassLoader());
		
	}

	
	public static final Parcelable.Creator<FiveZeroZeroCommentBean> CREATOR = new Parcelable.Creator<FiveZeroZeroCommentBean>() {
	      public FiveZeroZeroCommentBean createFromParcel(Parcel source) {
	            return new FiveZeroZeroCommentBean(source);
	      }
	      public FiveZeroZeroCommentBean[] newArray(int size) {
	            return new FiveZeroZeroCommentBean[size];
	      }
	};
}
