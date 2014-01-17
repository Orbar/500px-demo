package com.orbar.pxdemo.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

import com.orbar.pxdemo.LoginActivity;
import com.orbar.pxdemo.LoginManager;
import com.orbar.pxdemo.MainActivity;
import com.orbar.pxdemo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orbar.pxdemo.APIHelper.Category;
import com.orbar.pxdemo.APIHelper.FiveZeroZeroImageAPIBuilder;
import com.orbar.pxdemo.APIHelper.License;
import com.orbar.pxdemo.Adapters.CommentsAdapter;
import com.orbar.pxdemo.AsyncTasks.FivePXBaseAsyncTask;
import com.orbar.pxdemo.AsyncTasks.FivePXBaseImageAsyncTask;
import com.orbar.pxdemo.MainActivity.BackPressedListener;
import com.orbar.pxdemo.Model.FiveZeroZeroCommentBean;
import com.orbar.pxdemo.Model.FiveZeroZeroImageBean;
import com.orbar.pxdemo.Model.FiveZeroZeroImageBean.IMAGE_SIZE;
import com.orbar.pxdemo.Model.FiveZeroZeroUserBean.USER_IMAGE_SIZE;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AbsListView.OnScrollListener;

public class FivePXImageFragment extends Fragment implements BackPressedListener {

	static final String TAG = "FivePXImageFragment";
	
	public static final String ARG_IMAGE_BEAN = "arg_image_bean";
	static final String ARG_IMAGE_PREVIEW = "arg_image_preview";
	
	View rootView;
	ActionBar actionBar;
	Bitmap actionBarUserImage;
	
	private PhotoViewAttacher mAttacher;
	
	RelativeLayout userAccountLayout;
	RelativeLayout imageControlsLayout;
	RelativeLayout imageDetailsLayout;
	
	ViewFlipper imageDetailFlipper;
	
	SlidingUpPanelLayout mSlidingPaneLayout;
	
	// View declerations 
	ListView commentsListView;

	ImageButton imageStats;
	Button imageComments;
	Button imageLikes;
	Button imageFavorites;
	ImageButton submitComment;
	
	EditText submitCommentBody;
	
	// AsyncTask for downloading the user image
	DownloadUserImage mDownloadUserImage;
		
	FiveZeroZeroImageBean mFiveZeroZeroImageBean;
	
	FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder; 
	
	// inputs for the API calls
	AtomicInteger pageNumber = new AtomicInteger();
		
	// Booleans used for infinite scrolling
	AtomicBoolean loadingMore = new AtomicBoolean(true);
	AtomicBoolean stopLoadingData = new AtomicBoolean(false);
		
	// Images Adapter
	CommentsAdapter mCommentsAdapter;
				
	// AsyncTask for downloading the comments
	FivePXBaseAsyncTask mLoadCommentsList;
	
	// AsyncTask for downloading the image details
	FivePXBaseAsyncTask mLoadImageDetailsList;
	
	// AsyncTask for liking an image
	FivePXBaseAsyncTask mLikeImage;
		
	// AsyncTask for submitting Comment
	FivePXBaseAsyncTask mPostCommentImage;
	
	// AsyncTask for favoriting an image
	FivePXBaseAsyncTask mFavoriteImage;
		
	LoginManager mLoginManager;
	
	private GoogleMap map;
    
	
	/*
	 *  These Are used to determine what the login was requested for.
	 * 
	 * 	Basically if you click "Like" and you are not logged in, then after logging in, the like will be submitted
	 */
	
	LOGGING_REASON reason;
	
	public enum LOGGING_REASON {
		LIKE,
		FAVORITE,
		COMMENT,
	}
	
