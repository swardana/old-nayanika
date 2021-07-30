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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Unit test for {@link StaticPicture}.
 *
 * @author Sukma Wardana
 */
class StaticPictureTest {

    @Test
    @DisplayName("Test get picture name from StaticPicture")
    public void testObtainPictureNameFromStaticPicture(final @TempDir Path temp) {
        var img = temp.resolve("panorama.jpg");
        var expected = "panorama.jpg";
        var picture = new StaticPicture("panorama.jpg", img.toFile());
        var actual = picture.name();
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test read picture byte data from StaticPicture")
    public void testObtainPictureByteDataFromStaticPicture(
        final @TempDir Path temp
    ) throws IOException {
        var mockFile = Files.createTempFile(temp, "pic_", ".png");
        var picture = new StaticPicture("panorama.jpg", mockFile.toFile());
        var actual = picture.read();
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("Test throw exception when fail read picture byte data from StaticPicture")
    public void testThrowExceptionWhenPictureIsNotExist(final @TempDir Path temp) {
        var img = temp.resolve("panorama.jpg");
        var picture = new StaticPicture("panorama.jpg", img.toFile());
        assertThatThrownBy(() -> picture.read()).isInstanceOf(IOException.class);
    }

}