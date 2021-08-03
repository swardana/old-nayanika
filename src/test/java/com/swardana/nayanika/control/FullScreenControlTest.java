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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link FullScreenControl}.
 *
 * @author Sukma Wardana
 */
class FullScreenControlTest {

    @Test
    @DisplayName("Test default window full-screen state should be not full-screen")
    public void testDefaultWindowFullScreenStateShouldBeNotFullScreen() {
        var expected = false;
        var subject = new FullScreenControl();
        assertThat(subject.isFullScreen()).isEqualTo(expected);
        assertThat(subject.fullScreenProperty().get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test enter to full-screen mode to trigger window full-screen state")
    public void testToChangeTheFullScreenStateToTrue() {
        var expected = true;
        var subject = new FullScreenControl();
        subject.enterFullScreen();
        var actual = subject.fullScreenProperty().get();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test exit from full-screen mode to trigger window full-screen state")
    public void testToChangeTheFullScreenStateToFalse() {
        var expected = false;
        var subject = new FullScreenControl();
        subject.enterFullScreen();
        subject.exitFullScreen();
        var actual = subject.fullScreenProperty().get();
        assertThat(actual).isEqualTo(expected);
    }

}