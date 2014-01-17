package com.orbar.pxdemo.APIHelper;

public class FiveZeroZeroImageAPIBuilder {

	private static String API_BASE_POINT = "https://api.500px.com/v1";
	
	// API ENDPOINTS
	private static String API_BROWSE_BASE = "/photos?";
	private static String API_SEARCH_BASE = "/photos/search?";
	private static String API_COMMENTS_BASE = "/photos/";
	private static String API_COMMENTS_POST = "/comments?";
	private static String API_DETAILS_BASE = "/photos/";
	private static String API_LIKE_BASE = "/photos/";
	private static String API_LIKE_POST = "/vote?";
	
	private static String API_FAV_BASE = "/photos/";
	private static String API_FAV_POST = "/favorite";
	
	//DELETE photos/:id/favorite
	
	// API PARAMETERS
	private static String FEATURE = "feature=";
	private static String SORTING = "sort="; 
	private static String INCLUDE = "only=";
	private static String EXCLUDE = "exclude=";
	private static String IMAGE_SIZE = "image_size=";
	private static String RPP = "rpp=";
	private static String PAGE_NUM= "page=";
	private static String TERM= "term=";
	//private static String NESTED= "newsted";
	private static String VOTE= "vote=";
	private static String USER= "user_id=";
	
	
	// OAUTH VARIBALES
	public static final String TOKEN = "TOKEN";
	public static final String SECRET = "SECRET";
	
	// SPECIAL API LIMITS
	public static final int COMMENT_RPP_LIMIT= 20;
	
	
	private String APICall;
	
	private String feature = "";
	private String sorting = "";
	private String include = "";
	private String exclude = "";
	private String term = "";
	private boolean vote = false;
	private int user;
	
	private int imageSize = 2; 
	private int rpp = 30; 
	private int pageNum = 1; 
	
	public String getApiBasePoint() {
		return API_BASE_POINT;
	}
	
	/**
	 * @return the apiCall
	 */
	public String getBrowseCall() {
		APICall = API_BROWSE_BASE + getFeature() + getInclude() + getExclude() + getSorting() + getImageSize() + getRpp() + getPageNum();
		
		APICall = APICall.replace(" ", "%20");
		
		return APICall;
	}
	
	public String getSearchCall() {
		APICall = API_SEARCH_BASE + getTerm() + getFeature() + getSorting() + getImageSize() + getRpp() + getPageNum();
		
		APICall = APICall.replace(" ", "%20");
		
		return APICall;
	}
	
	public String getCommentsCall(int id) {
		APICall = API_COMMENTS_BASE +  id + API_COMMENTS_POST + getPageNum();
		
		APICall = APICall.replace(" ", "%20");
		
		return APICall;
	}
	
	public String postSubmitCommentCall(int id) {
		APICall = API_COMMENTS_BASE +  id + API_COMMENTS_POST;
		
		APICall = APICall.replace(" ", "%20");
		
		return APICall;
	}
	
	public String getImageDetailsCall(int id) {
		APICall = API_DETAILS_BASE +  id + "?";
		
		APICall = APICall.replace(" ", "%20");
		
		return APICall;
	}
	
	public String postImageLike(int id) {
		APICall = API_LIKE_BASE +  id + API_LIKE_POST + getVote();
		
		APICall = APICall.replace(" ", "%20");
		
		return APICall;
	}
	
	public String postImageFavorite(int id) {
		APICall = API_FAV_BASE +  id + API_FAV_POST;
		
		APICall = APICall.replace(" ", "%20");
		
		return APICall;
	}
	
	public String getUserBrowseCall() {
		APICall = API_BROWSE_BASE + getFeature() + getUser() + getRpp() + getPageNum();
		
		APICall = APICall.replace(" ", "%20");
		
		return APICall;
	}
	
	public FiveZeroZeroImageAPIBuilder setFeature(Feature mFeature) {
		this.feature = FEATURE + mFeature.toAPIString() +"&";
		return this;
	}
	
	public FiveZeroZeroImageAPIBuilder setSorting(Sort mSorting) {
		this.sorting = SORTING + mSorting.toAPIString() +"&";
		return this;
	}
	
	public FiveZeroZeroImageAPIBuilder setInclude(Category mCategory) {
		this.include = INCLUDE + mCategory.toString() +"&";
		return this;
	}
	
	public FiveZeroZeroImageAPIBuilder setExclude(Category mCategory) {
		this.exclude = EXCLUDE + mCategory.toString() +"&";
		return this;
	}

	/**
	 * @return the feature
	 */
	public String getFeature() {
		return feature;
	}

	/**
	 * @return the sorting
	 */
	public String getSorting() {
		return sorting;
	}

	/**
	 * @return the include
	 */
	public String getInclude() {
		return include;
	}

	/**
	 * @return the exclude
	 */
	public String getExclude() {
		return exclude;
	}

	/**
	 * @return the imageSize
	 */
	public String getImageSize() {
		return IMAGE_SIZE + imageSize +"&";
	}

	/**
	 * @param imageSize the imageSize to set
	 */
	public FiveZeroZeroImageAPIBuilder setImageSize(int imageSize) {
		this.imageSize = imageSize;
		return this;
	}

	/**
	 * @return the rpp
	 */
	public String getRpp() {
		return RPP + rpp + "&";
	}

	/**
	 * @return the rpp
	 */
	public int getRppAsInt() {
		return rpp;
	}

	/**
	 * @param rpp the rpp to set
	 */
	public FiveZeroZeroImageAPIBuilder setRpp(int rpp) {
		this.rpp = rpp;
		return this;
	}

	/**
	 * @return the pageNum
	 */
	public String getPageNum() {
		return PAGE_NUM + pageNum + "&";
	}

	/**
	 * @return the pageNum
	 */
	public int getPageNumAsInt() {
		return  pageNum;
	}
	
	/**
	 * @param pageNum the pageNum to set
	 */
	public FiveZeroZeroImageAPIBuilder setPageNum(int pageNum) {
		this.pageNum = pageNum;
		return this;
	}

	/**
	 * @return the term
	 */
	public String getTerm() {
		return TERM + term + "&";
	}

	/**
	 * @param term the term to set
	 */
	public FiveZeroZeroImageAPIBuilder setTerm(String term) {
		this.term = term;
		return this;
	}

	/**
	 * @return the vote
	 */
	public String getVote() {
		return VOTE + ((vote) ? "1" : "0") ;
	}

	/**
	 * @param vote the vote to set
	 */
	public FiveZeroZeroImageAPIBuilder setVote(boolean vote) {
		this.vote = vote;
		return this;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return USER + user + "&";
		
	}

	/**
	 * @param user the user to set
	 */
	public FiveZeroZeroImageAPIBuilder setUser(int user) {
		this.user = user;
		return this;
	}
	
}
