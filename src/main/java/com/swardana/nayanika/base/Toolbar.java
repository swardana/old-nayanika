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

package com.swardana.nayanika.base;

/**
 * A toolbar menu.
 *
 * @author Sukma Wardana
 */
public interface Toolbar {

    /**
     * Display the toolbar menu.
     * <p>
     *     This operation will change {@link #isVisible()} to {@code true}.
     * </p>
     */
    void showToolbar();

    /**
     * Hide the toolbar menu.
     * <p>
     *     This operation will change {@link #isVisible()} to {@code false}.
     * </p>
     */
    void hideToolbar();

    /**
     * Check whether the toolbar is displayed or not.
     *
     * @return {@code true} if the toolbar displayed.
     */
    boolean isVisible();

}
