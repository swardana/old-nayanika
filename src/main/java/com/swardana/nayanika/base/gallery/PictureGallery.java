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

import java.util.Iterator;
import java.util.List;

/**
 * A picture gallery.
 *
 * @author Sukma Wardana
 */
public class PictureGallery implements Gallery {

    private final String location;
    private final List<Picture> pictures;
    private final int lastIndex;
    private int index;

    /**
     * Creates new PictureGallery.
     *
     * @param location the picture gallery location.
     * @param pictures the list of pictures.
     */
    public PictureGallery(final String location, final List<Picture> pictures) {
        this.location = location;
        this.pictures = pictures;
        this.lastIndex = pictures.size() - 1;
        this.index = 0;
    }

    @Override
    public final String location() {
        return this.location;
    }

    @Override
    public final void first() {
        this.index = 0;
    }

    @Override
    public final void last() {
        this.index = lastIndex;
    }

    @Override
    public final Picture current() {
        return this.pictures.get(index);
    }

    @Override
    public final void current(final Picture pic) {
        boolean isFound = false;
        for (int i=0; i <= this.lastIndex; i++) {
            if (pic.name().equals(this.pictures.get(i).name())) {
                this.index = i;
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            throw new IllegalArgumentException(
                "The new picture is not part of this picture gallery!"
            );
        }
    }

    @Override
    public final void next() {
        if (!this.isLast()) {
            this.index++;
        }
    }

    @Override
    public final void previous() {
        if (!this.isFirst()) {
            this.index--;
        }
    }

    @Override
    public final boolean isFirst() {
        return this.index == 0;
    }

    @Override
    public final boolean isLast() {
        return this.index == lastIndex;
    }

    @Override
    public final boolean isEmpty() {
        return this.pictures.isEmpty();
    }

    @Override
    public final Iterator<Picture> iterator() {
        return this.pictures.iterator();
    }

}
