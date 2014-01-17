package com.orbar.pxdemo.Model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class FiveZeroZeroUserBean implements Parcelable {
	
	final static String TAG = "FiveZeroZeroUserBean";
	
	int id;
	String userName;
	String firstName;
	String lastName;
	int sex;
	String city;
	String state;
	String country;
	String registrationDate;
	String about;
	String domain;
	int upgradeStatus;
	boolean fotomotoOn;
	String locale;
	boolean showNude;
	boolean storeOn;
	String fullName;
	String userpicURL;
	String email;
	int photosCount;
	int affection;
	int inFavoritesCount;
	int friendsCount;
	int followersCount;
	int uploadLimit;
	String uploadLimitExpiry;
	String upgradeStatusExpiry;
	
	FiveZeroZeroUserContactsBean contactsBean;
	
	FiveZeroZeroUserEquipmentBean equipmentBean;
	
	 
	public enum USER_IMAGE_SIZE {
		SMALL(4),
		MEDIUM(3),
		LARGE(2),
		ORIGINAL(1);
		
		private final int value;

	    private USER_IMAGE_SIZE(int value) {
	        this.value = value;
	    }

	}
	
	public enum USER_UPGRADE_STATUS {
		NOTHING(0),
		PLUS(1),
		AWESOME(2);
		
		private final int value;

	    private USER_UPGRADE_STATUS(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	// Empty Constructor
	public FiveZeroZeroUserBean() {};
		
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the userpicURL
	 */
	public String getUserpicURL(USER_IMAGE_SIZE userImageSize) {
		return userpicURL + userImageSize.value + ".jpg";
	}
	/**
	 * @param userpicURL the userpicURL to set
	 */
	public void setUserpicURL(String userpicURL) {
		this.userpicURL = userpicURL;
	}
	/**
	 * @return the upgradeStatus
	 */
	public int getUpgradeStatus() {
		return upgradeStatus;
	}
	/**
	 * @param upgradeStatus the upgradeStatus to set
	 */
	public void setUpgradeStatus(int upgradeStatus) {
		this.upgradeStatus = upgradeStatus;
	}
	/**
	 * @return the followersCount
	 */
	public int getFollowersCount() {
		return followersCount;
	}
	/**
	 * @param followersCount the followersCount to set
	 */
	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}
	/**
	 * @return the affection
	 */
	public int getAffection() {
		return affection;
	}
	/**
	 * @param affection the affection to set
	 */
	public void setAffection(int affection) {
		this.affection = affection;
	}
	/**
	 * @return the sex
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the registrationDate
	 */
	public String getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return the about
	 */
	public String getAbout() {
		return about;
	}

	/**
	 * @param about the about to set
	 */
	public void setAbout(String about) {
		this.about = about;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the fotomotoOn
	 */
	public boolean isFotomotoOn() {
		return fotomotoOn;
	}

	/**
	 * @param fotomotoOn the fotomotoOn to set
	 */
	public void setFotomotoOn(boolean fotomotoOn) {
		this.fotomotoOn = fotomotoOn;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return the showNude
	 */
	public boolean isShowNude() {
		return showNude;
	}

	/**
	 * @param showNude the showNude to set
	 */
	public void setShowNude(boolean showNude) {
		this.showNude = showNude;
	}

	/**
	 * @return the storeOn
	 */
	public boolean isStoreOn() {
		return storeOn;
	}

	/**
	 * @param storeOn the storeOn to set
	 */
	public void setStoreOn(boolean storeOn) {
		this.storeOn = storeOn;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the photos_count
	 */
	public int getPhotosCount() {
		return photosCount;
	}

	/**
	 * @param photos_count the photos_count to set
	 */
	public void setPhotosCount(int photosCount) {
		this.photosCount = photosCount;
	}

	/**
	 * @return the inFavoritesCount
	 */
	public int getInFavoritesCount() {
		return inFavoritesCount;
	}

	/**
	 * @param inFavoritesCount the inFavoritesCount to set
	 */
	public void setInFavoritesCount(int inFavoritesCount) {
		this.inFavoritesCount = inFavoritesCount;
	}

	/**
	 * @return the friends_count
	 */
	public int getFriendsCount() {
		return friendsCount;
	}

	/**
	 * @param friends_count the friends_count to set
	 */
	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}

	/**
	 * @return the uploadLimit
	 */
	public int getUploadLimit() {
		return uploadLimit;
	}

	/**
	 * @param uploadLimit the uploadLimit to set
	 */
	public void setUploadLimit(int uploadLimit) {
		this.uploadLimit = uploadLimit;
	}

	/**
	 * @return the uploadTimeExpiry
	 */
	public String getUploadLimitExpiry() {
		return uploadLimitExpiry;
	}

	/**
	 * @param uploadTimeExpiry the uploadTimeExpiry to set
	 */
	public void setUploadLimitExpiry(String uploadLimitExpiry) {
		this.uploadLimitExpiry = uploadLimitExpiry;
	}

	/**
	 * @return the upgrageStatusExpiry
	 */
	public String getUpgradeStatusExpiry() {
		return upgradeStatusExpiry;
	}

	/**
	 * @param upgrageStatusExpiry the upgrageStatusExpiry to set
	 */
	public void setUpgradeStatusExpiry(String upgradeStatusExpiry) {
		this.upgradeStatusExpiry = upgradeStatusExpiry;
	}

	/**
	 * @return the userpicURL
	 */
	public String getUserpicURL() {
		return userpicURL;
	}

	/**
	 * @return the contactsBean
	 */
	public FiveZeroZeroUserContactsBean getContactsBean() {
		return contactsBean;
	}

	/**
	 * @param contactsBean the contactsBean to set
	 */
	public void setContactsBean(FiveZeroZeroUserContactsBean contactsBean) {
		this.contactsBean = contactsBean;
	}

	/**
	 * @return the equipmentBean
	 */
	public FiveZeroZeroUserEquipmentBean getEquipmentBean() {
		return equipmentBean;
	}

	/**
	 * @param equipmentBean the equipmentBean to set
	 */
	public void setEquipmentBean(FiveZeroZeroUserEquipmentBean equipmentBean) {
		this.equipmentBean = equipmentBean;
	}

	public void parseJSONObject(JSONObject userObject) {
		
		contactsBean = new FiveZeroZeroUserContactsBean();
		equipmentBean = new FiveZeroZeroUserEquipmentBean();
		
		try {
			if (userObject.has("id") && !userObject.isNull("id")) 
				id = userObject.getInt("id");
			if (userObject.has("username") && !userObject.isNull("username")) 
				userName = userObject.getString("username");
			if (userObject.has("firstname") && !userObject.isNull("firstname")) 
				firstName = userObject.getString("firstname");
			if (userObject.has("lastname") && !userObject.isNull("lastname")) 
				lastName = userObject.getString("lastname");
			if (userObject.has("sex") && !userObject.isNull("sex")) 
				sex = userObject.getInt("sex");
			if (userObject.has("city") && !userObject.isNull("city")) 
				city = userObject.getString("city");
			if (userObject.has("state") && !userObject.isNull("state")) 
				state = userObject.getString("state");
			if (userObject.has("country") && !userObject.isNull("country")) 
				country = userObject.getString("country");
			if (userObject.has("registration_date") && !userObject.isNull("registration_date")) 
				registrationDate = userObject.getString("registration_date");
			if (userObject.has("about") && !userObject.isNull("about")) 
				about = userObject.getString("about");
			if (userObject.has("domain") && !userObject.isNull("domain")) 
				domain = userObject.getString("domain");
			if (userObject.has("upgrade_status") && !userObject.isNull("upgrade_status")) 
				upgradeStatus = userObject.getInt("upgrade_status");
			if (userObject.has("fotomoto_on") && !userObject.isNull("fotomoto_on")) 
				fotomotoOn = userObject.getBoolean("fotomoto_on");
			if (userObject.has("locale") && !userObject.isNull("locale")) 
				locale = userObject.getString("locale");
			if (userObject.has("show_nude") && !userObject.isNull("show_nude")) 
				showNude = userObject.getBoolean("show_nude");
			if (userObject.has("store_on") && !userObject.isNull("store_on")) 
				storeOn = userObject.getBoolean("store_on");
			if (userObject.has("fullname") && !userObject.isNull("fullname")) 
				fullName = userObject.getString("fullname");
			if (userObject.has("email") && !userObject.isNull("email")) 
				email = userObject.getString("email");
			if (userObject.has("photos_count") && !userObject.isNull("photos_count")) 
				photosCount = userObject.getInt("photos_count");
			if (userObject.has("affection") && !userObject.isNull("affection")) 
				affection = userObject.getInt("affection");
			if (userObject.has("in_favorites_count") && !userObject.isNull("in_favorites_count")) 
				inFavoritesCount = userObject.getInt("in_favorites_count");
			if (userObject.has("friends_count") && !userObject.isNull("friends_count")) 
				friendsCount = userObject.getInt("friends_count");
			if (userObject.has("followers_count") && !userObject.isNull("followers_count"))
				followersCount = userObject.getInt("followers_count");
			if (userObject.has("upload_limit") && !userObject.isNull("upload_limit"))
				uploadLimit = userObject.getInt("upload_limit");
			if (userObject.has("upload_limit_expiry") && !userObject.isNull("upload_limit_expiry")) 
				uploadLimitExpiry = userObject.getString("upload_limit_expiry");
			if (userObject.has("upgrade_status_expiry") && !userObject.isNull("upgrade_status_expiry")) 
				upgradeStatusExpiry = userObject.getString("upgrade_status_expiry");
			
			if (userObject.has("userpic_url") && !userObject.isNull("id")) { 
				userpicURL = userObject.getString("userpic_url");
				userpicURL = userpicURL.substring(0, userpicURL.lastIndexOf("/") + 1);
			}
			
			
			if (userObject.has("contacts") && !userObject.isNull("contacts"))
				contactsBean.parseJSONObject(userObject.getJSONObject("contacts"));
			
			
			if (userObject.has("equipment") && !userObject.isNull("equipment"))
				equipmentBean.parseJSONObject(userObject.getJSONObject("equipment"));
			
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
	    
		dest.writeInt(id);
		dest.writeString(userName);
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeInt(sex);
		dest.writeString(city);
		dest.writeString(state);
		dest.writeString(country);
	    dest.writeString(registrationDate);
	    dest.writeString(about);
	    dest.writeString(domain);
	    dest.writeInt(upgradeStatus);
	    dest.writeByte((byte) (fotomotoOn ? 1 : 0)); // Boolean
		dest.writeString(locale);
	    dest.writeByte((byte) (showNude ? 1 : 0)); // Boolean
	    dest.writeByte((byte) (storeOn ? 1 : 0)); // Boolean
	    dest.writeString(fullName);
	    dest.writeString(userpicURL);
	    dest.writeString(email);
	    dest.writeInt(photosCount);
	    dest.writeInt(affection);
	    dest.writeInt(inFavoritesCount);
	    dest.writeInt(friendsCount);
	    dest.writeInt(followersCount);
	    dest.writeInt(uploadLimit);
	    dest.writeString(uploadLimitExpiry);
	    dest.writeString(upgradeStatusExpiry);
	    
	}
	
	// Parcelling part
	public FiveZeroZeroUserBean(Parcel source){
		Log.v(TAG, "ParcelData(Parcel source): time to put back parcel data");
        
		id = source.readInt();
		userName = source.readString();
		firstName = source.readString();
		lastName = source.readString();
		sex = source.readInt();
		city = source.readString();
		state = source.readString();
		country = source.readString();
		registrationDate = source.readString();
		about = source.readString();
		domain = source.readString();
		upgradeStatus = source.readInt();
		fotomotoOn = source.readByte() == 1;
		locale = source.readString();
		showNude = source.readByte() == 1;
		storeOn = source.readByte() == 1;
		fullName = source.readString();
		userpicURL = source.readString();
		email = source.readString();
		photosCount = source.readInt();
		affection = source.readInt();
		inFavoritesCount = source.readInt();
		friendsCount = source.readInt();
		followersCount = source.readInt();
		uploadLimit = source.readInt();
		uploadLimitExpiry = source.readString();
		upgradeStatusExpiry = source.readString();
		
	}

	
	public static final Parcelable.Creator<FiveZeroZeroUserBean> CREATOR = new Parcelable.Creator<FiveZeroZeroUserBean>() {
	      public FiveZeroZeroUserBean createFromParcel(Parcel source) {
	            return new FiveZeroZeroUserBean(source);
	      }
	      public FiveZeroZeroUserBean[] newArray(int size) {
	            return new FiveZeroZeroUserBean[size];
	      }
	};
}
