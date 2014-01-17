package com.orbar.pxdemo.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class FiveZeroZeroUserEquipmentBean implements Parcelable {
	
	final static String TAG = "FiveZeroZeroUserEquipmentBean";
	
	
	String camera[];
	String lens[];
	/*
	
	"equipment": {
	      "camera": [
	        "Kiev 88",
	        "Lomo Rocket",
	        "Lomo Action sampler"
	      ],
	      "lens": [
	        "Volna 80mm"
	      ]
	    },
	  */
	    
	// Empty Constructor
	public FiveZeroZeroUserEquipmentBean() {};
	

	/**
	 * @return the camera
	 */
	public String[] getCamera() {
		return camera;
	}
	/**
	 * @param camera the camera to set
	 */
	public void setCamera(String[] camera) {
		this.camera = camera;
	}
	/**
	 * @return the lens
	 */
	public String[] getLens() {
		return lens;
	}
	/**
	 * @param lens the lens to set
	 */
	public void setLens(String[] lens) {
		this.lens = lens;
	}






	public void parseJSONObject(JSONObject equipmentObject) {
		
		try {
			
			if (equipmentObject.has("camera") && !equipmentObject.isNull("camera")) {
				JSONArray camerasArr= equipmentObject.getJSONArray("camera");
				camera = new String[camerasArr.length()];
				for(int i=0;i<camerasArr.length();i++)
					camera[i]= camerasArr.getString(i);
			}
			if (equipmentObject.has("lens") && !equipmentObject.isNull("lens")) {
				JSONArray camerasArr= equipmentObject.getJSONArray("lens");
				lens = new String[camerasArr.length()];
				for(int i=0;i<camerasArr.length();i++)
					lens[i]= camerasArr.getString(i);
			}
			
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
	    
		dest.writeStringArray(camera);
		dest.writeStringArray(lens);
		    
	}
	
	// Parcelling part
	public FiveZeroZeroUserEquipmentBean(Parcel source){
		Log.v(TAG, "ParcelData(Parcel source): time to put back parcel data");
        
		source.readStringArray(camera);
		source.readStringArray(lens);
		
		Log.e(TAG, "" + camera.length);
	}

	
	public static final Parcelable.Creator<FiveZeroZeroUserEquipmentBean> CREATOR = new Parcelable.Creator<FiveZeroZeroUserEquipmentBean>() {
	      public FiveZeroZeroUserEquipmentBean createFromParcel(Parcel source) {
	            return new FiveZeroZeroUserEquipmentBean(source);
	      }
	      public FiveZeroZeroUserEquipmentBean[] newArray(int size) {
	            return new FiveZeroZeroUserEquipmentBean[size];
	      }
	};
}
