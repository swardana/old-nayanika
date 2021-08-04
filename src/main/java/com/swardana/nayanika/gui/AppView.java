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

/**
 * A view contract for the main application.
 *
 * @author Sukma Wardana
 */
public interface AppView {

    /**
     * Operation to change the app window into full-screen.
     */
    void enterFullScreen();

    /**
     * Operation to change the app window to exiting from full-screen.
     */
    void exitFullScreen();

    /**
     * Checking whether the current app window is in full-screen state or not.
     *
     * @return {@code true} if the current app window is in full-screen.
     */
    boolean isFullScreen();

}
