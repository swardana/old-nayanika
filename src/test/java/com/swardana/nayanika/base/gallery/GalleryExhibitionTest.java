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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

/**
 * Unit test for {@link GalleryExhibition}.
 *
 * @author Sukma Wardana
 */
class GalleryExhibitionTest {

    @Test
    @DisplayName("Test create picture gallery with default picture")
    public void testCreatePictureGalleryFromExhibition() {
        var expected = 3;
        var pictures = new ArrayList<Picture>();
        pictures.add(new StaticPicture("01", new File("foo/01")));
        pictures.add(new StaticPicture("02", new File("foo/02")));
        pictures.add(new StaticPicture("03", new File("foo/03")));

        var actual = new GalleryExhibition().exhibits("test", pictures);

        assertThat(actual).isNotNull().hasSize(expected);
    }

    @Test
    @DisplayName("Test create picture gallery with define the active picture")
    public void testCreatePictureGalleryWithDefaultPictureFromExhibition() {
        var expected = 3;
        var pictures = new ArrayList<Picture>();
        pictures.add(new StaticPicture("01", new File("foo/01")));
        pictures.add(new StaticPicture("02", new File("foo/02")));
        pictures.add(new StaticPicture("03", new File("foo/03")));

        var actual = new GalleryExhibition().exhibits(
            "test", pictures, new StaticPicture("02", new File("foo/02"))
        );

        assertThat(actual).isNotNull().hasSize(expected);
        assertThat(actual.current().name())
            .isEqualTo(
                new StaticPicture("02", new File("foo/02")).name()
            );
    }

}