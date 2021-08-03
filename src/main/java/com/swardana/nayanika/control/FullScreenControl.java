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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * An observable window full-screen state control.
 *
 * @author Sukma Wardana
 */
public class FullScreenControl implements FullScreenSubject {

    private final BooleanProperty fullScreenProperty;
    private boolean fullScreen;

    /**
     * Creates new FullScreenControl.
     */
    public FullScreenControl() {
        this.fullScreen = false;
        this.fullScreenProperty = new SimpleBooleanProperty(this, "fullScreen", false);
    }

    @Override
    public final void enterFullScreen() {
        this.fullScreen = true;
        this.fullScreenStateChanged();
    }

    @Override
    public final void exitFullScreen() {
        this.fullScreen = false;
        this.fullScreenStateChanged();
    }

    @Override
    public final boolean isFullScreen() {
        return fullScreen;
    }

    @Override
    public final ReadOnlyBooleanProperty fullScreenProperty() {
        return this.fullScreenProperty;
    }

    private void fullScreenStateChanged() {
        this.fullScreenProperty.setValue(this.isFullScreen());
    }

}
