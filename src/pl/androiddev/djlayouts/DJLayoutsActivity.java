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
}