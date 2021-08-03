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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.swardana.nayanika.base.gallery.Exhibition;
import com.swardana.nayanika.base.gallery.Gallery;
import com.swardana.nayanika.base.gallery.Picture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Unit test for {@link ExhibitionControl}.
 *
 * @author Sukma Wardana
 */
class ExhibitionControlTest {

    @Test
    @DisplayName("Test exhibits a picture gallery")
    public void testExhibitPictureGalleryToRaiseEvent() throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPictures = mock(List.class);

        var mockPicture = mock(Picture.class);
        when(mockPicture.read()).thenReturn(mockContent);

        var mockGallery = mock(Gallery.class);
        when(mockGallery.current()).thenReturn(mockPicture);

        var mockExhibition = mock(Exhibition.class);
        when(mockExhibition.exhibits("foo", mockPictures)).thenReturn(mockGallery);

        var command = new ExhibitionControl(mockExhibition);
        command.exhibits("foo", mockPictures);

        verify(mockExhibition, atMostOnce()).exhibits("foo", mockPictures);
        assertThat(command.galleryProperty().getValue()).isNotNull();
    }

}