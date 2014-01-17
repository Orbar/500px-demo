package com.orbar.pxdemo.APIHelper;

public enum Mode {

	BROWSE("Browse"),
	SEARCH("Search");
	
	private Mode(final String text) {
        this.text = text;
    }

    private final String text;

    @Override
    public String toString() {
        return text;
    }
}
