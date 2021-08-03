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
import com.swardana.nayanika.base.gallery.Picture;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Iterator;

/**
 * An observable picture gallery state control.
 *
 * @author Sukma Wardana
 */
class GalleryControl implements GallerySubject {

    private final Gallery origin;
    private final ObjectProperty<Image> imageProperty;
    private final BooleanProperty firstProperty;
    private final BooleanProperty lastProperty;

    /**
     * Creates new GalleryControl.
     *
     * @param gallery the picture gallery for others to observe.
     */
    GalleryControl(final Gallery gallery) {
        this.origin = gallery;
        this.imageProperty = new SimpleObjectProperty<>(this, "image", null);
        this.firstProperty = new SimpleBooleanProperty(this, "first", false);
        this.lastProperty = new SimpleBooleanProperty(this, "last", false);

        // initially called to populate image data from the origin gallery.
        this.pictureChanged();
    }

    @Override
    public final String location() {
        return this.origin.location();
    }

    @Override
    public final void first() {
        if (!this.isFirst()) {
            this.origin.first();
            this.pictureChanged();
        }
    }

    @Override
    public final void last() {
        if (!this.isLast()) {
            this.origin.last();
            this.pictureChanged();
        }
    }

    @Override
    public final Picture current() {
        return this.origin.current();
    }

    @Override
    public final void current(final Picture pic) {
        this.origin.current(pic);
        this.pictureChanged();
    }

    @Override
    public final void next() {
        if (!this.isLast()) {
            this.origin.next();
            this.pictureChanged();
        }
    }

    @Override
    public final void previous() {
        if (!this.isFirst()) {
            this.origin.previous();
            this.pictureChanged();
        }
    }

    @Override
    public final boolean isFirst() {
        return this.origin.isFirst();
    }

    @Override
    public final boolean isLast() {
        return this.origin.isLast();
    }

    @Override
    public final boolean isEmpty() {
        return this.origin.isEmpty();
    }

    @Override
    public final ReadOnlyObjectProperty<Image> imageProperty() {
        return this.imageProperty;
    }

    @Override
    public final ReadOnlyBooleanProperty firstProperty() {
        return this.firstProperty;
    }

    @Override
    public final ReadOnlyBooleanProperty lastProperty() {
        return this.lastProperty;
    }

    @Override
    public final Iterator<Picture> iterator() {
        return this.origin.iterator();
    }

    private void pictureChanged() {
        try {
            this.imageProperty.setValue(
                new Image(
                    this.origin.current().read()
                )
            );
        } catch (final IOException ex) {
            throw new RuntimeException("Fail to read picture byte data!");
        }
        this.firstProperty.setValue(this.origin.isFirst());
        this.lastProperty.setValue(this.origin.isLast());
    }

}
