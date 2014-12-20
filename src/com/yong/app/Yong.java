package com.yong.app;

import java.util.ArrayList;
import java.util.List;

public class Yong {
	private String mHanziUTF; //汉字的UTF编码，如 78E6
	private String mPathString; //控制书写顺序的字符串，将转化为二维数组
	private String mHanzi;    //一个汉字
	private int mFiveType;    //五种起笔0,1,2,3,4
	
	public Yong(String hanzi, String hanziUTF, int fiveType, String pathString) {
		mHanzi = hanzi; 
		mHanziUTF = hanziUTF;
		mFiveType = fiveType;
		mPathString = pathString;
		
	}
	public String getmHanziUTF() {
		return mHanziUTF;
	}
	public void setmHanziUTF(String mHanziUTF) {
		this.mHanziUTF = mHanziUTF;
	}
	public String getmPathString() {
		return mPathString;
	}
	public void setmPathList(String mPathString) {
		this.mPathString = mPathString;
	}
	//转化控制书写顺序的字符串为为二维数组
	public List<List> transPathString(){
		ArrayList pathList = new ArrayList();
		if (mPathString != null) {
			
		}
		return pathList;
	}
	public String getHanzi() {
		return mHanzi;
	}
	public void setHanzi(String mHanzi) {
		this.mHanzi = mHanzi;
	}
	public int getmFivetype() {
		return mFiveType;
	}
	public void setFivetype(int fivetype) {
		mFiveType = fivetype;
	}
}
