package com.orbar.pxdemo.APIHelper;

import java.util.HashMap;
import java.util.Map;

public enum Category {

	
	ALL("All", -1),
	ABSTRACT("Abstract", 10),
	ANIMALS("Animals", 11),
	BLACK_AND_WHITE("Black and White", 5),
    CELEBRITIES("Celebrities", 1),
    CITY_AND_ARCHITECTURE("City and Architecture", 9),
    COMMERCIAL("Commercial", 15),
    CONCERT("Concert", 16),
	FAMILY("Family", 20),
	FASHION("Fashion", 14),
	FILM("Film", 2),
    FINE_ART("Fine Art", 24),
    FOOD("Food", 23),
    JOURNALISM("Journalism", 3),
    LANDSCAPES("Landscapes", 8),
	MACRO("Macro", 12),
	NATURE("Nature", 18),
	//NUDE("Nude", 4),
    PEOPLE("People", 7),
    PERFORMING_ARTS("Performing Arts", 19),
    SPORT("Sport", 17),
    STILL_LIFE("Still Life", 6),
	STREET("Street", 21),
	TRANSPORTATION("Transportation", 26),
	TRAVEL("Travel", 13),
    UNDERWATER("Underwater", 22),
    URBAN_EXPLORATION("Urban Exploration", 27),
    WEDDING("Wedding", 25),
    UNCATEGORIZED("Uncategorized", 0),
	;
	
	private Category(final String text, final int value) {
		this.text = text;
		this.value = value;
        
    }

	private static final Map<Integer, Category> typesByValue = new HashMap<Integer, Category>();

	private final String text;
	private final int value;

	static {
        for (Category type : Category.values()) {
            typesByValue.put(type.value, type);
        }
    }
	
    @Override
    public String toString() {
        return text;
    }
    
    
    public static Category forValue(int value) {
        return typesByValue.get(value);
    }
}
