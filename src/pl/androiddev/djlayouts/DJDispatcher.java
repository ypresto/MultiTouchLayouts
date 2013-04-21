/*
Copyright (c) 2011, Damian Kolakowski
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. All advertising materials mentioning features or use of this software
   must display the following acknowledgement:
   This product includes software developed by the Damian Kolakowski.
4. Neither the name of the Damian Kolakowski nor the
   names of its contributors may be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Damian Kolakowski ''AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Damian Kolakowski BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package pl.androiddev.djlayouts;

import android.graphics.Rect;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class DJDispatcher {

    private final SparseIntArray mPointerToViewSparse = new SparseIntArray();

    private final Rect mTmpRect = new Rect();

    private MotionEvent convertEvent(View targetView, MotionEvent eventToConvert, int pointerIndex,
            int newAction) {
        final MotionEvent newEvent = MotionEvent.obtain(eventToConvert);
        newEvent.setAction(newAction);
        newEvent.setLocation(eventToConvert.getX(pointerIndex) - targetView.getLeft(),
                eventToConvert.getY(pointerIndex) - targetView.getTop());
        return newEvent;
    }

    public boolean dispatchTouchEvent(ViewGroup group, MotionEvent ev) {
        boolean returnValue = false;
        if (group == null || ev == null || ev.getPointerCount() <= 0)
            return false;
        final int childCount = group.getChildCount();
        final int pointerCount = ev.getPointerCount();
        final int actionCode = ev.getAction() & MotionEvent.ACTION_MASK;
        final int pointerIndex = ev.getAction() >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        final int pointerId = ev.getPointerId(pointerIndex);
        switch (actionCode) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                for (int j = 0; j < childCount; ++j) {
                    final View child = group.getChildAt(j);
                    child.getHitRect(mTmpRect);
                    if (mTmpRect.contains((int) ev.getX(pointerIndex), (int) ev.getY(pointerIndex))) {
                        mPointerToViewSparse.put(pointerId, j);
                        returnValue = child.dispatchTouchEvent(convertEvent(child, ev,
                                pointerIndex, MotionEvent.ACTION_DOWN));
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                final int childIndex = mPointerToViewSparse.get(pointerId, -1);
                if (childIndex == -1)
                    break;
                if (childIndex < childCount) {
                    final View targetView = group.getChildAt(childIndex);
                    final MotionEvent event = convertEvent(targetView, ev, pointerIndex,
                            MotionEvent.ACTION_UP);
                    returnValue = targetView.dispatchTouchEvent(event);
                    if (returnValue)
                        mPointerToViewSparse.delete(pointerId);
                } else {
                    mPointerToViewSparse.delete(pointerId);
                }
            }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < pointerCount; ++i) {
                    final int pointerIdForMove = ev.getPointerId(i);
                    final int childIndex = mPointerToViewSparse.get(pointerIdForMove, -1);
                    if (childIndex == -1)
                        continue;
                    if (childIndex < childCount) {
                        final View targetView = group.getChildAt(childIndex);
                        final MotionEvent event = convertEvent(targetView, ev, i,
                                MotionEvent.ACTION_MOVE);
                        if (targetView.dispatchTouchEvent(event))
                            returnValue = true;
                    } else {
                        mPointerToViewSparse.delete(pointerId);
                    }
                }
                break;
        }
        return returnValue;
    }
}
