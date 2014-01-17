package com.orbar.pxdemo.Adapters;

import java.util.ArrayList;
import java.util.List;

import com.orbar.pxdemo.R;
import com.orbar.pxdemo.Model.FiveZeroZeroImageBean;
import com.orbar.pxdemo.Model.FiveZeroZeroImageBean.IMAGE_SIZE;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
 
 
public class ImageAdapter extends BaseAdapter {
	private Context context;
	private final ArrayList<FiveZeroZeroImageBean> imageBeans;
	
	static class ViewHolder {
		ImageView image;
		}
	
	public ImageAdapter(Context context, List<FiveZeroZeroImageBean> imageBeans) {
		this.context = context;
		this.imageBeans = (ArrayList<FiveZeroZeroImageBean>) imageBeans;
	}
 
	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {                

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = inflater.inflate(R.layout.grid_image, null);   
		   
			// Creates a ViewHolder and store references to the child view                
		    holder = new ViewHolder();                
		    holder.image = (ImageView) convertView.findViewById(R.id.grid_item_image);                
		    convertView.setTag(holder);            
		} else {                
			holder = (ViewHolder) convertView.getTag();
		}        
		
		// Trigger the download of the URL asynchronously into the image view.
	    Picasso.with(context) //
	        .load(imageBeans.get(position).getImageUrl(IMAGE_SIZE.MEDIUM)) //
	        .placeholder(R.drawable.ic_placeholder_dowloading) //
	        .error(android.R.drawable.stat_notify_error) //
	        .into(holder.image);
		
		return convertView;
        
        
	}
 
	@Override
	public int getCount() {
		return imageBeans.size();
	}
 
	@Override
	public Object getItem(int position) {
		 return imageBeans.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
}