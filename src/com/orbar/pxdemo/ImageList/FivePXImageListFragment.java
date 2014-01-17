package com.orbar.pxdemo.ImageList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView.OnItemClickListener;

import com.orbar.pxdemo.LoginManager;
import com.orbar.pxdemo.MainActivity;
import com.orbar.pxdemo.R;
import com.orbar.pxdemo.APIHelper.Category;
import com.orbar.pxdemo.APIHelper.Feature;
import com.orbar.pxdemo.APIHelper.FiveZeroZeroImageAPIBuilder;
import com.orbar.pxdemo.APIHelper.Sort;
import com.orbar.pxdemo.Adapters.ImageAdapter;
import com.orbar.pxdemo.AsyncTasks.FivePXBaseAsyncTask;
import com.orbar.pxdemo.ImageView.FivePXImageFragment;
import com.orbar.pxdemo.MainActivity.BackPressedListener;
import com.orbar.pxdemo.Model.FiveZeroZeroImageBean;

public abstract class FivePXImageListFragment extends Fragment implements OnRefreshListener, BackPressedListener {
	
	static final String TAG = "FivePXFragment";
	
	public static final String ARG_CATEGORY = "arg_category";
	public static final String ARG_FEATURE = "arg_feature";
	public static final String ARG_SEARCH_TERM = "arg_search_term";
		
	// pull to refresh attacher object
	private PullToRefreshAttacher mPullToRefreshAttacher;

	// list of images, with meta data
	List<FiveZeroZeroImageBean> imageBeans = new ArrayList<FiveZeroZeroImageBean>();

	// View declerations 
	GridView mGridView;

	RelativeLayout optionsPopupLayout;
	RelativeLayout optionsLayout;
	
	RelativeLayout optionsFeatureLayout;
	RelativeLayout optionsSortLayout;
	
	Spinner optionsFeatureSpinner;
	Spinner optionsSortSpinner;
	ImageButton optionsRefreshButton;
	
	
	// Images Adapter
	ImageAdapter mImageAdapter;
	
	
	ArrayAdapter<Feature> featureAdapter;
	ArrayAdapter<Sort> sortAdapter;
	
	// inputs for the API calls
	protected AtomicInteger pageNumber = new AtomicInteger(1);
	
	protected Category mCategory;
	
	Feature mFeature;
	Sort mSort;
	
	protected String mSearchTerm;
	
	// Booleans used for infinite scrolling
	AtomicBoolean loadingMore = new AtomicBoolean(true);
	AtomicBoolean stopLoadingData = new AtomicBoolean(false);
	
	//Boolean used to not reset data when fist loading the spinner
	Boolean featureFirstRun = true;
	Boolean sortFirstRun = true;
	
	AtomicBoolean firstLoad = new AtomicBoolean(true);
	
	Boolean optionsOpen = false;
	
	// AsyncTask for downloading the images
	LoadImageList mLoadImageList;
	
	// API helper class
	FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder;
	
	Fragment fragment;
	
	public enum OPTIONS_TYPE {
		FILTER,
		SORT
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		
		View rootView = inflater.inflate(R.layout.fragment_five_px,
				container, false);

		// Allow using Menu items
		setHasOptionsMenu(true);
		
		mGridView = (GridView) rootView.findViewById(R.id.gridView);
		mGridView.setEmptyView( rootView.findViewById( R.id.empty_browse_view ));
		
		optionsPopupLayout = (RelativeLayout) rootView.findViewById(R.id.options_background);
		optionsLayout = (RelativeLayout) rootView.findViewById(R.id.options_layout);
		
		optionsFeatureLayout = (RelativeLayout) rootView.findViewById(R.id.options_feature);
		optionsSortLayout = (RelativeLayout) rootView.findViewById(R.id.options_sort);
		
		
		optionsFeatureSpinner = (Spinner) rootView.findViewById(R.id.options_feature_spinner);
		optionsSortSpinner = (Spinner) rootView.findViewById(R.id.options_sort_spinner);
		optionsRefreshButton = (ImageButton) rootView.findViewById(R.id.options_refresh_button);
		
		//Hide the options popup
		optionsPopupLayout.setVisibility(View.GONE);

		// Get the PullToRefresh attacher from the Activity.
		mPullToRefreshAttacher = ((MainActivity) getActivity()).getPullToRefreshAttacher();

