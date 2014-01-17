package com.orbar.pxdemo;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.orbar.pxdemo.Model.FiveZeroZeroImageBean;
import com.orbar.pxdemo.Model.FiveZeroZeroUserBean;
import com.orbar.pxdemo.Model.FiveZeroZeroImageBean.IMAGE_SIZE;
import com.orbar.pxdemo.Model.FiveZeroZeroUserBean.USER_IMAGE_SIZE;
import com.squareup.picasso.Picasso;

public class MyAccount2Fragment extends Fragment {

	static final String TAG = "MyAccount2Fragment";
	
	static final String ARG_USER_BEAN = "arg_user_bean";
	static final String ARG_IMAGE_BEAN = "arg_image_bean";
	
	FiveZeroZeroUserBean mFiveZeroZeroUserBean;
	FiveZeroZeroImageBean mFiveZeroZeroImageBean;
	
	private FadingActionBarHelper mFadingHelper;
    private Bundle mArguments;

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        Log.i(TAG, "Attached");
        
        mArguments = getArguments();
        //int actionBarBg = mArguments != null ? mArguments.getInt(ARG_ACTION_BG_RES) : R.drawable.ab_background_light;

        /*
        mFadingHelper = new FadingActionBarHelper()
            .actionBarBackground(R.drawable.ab_background_light) // when scrolled up
            .headerLayout(R.layout.header_light)
            .contentLayout(R.layout.fragment_my_account2)
            .lightActionBar(false);
        mFadingHelper.initActionBar(activity);
        */
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		
		//View rootView = inflater.inflate(R.layout.fragment_my_account2,
		//		container, false);

		mFadingHelper = new FadingActionBarHelper()
				.actionBarBackground(android.R.color.transparent) // when scrolled up
				.headerLayout(R.layout.header_light)
				.contentLayout(R.layout.fragment_my_account2)
				.lightActionBar(false);
		mFadingHelper.initActionBar(getActivity());
		
