package com.orbar.pxdemo.Adapters;

import java.util.ArrayList;
import java.util.List;

import com.orbar.pxdemo.R;
import com.orbar.pxdemo.Model.FiveZeroZeroCommentBean;
import com.orbar.pxdemo.Model.FiveZeroZeroUserBean.USER_IMAGE_SIZE;
import com.orbar.pxdemo.Model.FiveZeroZeroUserBean.USER_UPGRADE_STATUS;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
 
public class CommentsAdapter extends BaseAdapter {
	private Context context;
	private final ArrayList<FiveZeroZeroCommentBean> commentBeans;
	
	static class ViewHolder {
		ImageView commentUserIcon;
		ImageView commentUserUpgradeStatusIcon;
		TextView commentUserName;
		TextView commentTime;
		TextView commentBody;
		
		}
	
	public CommentsAdapter(Context context, List<FiveZeroZeroCommentBean> commentBeans) {
		this.context = context;
		this.commentBeans = (ArrayList<FiveZeroZeroCommentBean>) commentBeans;
	}
 
	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {                

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = inflater.inflate(R.layout.listview_comment_item, null);   
		   
			// Creates a ViewHolder and store references to the child view                
		    holder = new ViewHolder();                
		    holder.commentUserIcon = (ImageView) convertView.findViewById(R.id.comment_user_icon); 
		    holder.commentUserUpgradeStatusIcon = (ImageView) convertView.findViewById(R.id.comment_user_upgrade_status_icon); 
		    holder.commentUserName = (TextView) convertView.findViewById(R.id.comment_user);
		    holder.commentTime= (TextView) convertView.findViewById(R.id.comment_time);
		    holder.commentBody = (TextView) convertView.findViewById(R.id.comment_body);
		    
		    convertView.setTag(holder);            
		} else {                
			holder = (ViewHolder) convertView.getTag();
		}        
		
		// Trigger the download of the URL asynchronously into the image view.
		Picasso.with(context) //
		    .load(commentBeans.get(position).getUserBean().getUserpicURL(USER_IMAGE_SIZE.MEDIUM)) //
	        .into(new badgeTarget(position, holder));
	        
		holder.commentUserName.setText(commentBeans.get(position).getUserBean().getFullName());
		holder.commentTime.setText(commentBeans.get(position).getPrettyCreatedAt());
		holder.commentBody.setText(commentBeans.get(position).getBody());
		
		return convertView;
        
		
	}
 
	@Override
	public int getCount() {
		return commentBeans.size();
	}
 
	@Override
	public Object getItem(int position) {
		 return commentBeans.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
	private class badgeTarget implements Target {
		int position;
		ViewHolder holder;
		
		badgeTarget(int position, ViewHolder holder){
			this.position = position;
			this.holder = holder;
		}
		
		@Override public void onBitmapFailed(Drawable arg0) { holder.commentUserIcon.setImageResource(android.R.drawable.stat_notify_error); }
		@Override public void onPrepareLoad(Drawable arg0) { holder.commentUserIcon.setImageResource(R.drawable.ic_placeholder_dowloading); }
	    
		@Override
		public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
			
			holder.commentUserIcon.setImageBitmap(bitmap);
			
			applyUserBadgeAt(position);
		}
		
		private void applyUserBadgeAt (int position) {
			
			if ( commentBeans.get(position).getUserBean().getUpgradeStatus() == USER_UPGRADE_STATUS.PLUS.getValue()) {
				holder.commentUserUpgradeStatusIcon.setImageResource(R.drawable.ic_user_plus);
			} else if ( commentBeans.get(position).getUserBean().getUpgradeStatus() == USER_UPGRADE_STATUS.AWESOME.getValue()) {
				holder.commentUserUpgradeStatusIcon.setImageResource(R.drawable.ic_user_awesome);
			} else {
				// Because of view recycling, we need to clear the badge that is already there for the empty case
				holder.commentUserUpgradeStatusIcon.setImageResource(android.R.color.transparent);
			}
		}
	}
	
}