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

import com.swardana.nayanika.base.BuildVersion;
import com.swardana.nayanika.I18N;

/**
 * A behavior of {@link AboutView}.
 *
 * @author Sukma Wardana
 */
class AboutBehavior {

    private final AboutView view;

    /**
     * Creates new AboutBehavior.
     *
     * @param view
     */
    AboutBehavior(final AboutView view) {
        this.view = view;
    }

    /**
     * Called by the view.
     * <p>
     *     Set up the initial information.
     * </p>
     */
    final void initView() {
        this.view.about(this.about());
        this.view.license(this.license());
    }

    private String about() {
        return """
            Product Version
            %s-%s
            
            Build Information
            Version: %s
            Build: #%s
            JavaFX Version: %s
            Java Version: %s, %s
            
            Copyright \u00a9 2021, %s. All rights reserved
            """.formatted(
            I18N.INSTANCE.message("this.name"),
            BuildVersion.getInstance().buildVersion(),
            BuildVersion.getInstance().buildVersion(),
            BuildVersion.getInstance().buildNumber(),
            System.getProperty("javafx.version"),
            System.getProperty("java.runtime.version"),
            System.getProperty("java.vendor"),
            I18N.INSTANCE.message("this.owner")
        );
    }

    private String license() {
        return """
            Nayanika, picture viewer application
            Copyright (C) 2021  Sukma Wardana
            
            This program is free software: you can redistribute it and/or modify
            it under the terms of the GNU General Public License as published by
            the Free Software Foundation, either version 3 of the License, or
            (at your option) any later version.
            
            This program is distributed in the hope that it will be useful,
            but WITHOUT ANY WARRANTY; without even the implied warranty of
            MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
            GNU General Public License for more details.
            
            You should have received a copy of the GNU General Public License
            along with this program.  If not, see <https://www.gnu.org/licenses/>.
            """;
    }

}
