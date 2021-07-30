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

import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * An ascending sort algorithm.
 * <p>
 *     Sort pictures in ascending order by picture name.
 * </p>
 *
 * @author Sukma Wardana
 */
public class AscendingSortedPictures implements SortedPictures {

    private static final Logger LOGGER = Logger.getLogger(AscendingSortedPictures.class.getName());

    private final List<Picture> pictures;

    /**
     * Creates new AscendingSortedPictures.
     *
     * @param pictures the un-ordered pictures.
     */
    public AscendingSortedPictures(final List<Picture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public final List<Picture> sorted() {
        var comparator = new NumberAsNameComparator();
        List<Picture> sorted = this.pictures.stream()
            .sorted(comparator)
            .collect(Collectors.toList());
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Pictures before sort in ascending order:");
            this.pictures.forEach(
                p -> LOGGER.log(
                    Level.FINER,
                    "Picture before sorted. [pic={0}]",
                    new Object[]{p.name()}
                )
            );
            LOGGER.log(Level.FINER, "Pictures after sort in ascending order:");
            sorted.forEach(
                p -> LOGGER.log(
                    Level.FINER,
                    "Picture after sorted. [pic={0}]",
                    new Object[]{p.name()}
                )
            );
        }
        LOGGER.log(Level.INFO, "Sorted the pictures in ascending order.");
        return sorted;
    }

    final class NumberAsNameComparator implements Comparator<Picture> {

        private static final String DIGIT_AND_DECIMAL_REGEX = "[^\\d]";

        @Override
        public int compare(final Picture first, final Picture second) {
            int result;
            if (this.isNumber(first.name()) && this.isNumber(second.name())) {
                result = this.number(first.name()) - this.number(second.name());
            } else {
                result = first.name().compareTo(second.name());
            }
            return result;
        }

        private boolean isNumber(final String name) {
            boolean result = false;
            var temp = name.replaceAll(DIGIT_AND_DECIMAL_REGEX, "");
            if (!"".equals(temp)) {
                try {
                    Integer.parseInt(temp);
                    result = true;
                } catch (final NumberFormatException ex) {
                    result = false;
                }
            }
            return result;
        }

        private int number(final String name) {
            var number = name.replaceAll(DIGIT_AND_DECIMAL_REGEX, "");
            int result = 1;
            if (!number.isEmpty()) {
                result = Integer.parseInt(number);
            }
            return result;
        }

    }

}
