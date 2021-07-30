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
 * Unit test for {@link AscendingSortedPictures}.
 *
 * @author Sukma Wardana
 */
class AscendingSortedPicturesTest {

    @Test
    @DisplayName("Test sort pictures ascending when name as words")
    public void testSortPictureAscendingWhenPictureNameIsText() {
        var pictures = new ArrayList<Picture>();
        pictures.add(
            new StaticPicture("manual", new File("foo/manual.jpg"))
        );
        pictures.add(
            new StaticPicture("credit", new File("foo/credit.png"))
        );
        pictures.add(
            new StaticPicture("apendix.jpg", new File("foo/apendix.jpg"))
        );

        var actual = new AscendingSortedPictures(pictures).sorted();

        assertThat(actual)
            .isNotEmpty()
            .hasSize(3)
            .extracting(Picture::name)
            .containsExactly("apendix.jpg", "credit", "manual")
            .doesNotContainNull();
    }

    @Test
    @DisplayName("Test sort pictures ascending when name as number")
    public void testSortPictureAscendingWhenPictureNameIsNumber() {
        var pictures = new ArrayList<Picture>();
        pictures.add(
            new StaticPicture("003", new File("foo/manual.jpg"))
        );
        pictures.add(
            new StaticPicture("001", new File("foo/credit.png"))
        );
        pictures.add(
            new StaticPicture("02.jpg", new File("foo/apendix.jpg"))
        );

        var actual = new AscendingSortedPictures(pictures).sorted();

        assertThat(actual)
            .isNotEmpty()
            .hasSize(3)
            .extracting(Picture::name)
            .containsExactly("001", "02.jpg", "003")
            .doesNotContainNull();
    }

    @Test
    @DisplayName("Test sort pictures ascending when name is mix of words & number")
    public void testSortPictureAscendingWhenPictureNameIsMixOfNumberAndText() {
        var pictures = new ArrayList<Picture>();
        pictures.add(
            new StaticPicture("003", new File("foo/manual.jpg"))
        );
        pictures.add(
            new StaticPicture("AAA", new File("foo/credit.png"))
        );
        pictures.add(
            new StaticPicture("02.jpg", new File("foo/apendix.jpg"))
        );

        var actual = new AscendingSortedPictures(pictures).sorted();

        assertThat(actual)
            .isNotEmpty()
            .hasSize(3)
            .extracting(Picture::name)
            .containsExactly("02.jpg", "003", "AAA")
            .doesNotContainNull();
    }

    @Test
    @DisplayName("Test sort pictures ascending when name is combination of words & number")
    public void testSortPictureAscendingWhenPictureNameIsCombinationOfTextAndNumber() {
        var pictures = new ArrayList<Picture>();
        pictures.add(
            new StaticPicture("XY-003", new File("foo/manual.jpg"))
        );
        pictures.add(
            new StaticPicture("BXS-001", new File("foo/credit.png"))
        );
        pictures.add(
            new StaticPicture("02.jpg", new File("foo/apendix.jpg"))
        );

        var actual = new AscendingSortedPictures(pictures).sorted();

        assertThat(actual)
            .isNotEmpty()
            .hasSize(3)
            .extracting(Picture::name)
            .containsExactly("BXS-001", "02.jpg", "XY-003")
            .doesNotContainNull();
    }

}