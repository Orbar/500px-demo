package com.orbar.pxdemo.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class FiveZeroZeroImageBean implements Parcelable {
	
	final static String TAG = "FiveZeroZeroImageBean";
	
	int id;
	int userId;
	String name;
	String description;
	String camera;
	String lens;
	String focalLength;
	String iso;
	String shutterSpeed;
	String aperture;
	int timesViewed;
	float rating;
	int status;
	String createdAt;
	int category;
	String location;
	boolean privacy;
	String latitude;
	String longitude;
	String takenAt;
	int hiResUploaded;
	boolean forSale;
	int width;
	int height;
	int votesCount;
	int favoritesCount;
	int commentsCount;
	boolean nsfw;
	int salesCount;
	float highestRating;
	String highestRatingDate;
	int licenseType;
	String imageUrl;
	boolean storeDownload;
	boolean storePrint;
	
	
	// If logged in these will be included as well
	boolean voted;
	boolean favorited;
	boolean purchased;
	
	
	
	FiveZeroZeroUserBean userBean;
	ArrayList<FiveZeroZeroCommentBean> commentBeans = new ArrayList<FiveZeroZeroCommentBean>();;
	
	public enum IMAGE_SIZE {
		SMALL(1),
		MEDIUM(2),
		LARGE(3),
		ORIGINAL(4);
		
		private final int value;

	    private IMAGE_SIZE(int value) {
	        this.value = value;
	    }

	}
	
	// Empty Constructor
	public FiveZeroZeroImageBean() {};
	
	
	
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the camera
	 */
	public String getCamera() {
		return camera;
	}
	/**
	 * @param camera the camera to set
	 */
	public void setCamera(String camera) {
		this.camera = camera;
	}
	/**
	 * @return the lens
	 */
	public String getLens() {
		return lens;
	}
	/**
	 * @param lens the lens to set
	 */
	public void setLens(String lens) {
		this.lens = lens;
	}
	/**
	 * @return the focalLength
	 */
	public String getFocalLength() {
		return focalLength;
	}
	/**
	 * @param focalLength the focalLength to set
	 */
	public void setFocalLength(String focalLength) {
		this.focalLength = focalLength;
	}
	/**
	 * @return the iso
	 */
	public String getIso() {
		return iso;
	}
	/**
	 * @param iso the iso to set
	 */
	public void setIso(String iso) {
		this.iso = iso;
	}
	/**
	 * @return the shutterSpeed
	 */
	public String getShutterSpeed() {
		return shutterSpeed;
	}
	/**
	 * @param shutterSpeed the shutterSpeed to set
	 */
	public void setShutterSpeed(String shutterSpeed) {
		this.shutterSpeed = shutterSpeed;
	}
	/**
	 * @return the aperture
	 */
	public String getAperture() {
		return aperture;
	}
	/**
	 * @param aperture the aperture to set
	 */
	public void setAperture(String aperture) {
		this.aperture = aperture;
	}
	/**
	 * @return the timesViewed
	 */
	public int getTimesViewed() {
		return timesViewed;
	}
	/**
	 * @param timesViewed the timesViewed to set
	 */
	public void setTimesViewed(int timesViewed) {
		this.timesViewed = timesViewed;
	}
	/**
	 * @return the rating
	 */
	public float getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(float rating) {
		this.rating = rating;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
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
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the privacy
	 */
	public boolean isPrivacy() {
		return privacy;
	}
	/**
	 * @param privacy the privacy to set
	 */
	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the takenAt
	 */
	public String getTakenAt() {
		return takenAt;
	}
	/**
	 * @param takenAt the takenAt to set
	 */
	public void setTakenAt(String takenAt) {
		this.takenAt = takenAt;
	}
	/**
	 * @return the hiResUploaded
	 */
	public int getHiResUploaded() {
		return hiResUploaded;
	}
	/**
	 * @param hiResUploaded the hiResUploaded to set
	 */
	public void setHiResUploaded(int hiResUploaded) {
		this.hiResUploaded = hiResUploaded;
	}
	/**
	 * @return the forSale
	 */
	public boolean isForSale() {
		return forSale;
	}
	/**
	 * @param forSale the forSale to set
	 */
	public void setForSale(boolean forSale) {
		this.forSale = forSale;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return the votesCount
	 */
	public int getVotesCount() {
		return votesCount;
	}
	/**
	 * @param votesCount the votesCount to set
	 */
	public void setVotesCount(int votesCount) {
		this.votesCount = votesCount;
	}
	/**
	 * @return the favoritesCount
	 */
	public int getFavoritesCount() {
		return favoritesCount;
	}
	/**
	 * @param favoritesCount the favoritesCount to set
	 */
	public void setFavoritesCount(int favoritesCount) {
		this.favoritesCount = favoritesCount;
	}
	/**
	 * @return the commentsCount
	 */
	public int getCommentsCount() {
		return commentsCount;
	}
	/**
	 * @param commentsCount the commentsCount to set
	 */
	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}
	/**
	 * @return the nsfw
	 */
	public boolean isNsfw() {
		return nsfw;
	}
	/**
	 * @param nsfw the nsfw to set
	 */
	public void setNsfw(boolean nsfw) {
		this.nsfw = nsfw;
	}
	/**
	 * @return the salesCount
	 */
	public int getSalesCount() {
		return salesCount;
	}
	/**
	 * @param salesCount the salesCount to set
	 */
	public void setSalesCount(int salesCount) {
		this.salesCount = salesCount;
	}
	/**
	 * @return the highestRating
	 */
	public float getHighestRating() {
		return highestRating;
	}
	/**
	 * @param highestRating the highestRating to set
	 */
	public void setHighestRating(float highestRating) {
		this.highestRating = highestRating;
	}
	/**
	 * @return the highestRatingDate
	 */
	public String getHighestRatingDate() {
		return highestRatingDate;
	}
	/**
	 * @param highestRatingDate the highestRatingDate to set
	 */
	public void setHighestRatingDate(String highestRatingDate) {
		this.highestRatingDate = highestRatingDate;
	}
	/**
	 * @return the licenseType
	 */
	public int getLicenseType() {
		return licenseType;
	}
	/**
	 * @param licenseType the licenseType to set
	 */
	public void setLicenseType(int licenseType) {
		this.licenseType = licenseType;
	}
	/**
	 * @return the storeDownload
	 */
	public boolean isStoreDownload() {
		return storeDownload;
	}
	/**
	 * @param storeDownload the storeDownload to set
	 */
	public void setStoreDownload(boolean storeDownload) {
		this.storeDownload = storeDownload;
	}
	/**
	 * @return the storePrint
	 */
	public boolean isStorePrint() {
		return storePrint;
	}
	/**
	 * @param storePrint the storePrint to set
	 */
	public void setStorePrint(boolean storePrint) {
		this.storePrint = storePrint;
	}
	/**
	 * @return the voted
	 */
	public boolean isVoted() {
		return voted;
	}
	/**
	 * @param voted the voted to set
	 */
	public void setVoted(boolean voted) {
		this.voted = voted;
	}
	/**
	 * @return the favorited
	 */
	public boolean isFavorited() {
		return favorited;
	}
	/**
	 * @param favorited the favorited to set
	 */
	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}
	/**
	 * @return the purchased
	 */
	public boolean isPurchased() {
		return purchased;
	}
	/**
	 * @param purchased the purchased to set
	 */
	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl(IMAGE_SIZE imageSize) {
		return imageUrl + imageSize.value + ".jpg";
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
	/**
	 * @return the commentBeans
	 */
	public ArrayList<FiveZeroZeroCommentBean> getCommentBeans() {
		return commentBeans;
	}
	/**
	 * @param commentBeans the commentBeans to set
	 */
	public void setCommentBeans(ArrayList<FiveZeroZeroCommentBean> commentBeans) {
		this.commentBeans = commentBeans;
	}

	public void addCommentBean(FiveZeroZeroCommentBean commentBean) {
		
		commentBeans.add(commentBean);
	}

	public void addCommentBean(FiveZeroZeroCommentBean commentBean, int position) {
		
		commentBeans.add(position, commentBean);
	}


	public void parseJSONObject(JSONObject imageObject) {

		
		userBean = new FiveZeroZeroUserBean();
		
	    
		try {
			if (imageObject.has("id") && !imageObject.isNull("id")) 
				id = imageObject.getInt("id");
			if (imageObject.has("user_id") && !imageObject.isNull("user_id")) 
				userId = imageObject.getInt("user_id");
			if (imageObject.has("name") && !imageObject.isNull("name")) 
				name = imageObject.getString("name");
			if (imageObject.has("description") && !imageObject.isNull("description")) 
				description = imageObject.getString("description");
			if (imageObject.has("camera") && !imageObject.isNull("camera")) 
				camera = imageObject.getString("camera");
			if (imageObject.has("lens") && !imageObject.isNull("lens")) 
				lens = imageObject.getString("lens");
			if (imageObject.has("focal_length") && !imageObject.isNull("focal_length")) 
				focalLength = imageObject.getString("focal_length");
			if (imageObject.has("iso") && !imageObject.isNull("iso")) 
				iso = imageObject.getString("iso");
			if (imageObject.has("shutter_speed") && !imageObject.isNull("shutter_speed")) 
		    	shutterSpeed = imageObject.getString("shutter_speed");
			if (imageObject.has("aperture") && !imageObject.isNull("aperture")) 
				aperture = imageObject.getString("aperture");
			if (imageObject.has("times_viewed") && !imageObject.isNull("times_viewed")) 
				timesViewed = imageObject.getInt("times_viewed");
			if (imageObject.has("rating") && !imageObject.isNull("rating")) {
				rating = (float) imageObject.getDouble("rating");
				rating = Float.valueOf(new BigDecimal(rating).setScale(2, RoundingMode.HALF_UP).toString());
			}if (imageObject.has("status") && !imageObject.isNull("status")) 
				status = imageObject.getInt("status");
			if (imageObject.has("created_at") && !imageObject.isNull("created_at")) 
				createdAt = imageObject.getString("created_at");
			if (imageObject.has("category") && !imageObject.isNull("category")) 
				category = imageObject.getInt("category");
			if (imageObject.has("category") && !imageObject.isNull("category")) 
				category = imageObject.getInt("category");
			if (imageObject.has("location") && !imageObject.isNull("location")) 
				location  = imageObject.getString("location");
			//if (imageObject.has("privacy") && !imageObject.isNull("privacy")) 
		    //	privacy = imageObject.getBoolean("privacy");
			if (imageObject.has("latitude") && !imageObject.isNull("latitude")) {
				latitude = imageObject.getString("latitude");
				String sLat = imageObject.getString("latitude");
				double dLat = Double.valueOf(sLat);
				DecimalFormat df = new DecimalFormat("#.###");
				latitude = df.format(dLat);
			}
			if (imageObject.has("longitude") && !imageObject.isNull("longitude")) {
				String sLong = imageObject.getString("longitude");
				double dLong = Double.valueOf(sLong);
				DecimalFormat df = new DecimalFormat("#.##");
		        longitude = df.format(dLong);
			}
			if (imageObject.has("taken_at") && !imageObject.isNull("taken_at")) 
				takenAt = imageObject.getString("taken_at");
			if (imageObject.has("hi_res_uploaded") && !imageObject.isNull("hi_res_uploaded")) 
				hiResUploaded = imageObject.getInt("hi_res_uploaded");
			if (imageObject.has("for_sale") && !imageObject.isNull("for_sale")) 
				forSale = imageObject.getBoolean("for_sale");
			if (imageObject.has("width") && !imageObject.isNull("width")) 
				width = imageObject.getInt("width");
			if (imageObject.has("height") && !imageObject.isNull("height")) 
				height = imageObject.getInt("height");
			if (imageObject.has("votes_count") && !imageObject.isNull("votes_count")) 
				votesCount = imageObject.getInt("votes_count");
			if (imageObject.has("favorites_count") && !imageObject.isNull("favorites_count")) 
				favoritesCount = imageObject.getInt("favorites_count");
			if (imageObject.has("comments_count") && !imageObject.isNull("comments_count")) 
				commentsCount = imageObject.getInt("comments_count");
			if (imageObject.has("nsfw") && !imageObject.isNull("nsfw")) 
				nsfw = imageObject.getBoolean("nsfw");
			if (imageObject.has("sales_count") && !imageObject.isNull("sales_count")) 
				salesCount = imageObject.getInt("sales_count");
			if (imageObject.has("highest_rating") && !imageObject.isNull("highest_rating")) {
				highestRating = (float) imageObject.getDouble("highest_rating");
				highestRating = Float.valueOf(new BigDecimal(highestRating).setScale(2, RoundingMode.HALF_UP).toString());
			}
			if (imageObject.has("highest_rating_date") && !imageObject.isNull("highest_rating_date")) 
				highestRatingDate = imageObject.getString("highest_rating_date");
			if (imageObject.has("license_type") && !imageObject.isNull("license_type")) 
				licenseType = imageObject.getInt("license_type");
			if (imageObject.has("store_download") && !imageObject.isNull("store_download")) 
		    	storeDownload = imageObject.getBoolean("store_download");
			if (imageObject.has("store_print") && !imageObject.isNull("store_print")) 
				storePrint = imageObject.getBoolean("store_print");
			
			if (imageObject.has("voted") && !imageObject.isNull("voted")) {
				voted = imageObject.getBoolean("voted");
			} else {
				voted = false;
			}
			
			if (imageObject.has("favorited") && !imageObject.isNull("favorited")){ 
				favorited = imageObject.getBoolean("favorited");
			} else {
				favorited = false;
			}
			if (imageObject.has("purchased") && !imageObject.isNull("purchased")) {
				purchased = imageObject.getBoolean("purchased");
			} else {
				purchased = false;
			}
			
			if (imageObject.has("image_url") && !imageObject.isNull("image_url")) { 
				imageUrl = imageObject.getString("image_url");
				imageUrl = imageUrl.substring(0, imageUrl.lastIndexOf("/") + 1);
			}
			
			if (imageObject.has("user") && !imageObject.isNull("user"))
				userBean.parseJSONObject(imageObject.getJSONObject("user"));
			
			
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
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(camera);
		dest.writeString(lens);
		dest.writeString(focalLength);
		dest.writeString(iso);
		dest.writeString(shutterSpeed);
		dest.writeString(aperture);
		dest.writeInt(timesViewed);
	    dest.writeFloat(rating);
	    dest.writeString(createdAt);
	    dest.writeInt(status);
		dest.writeString(createdAt);
		dest.writeInt(category);
		dest.writeString(location);
		dest.writeByte((byte) (privacy ? 1 : 0)); // Boolean
		dest.writeString(latitude);
		dest.writeString(longitude);
		dest.writeString(takenAt);
		dest.writeInt(hiResUploaded);
		dest.writeByte((byte) (forSale ? 1 : 0)); // Boolean
		dest.writeInt(width);
	    dest.writeInt(height);
	    dest.writeInt(votesCount);
	    dest.writeInt(favoritesCount);
	    dest.writeInt(commentsCount);
	    dest.writeByte((byte) (nsfw ? 1 : 0)); // Boolean
	    dest.writeInt(salesCount);
	    dest.writeFloat(highestRating);
	    dest.writeString(highestRatingDate);
		dest.writeInt(licenseType);
		dest.writeString(imageUrl);
		dest.writeByte((byte) (storeDownload ? 1 : 0)); // Boolean
		dest.writeByte((byte) (storePrint ? 1 : 0)); // Boolean
	    
		dest.writeByte((byte) (voted ? 1 : 0)); // Boolean
		dest.writeByte((byte) (favorited ? 1 : 0)); // Boolean
		dest.writeByte((byte) (purchased ? 1 : 0)); // Boolean
		
		dest.writeParcelable(userBean, flags);
	}
	
	// Parcelling part
	public FiveZeroZeroImageBean(Parcel source){
		Log.v(TAG, "ParcelData(Parcel source): time to put back parcel data");
        
		id = source.readInt();
		userId = source.readInt();
		name = source.readString();
        description = source.readString();
        camera = source.readString();
        lens = source.readString();
        focalLength = source.readString();
        iso = source.readString();
        shutterSpeed = source.readString();
        aperture = source.readString();
        timesViewed = source.readInt();
		rating = source.readFloat();
        status = source.readInt();
		createdAt = source.readString();
		category = source.readInt();
		location = source.readString();
		privacy = source.readByte() == 1; 
		latitude = source.readString();
		longitude = source.readString();
		takenAt = source.readString();
		hiResUploaded = source.readInt();
		forSale = source.readByte() == 1;
		width = source.readInt();
		height = source.readInt();
		votesCount = source.readInt();
		favoritesCount = source.readInt();
		commentsCount = source.readInt();
		nsfw = source.readByte() == 1;
		salesCount = source.readInt();
		highestRating = source.readFloat();
		highestRatingDate = source.readString();
		licenseType = source.readInt();
		imageUrl = source.readString();
		storeDownload = source.readByte() == 1;
		storePrint = source.readByte() == 1;
		voted = source.readByte() == 1;
		favorited = source.readByte() == 1;
		purchased = source.readByte() == 1;
		
		userBean = (FiveZeroZeroUserBean) source.readParcelable(FiveZeroZeroUserBean.class.getClassLoader());
	}

	public static final Parcelable.Creator<FiveZeroZeroImageBean> CREATOR = new Parcelable.Creator<FiveZeroZeroImageBean>() {
	      public FiveZeroZeroImageBean createFromParcel(Parcel source) {
	            return new FiveZeroZeroImageBean(source);
	      }
	      public FiveZeroZeroImageBean[] newArray(int size) {
	            return new FiveZeroZeroImageBean[size];
	      }
	};

}
