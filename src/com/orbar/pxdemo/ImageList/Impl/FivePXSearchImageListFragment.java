package com.orbar.pxdemo.ImageList.Impl;

import com.orbar.pxdemo.APIHelper.FiveZeroZeroImageAPIBuilder;
import com.orbar.pxdemo.ImageList.FivePXImageListFragment;

public class FivePXSearchImageListFragment extends FivePXImageListFragment {

	/**
	 * @return {@inheritDoc} Configured for search calls
	 */
	@Override
	public FiveZeroZeroImageAPIBuilder configureAPI() {
		
		return new FiveZeroZeroImageAPIBuilder()
		.setTerm(mSearchTerm)
		.setPageNum(pageNumber.get());
	}

	/**
	 * @return {@inheritDoc} for searching images
	 */
	@Override
	public String getAPIcall(
			FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder) {
		return mFiveZeroZeroImageAPIBuilder.getSearchCall();
		
	}

	
}