	    // Add the Refreshable View and provide the refresh listener
	    mPullToRefreshAttacher.addRefreshableView(mGridView, this);
		
	    featureAdapter = new ArrayAdapter<Feature>(getActivity(), R.layout.feature_sort_row_item,R.id.feature_name, 
	    		Arrays.copyOfRange(Feature.values(), 0, Feature.values().length-1));
	    featureAdapter.setDropDownViewResource(R.layout.feature_sort_row_item);
	    
	    sortAdapter = new ArrayAdapter<Sort>(getActivity(), R.layout.feature_sort_row_item,R.id.feature_name, Sort.values());
	    sortAdapter.setDropDownViewResource(R.layout.feature_sort_row_item);
	    
	    optionsFeatureSpinner.setAdapter(featureAdapter);
	    optionsSortSpinner.setAdapter(sortAdapter);
	    
		
		//Get Selected Category
		Bundle bundle = this.getArguments();
		mCategory = Category.values()[bundle.getInt(ARG_CATEGORY)];
		
		mSearchTerm = bundle.getString(ARG_SEARCH_TERM, "");
		
		// Setup gridView adapter
		mImageAdapter = new ImageAdapter(getActivity(), imageBeans);
		mGridView.setAdapter(mImageAdapter);
		
		// Setup API call
		mFiveZeroZeroImageAPIBuilder = configureAPI();
		
				
		if (firstLoad.get()) {
			mLoadImageList = new LoadImageList(getActivity(), mFiveZeroZeroImageAPIBuilder, loadingMore, stopLoadingData, pageNumber);
        	mLoadImageList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        	
		}
        
        return rootView;
	}	
	
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		
		/*
		 *  Cancel all background downloading of images
		 *  This is to reduce data usage.
		 */
		
		if (mLoadImageList != null) {
			mLoadImageList.cancel(true);
		}
				
		// Nullify all listeners
		mGridView.setOnScrollListener(null);
		mGridView.setOnItemClickListener(null);
		optionsPopupLayout.setOnClickListener(null);
		optionsRefreshButton.setOnClickListener(null);
		optionsFeatureSpinner.setOnItemSelectedListener(null);
		optionsSortSpinner.setOnItemSelectedListener(null);
		
		// Stop the refreshing progress bar
    	mPullToRefreshAttacher.setRefreshComplete();
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		
		
		// set on scroll listener on the gridView
		mGridView.setOnScrollListener(new gridScrollListener());
		 
		// Set on item click listener to open image in bigger view
		mGridView.setOnItemClickListener(new gridImageClickListener());
		
		// Set on click listener to close the opened options view and go back to gridView
		optionsPopupLayout.setOnClickListener(new optionsCancelClickListener());
		
		// Set onClickListener for the refresh button
		optionsRefreshButton.setOnClickListener(new refreshButtonClickListener());
		
		// Set on item selected listener on Feature spinner
		optionsFeatureSpinner.setOnItemSelectedListener(new optionsFeatureSelectedListener());

