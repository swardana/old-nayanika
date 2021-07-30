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

import java.util.Arrays;
import java.util.List;

/**
 * The supported pictures media type.
 *
 * @author Sukma Wardana
 */
public enum SupportedPicture {

    /**
     * All supported media format.
     */
    ALL(
        "*.{png,PNG,JPG,jpg,JPEG,jpeg,GIF,gif,BMP,bmp}",
        Arrays.asList("*.bmp", "*.gif", "*.jpg", "*.jpeg", "*.png")
    ),
    /**
     * Only PNG file.
     */
    PNG("*.{png,PNG}", Arrays.asList("*.png")),
    /**
     * Only JPG file.
     */
    JPG("*.{JPG,jpg,JPEG,jpeg}", Arrays.asList("*.jpg", "*.jpeg")),
    /**
     * Only GIF file.
     */
    GIF("*.{GIF,gif}", Arrays.asList("*.gif")),
    /**
     * Only BMP file.
     */
    BMP("*.{BMP,bmp}", Arrays.asList("*.bmg"));

    private final String regex;
    private final List<String> extensions;

    SupportedPicture(final String regex, final List<String> ext) {
        this.regex = regex;
        this.extensions = ext;
    }

    /**
     * The regex pattern for {@link java.nio.file.PathMatcher}.
     *
     * @see  java.nio.file.PathMatcher
     * @return the regex pattern for supported picture media type.
     */
    public final String pattern() {
        return this.regex;
    }

    /**
     * File extension for supported media type.
     *
     * @return the list of supported extension.
     */
    public final List<String> extensions() {
        return this.extensions;
    }

}
