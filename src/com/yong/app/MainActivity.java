package com.yong.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.ClipData.Item;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnDragListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private GridView grid;
	private GridAdapter myAdapter;
	private YongView yongView;
	private YongDB yongDB;
	private String pathString;
	private Yong currentYong;
	private static final int INDEX_QUANTITY = 8;
	private String[] indexArray = new String[INDEX_QUANTITY];
	private List<IconIndex> iconIndexList = new ArrayList<IconIndex>();
	private HashMap<String,String> mapPathString = new HashMap<String,String>();
	private HashMap<String,String> mapHanzi = new HashMap<String,String>();
	private String downTag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		grid = (GridView)findViewById(R.id.grid);
		myAdapter = new GridAdapter(this, R.layout.item, R.id.hanzi, iconIndexList);
        grid.setAdapter(myAdapter);
        grid.setOnDragListener(new MyDragListener());
		
		yongDB = YongDB.getInstance(this);
		initIndex();
		
//		currentYong = yongDB.loadHanzi(indexArray[0]);
//		currentYong.getmPathString();
		
		Bitmap backImage;
		Bitmap coverImage;
		backImage  = BitmapFactory.decodeFile(pathString + "s" + indexArray[0] + ".png");
		coverImage = BitmapFactory.decodeFile(pathString + "e" + indexArray[0] + ".png");
		//pathJsonString = new String("[[[194,83,244,83,214,124],[243,82,263,112,247,133,216,123]],[[147,151,189,155,189,179,149,182],[188,154,211,148,212,176,188,178],[211,146,254,138,253,167,210,176],[217,174,251,167,243,190,219,191],[220,190,243,189,240,192,244,267,220,267],[219,265,244,266,242,366,212,365],[176,314,219,312,215,367]],[[101,214,130,212,131,240,102,240],[130,210,157,208,158,232,130,239],[156,207,207,197,212,223,158,230],[168,228,212,222,175,277,151,263,150,261],[150,261,175,276,103,337,89,313]],[[296,139,330,151,336,173,313,189,288,171],[288,170,314,188,264,220,250,224,252,208]],[[235,184,280,232,260,251,237,217],[279,232,315,263,287,284,260,249],[313,262,346,280,390,294,390,308,356,314,315,313,286,283]]]");
		String pathJsonString = mapPathString.get(indexArray[0]);
		yongView = (YongView)findViewById(R.id.yongWidget);
		yongView.setCurrentYong(indexArray[0], backImage, coverImage, pathJsonString);
		//yongView.setTag(indexArray[0]);
		yongView.setOnDragListener(new MyDragListener());
	}
	
	private void initIndex() {
		// TODO Auto-generated method stub
		SharedPreferences sharedPreferences = getSharedPreferences("index", Context.MODE_PRIVATE);
		
		if (sharedPreferences.contains("INDEX0")) {
			indexArray[0] = sharedPreferences.getString("INDEX0", "6C38");
		}
		if (sharedPreferences.contains("INDEX1")) {
			indexArray[1] = sharedPreferences.getString("INDEX1", "6709");
		}
		if (sharedPreferences.contains("INDEX2")) {
			indexArray[2] = sharedPreferences.getString("INDEX2", "4E0A");
		}
		if (sharedPreferences.contains("INDEX3")) {
			indexArray[3] = sharedPreferences.getString("INDEX3", "5927");
		}
		if (sharedPreferences.contains("INDEX4")) {
			indexArray[4] = sharedPreferences.getString("INDEX4", "4EBA");
		}
		if (sharedPreferences.contains("INDEX5")) {
			indexArray[5] = sharedPreferences.getString("INDEX5", "5B54");
		}
		if (sharedPreferences.contains("INDEX6")) {
			indexArray[6] = sharedPreferences.getString("INDEX6", "4E59");
		}
		if (sharedPreferences.contains("INDEX7")) {
			indexArray[7] = sharedPreferences.getString("INDEX7", "5DF1");
		}
		if (sharedPreferences.contains("PATHSTRING")) {
			pathString = sharedPreferences.getString("PATHSTRING", "/mnt/sdcard/DownFile/");
		}
		iconIndexList.add(new IconIndex("oracle", ""));
		Yong yong;
		yong = yongDB.loadHanzi(indexArray[0]);
		mapPathString.put(yong.getmHanziUTF(), yong.getmPathString());
		mapHanzi.put(yong.getmHanziUTF(), yong.getHanzi());
		for(int i = 1; i < indexArray.length; i++){
			yong = yongDB.loadHanzi(indexArray[i]);
			iconIndexList.add(new IconIndex(yong.getmHanziUTF(), yong.getHanzi()));
			mapPathString.put(yong.getmHanziUTF(), yong.getmPathString());
			mapHanzi.put(yong.getmHanziUTF(), yong.getHanzi());
		}
	}

	class MyDragListener implements OnDragListener {
    	Item item = null;
    	@Override
    	public boolean onDrag(View v, DragEvent event) {
      
    		// Handles each of the expected events
    	    switch (event.getAction()) {
    	    
    	    //signal for the start of a drag and drop operation.
    	    case DragEvent.ACTION_DRAG_STARTED:
    	        // do nothing
    	    	return true;
    	    //    break;
    	        
    	    //the drag point has entered the bounding box of the View
    	    case DragEvent.ACTION_DRAG_ENTERED:
    	        //change the shape of the view
    	        break;
    	        
    	    //the user has moved the drag shadow outside the bounding box of the View
    	    case DragEvent.ACTION_DRAG_EXITED:
    	        //change the shape of the view back to normal
    	        break;
    	        
    	    //drag shadow has been released,the drag point is within the bounding box of the View
    	    case DragEvent.ACTION_DROP:
    	        // if the view is the bottomlinear, we accept the drag item
    	    	  if(v == findViewById(R.id.yongWidget)) {
    	    		  downTag = (String)v.getTag();//获得下部分tag
    	    		  Log.d("downTag:", downTag);
    	    		  String clipData = event.getClipDescription().getLabel().toString();
    	    		  Log.d("clipData:", clipData);
    	    		  TextView textView = (TextView) event.getLocalState();
    	    		  textView.setTag(downTag);
    	    		  textView.setText((String)mapHanzi.get(downTag));
    	    		  
    	    		  textView.setVisibility(View.VISIBLE);
    	    		  YongView containView = (YongView) v;
    	    		  
    	    		  Bitmap backImage  = BitmapFactory.decodeFile(pathString + "s" + clipData + ".png");
    	    		  Bitmap coverImage = BitmapFactory.decodeFile(pathString + "e" + clipData + ".png");
    	    		  String pathJsonString = mapPathString.get(clipData);
    	    		  Log.d("pathJsonString:", pathJsonString);
    	    		  yongView.setCurrentYong(clipData, backImage, coverImage, pathJsonString);
    	    		  yongView.invalidate();
    	    		  
    	    		  containView.invalidate();
    	    		  
    	    	  } else {
    	    		  View view = (View) event.getLocalState();
    	    		  view.setVisibility(View.VISIBLE);
    	    	   }
    	    	  return true;
    	    //	  break;
    	    	  
    	    //the drag and drop operation has concluded.
    	    case DragEvent.ACTION_DRAG_ENDED:
    	    
    	    default:
    	    	return false;
    	    //    break;
    	    }
    	    return true;
    	}
    }
}
