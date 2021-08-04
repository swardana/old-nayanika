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

package com.swardana.nayanika.gui.about;

/**
 * A view contract for the about information.
 *
 * @author Sukma Wardana
 */
public interface AboutView {

    /**
     * Populate the about text information.
     *
     * @param text the about information.
     */
    void about(String text);

    /**
     * Populate the license text information.
     *
     * @param text the license information.
     */
    void license(String text);

    /**
     * Display the about information.
     */
    void showAbout();

    /**
     * Display the license information.
     */
    void showLicense();

}
