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

package com.swardana.nayanika.gui.menu.toolbar;

import com.swardana.nayanika.base.FullScreen;
import com.swardana.nayanika.base.Presentation;
import com.swardana.nayanika.base.gallery.Exhibition;
import com.swardana.nayanika.gui.menu.GalleryMenuBehavior;

/**
 * A behavior of {@link ToolbarView}.
 *
 * @author Sukma Wardana
 */
class ToolbarBehavior extends GalleryMenuBehavior {

    /**
     * Creates new ToolbarBehavior.
     *
     * @param view the toolbar view counter part.
     * @param fullScreen the app full-screen window.
     * @param exhibition the exhibition.
     * @param presentation the slide-show presentation.
     */
    ToolbarBehavior(
        final ToolbarView view,
        final FullScreen fullScreen,
        final Exhibition exhibition,
        final Presentation presentation
    ) {
        super(view, fullScreen, exhibition, presentation);
    }

}
