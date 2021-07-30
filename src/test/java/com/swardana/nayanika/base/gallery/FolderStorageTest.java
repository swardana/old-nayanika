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

import java.nio.file.Paths;

/**
 * Unit test for {@link FolderStorage}.
 *
 * @author Sukma Wardana
 */
class FolderStorageTest {

    private static final String FOLDER = "src/test/resources"
        + "/com/swardana/nayanika/base/gallery";

    @Test
    @DisplayName("Test search all supported picture in given folder")
    public void testSearchAllSupportedMediaPictureFormat() {
        var expected = 4;
        var path = Paths.get(FOLDER);
        var actual = new FolderStorage(path).search(SupportedPicture.ALL);
        assertThat(actual).isNotEmpty().hasSize(expected);
    }

    @Test
    @DisplayName("Test search only PNG picture in given folder")
    public void testSearchPngSupportedMediaPictureFormatOnly() {
        var expected = 2;
        var path = Paths.get(FOLDER);
        var actual = new FolderStorage(path).search(SupportedPicture.PNG);
        assertThat(actual).isNotEmpty().hasSize(expected);
    }

    @Test
    @DisplayName("Test search only JPG picture in given folder")
    public void testSearchJpgSupportedMediaPictureFormatOnly() {
        var expected = 2;
        var path = Paths.get(FOLDER);
        var actual = new FolderStorage(path).search(SupportedPicture.JPG);
        assertThat(actual).isNotEmpty().hasSize(expected);
    }

}