	private void getReusableViews(View rootView) {
		
		this.rootView = rootView;
		
		// Get ActionBar
		actionBar = getActivity().getActionBar();
		
		// Get Sliding Panel Layouts
		mSlidingPaneLayout = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
		imageControlsLayout = (RelativeLayout) rootView.findViewById(R.id.image_controls);
		
		// Get the comments ListView
		commentsListView = (ListView) rootView.findViewById(R.id.comments_list);
				
		// Get the sliding Panel, buttons, ViewFlipper, and child layouts
		imageControlsLayout = (RelativeLayout) rootView.findViewById(R.id.image_controls);
		
		imageDetailFlipper = (ViewFlipper) rootView.findViewById(R.id.image_details_flipper);
		imageDetailsLayout = (RelativeLayout) rootView.findViewById(R.id.image_details);
		
		imageStats = (ImageButton) rootView.findViewById(R.id.image_stats);
		imageComments = (Button) rootView.findViewById(R.id.image_comments);
		imageLikes = (Button) rootView.findViewById(R.id.image_likes);
		imageFavorites = (Button) rootView.findViewById(R.id.image_favorites);
		
		submitComment = (ImageButton) rootView.findViewById(R.id.submit_comment_btn);
		
		// Get The login manager
		mLoginManager = LoginManager.getInstance(getActivity());
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreateView");
		
		final View rootView = inflater.inflate(R.layout.fragment_five_px_image,
				container, false);
		
		
		getReusableViews(rootView);
		
		// Set the attributes for the custom ActionBar
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		
		// Set the placeholder until download finishes.
		actionBar.setIcon(R.drawable.ic_launcher);
		
		// get the ImageBean that was clicked
		Bundle bundle = this.getArguments();
		mFiveZeroZeroImageBean = (FiveZeroZeroImageBean) bundle.getParcelable(ARG_IMAGE_BEAN);
		
		// Inflate the custom action bar layout
		LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View actionView = inflator.inflate(R.layout.actionbar_five_px_image, null);

		// set the title and user name in the action bar
		TextView imageTitle= (TextView) actionView.findViewById(R.id.image_title);
		TextView imageuserName = (TextView) actionView.findViewById(R.id.image_username);
		
		imageTitle.setText(mFiveZeroZeroImageBean.getName());
		imageuserName.setText(mFiveZeroZeroImageBean.getUserBean().getUserName());
		
		actionBar.setCustomView(actionView);
		
		// get reference to userAccountLayout in the action bar
		userAccountLayout = (RelativeLayout) actionView.findViewById(R.id.image_account_layout);

		// Download the user image in Extra Small size. No Picasso for this image.
		mDownloadUserImage = new DownloadUserImage();
		mDownloadUserImage.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mFiveZeroZeroImageBean.getUserBean().getUserpicURL(USER_IMAGE_SIZE.LARGE));
		   
		
		// Setup the Sliding Panel layout 
		mSlidingPaneLayout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
        mSlidingPaneLayout.setAnchorPoint(0.2f);
        mSlidingPaneLayout.setDragView(imageControlsLayout);
        mSlidingPaneLayout.setEnableDragViewTouchEvents(true);
        
		// Download the Image and put it into a zoom-able and pan-able imageview. 
		ImageView mImageView = (ImageView) rootView.findViewById(R.id.image);
		
		// The MAGIC happens here!
        mAttacher = new PhotoViewAttacher(mImageView);

		Picasso.with(getActivity()) //
        .load(mFiveZeroZeroImageBean.getImageUrl(IMAGE_SIZE.ORIGINAL))
        //.placeholder(image.getDrawable()) // Use the smaller image while downloading the higher resolution image 
        .error(android.R.drawable.stat_notify_error) // The error image
        .into(mImageView, new Callback() {
        	@Override public void onError() {}

        	// When finished loading, update the attacher
			@Override
			public void onSuccess() {
				mAttacher.update();
			}
		});
        
		
		//mAttacher.setOnPhotoTapListener(new PhotoTapListener());
		
		// Setup the comments ListView
		mCommentsAdapter = new CommentsAdapter(getActivity(), mFiveZeroZeroImageBean.getCommentBeans());
		commentsListView.setAdapter(mCommentsAdapter);
		
		// set the comment count
		imageComments.setText(Integer.toString(mFiveZeroZeroImageBean.getCommentsCount()));
		imageLikes.setText(Integer.toString(mFiveZeroZeroImageBean.getVotesCount()));
		imageFavorites.setText(Integer.toString(mFiveZeroZeroImageBean.getFavoritesCount()));
		
		// get the last page number (we want to add pages in reverse order (newest first)
		pageNumber.set((int) Math.ceil(mFiveZeroZeroImageBean.getCommentsCount()/20.0));
		
	    mFiveZeroZeroImageAPIBuilder = new FiveZeroZeroImageAPIBuilder()
	    		.setPageNum(pageNumber.get());
		
