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

package com.swardana.nayanika.base.gallery;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A picture with static data.
 *
 * @author Sukma Wardana
 */
public class StaticPicture implements Picture {

    private final String name;
    private final File file;

    /**
     * Creates new StaticPicture.
     *
     * @param name the picture name, could have extension as well.
     * @param pic the {@link File} of this picture.
     */
    public StaticPicture(final String name, final File pic) {
        this.name = name;
        this.file = pic;
    }

    @Override
    public final String name() {
        return this.name;
    }

    @Override
    public final InputStream read() throws IOException {
        return new FileInputStream(this.file);
    }

}
