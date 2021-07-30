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

import java.io.IOException;
import java.io.InputStream;

/**
 * A picture.
 *
 * @author Sukma Wardana
 */
public interface Picture {

    /**
     * Picture name.
     * <p>
     *     Get the picture name. The picture name could have the extension as
     *     well.
     * </p>
     *
     * @return the picture name.
     */
    String name();

    /**
     * Read byte data stream of this picture.
     *
     * @return the byte data stream.
     * @throws IOException if fail to read this picture byte data stream.
     */
    InputStream read() throws IOException;

}
