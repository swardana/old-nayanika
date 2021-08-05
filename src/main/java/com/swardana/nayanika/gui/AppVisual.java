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

package com.swardana.nayanika.gui;

import com.swardana.nayanika.base.gallery.GalleryExhibition;
import com.swardana.nayanika.control.ExhibitionControl;
import com.swardana.nayanika.control.ExhibitionSubject;
import com.swardana.nayanika.control.FullScreenControl;
import com.swardana.nayanika.control.FullScreenSubject;
import com.swardana.nayanika.gui.frame.FrameVisual;
import com.swardana.nayanika.gui.menu.MenuVisual;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * A app container visual.
 *
 * @author Sukma Wardana
 */
public class AppVisual extends BorderPane implements AppView {

    private final Stage owner;

    private final MenuVisual menuVisual;
    private final FrameVisual frameVisual;

    private final AppBehavior behavior;
    private final FullScreenSubject control;

    /**
     * Creates new AppVisual.
     *
     * @param stage the primary stage.
     */
    public AppVisual(final Stage stage) {
        this(
            stage,
            new FullScreenControl(),
            new ExhibitionControl(
                new GalleryExhibition()
            )
        );
    }

    /**
     * Creates new AppVisual.
     *
     * @param stage the primary stage.
     * @param fullScreenSubject the full-screen observable state control.
     * @param exhibitionSubject the exhibition observable state control.
     */
    public AppVisual(
        final Stage stage,
        final FullScreenSubject fullScreenSubject,
        final ExhibitionSubject exhibitionSubject
    ) {
        this.owner = stage;

        this.menuVisual = new MenuVisual(
            stage, fullScreenSubject, exhibitionSubject
        );
        this.frameVisual = new FrameVisual(fullScreenSubject, exhibitionSubject);

        this.behavior = new AppBehavior(this, fullScreenSubject);
        this.control = fullScreenSubject;

        this.initGraphics();
        this.registerListeners();
    }

    @Override
    public final void enterFullScreen() {
        this.owner.setFullScreenExitHint("");
        this.owner.setFullScreen(true);
    }

    @Override
    public final void exitFullScreen() {
        this.owner.setFullScreen(false);
    }

    @Override
    public final boolean isFullScreen() {
        return this.owner.isFullScreen();
    }

    private void initGraphics() {
        this.setId("app");
        this.getStyleClass().add("theme-presets");

        this.setTop(this.menuVisual);
        this.setCenter(this.frameVisual);
    }

    private void registerListeners() {
        behavior.initView();
        this.owner.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldVal,
                final Boolean newVal
            ) {
                behavior.onViewWindowChange();
            }
        });
        this.control.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldVal,
                final Boolean newVal
            ) {
                behavior.onFullScreenControlChange();
            }
        });
    }

}
