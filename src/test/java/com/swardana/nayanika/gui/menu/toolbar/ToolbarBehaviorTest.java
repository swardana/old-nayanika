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

package com.swardana.nayanika.gui.menu.toolbar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.swardana.nayanika.base.FullScreen;
import com.swardana.nayanika.base.gallery.Exhibition;
import com.swardana.nayanika.base.gallery.Gallery;
import com.swardana.nayanika.base.gallery.SupportedPicture;
import com.swardana.nayanika.base.Presentation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * Unit test for {@link ToolbarBehavior}.
 *
 * @author Sukma Wardana
 */
class ToolbarBehaviorTest {

    private static final String FOLDER = "src/test/resources";

    @Test
    @DisplayName("Test enable slide-show menu when picture gallery change")
    public void testEnablePresentationMenuWhenPictureGalleryChange() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);
        var mockGallery = mock(Gallery.class);

        when(mockView.gallery()).thenReturn(mockGallery);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onPictureGalleryChange();

        verify(mockView).enableSlideShowMenu();
    }

    @Test
    @DisplayName("Test not enable slide-show menu when picture gallery change to empty")
    public void testNotEnablePresentationMenuWhenPictureGalleryChangeToEmpty() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);

        when(mockView.gallery()).thenReturn(null);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onPictureGalleryChange();

        verify(mockView, never()).enableSlideShowMenu();
    }

    @Test
    @DisplayName("Test open picture gallery when user not choose picture")
    public void testOpenPictureGalleryButUserNotChooseFile() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onOpenPictureGallery(null, SupportedPicture.ALL);

        verify(mockExhibition, never()).exhibits(anyString(), any());
    }

    @Test
    @DisplayName("Test open picture gallery when user choose directory")
    public void testOpenPictureGalleryButUserChooseDirectory() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);
        var mockFile = mock(File.class);
        when(mockFile.isDirectory()).thenReturn(true);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onOpenPictureGallery(mockFile, SupportedPicture.ALL);

        verify(mockExhibition, never()).exhibits(anyString(), any());
    }

    @Test
    @DisplayName("Test open picture gallery when user choose picture")
    public void testOpenPictureGallery() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);
        var mockFile = mock(File.class);
        when(mockFile.isDirectory()).thenReturn(false);
        when(mockFile.isFile()).thenReturn(true);
        when(mockFile.getAbsoluteFile()).thenReturn(new File(FOLDER));

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onOpenPictureGallery(mockFile, SupportedPicture.ALL);

        verify(mockExhibition).exhibits(anyString(), any(), any());
    }

    @Test
    @DisplayName("Test change the current active picture to next one")
    public void testChangeCurrentActivePictureToNext() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);
        var mockGallery = mock(Gallery.class);

        when(mockView.gallery()).thenReturn(mockGallery);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onViewNextPicture();

        verify(mockView).gallery();
        verify(mockGallery).next();
    }

    @Test
    @DisplayName("Test change the current active picture to previous one")
    public void testChangeCurrentActivePictureToPrevious() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);
        var mockGallery = mock(Gallery.class);

        when(mockView.gallery()).thenReturn(mockGallery);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onViewPreviousPicture();

        verify(mockView).gallery();
        verify(mockGallery).previous();
    }

    @Test
    @DisplayName("Test change the current active picture to first one")
    public void testChangeCurrentActivePictureToFirst() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);
        var mockGallery = mock(Gallery.class);

        when(mockView.gallery()).thenReturn(mockGallery);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onViewFirstPicture();

        verify(mockView).gallery();
        verify(mockGallery).first();
    }

    @Test
    @DisplayName("Test change the current active picture to last one")
    public void testChangeCurrentActivePictureToLast() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);
        var mockGallery = mock(Gallery.class);

        when(mockView.gallery()).thenReturn(mockGallery);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onViewLastPicture();

        verify(mockView).gallery();
        verify(mockGallery).last();
    }

    @Test
    @DisplayName("Test change to enter app full-screen mode")
    public void testEnterFullScreenMode() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onViewEnterFullScreen();

        verify(mockFullScreen).enterFullScreen();
    }

    @Test
    @DisplayName("Test change to exit app frin full-screen mode")
    public void testExitFullScreenMode() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onViewExitFullScreen();

        verify(mockFullScreen).exitFullScreen();
    }

    @Test
    @DisplayName("Test react from full-screen state to display exit full-screen menu")
    public void testReactFullScreenStateDisplayExitFullScreenMenu() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);

        when(mockFullScreen.isFullScreen()).thenReturn(true);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onFullScreenControlChange();

        verify(mockView).showExitFullScreenMenu();
    }

    @Test
    @DisplayName("Test react from full-screen state to display enter full-screen menu")
    public void testReactFullScreenStateDisplayEnterFullScreenMenu() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);

        when(mockFullScreen.isFullScreen()).thenReturn(false);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onFullScreenControlChange();

        verify(mockView).showEnterFullScreenMenu();
    }

    @Test
    @DisplayName("Test play slide-show when not in full-screen")
    public void testPlayPresentationWhenNotInFullScreenMode() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);
        var mockGallery = mock(Gallery.class);

        when(mockView.gallery()).thenReturn(mockGallery);
        when(mockFullScreen.isFullScreen()).thenReturn(false);
        when(mockGallery.isLast()).thenReturn(false);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onViewStartPresentation();

        verify(mockFullScreen).enterFullScreen();
        verify(mockPresentation).start();
    }

    @Test
    @DisplayName("Test play slide-show when in full-screen")
    public void testPlayPresentationWhenInFullScreenMode() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);
        var mockGallery = mock(Gallery.class);

        when(mockView.gallery()).thenReturn(mockGallery);
        when(mockFullScreen.isFullScreen()).thenReturn(true);
        when(mockGallery.isLast()).thenReturn(false);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onViewStartPresentation();

        verify(mockFullScreen, never()).enterFullScreen();
        verify(mockPresentation).start();
    }

    @Test
    @DisplayName("Test play slide-show when picture gallery already in the last")
    public void testPlayPresentationWhenGalleryAlreadyInTheLastPicture() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);
        var mockGallery = mock(Gallery.class);

        when(mockView.gallery()).thenReturn(mockGallery);
        when(mockFullScreen.isFullScreen()).thenReturn(false);
        when(mockGallery.isLast()).thenReturn(true);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onViewStartPresentation();

        verify(mockFullScreen, never()).enterFullScreen();
        verify(mockPresentation, never()).start();
    }

    @Test
    @DisplayName("Test stop slide-show")
    public void testStopPresentation() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onViewStopPresentation();

        verify(mockPresentation).stop();
    }

    @Test
    @DisplayName("Test react from slide-show state to display stop slide-show menu")
    public void testReactPresentationStateDisplayStopPresentationMenu() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);

        when(mockPresentation.isRunning()).thenReturn(true);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onPresentationControlChange();

        verify(mockView).showStopSlideShowMenu();
    }

    @Test
    @DisplayName("Test react from slide-show state to display start slide-show menu")
    public void testReactPresentationStateDisplayStartPresentationMenu() {
        var mockView = mock(ToolbarView.class);
        var mockFullScreen = mock(FullScreen.class);
        var mockExhibition = mock(Exhibition.class);
        var mockPresentation = mock(Presentation.class);

        when(mockPresentation.isRunning()).thenReturn(false);

        var behavior = new ToolbarBehavior(
            mockView, mockFullScreen, mockExhibition, mockPresentation
        );
        behavior.onPresentationControlChange();

        verify(mockView).showStartSlideShowMenu();
    }

}