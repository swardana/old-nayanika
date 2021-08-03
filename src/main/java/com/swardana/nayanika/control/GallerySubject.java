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

package com.swardana.nayanika.control;

import com.swardana.nayanika.base.gallery.Gallery;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.image.Image;

/**
 * An observable subject for gallery.
 *
 * @author Sukma Wardana
 */
public interface GallerySubject extends Gallery {

    /**
     * An observable boolean first picture property.
     * <p>
     *     Whenever the {@link #isFirst()} state is change, it will trigger to
     *     change this observable first property.
     * </p>
     *
     * @return the observable first picture property.
     */
    ReadOnlyBooleanProperty firstProperty();

    /**
     * An observable boolean last picture property.
     * <p>
     *     Whenever the {@link #isLast()} state is change, it will trigger to
     *     change this observable last property.
     * </p>
     *
     * @return the observable last picture property.
     */
    ReadOnlyBooleanProperty lastProperty();

    /**
     * An observable image property.
     * <p>
     *     Whenever the {@link #current()} active picture state is change,
     *     it will trigger to change this observable image property.
     * </p>
     *
     * @return the observable image property.
     */
    ReadOnlyObjectProperty<Image> imageProperty();

}
