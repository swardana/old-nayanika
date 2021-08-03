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

import com.swardana.nayanika.base.FullScreen;
import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 * An observable subject for window full-screen mode.
 *
 * @author Sukma Wardana
 */
public interface FullScreenSubject extends FullScreen {

    /**
     * An observable window full-screen state property.
     *
     * @return the observable full-screen state.
     */
    ReadOnlyBooleanProperty fullScreenProperty();

}
