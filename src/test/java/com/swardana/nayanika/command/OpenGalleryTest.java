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

package com.swardana.nayanika.command;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.swardana.nayanika.base.gallery.Exhibition;
import com.swardana.nayanika.base.gallery.Picture;
import com.swardana.nayanika.base.gallery.SupportedPicture;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for {@link OpenGallery}.
 *
 * @author Sukma Wardana
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OpenGalleryTest {

    private ExecutorService executor;

    @BeforeAll
    public void setup() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    @AfterAll
    public void tearDown() {
        this.executor.shutdown();

        try {
            if (!this.executor.awaitTermination(60, TimeUnit.SECONDS)) {
                this.executor.shutdownNow();

                if (!this.executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("The executor service did not terminate.");
                }
            }
        } catch (final InterruptedException ex) {
            this.executor.shutdown();
            Thread.currentThread().interrupt();
        }
    }

    @Test
    @DisplayName("Test exhibit picture gallery from directory")
    public void testExhibitPictureGalleryFromDirectory() {
        var mockExhibition = mock(Exhibition.class);
        var file = new File("src/test/resources/com/swardana/nayanika/base/gallery");

        var command = new OpenGallery(mockExhibition, this.executor);
        command.execute(file, SupportedPicture.ALL);

        verify(mockExhibition, never()).exhibits(anyString(), anyList(), any(Picture.class));
        verify(mockExhibition, times(1)).exhibits(anyString(), anyList());
    }

    @Test
    @DisplayName("Test exhibit picture gallery from picture")
    public void testExhibitPictureGalleryFromPicture() {
        var mockExhibition = mock(Exhibition.class);
        var file = new File(
            "src/test/resources/com/swardana/nayanika/base/gallery/tattoo-dragon-jpeg.jpeg"
        );

        var command = new OpenGallery(mockExhibition, this.executor);
        command.execute(file, SupportedPicture.ALL);

        verify(mockExhibition, never()).exhibits(anyString(), anyList());
        verify(mockExhibition, times(1)).exhibits(anyString(), anyList(), any(Picture.class));
    }

}