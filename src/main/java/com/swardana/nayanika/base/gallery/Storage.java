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
 * A storage.
 * <p>
 *     Place where pictures being stored.
 * </p>
 *
 * @author Sukma Wardana
 */
public interface Storage {

    /**
     * Search supported picture on the storage.
     *
     * @param filter the applied filter to get the supported picture.
     * @return the list of picture.
     */
    List<Picture> search(SupportedPicture filter);

}
