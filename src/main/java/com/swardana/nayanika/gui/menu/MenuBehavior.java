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

import com.swardana.nayanika.base.Toolbar;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A behavior of {@link MenuView}.
 *
 * @author Sukma Wardana
 */
class MenuBehavior {

    private static final Logger LOGGER = Logger.getLogger(MenuBehavior.class.getName());

    private final MenuView view;
    private final Toolbar control;

    /**
     * Creates new MenuBehavior.
     *
     * @param view the menu view counter part.
     * @param control the app toolbar menu.
     */
    MenuBehavior(final MenuView view, final Toolbar control) {
        this.view = view;
        this.control = control;
    }

    /**
     * Called by the view.
     * <p>
     *     Set up the initial data or configuration for menu view.
     * </p>
     */
    final void initView() {
        this.toolbarVisibility();
    }

    /**
     * Handle when the control toolbar state changes, will affect the
     * visual toolbar state.
     */
    final void onToolbarControlChange() {
        this.toolbarVisibility();
    }

    private void toolbarVisibility() {
        if (this.control.isVisible()) {
            this.view.showToolbar();
        } else {
            this.view.hideToolbar();
        }
        LOGGER.log(
            Level.FINE,
            "Initiate the toolbar visibility. [toolbar={0}]",
            new Object[]{this.control.isVisible()}
        );
        LOGGER.log(Level.INFO, "Successfully initiate the toolbar visibility.");
    }

}
