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

import com.swardana.nayanika.base.gallery.Gallery;

/**
 * A view contract for Gallery menu.
 *
 * @author Sukma Wardana
 */
public interface GalleryMenuView {

    /**
     * Operation to enabling play slide-show menu.
     */
    void enableSlideShowMenu();

    /**
     * Operation to display the enter full-screen menu.
     * <p>
     *     This operation called when exiting from full-screen state to hide
     *     the exit full-screen menu.
     * </p>
     */
    void showEnterFullScreenMenu();

    /**
     * Operation to display the exit full-screen menu.
     * <p>
     *     This operation called when entering full-screen state to hide
     *     the enter full-screen menu.
     * </p>
     */
    void showExitFullScreenMenu();

    /**
     * Operation to display the start slide-show menu.
     * <p>
     *     This operation called when slide-show presentation being stopped, and
     *     will hide the stop slide-show menu.
     * </p>
     */
    void showStartSlideShowMenu();

    /**
     * Operation to display the stopt slide-show menu.
     * <p>
     *     This operation called when slide-show presentation being play, and
     *     will hide the stop slide-show menu.
     * </p>
     */
    void showStopSlideShowMenu();

    /**
     * The current active gallery.
     *
     * @return the active gallery.
     */
    Gallery gallery();

}
