package com.yong.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class YongView extends View {
	private Bitmap mBitmap;
    private Canvas mCanvas  = new Canvas();
    private Paint mPaint;
    private Path  mPath;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
	private String mCurrentYongUTF; //当前字的UTF码
	//private String yong; 
	private Context mContext;
	private Resources res;
	private Bitmap mCoverImage;
	////////////////////////////////////////////////////////////////////////////////////////////
	ArrayList<ArrayList<Polygon>> allBrush = new ArrayList<ArrayList<Polygon>>(); //一串Brush
	ArrayList<Polygon> finishedStack = new ArrayList<Polygon>(); //已经绘制的块
	ArrayList<Polygon> currentBrush;//当前Brush
	Polygon currentPolygon;//当前块
	Polygon firstPolygon;//首块
	Polygon endPolygon;//尾块
	
	int polygonQuantity;//当前刷所含块数
	int brushQuantity;//总刷数
	int brushIndex;   //当前刷序号
	int polygonIndex; //当前块序号
	
	int fromStack = 0; //当前刷栈中开始位置
	boolean currentStart = false; //当前刷开始标志
	
	private Path circlePath;
	private Paint circlePaint;
	private boolean brushOver = false;
	/////////////////////////////////////////////////////////////////////////////////////////////
	public YongView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		res = mContext.getResources();
   	}
	public YongView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public YongView(Context context) {
		this(context, null);
	}
	private void setScreenWH() {
        // get screen info
        DisplayMetrics dm = new DisplayMetrics();
        dm = this.getResources().getDisplayMetrics();
        // get screen width
        int screenWidth = dm.widthPixels;
        // get screen height
        int screenHeight = dm.heightPixels;        
    }
    /**
     * 
     * @param mCoverImage
     * @note set cover bitmap , which  overlay on background. 
     */
    private void setCoverBitmap(Bitmap mCoverImage) {
        // setting paint
        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAntiAlias(true);
        
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        // converting bitmap into mutable bitmap
        mPath = new Path();
        mBitmap = Bitmap.createBitmap(400, 400, Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawBitmap(mCoverImage, 0, 0, null);
    }

	@Override
    protected void onDraw(Canvas canvas) {
    	canvas.drawBitmap(mBitmap, 0, 0, null);
    	if(currentStart){
    		mCanvas.drawPath(mPath, mPaint);
    	}else{
    	   	for(int i = 0; i < finishedStack.size(); i++) {
    			finishedStack.get(i).draw(mCanvas, mPaint);
    		}
    	}

        super.onDraw(canvas);
    }

    
    @SuppressLint("ClickableViewAccessibility")
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            	if(currentPolygon.contains(x, y)){
            		currentStart = true;
            		mPath.addPath(currentPolygon.getPath());
            	}
            	invalidate();
                mX = x;
                mY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //touch_move(x, y);
            	//circlePath = new Path();
            	//circlePath.addCircle(x, y, 12, Path.Direction.CW);
            	float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                	if(currentStart && currentPolygon.contains(x, y)){
                		finishedStack.add(currentPolygon);
                		mPath.addPath(currentPolygon.getPath());
                		//更新当前块
                		if(polygonIndex < (polygonQuantity - 1)){
                			polygonIndex++;
                			currentPolygon = currentBrush.get(polygonIndex);
                			brushOver = false;
                		}else{
                			brushOver = true;
                			fromStack = fromStack + polygonQuantity;
                			currentStart = false;
                			if(brushIndex < (brushQuantity - 1)){
                				brushIndex++;
                				currentBrush = allBrush.get(brushIndex);
                				polygonQuantity = currentBrush.size();
                			}
                		}
                	}
                }
                invalidate();
            	mX = x;
                mY = y;
                break;
            case MotionEvent.ACTION_UP:
            	currentStart = false;
            	if(brushOver){
            		polygonIndex = 0;
            		currentPolygon = currentBrush.get(polygonIndex);
            	}else{
            		currentPolygon = currentBrush.get(0);
            		polygonIndex = 0;
            		int endStack = finishedStack.size();
            		int partNum = endStack - fromStack;
            		for(int i = 0; i < partNum; i++){
            			finishedStack.remove(finishedStack.size() - 1);
            		}
            		mCanvas.setBitmap(mBitmap);
            		mCanvas.drawBitmap(mCoverImage, 0, 0, null);
            	}
            	invalidate();
                break;
        }
        return true;
    }
    
    /*
	public String getmCurrentYongUTF() {
		return mCurrentYongUTF;
	}

	public void setmCurrentYongUTF(String mCurrentYongUTF) {
		this.mCurrentYongUTF = mCurrentYongUTF;
	}
	*/
    
	public void setCurrentYong(String yongUTF, Bitmap backImage, Bitmap coverImage, String pathJsonString) {
		finishedStack.clear();
		allBrush.clear();
		mCurrentYongUTF = yongUTF;
		
        parseJSONWithJSONArray(pathJsonString);
        brushQuantity = allBrush.size();
        brushIndex = 0;
        currentBrush = allBrush.get(brushIndex);
        polygonQuantity = allBrush.get(brushIndex).size();
        polygonIndex = 0;
        currentPolygon = currentBrush.get(polygonIndex);
        
        setFocusable(true);
        setScreenWH();
        mCoverImage = coverImage;
        this.setBackgroundDrawable(new BitmapDrawable(backImage)); //实心字图文件
        setCoverBitmap(mCoverImage);

        //mPath = new Path();
        this.setTag(yongUTF);
    }
    
    public String getCurrentYong(){
    	return mCurrentYongUTF;
    }
    
	private void parseJSONWithJSONArray(String pathJsonString) {
		try {
			JSONArray brushJson = new JSONArray(pathJsonString); //所有刷子
			for (int i = 0; i < brushJson.length(); i++) {
				JSONArray coordJson = brushJson.getJSONArray(i);//一把刷子中的所有块
				ArrayList<Polygon> polygonList = new ArrayList<Polygon>();
				for(int j = 0; j < coordJson.length(); j++) {
					JSONArray polygonArray = coordJson.getJSONArray(j);//一个块中的所有点
					int polyLength = polygonArray.length();
					int[] coordArray = new int[polyLength];
					for(int k = 0; k < polygonArray.length(); k++){
						coordArray[k] = polygonArray.getInt(k);
					}
					Polygon aPolygon = new Polygon(coordArray);
					polygonList.add(aPolygon);
				}
				allBrush.add(polygonList);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}