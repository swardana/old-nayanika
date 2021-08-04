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

package com.swardana.nayanika;

import java.io.InputStream;
import java.util.Locale;

/**
 * A constants for application platform.
 *
 * @author Sukma Wardana
 */
public final class AppPlatform {

    private AppPlatform() {}

    private static final String OS_NAME = System.getProperty("os.name")
        .toLowerCase(Locale.ROOT);

    /**
     * True if current platform is running Linux.
     */
    public static final boolean IS_LINUX = OS_NAME.contains("linux");

    /**
     * True if current platform is running Mac OS X.
     */
    public static final boolean IS_MAC = OS_NAME.contains("mac");

    /**
     * True if current platform is running Windows.
     */
    public static final boolean IS_WINDOWS = OS_NAME.contains("windows");

    public static final String CSS_STYLE = AppPlatform.class
        .getResource("css/style.css").toExternalForm();

    public static final InputStream ICON = AppPlatform.class
        .getResourceAsStream("icon/icon.png");

}
