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

package com.swardana.nayanika.base;

import com.swardana.nayanika.base.slideshow.SlideShow;

/**
 * A slide-show presentation.
 *
 * @author Sukma Wardana
 */
public interface Presentation {

    /**
     * Update the current slide-show presentation.
     *
     * @param slide the new active slide-show presentation.
     */
    void slide(SlideShow slide);

    /**
     * Start play the slide-show presentation.
     * <p>
     *     This will change the value of {@link #isRunning()} to {@code true}.
     * </p>
     */
    void start();

    /**
     * Stop play the slide-show presentation.
     * <p>
     *     This will change the value of {@link #isRunning()} to {@code false}.
     * </p>
     */
    void stop();

    /**
     * Check whether the presentation is running or not.
     *
     * @return {@code true} if the slide-show presentation is running.
     */
    boolean isRunning();

}
