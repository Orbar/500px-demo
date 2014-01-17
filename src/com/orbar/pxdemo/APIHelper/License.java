package com.orbar.pxdemo.APIHelper;

import java.util.HashMap;
import java.util.Map;

public enum License {

	
	STANDARD("Standard 500px License", 0),
	CC_NON_COM_ATTR("Creative Commons License Non Commercial Attribution", 1),
	CC_NON_COM_NO_DRV("Creative Commons License Non Commercial No Derivatives", 2),
	CC_NON_COM_ALIKE("Creative Commons License Non Commercial Share Alike", 3),
    CC_ATTR("Creative Commons License Attribution", 4),
    CC_NO_ATTR("Creative Commons License No Derivatives", 5),
    CC_ALIKE("Creative Commons License Share Alike", 6),
    
	;
	
	private License(final String text, final int value) {
		this.text = text;
		this.value = value;
        
    }

	private static final Map<Integer, License> typesByValue = new HashMap<Integer, License>();

	private final String text;
	private final int value;

	static {
        for (License type : License.values()) {
            typesByValue.put(type.value, type);
        }
    }
	
    @Override
    public String toString() {
        return text;
    }
    
    
    public static License forValue(int value) {
        return typesByValue.get(value);
    }
}
