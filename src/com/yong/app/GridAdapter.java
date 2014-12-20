package com.yong.app;

import java.util.ArrayList;
import java.util.List;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;

public class GridAdapter extends ArrayAdapter<IconIndex>{
	
	private Context mContext;
	private Resources res;
	
	//private Resources res;
    public GridAdapter(Context context, int layout, int resId, List<IconIndex> items) {
    	super(context, layout, resId, items);
    	mContext = context;
    	res = mContext.getResources();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	Drawable drawable;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.hanzi);
        
        Typeface typeFace = Typeface.createFromAsset(res.getAssets(), "fonts/simkai.ttf");
        textView.setTypeface(typeFace);
        IconIndex iconIndex = getItem(position);
        
        if(iconIndex.getUTFcode() == "oracle"){
        	textView.setText("");
	        textView.setTag("oracle");
	        drawable=res.getDrawable(R.drawable.yongicon); 
        }else{
	        textView.setText(iconIndex.getHanzi());
	        textView.setTag(iconIndex.getUTFcode());
		    drawable=res.getDrawable(R.drawable.iconback); 
        }
	    textView.setBackgroundDrawable(drawable); 
	    textView.setOnLongClickListener(new MyClickListener());        
        return convertView;
    }

	private final class MyClickListener implements OnLongClickListener {

	    // called when the item is long-clicked
		@Override
		public boolean onLongClick(View view) {
			if ((String)view.getTag() != "oracle") {
			//	return true;
			// TODO Auto-generated method stub
			
			// create it from the object's tag
				ClipData.Item itemTag = new ClipData.Item((String)view.getTag());

				String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
				ClipData data = new ClipData(view.getTag().toString(), mimeTypes, itemTag);
	        
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
	   
				view.startDrag( data, //data to be dragged
	        				shadowBuilder, //drag shadow
	        				view, //local data about the drag and drop operation
	        				0   //no needed flags
	        			  );
	        
	        
				view.setVisibility(View.INVISIBLE);
				return true;	
			}else{
				return false;
			}
		}	
	}    
}
