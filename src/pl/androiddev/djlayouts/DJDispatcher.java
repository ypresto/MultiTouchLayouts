package pl.androiddev.djlayouts;

import java.util.HashMap;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class DJDispatcher {
	
	private final HashMap<Integer, Integer> mPointerToViewMap = new HashMap<Integer, Integer>();
	private final Rect mTmpRect = new Rect();
	
	private MotionEvent convertEvent(View targetView, MotionEvent eventToConvert, int pointerId, int newAction) {
		final MotionEvent newEvent = MotionEvent.obtain(eventToConvert);
		newEvent.setAction(newAction);
		newEvent.setLocation(eventToConvert.getX(pointerId)-targetView.getLeft(), eventToConvert.getY(pointerId)-targetView.getTop());
		return newEvent;
	}
	
	public boolean dispatchTouchEvent(ViewGroup group, MotionEvent ev) {	
		boolean returnValue = false;
		if ( group == null || ev == null || ev.getPointerCount() <= 0 ) return false;
		final int childCount 	= group.getChildCount();
		final int pointerCount 	= ev.getPointerCount();
		final int actionCode 	= ev.getAction() & MotionEvent.ACTION_MASK;
		final int pointerId 	= ev.getPointerId(ev.getAction() >> MotionEvent.ACTION_POINTER_INDEX_SHIFT);
		switch ( actionCode ) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			for ( int j = 0 ; j < childCount ; ++j ) {
				final View child = group.getChildAt(j);
				child.getHitRect(mTmpRect);
				if ( mTmpRect.contains((int)ev.getX(pointerId), (int)ev.getY(pointerId)))  {
					mPointerToViewMap.put(pointerId, j);
					returnValue = child.dispatchTouchEvent(convertEvent(child, ev, pointerId, MotionEvent.ACTION_DOWN));
					break;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			if ( mPointerToViewMap.containsKey(pointerId)) {
				final int childIndex = mPointerToViewMap.get(pointerId);
				if ( childIndex < childCount ) {
					final View tragetView = group.getChildAt(childIndex);
					final MotionEvent event = convertEvent(tragetView, ev, pointerId, MotionEvent.ACTION_UP);
					returnValue = tragetView.dispatchTouchEvent(event);
					if ( returnValue ) mPointerToViewMap.remove(pointerId);
				} else {
					mPointerToViewMap.remove(pointerId);
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			for ( int i = 0 ; i < pointerCount ; ++i ) {
				final int pointerIdForMove = ev.getPointerId(i);
				if ( mPointerToViewMap.containsKey(pointerIdForMove) ) {
					final int childIndex = mPointerToViewMap.get(pointerIdForMove);
					if ( childIndex < childCount ) {
						final View tragetView = group.getChildAt(childIndex);
						final MotionEvent event = convertEvent(tragetView, ev, pointerIdForMove, MotionEvent.ACTION_MOVE);
						if ( tragetView.dispatchTouchEvent(event) ) returnValue = true;
					} else {
						mPointerToViewMap.remove(pointerId);
					}
				}
			}
			break;
		}
		return returnValue;
	}
}