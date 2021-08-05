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

package com.swardana.nayanika.gui.menu.menubar;

import com.swardana.nayanika.gui.menu.GalleryMenuView;

/**
 * A view contract for menubar visual.
 *
 * @author Sukma Wardana
 */
public interface MenubarView extends GalleryMenuView {

    /**
     * Operation to update the active language.
     *
     * @param lang the new active language.
     */
    void updateActiveLanguage(String lang);

    /**
     * Operation to update the toolbar menu visibility.
     *
     * @param val the new state of toolbar menu visibility.
     */
    void updateToolbarVisibility(boolean val);

    /**
     * Operation to display setting dialog window.
     */
    void showSettingDialog();

    /**
     * Operation to display about window.
     */
    void showAboutDialog();

    /**
     * The current toolbar visibility menu.
     *
     * @return {@code true} if the toolbar visibility is display.
     */
    boolean isToolbarVisible();

}
