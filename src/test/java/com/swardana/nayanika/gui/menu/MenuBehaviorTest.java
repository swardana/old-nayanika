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

package com.swardana.nayanika.gui.menu;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.swardana.nayanika.control.ToolbarSubject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link MenuBehavior}.
 *
 * @author Sukma Wardana
 */
class MenuBehaviorTest {

    @Test
    @DisplayName("Test init view")
    public void testViewInitSetup() {
        var mockView = mock(MenuView.class);
        var mockControl = mock(ToolbarSubject.class);
        when(mockControl.isVisible()).thenReturn(true);

        var behavior = new MenuBehavior(mockView, mockControl);
        behavior.initView();

        verify(mockView).showToolbar();
    }

    @Test
    @DisplayName("Test toolbar control change to show toolbar")
    public void testViewShowToolbarMenuBecauseControlChange() {
        var mockView = mock(MenuView.class);
        var mockControl = mock(ToolbarSubject.class);
        when(mockControl.isVisible()).thenReturn(true);

        var behavior = new MenuBehavior(mockView, mockControl);
        behavior.onToolbarControlChange();

        verify(mockView).showToolbar();
    }

    @Test
    @DisplayName("Test toolbar control change to hidde toolbar")
    public void testViewHideToolbarMenuBecauseControlChange() {
        var mockView = mock(MenuView.class);
        var mockControl = mock(ToolbarSubject.class);
        when(mockControl.isVisible()).thenReturn(false);

        var behavior = new MenuBehavior(mockView, mockControl);
        behavior.onToolbarControlChange();

        verify(mockView).hideToolbar();
    }

}