	    mLoadCommentsList = new LoadCommentsList(getActivity(), mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean, 
	    		loadingMore, stopLoadingData, pageNumber);
	    mLoadCommentsList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		
	    mLoadImageDetailsList = new LoadImageDetailsList(getActivity(), mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean);
        mLoadImageDetailsList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        
		return rootView;
	}	
	
	
	
		@Override
	public void onPause() {
		super.onPause();
		
		Log.d(TAG, "onPause");
		
		// Stop all background threads (if running)
		if (mDownloadUserImage != null) {
			mDownloadUserImage.cancel(true);
		}
		
		if (mLoadCommentsList != null) {
			mLoadCommentsList.cancel(true);
		}
		
		if (mLikeImage != null) {
			mLikeImage.cancel(true);
		}
		
		if (mFavoriteImage != null) {
			mFavoriteImage.cancel(true);
		}
		
		if (mPostCommentImage != null) {
			mPostCommentImage.cancel(true);
		}
		
		
		//Restore the original actionbar settings
		actionBar.setIcon(R.drawable.ic_launcher);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		
		// Remove all weak refrences to listeners
		mSlidingPaneLayout.setPanelSlideListener(null);
		userAccountLayout.setOnClickListener(null);
		imageStats.setOnClickListener(null);
		imageComments.setOnClickListener(null);
		imageLikes.setOnClickListener(null);
		imageFavorites.setOnClickListener(null);
		imageControlsLayout.setOnClickListener(null);
		commentsListView.setOnScrollListener(null);
	    submitComment.setOnClickListener(null);
	    mAttacher.setOnPhotoTapListener(null);
		
	    
	    
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();
		
		//Restore the original actionbar settings
		actionBar.setIcon(R.drawable.ic_launcher);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		
		BitmapDrawable icon = new BitmapDrawable(getResources(), actionBarUserImage);
        
		actionBar.setIcon(icon);
		
		// Remove Listeners
		mSlidingPaneLayout.setPanelSlideListener(new slideUpLayoutListener());
		userAccountLayout.setOnClickListener(new userAccountClickListener());
		imageStats.setOnClickListener(new ImageControlsListener());
		imageComments.setOnClickListener(new ImageControlsListener());
		imageLikes.setOnClickListener(new ImageControlsListener());
		imageFavorites.setOnClickListener(new ImageControlsListener());
		imageControlsLayout.setOnClickListener(new ImageControlsListener());
		commentsListView.setOnScrollListener(new commentScrollListener());
		submitComment.setOnClickListener(new submitCommentListener());
		mAttacher.setOnPhotoTapListener(new PhotoTapListener());
		
	}
		
	
	
	private void loadImageDetails(View rootView) {
		
		TextView ratingContent = (TextView) rootView.findViewById(R.id.rating);
		ratingContent.setText("" + (mFiveZeroZeroImageBean.getRating()));
		
		TextView highestRatingContent = (TextView) rootView.findViewById(R.id.highest_rating);
		highestRatingContent.setText("" + mFiveZeroZeroImageBean.getHighestRating());
		
		TextView viewsContent = (TextView) rootView.findViewById(R.id.views);
		viewsContent.setText(Integer.toString(mFiveZeroZeroImageBean.getTimesViewed()));
		
		TextView descriptionContent = (TextView) rootView.findViewById(R.id.description);
		descriptionContent.setText(mFiveZeroZeroImageBean.getDescription());
		
		TextView cameraContent = (TextView) rootView.findViewById(R.id.camera_content);
		cameraContent.setText(mFiveZeroZeroImageBean.getCamera());
		
		TextView focalLengthContent = (TextView) rootView.findViewById(R.id.focal_length_content);
		focalLengthContent.setText(mFiveZeroZeroImageBean.getFocalLength());
		
		TextView shutterSpeedContent = (TextView) rootView.findViewById(R.id.shutter_speed_content);
		shutterSpeedContent.setText(mFiveZeroZeroImageBean.getShutterSpeed());
		
		TextView isoContent = (TextView) rootView.findViewById(R.id.iso_content);
		isoContent.setText(mFiveZeroZeroImageBean.getIso());
		
		TextView categotyContent = (TextView) rootView.findViewById(R.id.category_content);
		categotyContent.setText(Category.forValue(mFiveZeroZeroImageBean.getCategory()).toString());
		
		TextView apertureContent = (TextView) rootView.findViewById(R.id.aperture_content);
		apertureContent.setText(mFiveZeroZeroImageBean.getAperture());
		
		TextView uploadedAtContent = (TextView) rootView.findViewById(R.id.uploaded_content);
		uploadedAtContent.setText(mFiveZeroZeroImageBean.getCreatedAt());
		
		TextView takenAtoContent = (TextView) rootView.findViewById(R.id.taken_content);
		takenAtoContent.setText(mFiveZeroZeroImageBean.getTakenAt());
		
		TextView licenseContent = (TextView) rootView.findViewById(R.id.license_content);
		licenseContent.setText(License.forValue(mFiveZeroZeroImageBean.getLicenseType()).toString());
		
		TextView locationContent = (TextView) rootView.findViewById(R.id.location_content);
		if (mFiveZeroZeroImageBean.getLocation() != null) {
			locationContent.setText(mFiveZeroZeroImageBean.getLocation());
		} else {
			locationContent.setText(null);
			locationContent.setVisibility(View.GONE);
		}
		
		if (mFiveZeroZeroImageBean.getLatitude() != null && mFiveZeroZeroImageBean.getLatitude() != null) {
			
			setupMap(mFiveZeroZeroImageBean.getLatitude(), mFiveZeroZeroImageBean.getLongitude(),
					mFiveZeroZeroImageBean.getName(), mFiveZeroZeroImageBean.getDescription());
		} else {
			View mapView = rootView.findViewById(R.id.map);
			mapView.setVisibility(View.GONE);
		}
		
		// when logged in - highlight images that are "Liked" (voted)
		if (mFiveZeroZeroImageBean.isVoted()) {
			imageLikes.setBackgroundResource(R.drawable.ic_background_like);
		} else {
			imageLikes.setBackgroundResource(R.drawable.ic_background);
		}
		
		// when logged in - highlight images that are "favorited"
		if (mFiveZeroZeroImageBean.isFavorited()) {
			imageFavorites.setBackgroundResource(R.drawable.ic_background_fav);
		} else {
			imageFavorites.setBackgroundResource(R.drawable.ic_background);
		}
		
		
		
	}
	
	private void setupMap(String latitude, String longitude, String imageName, String imageDescription) {
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	            .getMap();

		// get references to transparent image and ScrollView
		final ScrollView mainScrollView = (ScrollView) rootView.findViewById(R.id.image_details_scrollView);
		ImageView transparentImageView = (ImageView) rootView.findViewById(R.id.transparent_image);

		
		// allows scrolling on the map independently of the scrollView
		transparentImageView.setOnTouchListener(new View.OnTouchListener() {

		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        int action = event.getAction();
		        
		        switch (action) {
		           case MotionEvent.ACTION_DOWN:
		                // Disallow ScrollView to intercept touch events.
		        	   mainScrollView.requestDisallowInterceptTouchEvent(true);
		                // Disable touch on transparent view
		                return false;

		           case MotionEvent.ACTION_UP:
		                // Allow ScrollView to intercept touch events.
		                mainScrollView.requestDisallowInterceptTouchEvent(false);
		                return true;

		           case MotionEvent.ACTION_MOVE:
		                mainScrollView.requestDisallowInterceptTouchEvent(true);
		                return false;

		           default: 
		                return true;
		        }
		    }

			
		});
		
		
		LatLng imageLocation = new LatLng (Double.valueOf(latitude), Double.valueOf(longitude));
		LatLng mapCenter = new LatLng (Double.valueOf(latitude) - 1, Double.valueOf(longitude));
		
		
		map.addMarker(new MarkerOptions()
				.position(imageLocation)
				.snippet(imageDescription)
		        .title(imageName)
		        );
		
		// Move the camera instantly to position with a zoom of 6.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 6));
		
	}

	public class userAccountClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Toast.makeText(getActivity(), "Opening User Account for user: " + mFiveZeroZeroImageBean.getUserBean().getUserName(), Toast.LENGTH_SHORT).show();
		}
	
	}
	
	public class commentScrollListener implements OnScrollListener {
		
		@Override
	    public void onScroll(AbsListView view, int firstVisibleItem,
	            int visibleItemCount, int totalItemCount) {
	    	
	    	int lastInScreen = firstVisibleItem + visibleItemCount;
	        
	    	// start loading new items when there are only 3 rows left, and nothing is being loaded already
	    	if ((lastInScreen == (totalItemCount - 3)) && !(loadingMore.get())) {

	    		Log.d(TAG,"stopLoadingData = " + stopLoadingData.get());
	    		
	        	//this could be used for finite scroll. ie. a set number of pages/images
	            if (stopLoadingData.get() == false) {
	            	
	            	// Create the API call
	            	mFiveZeroZeroImageAPIBuilder
	         				.setPageNum(pageNumber.get());
	         		
	            	mLoadCommentsList = new LoadCommentsList(getActivity(), mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean, 
	            			loadingMore, stopLoadingData, pageNumber);
	        	    mLoadCommentsList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	            }
	        }
	    }
		@Override public void onScrollStateChanged(AbsListView view, int scrollState) {}

	}
	
	public class submitCommentListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			submitComment();
		}
	}
	
	private void submitComment() {
		// TODO Auto-generated method stub
		
		submitCommentBody = (EditText) rootView.findViewById(R.id.submit_comment_body);
		
		String commentBody = submitCommentBody.getText().toString();
		
		if (mLoginManager.isLoggedIn()){
			
			mPostCommentImage = new PostCommentImage(getActivity(), mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean, 
					mCommentsAdapter, commentBody, submitCommentBody);
	    	mPostCommentImage.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	    	
    	} else {
    		reason = LOGGING_REASON.COMMENT;
			login();
    	}
			
			
		
	}
	
	public class ImageControlsListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == imageStats){
				if (!mSlidingPaneLayout.isExpanded()) { // Closed, then set and open
					imageDetailFlipper.setDisplayedChild(0);
					mSlidingPaneLayout.expandPane();
				} else if (mSlidingPaneLayout.isExpanded() && imageDetailFlipper.getDisplayedChild() == 0) { // Open and already set, then close
					mSlidingPaneLayout.collapsePane();
				} else { // open and not set, then set
					imageDetailFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in));
					imageDetailFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
					imageDetailFlipper.setDisplayedChild(0);
				}
			} else if (v == imageComments) {
				if (!mSlidingPaneLayout.isExpanded()) { // Closed, then set and open
					imageDetailFlipper.setDisplayedChild(1);
					mSlidingPaneLayout.expandPane();
				} else if (mSlidingPaneLayout.isExpanded() && imageDetailFlipper.getDisplayedChild() == 1) { // Open and already set, then close
					mSlidingPaneLayout.collapsePane();
				} else{ // open and not set, then set
					imageDetailFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in));
					imageDetailFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
					imageDetailFlipper.setDisplayedChild(1);
				}
			} else if (v == imageLikes) {
				// Like button
				if (mLoginManager.isLoggedIn()){
					
					processLike();
		    	} else {
		    		reason = LOGGING_REASON.LIKE;
					login();
		    	}
				// go to login activity()
				
			} else if (v == imageFavorites) {
				// Favorite button
				if (mLoginManager.isLoggedIn()){
					
					processFavorite();
		    	}
				else {
					reason = LOGGING_REASON.FAVORITE;
					login();
				}
				
			}
			else if (v == imageControlsLayout) {
				if (!mSlidingPaneLayout.isExpanded()) {
					mSlidingPaneLayout.expandPane();
				} else {
					mSlidingPaneLayout.collapsePane();
				}
			}
			imageDetailFlipper.setInAnimation(null);
			imageDetailFlipper.setOutAnimation(null);
		}
	
	}
	
	
	public class slideUpLayoutListener implements PanelSlideListener {
		
		@Override
        public void onPanelSlide(View panel, float slideOffset) {
            
			// Dyamically adjust alpha on the Sliding panel background (could do the same for the rest of panel in future
            int alpha = (int) (120 + (1 - slideOffset) * 100);
            int color = Color.argb(alpha, 0, 0, 0);
            imageControlsLayout.setBackgroundColor(color);
            
            imageDetailFlipper.setAlpha(0.5f + ((1 - slideOffset) * 0.5f));
        }

        @Override public void onPanelExpanded(View panel) {}
        @Override public void onPanelCollapsed(View panel) {}
        @Override public void onPanelAnchored(View panel) {}
    }


	public class PhotoTapListener implements OnPhotoTapListener {
	
		@Override
		public void onPhotoTap(View view, float x, float y) {
			showOrHideActionBar();
		}
	}
	
	private void showOrHideActionBar() {
		
		Log.d(TAG, "" + rootView.getSystemUiVisibility());
		
		if (rootView.getSystemUiVisibility() == View.SYSTEM_UI_FLAG_VISIBLE){
			rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		} else {
			rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
		}
	}
	
	
	@Override
	public boolean onBackPressed() {
		Log.d(TAG, "clicked back");
		
		if (mSlidingPaneLayout.isExpanded()) {
			mSlidingPaneLayout.collapsePane();
			return false;
		}
		
		if (!getActivity().getActionBar().isShowing()) {
			showOrHideActionBar();
			return false;
		}
			
		return true;
	}
	
	
	private void processLike() {
		mLikeImage = new LikeImage(getActivity(), mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean);
	    mLikeImage.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	private void processFavorite() {
		mFavoriteImage = new FavoriteImage(getActivity(), mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean);
	    mFavoriteImage.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class LoadImageDetailsList extends FivePXBaseImageAsyncTask {

		LoadImageDetailsList(Context mContext, FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder,
				LoginManager mLoginManager, FiveZeroZeroImageBean mFiveZeroZeroImageBean) {
			
			super(mContext, mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean);
		}

		@Override
		protected JSONObject callAPI() {
			
			return api.get(mFiveZeroZeroImageAPIBuilder.getImageDetailsCall(mFiveZeroZeroImageBean.getId()));
		}
		
		@Override
		protected void parseReturn(JSONObject resObj) throws JSONException {
			mFiveZeroZeroImageBean.parseJSONObject(resObj.getJSONObject("photo"));
		}
		
		@Override
		protected void updateView() {
			loadImageDetails(rootView);
		}
			
	}

	public class LikeImage extends FivePXBaseImageAsyncTask {

		LikeImage( Context mContext, FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder,
				LoginManager mLoginManager, FiveZeroZeroImageBean mFiveZeroZeroImageBean) {
			
			super(mContext, mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean);
		}

		@Override
		protected JSONObject callAPI() {
			
			// Set vote/like status to opposite it's current value
			mFiveZeroZeroImageAPIBuilder = mFiveZeroZeroImageAPIBuilder.setVote(!mFiveZeroZeroImageBean.isVoted());
        	
			Log.d(TAG, mFiveZeroZeroImageAPIBuilder.postImageLike(mFiveZeroZeroImageBean.getId()));
        	
			return api.get(mFiveZeroZeroImageAPIBuilder.getImageDetailsCall(mFiveZeroZeroImageBean.getId()));
		}
			
		@Override protected void parseReturn(JSONObject resObj) throws JSONException { mFiveZeroZeroImageBean.setVoted(!mFiveZeroZeroImageBean.isVoted()); }
		
		@Override protected void updateView() { loadImageDetails(rootView); }
			
		@Override
		protected void ShowErrToast() {
			Toast.makeText(getActivity(), "Unable to " + ((mFiveZeroZeroImageBean.isVoted()) ? "unlike": "like") +": " +
    				((mFiveZeroZeroImageBean.isVoted()) ? "Have you completed your profile?": "Is this your own image?"), Toast.LENGTH_SHORT).show();
		}
		
		@Override protected boolean LoginRequired() {return true;}
		
		@Override protected boolean hasErrToast() {return true;}
	}
	
	public class FavoriteImage extends FivePXBaseImageAsyncTask {
		
		FavoriteImage( Context mContext, FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder,
				LoginManager mLoginManager, FiveZeroZeroImageBean mFiveZeroZeroImageBean) {
			
			super(mContext, mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean);
		}
		
		@Override
		protected JSONObject callAPI() {
			
			Log.d(TAG, mFiveZeroZeroImageAPIBuilder.postImageFavorite(mFiveZeroZeroImageBean.getId()));
        	
			JSONObject resObj = null;
			
			if (!mFiveZeroZeroImageBean.isFavorited()) {
        		//Post Favorite if not already in favorite list
				resObj = api.post(mFiveZeroZeroImageAPIBuilder.postImageFavorite(mFiveZeroZeroImageBean.getId()), new ArrayList<NameValuePair>());
        	} else {
        		//Delete Favorite if already in favorite list
				resObj = api.delete(mFiveZeroZeroImageAPIBuilder.postImageFavorite(mFiveZeroZeroImageBean.getId()), new ArrayList<NameValuePair>());
        	}
			return resObj;
		}
		
        @Override protected void parseReturn(JSONObject resObj) throws JSONException { mFiveZeroZeroImageBean.setFavorited(!mFiveZeroZeroImageBean.isFavorited()); }
		
		@Override protected void updateView() { loadImageDetails(rootView); }
		
		
        @Override
        protected void ShowErrToast() {
        	Toast.makeText(getActivity(), "Unable to " + ((mFiveZeroZeroImageBean.isVoted()) ? "unfavorite": "favorite") +": " +
    				((mFiveZeroZeroImageBean.isVoted()) ? "Have you completed your profile?": "Is this your own image?"), Toast.LENGTH_SHORT).show();
		}
        
        @Override protected boolean LoginRequired() {return true;}
		
		@Override protected boolean hasErrToast() {return true;}
	}
		
	public class PostCommentImage extends FivePXBaseImageAsyncTask {

		CommentsAdapter mCommentsAdapter;
		String commentBody;
		
		EditText submitCommentBody;
		
		PostCommentImage( Context mContext, FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder,
				LoginManager mLoginManager, FiveZeroZeroImageBean mFiveZeroZeroImageBean, 
				CommentsAdapter mCommentsAdapter,String commentBody, EditText submitCommentBody) {
			
			super(mContext, mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean);
			
			this.mCommentsAdapter = mCommentsAdapter;
			this.commentBody = commentBody;
			this.submitCommentBody = submitCommentBody;
		}
		
		
		
	    
       		@Override
       		protected JSONObject callAPI() {
			//verify that commentBody exists
			if (commentBody != null && !commentBody.equalsIgnoreCase("")) {
				ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
	            nameValuePair.add(new BasicNameValuePair("body",  commentBody));
	            
	        	Log.d(TAG, mFiveZeroZeroImageAPIBuilder.postSubmitCommentCall(mFiveZeroZeroImageBean.getId()));
	        	
	        	
	        	return api.post(mFiveZeroZeroImageAPIBuilder.postSubmitCommentCall(mFiveZeroZeroImageBean.getId()), nameValuePair);
			}
			// if comment Body is null or empty return null, this will cause onPostExecute to show error
			return null;
		}

       	
		@Override
		protected void parseReturn(JSONObject resObj) throws JSONException {
			
			JSONObject comment = resObj.getJSONObject("comment");
				
			FiveZeroZeroCommentBean commentBean = new FiveZeroZeroCommentBean(); 
			    	
			commentBean.parseJSONObject(comment);
		        	
			mFiveZeroZeroImageBean.addCommentBean(commentBean, 0);
		}


		@Override
		protected void updateView() {
			mCommentsAdapter.notifyDataSetChanged();
			
			clearComments(submitCommentBody);
			hideKeybard(submitCommentBody);
		}
		
		@Override
		protected void ShowErrToast() {
			Toast.makeText(getActivity(), "Error while posting comment", Toast.LENGTH_SHORT).show();

			clearComments(submitCommentBody);
			hideKeybard(submitCommentBody);
		}
		
		@Override protected boolean LoginRequired() {return true;}
		
		@Override protected boolean hasErrToast() {return true;}
		
		private void clearComments(EditText mEditText){
			// empty the edit text field
			mEditText.setText("");
		}
		
		private void hideKeybard(EditText mEditText) {
			// close the keyboard
			InputMethodManager imm = (InputMethodManager)getActivity().getSystemService( Context.INPUT_METHOD_SERVICE);
         	imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
		}
	}
	
	public class LoadCommentsList extends FivePXBaseImageAsyncTask {

		AtomicBoolean loadingMore;
		AtomicBoolean stopLoadingData;
		AtomicInteger pageNumber;
		
		/* Comments are downloaded at the same time (up to 20 at a time)
		 * but need to be processed and loaded to the view 1 at a time
		 * so we use publishProgress and onProgressUpdate instead of updateView 
		 * to update the view.
		 * 
		 * We use comments JSONObject to store all the comments and allow 
		 * updateView to make decisions based on the number of comments
		 */
		
		private JSONArray comments;
		
		/*
		 * totalPages and currentPage are used to determine the position of the current comment 
		 * in the list of all comments
		 */
		private int totalPages, currentPage;
		
		LoadCommentsList( Context mContext, FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder,
				LoginManager mLoginManager, FiveZeroZeroImageBean mFiveZeroZeroImageBean, 
				AtomicBoolean loadingMore, AtomicBoolean stopLoadingData, AtomicInteger pageNumber) {
			
			super(mContext, mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean);
			 
			this.loadingMore = loadingMore;
			this.stopLoadingData = stopLoadingData;
			this.pageNumber = pageNumber;
		}
		
		
		@Override
		protected void onPreExecute() {
			//Log.d(TAG, "This is running");
			loadingMore.set(true);
			super.onPreExecute();
		}
		
        @Override
        protected JSONObject callAPI() {
        	
        	Log.d(TAG, mFiveZeroZeroImageAPIBuilder.getCommentsCall(mFiveZeroZeroImageBean.getId()));
        	
			return  api.get(mFiveZeroZeroImageAPIBuilder.getCommentsCall(mFiveZeroZeroImageBean.getId()));
        }
        

		@Override
		protected void parseReturn(JSONObject resObj) throws JSONException {
			comments = resObj.getJSONArray("comments");
			
			totalPages = resObj.getInt("total_pages");
			currentPage = resObj.getInt("current_page");
            
			for (int i = comments.length() -1; i >= 0; i--){
				FiveZeroZeroCommentBean commentBean = new FiveZeroZeroCommentBean(); 
		    	
				commentBean.parseJSONObject(comments.getJSONObject(i));
				
				mFiveZeroZeroImageBean.addCommentBean(commentBean);
		    	
	        	publishProgress();
			}
		}

		protected void onProgressUpdate(Void... progress) {
	        mCommentsAdapter.notifyDataSetChanged();
	    }
		
		
		@Override
		protected void updateView() {
			// We use publishProgress and onProgressUpdate to update the UI so no need for updateView here
		}
        
		@Override
		protected void postProcess() {
			Log.d(TAG, "Running postProcess");
        	
			
			loadingMore.set(false);
	           
            pageNumber.decrementAndGet();
            
            if (pageNumber.get() == 0) {
            	// stop loading if last item in page
                stopLoadingData.set(true);
            }
            
            // If there is more than 1 page, and the number in this current page is small (under 10), and we are on the last page (aka, first run) then load another page
            // For example, if there are a total of 43 comments: if we load the 3 from the last page, the onScrollListener will never be called,
            // so we load another page with 20 more comments
            if (totalPages > 1 && comments.length() < 10 && currentPage >= totalPages) {
            	
            	mFiveZeroZeroImageAPIBuilder.setPageNum(pageNumber.get());
         		
            	mLoadCommentsList = new LoadCommentsList(getActivity(), mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean, loadingMore, stopLoadingData, pageNumber);
        	    mLoadCommentsList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
		}

		
		@Override protected boolean hasPostProcessing() {return true;}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class DownloadUserImage extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... urls) {
			return download_Image(urls[0]);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
		    
			if (result != null) {
				Log.d(TAG, "image downloaded successfully");
				actionBarUserImage = result;
				
				Resources res = getResources();
		        BitmapDrawable icon = new BitmapDrawable(res, result);
		        
				actionBar.setIcon(icon);
			} else {
				actionBar.setIcon(R.drawable.ic_launcher);
				Log.e(TAG, "failed to download image for user");
				
			}
		}


		private Bitmap download_Image(String url) {
		    //---------------------------------------------------
		    Bitmap bm = null;
		    try {
		        URL aURL = new URL(url);
		        URLConnection conn = aURL.openConnection();
		        conn.connect();
		        InputStream is = conn.getInputStream();
		        BufferedInputStream bis = new BufferedInputStream(is);
		        bm = BitmapFactory.decodeStream(bis);
		        bis.close();
		        is.close();
		    } catch (IOException e) {
		        Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
		    } 
		    return bm;
		    
		}
	}
	
	
	
	public void login () {
		Intent i = new Intent(getActivity(), LoginActivity.class);
		startActivityForResult(i, 1);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if(resultCode == Activity.RESULT_OK){      
				//boolean result=data.getBooleanExtra("result", false);     
		         
		        if (getActivity() instanceof MainActivity) {
		        	((MainActivity) getActivity()).onSuccessLogin();
		        }
		         
		        if (reason == LOGGING_REASON.FAVORITE) {
		         
		        	processFavorite();
		        }
		         
		        if (reason == LOGGING_REASON.LIKE) {
			         
		        	processLike();
		        }
		         
		        if (reason == LOGGING_REASON.COMMENT) {
			         
		        	submitComment();
		        }
		        
		        // The comment count, Like count, or the Favorite count (and color) will change.
		        mLoadImageDetailsList = new LoadImageDetailsList(getActivity(), mFiveZeroZeroImageAPIBuilder, mLoginManager, mFiveZeroZeroImageBean);
		        mLoadImageDetailsList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
		    if (resultCode == Activity.RESULT_CANCELED) {    
		    	// Do Nothing, no need to process whatever triggered the login task
		    }
		}
	}
	
	
	
	public void logout() {

		// when logged in - highlight images that are "Liked" (voted)
		imageLikes.setBackgroundResource(R.drawable.ic_background);
		
		imageFavorites.setBackgroundResource(R.drawable.ic_background);
		
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		Log.d(TAG, "onDestroyView");
		super.onDestroyView();
		
		Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));   
		FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
		
		// Need to call clean-up
		mAttacher.cleanup();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}



	/* (non-Javadoc)
	 * @see android.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach");
		super.onAttach(activity);
	}



	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}



	

	/* (non-Javadoc)
	 * @see android.app.Fragment#onDetach()
	 */
	@Override
	public void onDetach() {
		Log.d(TAG, "onDetach");
		super.onDetach();
	}


	/* (non-Javadoc)
	 * @see android.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		Log.d(TAG, "onStart");
		super.onStart();
	}



	/* (non-Javadoc)
	 * @see android.app.Fragment#onStop()
	 */
	@Override
	public void onStop() {
		Log.d(TAG, "onStop");
		super.onStop();
	}
	
}
