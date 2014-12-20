package com.yong.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.shapes.PathShape;
import android.util.Log;

public class Polygon {

	public int[] ypoints;
	public int[] xpoints;
	private int[] coords;

	public Polygon(int coord[]) {
		coords = coord;
	}
	
	private Rect calculateBounds() {
		int boundsMinX = Integer.MAX_VALUE;
		int boundsMinY = Integer.MAX_VALUE;
		int boundsMaxX = Integer.MIN_VALUE;
		int boundsMaxY = Integer.MIN_VALUE;
		int x, y;
		for (int i = 0; i < coords.length; i = i + 2) {
			x = coords[i];
			boundsMinX = Math.min(boundsMinX, x);
			boundsMaxX = Math.max(boundsMaxX, x);
		}
		for (int i = 1; i < coords.length; i = i + 2) {
			y = coords[i];
			boundsMinY = Math.min(boundsMinY, y);
			boundsMaxY = Math.max(boundsMaxY, y);
		}
		
		int boundsWidth = boundsMaxX - boundsMinX;
		if(boundsWidth < 60){
			int dw = (60-boundsWidth)/2;
			boundsMaxX = boundsMaxX + dw;
			boundsMinX = boundsMinX - dw;
		}
		int boundsHigh = boundsMaxY - boundsMinY;
		if(boundsHigh < 60){
			int dh = (60-boundsWidth)/2;
			boundsMaxY = boundsMaxY + dh;
			boundsMinY = boundsMinY - dh;
		}
		return new Rect(boundsMinX, boundsMinY, boundsMaxX,	boundsMaxY);
	}
	
	public boolean contains(int x, int y) {
		Rect rect = calculateBounds();
		return rect.contains(x, y);
	}
	public void  draw(Canvas canvas, Paint paint){
		Path path = getPath();
		canvas.drawPath(path, paint);
	}
	
	public Path getPath(){
		Path path = new Path();
		path.moveTo(coords[0], coords[1]);
		for(int i = 2; i < coords.length - 1; i = i + 2) {
			path.lineTo(coords[i], coords[i + 1]);
		}
		path.close();
		return path;
	}
}

