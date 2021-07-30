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

/**
 * A picture gallery.
 * <p>
 *     Support navigation between picture inside of the gallery.
 * </p>
 *
 * @author Sukma Wardana
 */
public interface Gallery extends Iterable<Picture> {

    /**
     * The location of this gallery.
     *
     * @return the gallery location.
     */
    String location();

    /**
     * Update the current active picture to the first picture from this gallery.
     * <p>
     *     This operation will affect the state of this gallery. It will change:
     * </p>
     * <ul>
     *     <li>{@link #isFirst()} : {@code true}</li>
     *     <li>{@link #isLast()} : {@code false}</li>
     *     <li>
     *         {@link #current()} : change to the first picture from this gallery.
     *     </li>
     * </ul>
     */
    void first();

    /**
     * Update the current active picture to the last picture from this gallery.
     * <p>
     *     This operation will affect the state of this gallery. It will change:
     * </p>
     * <ul>
     *     <li>{@link #isFirst()} : {@code false}</li>
     *     <li>{@link #isLast()} : {@code true}</li>
     *     <li>
     *         {@link #current()} : change to the first picture from this gallery.
     *     </li>
     * </ul>
     */
    void last();

    /**
     * Get the current active picture from gallery.
     *
     * @return the current active picture.
     */
    Picture current();

    /**
     * Update the current active picture on this gallery.
     * <p>
     *     Change the current active picture with new one. Attention, the new
     *     picture must be part of this gallery, otherwise will throws
     *     {@link IllegalArgumentException}.
     * </p>
     * <p>
     *     Changing the current active picture will affect gallery state:
     * </p>
     * <ul>
     *     <li>{@link #isFirst()}</li>
     *     <li>{@link #isLast()}</li>
     *     <li>{@link #current()}</li>
     * </ul>
     *
     * @param pic the new picture that must part of the gallery.
     * @throws IllegalArgumentException if the new picture is not part of this
     * gallery.
     */
    void current(Picture pic);

    /**
     * Update the current active picture to the next picture from this gallery.
     * <p>
     *     This will change the {@link #current()} picture into next picture
     *     from current active one. If the next picture is already at the last,
     *     it will also change the state of {@link #isLast()} to
     *     {@code true}.
     * </p>
     * <p>
     *     If the current active picture is already on the last picture from
     *     this gallery, the operation will not affect and not change anything.
     * </p>
     */
    void next();

    /**
     * Update the current active picture to the previous picture from this
     * gallery.
     * <p>
     *     This will change the {@link #current()} picture into previous picture
     *     from current active one. If the previous picture is already at the
     *     beginning, it will also change the state of {@link #isFirst()} to
     *     {@code true}.
     * </p>
     * <p>
     *     If the current active picture is already on the beginning, from
     *     this gallery, the operation will not affect and not change anything.
     * </p>
     */
    void previous();

    /**
     * Check whether the current picture is at the beginning or not.
     *
     * @return {@code true} if the current picture is at the beginning.
     */
    boolean isFirst();

    /**
     * Check whether the current picture is at the last or not.
     *
     * @return {@code true} if the current picture is at the last.
     */
    boolean isLast();

    /**
     * Check whether the gallery have pictures or not.
     *
     * @return {@code true} if the gallery don't have any picture(s).
     */
    boolean isEmpty();

}
