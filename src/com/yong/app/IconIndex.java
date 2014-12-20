package com.yong.app;

import android.graphics.Bitmap;

public class IconIndex {
	private String UTFcode;
    private String hanzi;
	public IconIndex(String theUTFcode, String theHanzi) {
		super();
		this.UTFcode = theUTFcode;
		this.hanzi = theHanzi;
	}
    public String getUTFcode() {
		return UTFcode;
	}
	public String getHanzi() {
		return hanzi;
	}
}