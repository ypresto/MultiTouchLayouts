package com.ypresto.android.multitouchlayouts.test;

import com.ypresto.android.multitouchlayouts.MultiTouchDispatcher;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MultiTouchDispatcherTest extends AndroidTestCase {
    
    private MultiTouchDispatcher mDispatcher;

    protected void setUp() throws Exception {
        super.setUp();
        mDispatcher = new MultiTouchDispatcher();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mDispatcher = null;
    }

    public void testDispatchTouchEvent() {
        Context context = getContext();
        View v1 = new View(context) {
            
            @Override
            public boolean dispatchTouchEvent(MotionEvent event) {
                // TODO Auto-generated method stub
                return super.dispatchTouchEvent(event);
            }
            
        };
        LinearLayout layout = new LinearLayout(context);
        MotionEvent ev = MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 100, 100, 1.0f, 1.0f, 0, 1.0f, 1.0f, 0, 0);
        mDispatcher.dispatchTouchEvent((ViewGroup) layout, ev);
    }

}
