/*
Copyright (c) 2011, Damian Kolakowski
Copyright (c) 2013, Yuya Tanaka
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

package com.ypresto.android.multitouchlayouts.test;

import com.ypresto.android.multitouchlayouts.MultiTouchDispatcher;

import junit.framework.TestCase;

/**
 * @author yuya
 *
 */
public class MultiTouchDispatcherTest extends TestCase {
    
    MultiTouchDispatcher mDispatcher;

    protected void setUp() throws Exception {
        super.setUp();
        mDispatcher = new MultiTouchDispatcher();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mDispatcher = null;
    }

    /**
     * Test method for {@link com.ypresto.android.multitouchlayouts.MultiTouchDispatcher#dispatchTouchEvent(android.view.ViewGroup, android.view.MotionEvent)}.
     */
    public void testDispatchTouchEvent() {
        fail("Not yet implemented"); // TODO
    }

}
