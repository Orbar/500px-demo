package com.orbar.pxdemo.ImageList.Impl;

import com.orbar.pxdemo.APIHelper.Feature;
import com.orbar.pxdemo.APIHelper.FiveZeroZeroImageAPIBuilder;
import com.orbar.pxdemo.APIHelper.Sort;
import com.orbar.pxdemo.ImageList.FivePXImageListFragment;

public class FivePXBrowseImageListFragment extends FivePXImageListFragment {

	/**
	 * @return {@inheritDoc} Configured for browse calls
	 */
	@Override
	public FiveZeroZeroImageAPIBuilder configureAPI() {
		
		return new FiveZeroZeroImageAPIBuilder()
		.setFeature(Feature.POPULAR)
		.setInclude(mCategory)
		.setSorting(Sort.TIMES_VIEWED)
		.setPageNum(pageNumber.get());
	}

	/**
	 * @return {@inheritDoc} for browsing images
	 */
	@Override
	public String getAPIcall(
			FiveZeroZeroImageAPIBuilder mFiveZeroZeroImageAPIBuilder) {
		return mFiveZeroZeroImageAPIBuilder.getBrowseCall();
		
	}

	
}
