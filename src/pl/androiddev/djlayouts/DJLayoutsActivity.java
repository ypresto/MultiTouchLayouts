package pl.androiddev.djlayouts;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class DJLayoutsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final SeekBar bar1 = new SeekBar(this) {
        	{
        		setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
        	}
        };
        
        final SeekBar bar2 = new SeekBar(this) {
        	{
        		setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
        	}
        };
        
        final DJLinearLayout layout = new DJLinearLayout(this) {
        	{
        		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                setOrientation(LinearLayout.HORIZONTAL);
                setGravity(Gravity.CENTER_VERTICAL);
                addView(bar1);
                addView(bar2);
        	}
        };

        setContentView(layout);
    }
    
    private class DJLinearLayout extends LinearLayout {

    	private final DJDispatcher mDJDispatcher = new DJDispatcher();
    	
		public DJLinearLayout(Context context) {
			super(context);
		}
		
		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			return mDJDispatcher.dispatchTouchEvent(this, ev);
		}
    }
    
    public class DJDispatcher {
    	
    	private final HashMap<Integer, Integer> mPointerToViewMap = new HashMap<Integer, Integer>();
    	private final Rect mTmpRect = new Rect();
    	
    	private MotionEvent convertEvent(View targetView, MotionEvent eventToConvert, int pointerId) {
    		int action = eventToConvert.getAction();
    		final int actionCode = action & MotionEvent.ACTION_MASK;
    		if ( actionCode ==  MotionEvent.ACTION_POINTER_DOWN ) {
    			action = MotionEvent.ACTION_DOWN;
    		}
    		if ( actionCode == MotionEvent.ACTION_POINTER_UP ) {
    			action = MotionEvent.ACTION_UP;
    		}
    		final MotionEvent newEvent = MotionEvent.obtain(eventToConvert);
    		newEvent.setAction(action);
    		newEvent.setLocation(eventToConvert.getX(pointerId)-targetView.getLeft(), eventToConvert.getY(pointerId)-targetView.getTop());
    		return newEvent;
    	}
    	
    	public boolean dispatchTouchEvent(ViewGroup group, MotionEvent ev) {
    		if ( group == null || ev == null || ev.getPointerCount() <= 0 ) return false;
    		boolean result = false;
    		final int pointerCount = ev.getPointerCount();
    		final int childCount = group.getChildCount();
    		for ( int i = 0 ; i < pointerCount ; ++i ) {
    			final int pointerId = ev.getPointerId(i);
    			if ( mPointerToViewMap.containsKey(pointerId) ) {
    				final int childIndex = mPointerToViewMap.get(pointerId);
    				if ( childIndex < childCount ) {
    					final View tragetView = group.getChildAt(childIndex);
    					final MotionEvent event = convertEvent(tragetView, ev, pointerId);
    					result = tragetView.dispatchTouchEvent(convertEvent(tragetView, ev, pointerId));
    					if ( !result || event.getAction() == MotionEvent.ACTION_UP ) mPointerToViewMap.remove(pointerId);
    				} else {
    					mPointerToViewMap.remove(pointerId);
    				}
    			} else {
    				for ( int j = 0 ; j < childCount ; ++j ) {
    					final View child = group.getChildAt(j);
    					child.getHitRect(mTmpRect);
    					if ( mTmpRect.contains((int)ev.getX(pointerId), (int)ev.getY(pointerId)))  {
    						mPointerToViewMap.put(pointerId, j);
        					result = child.dispatchTouchEvent(convertEvent(child, ev, pointerId));
    						break;
    					}
    				}
    			}
    		}
    		return result;
    	}
    }
}