		// Set on item selected listener on Feature spinner
		optionsSortSpinner.setOnItemSelectedListener(new optionsSortSelectedListener());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.grid_menu, menu);
	}

	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		boolean drawerState = false;
		
		if (getActivity() instanceof MainActivity) {
			drawerState = ((MainActivity)getActivity()).getDrawerState();
		}
		menu.findItem(R.id.action_sort).setVisible(!drawerState);
		menu.findItem(R.id.action_about).setVisible(!drawerState);
		
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    
		// Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_about:
        	showOptionsWindow(OPTIONS_TYPE.FILTER);
            return true;
        case R.id.action_sort:
        	showOptionsWindow(OPTIONS_TYPE.SORT);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
	}
	
	// intercept a back press and close the options popup if open
	@Override
	public boolean onBackPressed() {
		Log.i(TAG, "clicked back");
		
		if (optionsOpen) {
			closeOptionsView();
			return false;
		}
		return true;
	}
	
	/*
	 * Setup Listeners
	 */
	
	// infinite scroll listener on the Grid
	public class gridScrollListener implements OnScrollListener {
		
		@Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {

	    }

	    @Override
	    public void onScroll(AbsListView view, int firstVisibleItem,
	            int visibleItemCount, int totalItemCount) {
	    	
	    	int lastInScreen = firstVisibleItem + visibleItemCount;
	        
	    	// start loading new items when there are only 3 rows left, and nothing is being loaded already
	        if ((lastInScreen == (totalItemCount - 9)) && !(loadingMore.get())) {

	        	//this could be used for finite scroll. ie. a set number of pages/images
	            if (stopLoadingData.get() == false) {
	            	
	            	// Create the API call
	            	mFiveZeroZeroImageAPIBuilder
	         				.setPageNum(pageNumber.get());
	         		
	            	mLoadImageList = new LoadImageList(getActivity(), mFiveZeroZeroImageAPIBuilder, loadingMore, stopLoadingData, pageNumber);
	            	mLoadImageList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	            }
	        }
	        
	    }
	}
	
	// when clicking on an image
	public class gridImageClickListener implements OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			
			LoginManager mLoginManager = LoginManager.getInstance(getActivity());
		    
			Log.e(TAG, Boolean.toString(mLoginManager.isLoggedIn()));
			
			fragment = new FivePXImageFragment();
	        
	        Bundle args = new Bundle();
	        
	        args.putParcelable(FivePXImageFragment.ARG_IMAGE_BEAN, imageBeans.get(position));
	        
	        fragment.setArguments(args);

	        FragmentManager fragmentManager = getFragmentManager();
	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
			   
		}
	}
	
	// when clicking on the refresh button
	public class refreshButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			String test = optionsFeatureSpinner.getSelectedItem().toString();
			
			Log.i(TAG, test);
			
			closeOptionsView();
			
			reloadData();
		}
	
	}

	// Reload the data using the same settings. clear what's already there
	private void reloadData() {
		
		// Reset Api values and clear existing data set
		pageNumber.set(1);
		
		imageBeans.clear();
		mImageAdapter.notifyDataSetChanged();
        
		// Setup API call
		mFiveZeroZeroImageAPIBuilder
				.setPageNum(pageNumber.get());
			
		mLoadImageList = new LoadImageList(getActivity(), mFiveZeroZeroImageAPIBuilder, loadingMore, stopLoadingData, pageNumber);
    	mLoadImageList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	
	// Show the options floating window with either the filter dropdown or the sort dropdown
	private void showOptionsWindow(OPTIONS_TYPE mOptionsType) {
			Animation fadeIn;
			Animation slideIn;
			
		switch (mOptionsType) {
		case FILTER:
			optionsPopupLayout.setVisibility(View.VISIBLE);
			
			optionsFeatureLayout.setVisibility(View.VISIBLE);
			optionsSortLayout.setVisibility(View.GONE);
			
			fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
			optionsPopupLayout.startAnimation(fadeIn);
			
			
			slideIn = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_and_bounce_down);
			optionsLayout.startAnimation(slideIn);
		
			getActivity().getActionBar().setTitle(R.string.filter_actionBar_title);
			
			optionsOpen = true;
			
			break;
		case SORT:
			optionsPopupLayout.setVisibility(View.VISIBLE);
			
			optionsFeatureLayout.setVisibility(View.GONE);
			optionsSortLayout.setVisibility(View.VISIBLE);
			
			fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
			optionsPopupLayout.startAnimation(fadeIn);
			
			
			slideIn = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_and_bounce_down);
			optionsLayout.startAnimation(slideIn);
		
			getActivity().getActionBar().setTitle(R.string.sort_actionBar_title);
			
			optionsOpen = true;
			break;
		}
		
	}
	
	// called when clicking outside of the options box
	public class optionsCancelClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			closeOptionsView();
		}
	}

	// load new data when changing a feature
	public class optionsFeatureSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
				int position, long id)  {
			
			if (!featureFirstRun) {
				
				mFeature = Feature.fromString(optionsFeatureSpinner.getSelectedItem().toString());
				
				// Setup API call
				mFiveZeroZeroImageAPIBuilder
						.setFeature(mFeature);
				
				reloadData();
				
				closeOptionsView();
			}		
			featureFirstRun = false;
		}

		@Override public void onNothingSelected(AdapterView<?> arg0) {}
		
	}

	// load new data when changing the sort order
	public class optionsSortSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
				int position, long id)  {
			
			if (!sortFirstRun) {
				
				mSort = Sort.fromString(optionsSortSpinner.getSelectedItem().toString());
				
				// Setup API call
				mFiveZeroZeroImageAPIBuilder
						.setSorting(mSort);
				
				reloadData();
				
				closeOptionsView();
			}		
			sortFirstRun = false;
		}

		@Override public void onNothingSelected(AdapterView<?> arg0) {}
		
	}
	
	// Close the options box
	private void closeOptionsView() {
		Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
		
		fadeOut.setAnimationListener(new AnimationListener() {
			@Override
		    public void onAnimationEnd(Animation animation) {
				optionsPopupLayout.setVisibility(View.GONE);
				
				getActivity().getActionBar().setTitle(mCategory.toString());
		    }
			@Override public void onAnimationRepeat(Animation animation) {}
			@Override public void onAnimationStart(Animation animation) {}
		});
		
		optionsPopupLayout.startAnimation(fadeOut);
	
		
		
		Animation slideout = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_and_bounce_up);
		optionsLayout.startAnimation(slideout);
	
		optionsOpen = false;
	}
	
	
	
	
	// reload the data when a pull to refresh has been triggered
	@Override
	public void onRefreshStarted(View view) {
		reloadData();
	}
	
	// pass along to FivePXImageFragment if exists
	public void logout() {
		// TODO Auto-generated method stub
		
		if (fragment != null && fragment instanceof FivePXImageFragment) {
			((FivePXImageFragment) fragment).logout();
		}
		
	}
	
	
	/**
	 * An asynchronous task to download images and place them in the grid
	 * 
	 * 
	 * @author or
	 *
	 */
	public class LoadImageList extends FivePXBaseAsyncTask {
		
		private AtomicBoolean loadingMore;
		private AtomicBoolean firstLoad;
		private AtomicInteger pageNumber;

		LoadImageList( Context mContext, FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder,
				AtomicBoolean loadingMore, AtomicBoolean firstLoad, AtomicInteger pageNumber) {
			
			super(mContext, mFiveZeroZeroImageAPIBuilder);
			 
			this.loadingMore = loadingMore;
			this.firstLoad = firstLoad;
			this.pageNumber = pageNumber;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			loadingMore.set(true);
			
			if (!firstLoad.get()) {
				mPullToRefreshAttacher.setRefreshing(true);
			} else {
				firstLoad.set(false);
			}
		}
		
		@Override
        protected JSONObject callAPI() {
        	
			Log.d(TAG, getAPIcall(mFiveZeroZeroImageAPIBuilder));
        	
			return  api.get(getAPIcall(mFiveZeroZeroImageAPIBuilder));
        }
		
		@Override
		protected void parseReturn(JSONObject resObj) throws JSONException {
			JSONArray photos = resObj.getJSONArray("photos");
			
			
			for (int i = 0; i < photos.length(); i++){
				FiveZeroZeroImageBean imageBean = new FiveZeroZeroImageBean(); 
		    	
	        	imageBean.parseJSONObject(photos.getJSONObject(i));
	        	
	        	imageBeans.add(imageBean);
	        	
	        	publishProgress();
			}
			
		}
		
		protected void onProgressUpdate(Void... progress) {
			mImageAdapter.notifyDataSetChanged();
	    }
		
		@Override
		protected void updateView() {
			// We use publishProgress and onProgressUpdate to update the UI so no need for updateView here
		}
		
		@Override
		protected void postProcess() {
			loadingMore.set(false);
        	pageNumber.incrementAndGet();
        	
        	// Stop the refreshing progress bar
        	mPullToRefreshAttacher.setRefreshComplete();
		}
		
		@Override protected boolean hasPostProcessing() {return true;}
		
	}
	
	
	/**
	 * Build the assembled API call
	 * 
	 * @param mFiveZeroZeroImageAPIBuilder an already configured {@link FiveZeroZeroImageAPIBuilder}
	 * 
	 * @return The assembled API call
	 */
	public abstract String getAPIcall(FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder);
	
	
	/**
	 * Create and prepare the API builder for the first load of the image grid
	 * 
	 * @return a new configured API builder object that is ready to go.
	 */
	public abstract FiveZeroZeroImageAPIBuilder configureAPI();
	
}

