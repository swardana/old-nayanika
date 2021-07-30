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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for {@link PictureGallery}.
 *
 * @author Sukma Wardana
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PictureGalleryTest {

    private List<Picture> pictures;

    @BeforeAll
    public void setup() {
        this.pictures = new ArrayList<>();
        this.pictures.add(new StaticPicture("01.jpg", new File("foo/01.jpg")));
        this.pictures.add(new StaticPicture("02.jpg", new File("foo/02.jpg")));
        this.pictures.add(new StaticPicture("03.jpg", new File("foo/03.jpg")));
        this.pictures.add(new StaticPicture("04.jpg", new File("foo/04.jpg")));
        this.pictures.add(new StaticPicture("05.jpg", new File("foo/05.jpg")));
        this.pictures.add(new StaticPicture("06.jpg", new File("foo/06.jpg")));
    }

    @AfterAll
    public void teardown() {
        this.pictures = null;
    }

    @Test
    @DisplayName("Test picture gallery location")
    public void testPictureGalleryLocation() {
        var expected = "foo";
        var gallery = new PictureGallery("foo", this.pictures);
        var actual = gallery.location();
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test change current picture on gallery to the next picture")
    public void testChangeCurrentPictureToTheNextPicture() {
        int curIdx = 2;
        int nextIdx = 3;

        var gallery = new PictureGallery("foo", this.pictures);
        gallery.current(this.pictures.get(curIdx));

        gallery.next();

        var expected = this.pictures.get(nextIdx).name();
        var actual = gallery.current().name();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test change current picture on gallery to the previous picture")
    public void testChangeCurrentPictureToThePreviousPicture() {
        int curIdx = 3;
        int previousIdx = 2;

        var gallery = new PictureGallery("foo", this.pictures);
        gallery.current(this.pictures.get(curIdx));

        gallery.previous();

        var expected = this.pictures.get(previousIdx).name();
        var actual = gallery.current().name();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test change current picture on gallery to the last picture")
    public void testChangeCurrentPictureToTheLastPicture() {
        int lastIdx = this.pictures.size() - 1;

        var gallery = new PictureGallery("foo", this.pictures);
        gallery.last();

        var expected = this.pictures.get(lastIdx).name();
        var actual = gallery.current().name();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test change current picture on gallery to the beginning picture")
    public void testChangeCurrentPictureToTheBeginningPicture() {
        var gallery = new PictureGallery("foo", this.pictures);
        gallery.last();
        gallery.first();

        var expected = this.pictures.get(0).name();
        var actual = gallery.current().name();

        assertThat(actual).isNotNull().isEqualTo(expected);
        assertThat(gallery.isFirst()).isTrue();
        assertThat(gallery.isLast()).isFalse();
    }

    @Test
    @DisplayName("Test change to next picture when already at the last picture")
    public void testChangeCurrentPictureToTheNextPictureWhenCurrentIsAlreadyAtLast() {
        var gallery = new PictureGallery("foo", this.pictures);

        var lastIdx = this.pictures.size() - 1;
        gallery.last();

        gallery.next();
        gallery.next();
        gallery.next();

        var expected = this.pictures.get(lastIdx).name();
        var actual = gallery.current().name();

        assertThat(actual).isNotNull().isEqualTo(expected);
        assertThat(gallery.isFirst()).isFalse();
        assertThat(gallery.isLast()).isTrue();
    }

    @Test
    @DisplayName("Test change to previous picture when already at the beginning picture")
    public void testChangeCurrentPictureToThePreviousPictureWhenCurrentIsAlreadyAtBeginning() {
        var gallery = new PictureGallery("foo", this.pictures);

        gallery.first();

        gallery.previous();
        gallery.previous();
        gallery.previous();

        var expected = this.pictures.get(0).name();
        var actual = gallery.current().name();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test update current picture")
    public void testUpdateTheCurrentActivePictureWithPicturePartOfGallery() {
        var expected = new StaticPicture(
            "04.jpg",
            new File("foo/04.jpg")
        ).name();

        var gallery = new PictureGallery("foo", this.pictures);
        gallery.current(new StaticPicture("04.jpg", new File("foo/04.jpg")));

        var actual = gallery.current().name();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test thrown exception when update current picture with unknown picture")
    public void testUpdateCurrentActivePictureWithPictureOutsideGallery() {
        var gallery = new PictureGallery("foo", this.pictures);
        assertThatThrownBy(
            () -> gallery.current(
                new StaticPicture("unknown", new File("unknown"))
            )
        ).isInstanceOf(IllegalArgumentException.class).hasMessage(
            "The new picture is not part of this picture gallery!"
        );
    }

}