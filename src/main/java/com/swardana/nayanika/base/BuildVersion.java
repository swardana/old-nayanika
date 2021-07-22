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

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Nayanika build version information.
 * <p>
 *     A singleton for processing {@code version.properties} file to obtain
 *     build information, if couldn't get the information will go with default
 *     value.
 * </p>
 *
 * @author Sukma Wardana
 */
public final class BuildVersion {

    private static final Logger LOGGER = Logger.getLogger(BuildVersion.class.getName());

    private static BuildVersion instance;
    private static final Properties PROPS;

    static {
        PROPS = new Properties();
        try (var stream = BuildVersion.class.getResourceAsStream("version.properties")) {
            PROPS.load(stream);
        } catch (final IOException ex) {
            LOGGER.log(
                Level.WARNING,
                "Fail to process version.properties file! Goes with default value."
            );
        }
    }

    private BuildVersion() {}

    public static BuildVersion getInstance() {
        if (instance == null) {
            instance = new BuildVersion();
        }
        return instance;
    }

    public String buildVersion() {
        return PROPS.getProperty("build.version", "UNSET");
    }

    public String buildNumber() {
        return PROPS.getProperty("build.number", "UNSET");
    }

}
