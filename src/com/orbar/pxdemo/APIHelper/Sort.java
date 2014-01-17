package com.orbar.pxdemo.APIHelper;

public enum Sort {
	CREATED_AT("Created At","created_at"),
	RATING("Rating","rating"),
	TIMES_VIEWED("Times Viewed","times_viewed"),
	VOTES_COUNT("Votes Count","votes_count"),
    FAVORITES_COUNT("Favorites Count","favorites_count"),
    COMMENTS_COUNT("Comments Count","comments_count"),
    TAKEN_AT("Taken At","taken_at")
    ;
	
	
	private Sort(final String visibleName, final String apiName) {
        this.visibleName = visibleName;
        this.apiName = apiName;
    }

	private final String apiName;
	private final String visibleName;

    @Override
    public String toString() {
        return visibleName;
    }
    
    public String toAPIString() {
        return apiName;
    }
    
    public static Sort fromString(String apiName) {
    	if (apiName != null) {
    		for (Sort s : Sort.values()) {
    			if (apiName.equalsIgnoreCase(s.visibleName)) {
    				return s;
    			}
    		}
    	}
    	return null;
    }
}
