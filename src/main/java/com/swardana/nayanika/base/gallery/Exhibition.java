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

import java.util.List;

/**
 * An exhibition.
 *
 * @author Sukma Wardana
 */
public interface Exhibition {

    /**
     * Exhibits a picture gallery.
     *
     * @param name the exhibition name.
     * @param pictures the collection of pictures.
     * @return the picture gallery exhibition.
     */
    Gallery exhibits(String name, List<Picture> pictures);

    /**
     * Exhibits a picture gallery.
     * <p>
     *     Declare the current active picture as well.
     * </p>
     *
     * @param name the exhibition name.
     * @param pictures the collection of pictures.
     * @param pic the showing picture.
     * @return the picture gallery exhibition.
     */
    Gallery exhibits(String name, List<Picture> pictures, Picture pic);

}
