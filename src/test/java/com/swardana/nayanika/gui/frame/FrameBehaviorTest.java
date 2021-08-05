/*
 * Nayanika, picture viewer application
 * Copyright (C) 2021  Sukma Wardana
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.swardana.nayanika.gui.frame;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.swardana.nayanika.base.FullScreen;
import com.swardana.nayanika.base.gallery.Gallery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link FrameBehavior}.
 *
 * @author Sukma Wardana
 */
class FrameBehaviorTest {

    @Test
    @DisplayName("Test on view zoom")
    public void testViewOnZoomAction() {
        var mockView = mock(FrameView.class);
        when(mockView.horizontalScrollOffset()).thenReturn(0.0);
        when(mockView.verticalScrollOffset()).thenReturn(0.0);
        var mockControl = mock(FullScreen.class);

        var behavior = new FrameBehavior(mockView, mockControl);
        behavior.onViewZoom(0.0, 0.0, 0.0);

        verify(mockView).rescalePicture(anyDouble(), anyDouble());
        verify(mockView).repositionScroller(anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    @DisplayName("Test on double click on view")
    public void testViewDoubleClickUsingMouse() {
        var mockView = mock(FrameView.class);
        var mockControl = mock(FullScreen.class);

        var behavior = new FrameBehavior(mockView, mockControl);
        behavior.onViewMouseClicked(2);

        verify(mockView).resetPictureScale();
    }

    @Test
    @DisplayName("Test on single click on view")
    public void testViewSingleClickUsingMouse() {
        var mockView = mock(FrameView.class);
        var mockControl = mock(FullScreen.class);

        var behavior = new FrameBehavior(mockView, mockControl);
        behavior.onViewMouseClicked(1);

        verify(mockView, never()).resetPictureScale();
    }

    @Test
    @DisplayName("Test mouse drag on zoomed picture")
    public void testViewMouseDraggedOnZoomedPicture() {
        var mockView = mock(FrameView.class);
        var mockControl = mock(FullScreen.class);

        var behavior = new FrameBehavior(mockView, mockControl);
        behavior.onViewMouseDragged(3, 2, 3, 2);

        verify(mockView).horizontalMove(eq(1.0));
        verify(mockView).verticalMove(eq(1.0));
    }

    @Test
    @DisplayName("Test view change active picture to the next one")
    public void testViewChangeNextPicture() {
        var mockGallery = mock(Gallery.class);
        var mockView = mock(FrameView.class);
        when(mockView.gallery()).thenReturn(mockGallery);
        var mockControl = mock(FullScreen.class);

        var behavior = new FrameBehavior(mockView, mockControl);
        behavior.onViewNextPicture();

        verify(mockView).gallery();
        verify(mockGallery).next();
    }

    @Test
    @DisplayName("Test view change active picture to the previous one")
    public void testViewChangePreviousPicture() {
        var mockGallery = mock(Gallery.class);
        var mockView = mock(FrameView.class);
        when(mockView.gallery()).thenReturn(mockGallery);
        var mockControl = mock(FullScreen.class);

        var behavior = new FrameBehavior(mockView, mockControl);
        behavior.onViewPreviousPicture();

        verify(mockView).gallery();
        verify(mockGallery).previous();
    }

    @Test
    @DisplayName("Test view change active picture should rescale and request focus")
    public void testImageChangeTriggerRescaleAndRequestFocus() {
        var mockView = mock(FrameView.class);
        var mockControl = mock(FullScreen.class);

        var behavior = new FrameBehavior(mockView, mockControl);
        behavior.onViewImageChange();

        verify(mockView).focus();
        verify(mockView).resetPictureScale();
    }

}