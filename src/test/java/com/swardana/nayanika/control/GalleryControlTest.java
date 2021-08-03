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

package com.swardana.nayanika.control;

import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.swardana.nayanika.base.gallery.Gallery;
import com.swardana.nayanika.base.gallery.Picture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Unit test for {@link GalleryControl}.
 *
 * @author Sukma Wardana
 */
class GalleryControlTest {

    @Test
    @DisplayName("Test change current picture to the next")
    public void testChangeCurrentPictureToTheNextPicture() throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPicture = mock(Picture.class);
        when(mockPicture.name()).thenReturn("1.jpg");
        when(mockPicture.read()).thenReturn(mockContent);

        var mockGallery = mock(Gallery.class);
        when(mockGallery.current()).thenReturn(mockPicture);
        when(mockGallery.isLast()).thenReturn(false);

        var subject = new GalleryControl(mockGallery);
        subject.next();

        verify(mockGallery, atMostOnce()).next();
        verify(mockGallery, times(2)).current();
        verify(mockGallery, times(2)).isFirst();
        verify(mockGallery, times(3)).isLast();
    }

    @Test
    @DisplayName("Test change current picture to the next when already at the last")
    public void testChangeCurrentPictureToTheNextPictureWhenOnLast() throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPicture = mock(Picture.class);
        when(mockPicture.name()).thenReturn("1.jpg");
        when(mockPicture.read()).thenReturn(mockContent);

        var mockGallery = mock(Gallery.class);
        when(mockGallery.current()).thenReturn(mockPicture);
        when(mockGallery.isLast()).thenReturn(true);

        var subject = new GalleryControl(mockGallery);
        subject.next();

        verify(mockGallery, never()).next();
        verify(mockGallery, times(1)).current();
        verify(mockGallery, times(1)).isFirst();
        verify(mockGallery, times(2)).isLast();
    }

    @Test
    @DisplayName("Test change current picture to the previous")
    public void testChangeCurrentPictureToThePreviousPicture() throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPicture = mock(Picture.class);
        when(mockPicture.name()).thenReturn("1.jpg");
        when(mockPicture.read()).thenReturn(mockContent);

        var mockGallery = mock(Gallery.class);
        when(mockGallery.current()).thenReturn(mockPicture);
        when(mockGallery.isLast()).thenReturn(false);

        var subject = new GalleryControl(mockGallery);
        subject.previous();

        verify(mockGallery, atMostOnce()).previous();
        verify(mockGallery, times(2)).current();
        verify(mockGallery, times(3)).isFirst();
        verify(mockGallery, times(2)).isLast();
    }

    @Test
    @DisplayName("Test change current picture to the previous when already at the first")
    public void testChangeCurrentPictureToThePreviousPictureWhenOnFirst() throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPicture = mock(Picture.class);
        when(mockPicture.name()).thenReturn("1.jpg");
        when(mockPicture.read()).thenReturn(mockContent);

        var mockGallery = mock(Gallery.class);
        when(mockGallery.current()).thenReturn(mockPicture);
        when(mockGallery.isFirst()).thenReturn(true);

        var subject = new GalleryControl(mockGallery);
        subject.previous();

        verify(mockGallery, never()).previous();
        verify(mockGallery, times(1)).current();
        verify(mockGallery, times(2)).isFirst();
        verify(mockGallery, times(1)).isLast();
    }

    @Test
    @DisplayName("Test change current picture to the last")
    public void testChangeCurrentPictureToTheLastPicture() throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPicture = mock(Picture.class);
        when(mockPicture.name()).thenReturn("1.jpg");
        when(mockPicture.read()).thenReturn(mockContent);

        var mockGallery = mock(Gallery.class);
        when(mockGallery.current()).thenReturn(mockPicture);
        when(mockGallery.isLast()).thenReturn(false);

        var subject = new GalleryControl(mockGallery);
        subject.last();

        verify(mockGallery, atMostOnce()).last();
        verify(mockGallery, times(2)).current();
        verify(mockGallery, times(2)).isFirst();
        verify(mockGallery, times(3)).isLast();
    }

    @Test
    @DisplayName("Test change current picture to the last when already at the last")
    public void testChangeCurrentPictureToTheLastPictureWhenOnLast() throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPicture = mock(Picture.class);
        when(mockPicture.name()).thenReturn("1.jpg");
        when(mockPicture.read()).thenReturn(mockContent);

        var mockGallery = mock(Gallery.class);
        when(mockGallery.current()).thenReturn(mockPicture);
        when(mockGallery.isLast()).thenReturn(true);

        var subject = new GalleryControl(mockGallery);
        subject.last();

        verify(mockGallery, never()).last();
        verify(mockGallery, times(1)).current();
        verify(mockGallery, times(1)).isFirst();
        verify(mockGallery, times(2)).isLast();
    }

    @Test
    @DisplayName("Test change current picture to the first")
    public void testChangeCurrentPictureToTheFirstPicture() throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPicture = mock(Picture.class);
        when(mockPicture.name()).thenReturn("1.jpg");
        when(mockPicture.read()).thenReturn(mockContent);

        var mockGallery = mock(Gallery.class);
        when(mockGallery.current()).thenReturn(mockPicture);
        when(mockGallery.isLast()).thenReturn(false);

        var subject = new GalleryControl(mockGallery);
        subject.first();

        verify(mockGallery, atMostOnce()).first();
        verify(mockGallery, times(2)).current();
        verify(mockGallery, times(3)).isFirst();
        verify(mockGallery, times(2)).isLast();
    }

    @Test
    @DisplayName("Test change current picture to the first when already at the first")
    public void testChangeCurrentPictureToTheFirstPictureWhenOnFirst() throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPicture = mock(Picture.class);
        when(mockPicture.name()).thenReturn("1.jpg");
        when(mockPicture.read()).thenReturn(mockContent);

        var mockGallery = mock(Gallery.class);
        when(mockGallery.current()).thenReturn(mockPicture);
        when(mockGallery.isFirst()).thenReturn(true);

        var subject = new GalleryControl(mockGallery);
        subject.first();

        verify(mockGallery, never()).first();
        verify(mockGallery, times(1)).current();
        verify(mockGallery, times(2)).isFirst();
        verify(mockGallery, times(1)).isLast();
    }

}