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

package com.swardana.nayanika.gui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.swardana.nayanika.base.FullScreen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link AppBehavior}.
 *
 * @author Sukma Wardana
 */
class AppBehaviorTest {

    @Test
    @DisplayName("Test init view")
    public void testViewInitSetup() {
        var mockView = mock(AppView.class);
        var mockControl = mock(FullScreen.class);
        when(mockControl.isFullScreen()).thenReturn(true);

        var behavior = new AppBehavior(mockView, mockControl);
        behavior.initView();

        verify(mockView).enterFullScreen();
    }

    @Test
    @DisplayName("Test when app and full-screen control have equal state")
    public void testDoNothingWhenViewAndControlHaveEqualState() {
        var mockView = mock(AppView.class);
        when(mockView.isFullScreen()).thenReturn(true);
        var mockControl = mock(FullScreen.class);
        when(mockControl.isFullScreen()).thenReturn(true);

        var behavior = new AppBehavior(mockView, mockControl);
        behavior.onViewWindowChange();

        verify(mockControl, never()).enterFullScreen();
        verify(mockControl, never()).exitFullScreen();
    }

    @Test
    @DisplayName("Test app window enter full-screen mode")
    public void testChangeControlToFullScreenBecauseViewChange() {
        var mockView = mock(AppView.class);
        when(mockView.isFullScreen()).thenReturn(true);
        var mockControl = mock(FullScreen.class);
        when(mockControl.isFullScreen()).thenReturn(false);

        var behavior = new AppBehavior(mockView, mockControl);
        behavior.onViewWindowChange();

        verify(mockControl).enterFullScreen();
    }

    @Test
    @DisplayName("Test app window exit full-screen mode")
    public void testChangeControlExitFullScreenBecauseViewChange() {
        var mockView = mock(AppView.class);
        when(mockView.isFullScreen()).thenReturn(false);
        var mockControl = mock(FullScreen.class);
        when(mockControl.isFullScreen()).thenReturn(true);

        var behavior = new AppBehavior(mockView, mockControl);
        behavior.onViewWindowChange();

        verify(mockControl).exitFullScreen();
    }

    @Test
    @DisplayName("Test when full-screen control and app have equal state")
    public void testDoNothingWhenControlAndViewHaveEqualState() {
        var mockView = mock(AppView.class);
        when(mockView.isFullScreen()).thenReturn(true);
        var mockControl = mock(FullScreen.class);
        when(mockControl.isFullScreen()).thenReturn(true);

        var behavior = new AppBehavior(mockView, mockControl);
        behavior.onFullScreenControlChange();

        verify(mockView, never()).enterFullScreen();
        verify(mockView, never()).exitFullScreen();
    }

    @Test
    @DisplayName("Test full-screen control change to full-screen state")
    public void testChangeViewToFullScreenBecauseControlChange() {
        var mockView = mock(AppView.class);
        when(mockView.isFullScreen()).thenReturn(false);
        var mockControl = mock(FullScreen.class);
        when(mockControl.isFullScreen()).thenReturn(true);

        var behavior = new AppBehavior(mockView, mockControl);
        behavior.onFullScreenControlChange();

        verify(mockView).enterFullScreen();
    }

    @Test
    @DisplayName("Test app window exit full-screen mode")
    public void testChangeViewExitFullScreenBecauseControlChange() {
        var mockView = mock(AppView.class);
        when(mockView.isFullScreen()).thenReturn(true);
        var mockControl = mock(FullScreen.class);
        when(mockControl.isFullScreen()).thenReturn(false);

        var behavior = new AppBehavior(mockView, mockControl);
        behavior.onFullScreenControlChange();

        verify(mockView).exitFullScreen();
    }

}