package com.yong.app;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Environment;

public class YongApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        String[] indexArray = new String[8];
        
        String sdcardSave = "DownFile"; 
        //String SDcardPath=Environment.getExternalStorageDirectory()+"";
        String pathString=Environment.getExternalStorageDirectory() + "/" + sdcardSave + "/";
        
		File file=new File(pathString);  
		if (!file.exists()) {
			   new File(pathString).mkdir();
		}
		
        SharedPreferences sharedPreferences = getSharedPreferences("index", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        indexArray[0] = sharedPreferences.getString("INDEX0", null); //index0，index1...为"78E0"之类utf8码
        indexArray[1] = sharedPreferences.getString("INDEX1", null);
        indexArray[2] = sharedPreferences.getString("INDEX2", null);
        indexArray[3] = sharedPreferences.getString("INDEX3", null);
        indexArray[4] = sharedPreferences.getString("INDEX4", null);
        indexArray[5] = sharedPreferences.getString("INDEX5", null);
        indexArray[6] = sharedPreferences.getString("INDEX6", null);
        indexArray[7] = sharedPreferences.getString("INDEX7", null);
        if (indexArray[0] == null || indexArray[1] == null || indexArray[2] == null || indexArray[3] == null || indexArray[4] == null || indexArray[5] == null || indexArray[6] == null || indexArray[7] == null) {
        	Resources res=getResources();
        	indexArray = res.getStringArray(R.array.hanzis);
            editor.putString("INDEX0", indexArray[0]);
            editor.putString("INDEX1", indexArray[1]);
            editor.putString("INDEX2", indexArray[2]);
            editor.putString("INDEX3", indexArray[3]);
            editor.putString("INDEX4", indexArray[4]);
            editor.putString("INDEX5", indexArray[5]);
            editor.putString("INDEX6", indexArray[6]);
            editor.putString("INDEX7", indexArray[7]);
        }
        if (sharedPreferences.getString("PATHSTRING", null) == null) {
        	editor.putString("PATHSTRING", pathString);
        }
        editor.commit();
    }
}