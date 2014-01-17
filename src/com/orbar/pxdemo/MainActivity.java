/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.orbar.pxdemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.fivehundredpx.api.PxApi;
import com.orbar.pxdemo.R;
import com.orbar.pxdemo.APIHelper.Category;
import com.orbar.pxdemo.APIHelper.Feature;
import com.orbar.pxdemo.APIHelper.FiveZeroZeroImageAPIBuilder;
import com.orbar.pxdemo.APIHelper.FiveZeroZeroUsersAPIBuilder;
import com.orbar.pxdemo.ImageList.FivePXImageListFragment;
import com.orbar.pxdemo.ImageList.Impl.FivePXBrowseImageListFragment;
import com.orbar.pxdemo.ImageList.Impl.FivePXSearchImageListFragment;
import com.orbar.pxdemo.Model.FiveZeroZeroImageBean;
import com.orbar.pxdemo.Model.FiveZeroZeroUserBean;
import com.orbar.pxdemo.Model.FiveZeroZeroImageBean.IMAGE_SIZE;
import com.orbar.pxdemo.Model.FiveZeroZeroUserBean.USER_IMAGE_SIZE;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;



public class MainActivity extends Activity implements LoginManager.LoginListener {
    
	static final String TAG = "MainActivity";
	
	// Drawer related View
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerCategoryList;
    private View mDrawerView;
    
    // My Account button and Logout button
    private ViewFlipper mMyAccountViewFlipper;
    private TextView mAccountUserName;
    private ImageView mAccountUserIcon;
    private ImageView mAccountUserBackground;
    private ImageButton mLogoutButton;
    
    // custom ActionBar Toggle, used to open/close the drawer
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    
    // ActionBar pull to refresh Attacher
    private PullToRefreshAttacher mPullToRefreshAttacher;
    
    // Search elements
    private SearchView mSearchView;
    private MenuItem searchItem;
    
    // API builder to download the userAccount when logged in
    FiveZeroZeroUsersAPIBuilder mFiveZeroZeroUsersAPIBuilder;
    
    // API builder to download the user's images when logged in
    FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder;
    
    // User Bean to hold the logged in use information
    FiveZeroZeroUserBean mFiveZeroZeroUserBean;
    
    // User Bean to hold the logged in use information
    FiveZeroZeroImageBean mFiveZeroZeroImageBean;
    
    // fragment holder
    Fragment fragment;
    
    // Login Manager
    LoginManager mLoginManager;
    
    // AsyncTask for liking an image
    LoadMyAccount mLoadMyAccount;
 	
    // AsyncTask for liking an image
    LoadMyImage mLoadMyImage;
 	
    // used in order to keep track of asyncronous request to open the my account page, version other login requests (like liking an image)
 	boolean gotoMyAccount = false;
    
 	
 	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        
        // Load the drawer views
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerCategoryList = (ListView) findViewById(R.id.category_list);
        mDrawerView = (View) findViewById(R.id.left_drawer);
        
        // Load account and logout textviews (buttons)
        mMyAccountViewFlipper = (ViewFlipper) findViewById(R.id.my_account_flipper);
        mAccountUserName = (TextView) findViewById(R.id.account_header_user_name);
        mAccountUserIcon = (ImageView) findViewById(R.id.account_header_user_icon);
        mAccountUserBackground = (ImageView) findViewById(R.id.account_header_background);
        		
        Log.d(TAG, "mMyAccountViewFlipper is null? " + Boolean.toString(mMyAccountViewFlipper ==null));
        
        mLogoutButton = (ImageButton) findViewById(R.id.account_header_logout);
        
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        // set up the drawer's list view with items and click listener
        mDrawerCategoryList.setAdapter(new ArrayAdapter<Category>(this,
                R.layout.drawer_list_item, Category.values()));
        
        // setup the open close listener for the drawer
        mDrawerCategoryList.setOnItemClickListener(new DrawerItemClickListener());

        // Enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // select the first category (All) if so saved category (ie if orientation changes)
        if (savedInstanceState == null) {
            selectItem(0);
        }
        
        // Make the refresh scroll distance to 20% of the GridView height
        PullToRefreshAttacher.Options ptrOptions = new PullToRefreshAttacher.Options();
        ptrOptions.refreshScrollDistance = 0.2f;
		 
        // get a PullToRefreshAttacher instance
	    mPullToRefreshAttacher = PullToRefreshAttacher.get(this, ptrOptions);
        
	    
	    // Get the login manager
	    mLoginManager = LoginManager.getInstance(this);
	   
	    // if not logged in, attempt to login (background), otherwise, start the login actions
	    if (!mLoginManager.isLoggedIn()){
	    	mLoginManager.login();
    	} else {
    		onSuccessLogin();
    	}
	    
	    // if clicked on my account, open my account page if logged in, other wise go to login page
	    mMyAccountViewFlipper.setOnClickListener(new OnClickListener () {
	    	
	    	@Override public void onClick(View v) { 
	    		if (mLoginManager.isLoggedIn()) {
	    			openMyAccount();
	    		} else {
	    			gotoMyAccount = true;
	    			gotoLogin();
	    		}
	    		
	    	}
	    });
	    
	    // Logout
	    mLogoutButton.setOnClickListener(new OnClickListener () {

			@Override public void onClick(View v) { logout(); }
	    });
    }
    
    @Override
	protected void onPause() {
		super.onPause();
		
		
		// stop the load my Account (if leaving activity/app)
	    if (mLoadMyAccount != null) {
	    	mLoadMyAccount.cancel(true);
	    }
	 	
	    if (mLoadMyImage != null) {
	    	mLoadMyImage.cancel(true);
	    }
	    
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu and attach the search listener
    	
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        
        searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint(getResources().getText(R.string.search_hint));
        
        mSearchView.setOnQueryTextListener(new searchQueryListener());
        
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerView);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
         
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        fragment = new FivePXBrowseImageListFragment();
        
        
        Bundle args = new Bundle();
        args.putInt(FivePXImageListFragment.ARG_CATEGORY, ((Category)mDrawerCategoryList.getItemAtPosition(position)).ordinal());
        
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerCategoryList.setItemChecked(position, true);
        
        String selected = ((Category)mDrawerCategoryList.getItemAtPosition(position)).toString();
        setTitle(selected);
        
        mDrawerLayout.closeDrawer(mDrawerView);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    /**
     * returns a reference to the activity's PullToRefreshAttacher
     * 
     * @return PullToRefreshAttacher 	The activity's PullToRefreshAttacher object 
     */
    public PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }
     
    /**
     * Start the FivePXFragment in search mode and the passed query
     * 
     * @author or
     *
     */
    public class searchQueryListener implements OnQueryTextListener {

		@Override
		public boolean onQueryTextSubmit(String query) {
	    	Toast.makeText(MainActivity.this, "searching for " + query, Toast.LENGTH_SHORT).show();
	    	searchItem.collapseActionView();
	    	
	    	fragment = new FivePXSearchImageListFragment();
	        
	        Bundle args = new Bundle();
	        args.putString(FivePXImageListFragment.ARG_SEARCH_TERM, query);
	        
	        fragment.setArguments(args);

	        FragmentManager fragmentManager = getFragmentManager();
	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	    	
	        setTitle(query);
	        
	    	return true;
	    }
		
		@Override public boolean onQueryTextChange(String newText) { return false;}
	}
    
    // Intercepts a back press action and passes it down to fragments for custom actions (close the pull up panel)
    @Override
	public void onBackPressed() {
		FragmentManager fragmentManager = getFragmentManager();
		
		Fragment fragment = fragmentManager.findFragmentById(R.id.content_frame);
		
		if (fragment != null && fragment instanceof BackPressedListener) {
			if (((BackPressedListener) fragment).onBackPressed()) {
				super.onBackPressed();
			}
		} else {
			super.onBackPressed();
		}
		
	}
    
    /**
     * Returns the open/close state of the navigation drawer
     * 
     * @return True if drawer is open and False if close
     */
	public boolean getDrawerState() {
		return mDrawerLayout.isDrawerOpen(mDrawerView);
	}

	/**
	 * Start the login activity and setup up a listener for when it returns
	 */
	private void gotoLogin () {
		Intent i = new Intent(this, LoginActivity.class);
		startActivityForResult(i, 1);
		
	}
	
	/**
	 * receives the result of a successful login and starts the login actions
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if(resultCode == Activity.RESULT_OK){      
		         
				onSuccessLogin();
				
			}
			if (resultCode == Activity.RESULT_CANCELED) {    
				Log.e(TAG, "Unable to login");
			}
		}
	}
	
	// logs out, and calls fragments to update their views (for eg. stop highlighting the like button) 
	private void logout () {
		mLoginManager.logout();
		
		mMyAccountViewFlipper.setDisplayedChild(0);
		
		if (fragment instanceof MyAccountFragment) {
			selectItem(0);
		} else if (fragment instanceof FivePXImageListFragment) {
			
			((FivePXImageListFragment) fragment).logout();
			
		}
		
		mDrawerLayout.closeDrawer(mDrawerView);
	}
	
	

	// Define an interface for fragments that have special back actions
	public interface BackPressedListener {
    	
    	boolean onBackPressed();
    }

	// Show the logout button, and starts the task to download the user account
	@Override
	public void onSuccessLogin() {
		
		mMyAccountViewFlipper.setDisplayedChild(1);
		
		mFiveZeroZeroUsersAPIBuilder = new FiveZeroZeroUsersAPIBuilder();
		
		mLoadMyAccount = new LoadMyAccount();
	    mLoadMyAccount.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, mFiveZeroZeroUsersAPIBuilder);
	}


	// Show a toast to let user know that automatic login failed (probably connection or password reset) unless there is no user already set
	@Override
	public void onFailedLogin(boolean credentialsExist) {
		if (credentialsExist)
			// Maybe password expired, account locked, or just changed password
			Toast.makeText(this, "Unable to login using saved credentials", Toast.LENGTH_SHORT).show();
	}

	// when clicking on my account, clear the selected category, start the my account fragment and pass the my account object
	protected void openMyAccount() {
		
		// remove selected item and update title, then close the drawer
        mDrawerCategoryList.clearChoices();
        mDrawerCategoryList.requestLayout();
        
        setTitle(R.string.my_account);
        
        mDrawerLayout.closeDrawer(mDrawerView);
        
		fragment = new MyAccount2Fragment();
        
        Bundle args = new Bundle();
       
        args.putParcelable(MyAccount2Fragment.ARG_USER_BEAN, mFiveZeroZeroUserBean);
        args.putParcelable(MyAccount2Fragment.ARG_IMAGE_BEAN, mFiveZeroZeroImageBean);
        
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        
	}
	
	/**
	 * An asynchronous task to download the user's profile
	 * 
	 * @author or
	 */
	public class LoadMyAccount extends AsyncTask<FiveZeroZeroUsersAPIBuilder, Void, JSONObject> {

		FiveZeroZeroUsersAPIBuilder mFiveZeroZeroUsersAPIBuilder; 
		
		@Override
        protected JSONObject doInBackground(FiveZeroZeroUsersAPIBuilder... parameters) {
            
			mFiveZeroZeroUsersAPIBuilder = parameters[0];
			
			// Use API to like an image
        	PxApi api = new PxApi(mLoginManager.getAccessMyToken(), getString(R.string.px_consumer_key),
        			getString(R.string.px_consumer_secret));
        	
        	JSONObject resObj = null;
        	
        	Log.d(TAG, mFiveZeroZeroUsersAPIBuilder.getMyAccount());
        	
        	resObj = api.get(mFiveZeroZeroUsersAPIBuilder.getMyAccount());
        	
        	return resObj;
        }
	    
        @Override
        protected void onPostExecute(JSONObject resObj) {
        	
        	try {
				
        		if (resObj != null) {
        			mFiveZeroZeroUserBean = new FiveZeroZeroUserBean();
        			mFiveZeroZeroUserBean.parseJSONObject(resObj.getJSONObject("user"));
        			
        			mAccountUserName.setText(mFiveZeroZeroUserBean.getFullName());
        			
        			Picasso.with(MainActivity.this).load(mFiveZeroZeroUserBean.getUserpicURL(USER_IMAGE_SIZE.MEDIUM)).into(target);
        			
        			mLoadMyImage = new LoadMyImage();
        			
        			mLoadMyImage.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new FiveZeroZeroImageAPIBuilder(), mFiveZeroZeroUserBean);
        			
        			// if login trigger was to open my account then now is the time
        			//if (gotoMyAccount) {
    				//	openMyAccount();
    				//}
        		}
				
        	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
        }
        
        private Target target = new Target() {

			@Override public void onBitmapFailed(Drawable arg0) {}
			@Override public void onPrepareLoad(Drawable arg0) {}
		    
			@Override
			public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
				
				mAccountUserIcon.setImageBitmap(bitmap);
			}
			
		};
	}
	
	/**
	 * An asynchronous task to download the user's profile
	 * 
	 * @author or
	 */
	public class LoadMyImage extends AsyncTask<Object, Void, JSONObject> {

		FiveZeroZeroUsersAPIBuilder mFiveZeroZeroUsersAPIBuilder; 
		FiveZeroZeroUserBean mFiveZeroZeroUserBean; 
		
		
		@Override
        protected JSONObject doInBackground(Object... parameters) {
            
			mFiveZeroZeroImageAPIBuilder = (FiveZeroZeroImageAPIBuilder) parameters[0];
			
			mFiveZeroZeroUserBean = (FiveZeroZeroUserBean) parameters[1];
			
			Log.d(TAG, Boolean.toString(mLoginManager.isLoggedIn()));
			
			// Use API to like an image
        	PxApi api = new PxApi(mLoginManager.getAccessMyToken(), getString(R.string.px_consumer_key),
        			getString(R.string.px_consumer_secret));
        	
        	JSONObject resObj = null;
        	
        	mFiveZeroZeroImageAPIBuilder
        			.setFeature(Feature.USER)
        			.setRpp(1)
        			.setPageNum(1)
        			.setUser(mFiveZeroZeroUserBean.getId());
        	
        	Log.d(TAG, mFiveZeroZeroImageAPIBuilder.getUserBrowseCall());
        	
        	resObj = api.get(mFiveZeroZeroImageAPIBuilder.getUserBrowseCall());
        	
        	//Log.d(TAG, resObj.toString());
        	
        	
        	return resObj;
        }
	    
        @Override
        protected void onPostExecute(JSONObject resObj) {
        	
        	try {
				
        		if (resObj != null) {
        			mFiveZeroZeroImageBean = new FiveZeroZeroImageBean();
        			
        			JSONArray photos = resObj.getJSONArray("photos");
        			
        			if (photos.length() > 0) {
        				mFiveZeroZeroImageBean.parseJSONObject(photos.getJSONObject(0));
		        	
	        			Picasso.with(MainActivity.this).load(mFiveZeroZeroImageBean.getImageUrl(IMAGE_SIZE.LARGE)).into(mAccountUserBackground);
        			}
        			// if login trigger was to open my account then now is the time
        			if (gotoMyAccount) {
    					openMyAccount();
    				}
        		}
				
        	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
        }
        
	}
}