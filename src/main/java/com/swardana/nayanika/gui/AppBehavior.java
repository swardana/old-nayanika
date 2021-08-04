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

import com.swardana.nayanika.base.FullScreen;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A behavior of {@link AppView}.
 *
 * @author Sukma Wardana
 */
class AppBehavior {

    private static final Logger LOGGER = Logger.getLogger(AppBehavior.class.getName());

    private final AppView view;
    private final FullScreen control;

    /**
     * Creates new AppBehavior.
     *
     * @param view the app view counter-part.
     * @param control the app full-screen window.
     */
    AppBehavior(final AppView view, final FullScreen control) {
        this.view = view;
        this.control = control;
    }

    /**
     * Called by the view.
     * <p>
     *     Set up the initial data or configuration for app view.
     * </p>
     */
    final void initView() {
        if (this.control.isFullScreen()) {
            this.view.enterFullScreen();
        } else {
            this.view.exitFullScreen();
        }
        LOGGER.log(
            Level.FINE,
            "Initiate the visual full-screen state. [full-screen={0}]",
            new Object[]{this.control.isFullScreen()}
        );
        LOGGER.log(Level.INFO, "Successfully initiate the app view screen.");
    }

    /**
     * Called by the view.
     * <p>
     *     Handle when the visual full-screen state changes, will affect the
     *     control full-screen state.
     * </p>
     */
    final void onViewWindowChange() {
        if (this.view.isFullScreen() == this.control.isFullScreen()) {
            LOGGER.log(
                Level.FINER,
                "The visual and control full-screen state is equals. [full-screen={0}]",
                new Object[]{this.view.isFullScreen()}
            );
            return;
        }
        if (this.view.isFullScreen()) {
            this.control.enterFullScreen();
        } else {
            this.control.exitFullScreen();
        }
        LOGGER.log(
            Level.FINE,
            "The visual full-screen state is changed. [full-screen={0}]",
            new Object[]{this.view.isFullScreen()}
        );
        LOGGER.log(Level.INFO, "Successfully change the full-screen state control.");
    }

    /**
     * Handle when the control full-screen state changes, will affect the
     * visual full-screen state.
     */
    final void onFullScreenControlChange() {
        if (this.control.isFullScreen() == this.view.isFullScreen()) {
            LOGGER.log(
                Level.FINER,
                "The control and visual full-screen state is equals. [full-screen={0}]",
                new Object[]{this.control.isFullScreen()}
            );
            return;
        }
        if (this.control.isFullScreen()) {
            this.view.enterFullScreen();
        } else {
            this.view.exitFullScreen();
        }
        LOGGER.log(
            Level.FINE,
            "The control full-screen state is changed. [full-screen={0}]",
            new Object[]{this.control.isFullScreen()}
        );
        LOGGER.log(Level.INFO, "Successfully change the visual full-screen state.");
    }

}
