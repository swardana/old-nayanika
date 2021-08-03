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
 * Unit test for {@link ToolbarControl}.
 *
 * @author Sukma Wardana
 */
class ToolbarControlTest {

    @Test
    @DisplayName("Test default toolbar visibility state should be displayed")
    public void testDefaultToolbarVisibilityStateShouldBeTrue() {
        var expected = true;
        var subject = new ToolbarControl();
        assertThat(subject.isVisible()).isEqualTo(expected);
        assertThat(subject.visibilityProperty().get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test hide toolbar to trigger toolbar visibility state")
    public void testToChangeToolbarVisibilityStateToFalse() {
        var expected = false;
        var subject = new ToolbarControl();
        subject.hideToolbar();
        var actual = subject.visibilityProperty().get();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test display toolbar to trigger toolbar visibility state")
    public void testToChangeToolbarVisibilityStateToTrue() {
        var expected = true;
        var subject = new ToolbarControl();
        subject.hideToolbar();
        subject.showToolbar();
        var actual = subject.visibilityProperty().get();
        assertThat(actual).isEqualTo(expected);
    }

}