		// create ContextThemeWrapper from the original Activity Context with the custom theme
		final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_TranslucentActionBar);

	    // clone the inflater using the ContextThemeWrapper
	    LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

	    View rootView = mFadingHelper.createView(localInflater);

		// get the ImageBean that was clicked
		Bundle bundle = this.getArguments();
		mFiveZeroZeroUserBean = (FiveZeroZeroUserBean) bundle.getParcelable(ARG_USER_BEAN);
		mFiveZeroZeroImageBean = (FiveZeroZeroImageBean) bundle.getParcelable(ARG_IMAGE_BEAN);
				
		((MainActivity) getActivity()).setTitle(R.string.my_account);
		
		ImageView headerImage = (ImageView) rootView.findViewById(R.id.image_header);
        headerImage.setImageResource(R.drawable.ny_light);
		
        Picasso.with(getActivity()).load(mFiveZeroZeroImageBean.getImageUrl(IMAGE_SIZE.LARGE)).into(headerImage);
		
		// get the textviews for the account page
		TextView userFullName = (TextView) rootView.findViewById(R.id.user_full_name);
		ImageView userImage = (ImageView) rootView.findViewById(R.id.user_account_image);
		
		TextView userAffection = (TextView) rootView.findViewById(R.id.user_affection);
		TextView userPhotosCount = (TextView) rootView.findViewById(R.id.user_photos_count);
		TextView userFollowers = (TextView) rootView.findViewById(R.id.user_followers_count);
		
		TextView aboutMe = (TextView) rootView.findViewById(R.id.user_about_me);
		
		TextView userCameras = (TextView) rootView.findViewById(R.id.user_cameras);
		TextView userLenses = (TextView) rootView.findViewById(R.id.user_lenses);
		
		// Download the user image
		Picasso.with(getActivity())
			.load(mFiveZeroZeroUserBean.getUserpicURL(USER_IMAGE_SIZE.ORIGINAL))
			.placeholder(R.drawable.ic_user)
			.error(R.drawable.ic_user)
			.into(userImage);
		
		
		
		// set the user values
		userFullName.setText(mFiveZeroZeroUserBean.getFullName());
		userAffection.setText(Integer.toString(mFiveZeroZeroUserBean.getAffection()));
		userPhotosCount.setText(Integer.toString(mFiveZeroZeroUserBean.getPhotosCount()));
		userFollowers.setText(Integer.toString(mFiveZeroZeroUserBean.getFollowersCount()));
		
		aboutMe.setText(mFiveZeroZeroUserBean.getAbout());
		
		String cameras[] = mFiveZeroZeroUserBean.getEquipmentBean().getCamera();
		String lens[] = mFiveZeroZeroUserBean.getEquipmentBean().getLens();
		
		StringBuilder builder = new StringBuilder();
		
		if (cameras != null) {
			for(String s : cameras) {
				builder.append(s + "\n");
			}
			String cams = builder.toString();
			
			userCameras.setText(cams);
		}
		
		builder = new StringBuilder();
		if (lens != null) {
			for(String s : lens) {
				builder.append(s + "\n");
			} 
			String lenses = builder.toString();
			
			userLenses.setText(lenses);
		}
		
		ImageButton user500Account = (ImageButton) rootView.findViewById(R.id.user_500px_link);
		ImageButton userWebsite = (ImageButton) rootView.findViewById(R.id.user_website_link);
		ImageButton userFaceBook = (ImageButton) rootView.findViewById(R.id.user_facebook_link);
		ImageButton userTwitter = (ImageButton) rootView.findViewById(R.id.user_twitter_link);
		ImageButton userFlickr= (ImageButton) rootView.findViewById(R.id.user_flickr_link);
		
		user500Account.setOnClickListener(new pxClickListener());
		
		// Website
		if (mFiveZeroZeroUserBean.getContactsBean().getWebsite() != null) {
			userWebsite.setImageResource(R.drawable.ic_website_on);
			
			userWebsite.setOnClickListener(new websiteClickListener());
		} else {
			userWebsite.setClickable(false);
		}
		
		// Facebook
		if (mFiveZeroZeroUserBean.getContactsBean().getFacebook() != null) {
			userFaceBook.setImageResource(R.drawable.ic_facebook_on);
			
			userFaceBook.setOnClickListener(new facebookClickListener());
		} else {
			userFaceBook.setClickable(false);
		}
		
		// Twitter
		if (mFiveZeroZeroUserBean.getContactsBean().getTwitter() != null) {
			userTwitter.setImageResource(R.drawable.ic_twitter_on);
			
			userTwitter.setOnClickListener(new twitterClickListener());
		} else {
			userTwitter.setClickable(false);
		}
		
		// Flickr
		if (mFiveZeroZeroUserBean.getContactsBean().getFlickr() != null) {
			userFlickr.setImageResource(R.drawable.ic_flickr_on);
			
			userFlickr.setOnClickListener(new flickrClickListener());
		} else {
			userFlickr.setClickable(false);
		}
		
		return rootView;
	}
	
	
	
	// Open the user's account in the browser
	private class pxClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			launch500px(mFiveZeroZeroUserBean.getUserName());
		}
		
	}
	
	// Open the user's website in the browser
	private class websiteClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			launchWebsite(mFiveZeroZeroUserBean.getContactsBean().getWebsite());
		}
		
	}
	
	// Open the user's facebook profile in the facebook app, if installed or in the browser 
	private class facebookClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			launchFacebook(mFiveZeroZeroUserBean.getContactsBean().getFacebook());
		}
		
	}
	
	// Open the user's twitter profile in the twitter app, if installed or in the browser 
	private class twitterClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			launchTwitter(mFiveZeroZeroUserBean.getContactsBean().getTwitter());
		}
		
	}
	
	// Open the user's flickr account in the browser
	private class flickrClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			launchFlickr(mFiveZeroZeroUserBean.getContactsBean().getFlickr());
		}
		
	}
	
	
	// Open the user's account in the browser
	public final void launch500px(String userName) {
		
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://500px.com/" + userName));      
			startActivity(intent); 
			
		} catch (Exception e) {
			Log.e(TAG, "An Error has occured while opening 500px profile");
		}
	}
	
	// Open the user's website in the browser
	public final void launchWebsite(String website) {
		
		try {
			if (!website.startsWith("http://")){
				website = "http://" + website;
			}
			
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(website));
			startActivity(intent);
			
		} catch (Exception e) {
			Log.e(TAG, "An Error has occured while opening user's webSite");
		}
	}

	// Open the user's facebook profile in the facebook app, if installed or in the browser 
	public final void launchFacebook(String facebookId) {
		try {
			final String urlFb = "fb://profile/"+facebookId;
	        Intent intent = new Intent(Intent.ACTION_VIEW);
	        intent.setData(Uri.parse(urlFb));
	
	        // If a Facebook app is installed, use it. Otherwise, launch
	        // a browser
	        final PackageManager packageManager = getActivity().getPackageManager();
	        List<ResolveInfo> list =
	            packageManager.queryIntentActivities(intent,
	            PackageManager.MATCH_DEFAULT_ONLY);
	        if (list.size() == 0) {
	            final String urlBrowser = "https://www.facebook.com/pages/"+facebookId;
	            intent.setData(Uri.parse(urlBrowser));
	        }
	
	        startActivity(intent);
		}
		catch (Exception e) {
			Log.e(TAG, "An Error has occured while opening facebook");
		}
    }
	
	// Open the user's twitter profile in the twitter app, if installed or in the browser 
	public final void launchTwitter (String userName) {
		try {
			try {
				   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + userName)));
				} catch (Exception e) {
				   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + userName)));
			}
		} catch (Exception e) {
			final String urlBrowser = "https://twitter.com/" + userName;
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(urlBrowser));
            
            startActivity(intent);
		}
		
	}
	
	// Open the user's flickr account in the browser
	public final void launchFlickr(String userName) {
		
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.flickr.com/photos/" + userName));      
			startActivity(intent); 
			
		} catch (Exception e) {
			Log.e(TAG, "An Error has occured while opening 500px profile");
		}
	}
	
	
}
