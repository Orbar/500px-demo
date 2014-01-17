package com.orbar.pxdemo.APIHelper;

public enum Feature {
	
	POPULAR("Popular","popular"),
	UPCOMING("Upcoming","upcoming"),
	EDITORS("Editors Choich","editors"),
	FRESH_TODAY("Fresh Today", "fresh_today"),
    FRESH_YESTERDAY("Fresh Yesterday","fresh_yesterday"),
    FRESH_WEEK("Fresh Week","fresh_week"),
    USER("User", "user")
    ;
	
	private Feature(final String visibleName, final String apiName) {
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
    
    public static Feature fromString(String apiName) {
    	if (apiName != null) {
    		for (Feature f : Feature.values()) {
    			if (apiName.equalsIgnoreCase(f.visibleName)) {
    				return f;
    			}
    		}
    	}
    	return null;
    }
}
