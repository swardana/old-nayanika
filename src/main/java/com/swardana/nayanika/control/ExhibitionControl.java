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

import com.swardana.nayanika.base.gallery.Exhibition;
import com.swardana.nayanika.base.gallery.Gallery;
import com.swardana.nayanika.base.gallery.GalleryExhibition;
import com.swardana.nayanika.base.gallery.Picture;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

/**
 * An observable exhibition state control.
 *
 * @author Sukma Wardana
 */
public class ExhibitionControl implements ExhibitionSubject {

    private final Exhibition origin;
    private final ObjectProperty<GallerySubject> galleryProperty;

    /**
     * Creates new ExhibitionControl.
     */
    public ExhibitionControl() {
        this(new GalleryExhibition());
    }

    /**
     * Creates new ExhibitionControl.
     *
     * @param exhibition the exhibition for others to observe.
     */
    public ExhibitionControl(final Exhibition exhibition) {
        this.origin = exhibition;
        this.galleryProperty = new SimpleObjectProperty<>(this, "gallery", null);
    }

    @Override
    public final Gallery exhibits(final String name, final List<Picture> pictures) {
        var gallery = this.origin.exhibits(name, pictures);
        this.galleryChanged(gallery);
        return gallery;
    }

    @Override
    public final Gallery exhibits(
        final String name, final List<Picture> pictures, final Picture pic
    ) {
        var gallery = this.origin.exhibits(name, pictures, pic);
        this.galleryChanged(gallery);
        return gallery;
    }

    @Override
    public final ReadOnlyObjectProperty<GallerySubject> galleryProperty() {
        return this.galleryProperty;
    }

    private void galleryChanged(final Gallery gallery) {
        this.galleryProperty.setValue(
            new GalleryControl(gallery)
        );
    }

}
