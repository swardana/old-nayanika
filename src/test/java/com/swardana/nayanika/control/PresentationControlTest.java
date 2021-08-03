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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.swardana.nayanika.base.slideshow.SlideShow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link PresentationControl}.
 *
 * @author Sukma Wardana
 */
class PresentationControlTest {

    @Test
    @DisplayName("Test not start the presentation when the slide is not set")
    public void testStartPresentationWhileTheSlideIsNotSet() {
        var expected = false;
        var presentation = new PresentationControl();
        presentation.start();
        var actual = presentation.isRunning();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test start the presentation")
    public void testStartPresentation() {
        var expected = true;

        var mockSlide = mock(SlideShow.class);

        var presentation = new PresentationControl();
        presentation.slide(mockSlide);
        presentation.start();

        var actual = presentation.isRunning();

        assertThat(actual).isEqualTo(expected);
        verify(mockSlide, times(1)).play();
    }

    @Test
    @DisplayName("Test stop the presentation")
    public void testStopPresentation() {
        var expected = false;

        var mockSlide = mock(SlideShow.class);

        var presentation = new PresentationControl();
        presentation.slide(mockSlide);
        presentation.start();
        presentation.stop();

        var actual = presentation.isRunning();

        assertThat(actual).isEqualTo(expected);
        verify(mockSlide, times(1)).stop();
    }

}