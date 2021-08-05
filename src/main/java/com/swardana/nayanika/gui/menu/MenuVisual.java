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

package com.swardana.nayanika.gui.menu;

import com.swardana.nayanika.control.ExhibitionSubject;
import com.swardana.nayanika.control.FullScreenSubject;
import com.swardana.nayanika.control.GallerySubject;
import com.swardana.nayanika.control.PresentationControl;
import com.swardana.nayanika.control.PresentationSubject;
import com.swardana.nayanika.control.ToolbarControl;
import com.swardana.nayanika.control.ToolbarSubject;
import com.swardana.nayanika.gui.menu.menubar.MenubarVisual;
import com.swardana.nayanika.gui.menu.toolbar.ToolbarVisual;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A menu visual.
 *
 * @author Sukma Wardana
 */
public class MenuVisual extends VBox implements MenuView {

    private final MenubarVisual menubarVisual;
    private final ToolbarVisual toolbarVisual;

    private final MenuBehavior behavior;
    private final ExhibitionSubject exhibitionSubject;
    private final PresentationSubject presentationSubject;
    private final ToolbarSubject toolbarSubject;

    /**
     * Creates new MenuVisual.
     *
     * @param stage the primary stage.
     * @param fullScreenSubject the full-screen observable state control.
     * @param exhibitionSubject the exhibition observable state control.
     */
    public MenuVisual(
        final Stage stage,
        final FullScreenSubject fullScreenSubject,
        final ExhibitionSubject exhibitionSubject
    ) {
        this(
            stage,
            fullScreenSubject,
            exhibitionSubject,
            new ToolbarControl(),
            new PresentationControl()
        );
    }

    /**
     * Creates new MenuVisual.
     *
     * @param stage the primary stage.
     * @param fullScreenSubject the full-screen observable state control.
     * @param exhibitionSubject the exhibition observable state control.
     * @param toolbarSubject the toolbar observable state control.
     * @param presentationSubject the presentation observable state control.
     */
    public MenuVisual(
        final Stage stage,
        final FullScreenSubject fullScreenSubject,
        final ExhibitionSubject exhibitionSubject,
        final ToolbarSubject toolbarSubject,
        final PresentationSubject presentationSubject
    ) {
        this.menubarVisual = new MenubarVisual(
            stage,
            fullScreenSubject,
            exhibitionSubject,
            presentationSubject,
            toolbarSubject
        );
        this.toolbarVisual = new ToolbarVisual(
            stage, fullScreenSubject, exhibitionSubject, presentationSubject
        );
        this.behavior = new MenuBehavior(this, toolbarSubject);
        this.exhibitionSubject = exhibitionSubject;
        this.presentationSubject = presentationSubject;
        this.toolbarSubject = toolbarSubject;

        this.initGraphics();
        this.registerListeners();
    }

    @Override
    public final void showToolbar() {
        this.getChildren().setAll(this.menubarVisual, this.toolbarVisual);
    }

    @Override
    public final void hideToolbar() {
        this.getChildren().setAll(this.menubarVisual);
    }

    private void initGraphics() {
        this.setId("menu");
        this.getChildren().setAll(this.menubarVisual, this.toolbarVisual);
    }

    private void registerListeners() {
        this.toolbarSubject.visibilityProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldVal,
                final Boolean newVal
            ) {
                behavior.onToolbarControlChange();
            }
        });
        this.presentationSubject.runningProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldVal,
                final Boolean newVal
            ) {
                stopSlideShowOnLastPicture();
            }
        });
    }

    /**
     * Operation to stop presentation when picture gallery already on the last.
     * <p>
     *     This operation will listen to the active {@link GallerySubject} and
     *     whenever the {@link GallerySubject#lastProperty()} is {@code true}
     *     will call the {@link PresentationSubject#stop()}.
     * </p>
     */
    private void stopSlideShowOnLastPicture() {
        this.exhibitionSubject.galleryProperty().getValue()
            .lastProperty()
            .addListener((obv, old, val) -> this.presentationSubject.stop());
    }

}
