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
 * An observable toolbar menu state control.
 *
 * @author Sukma Wardana
 */
public class ToolbarControl implements ToolbarSubject {

    private final BooleanProperty visibilityProperty;
    private boolean visible;

    /**
     * Creates new ToolbarControl.
     */
    public ToolbarControl() {
        this.visible = true;
        this.visibilityProperty = new SimpleBooleanProperty(this, "visible", true);
    }

    @Override
    public final void showToolbar() {
        this.visible = true;
        this.visibilityStateChanged();
    }

    @Override
    public final void hideToolbar() {
        this.visible = false;
        this.visibilityStateChanged();
    }

    @Override
    public final boolean isVisible() {
        return this.visible;
    }

    @Override
    public final ReadOnlyBooleanProperty visibilityProperty() {
        return this.visibilityProperty;
    }

    private void visibilityStateChanged() {
        this.visibilityProperty.setValue(this.isVisible());
    }

}
