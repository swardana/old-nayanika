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

package com.swardana.nayanika.gui.menu.toolbar;

import com.swardana.mangata.metro.MetroIconFont;
import com.swardana.mangata.metro.MetroIcon;
import com.swardana.nayanika.base.gallery.Gallery;
import com.swardana.nayanika.base.gallery.SupportedPicture;
import com.swardana.nayanika.control.ExhibitionSubject;
import com.swardana.nayanika.control.FullScreenSubject;
import com.swardana.nayanika.control.GallerySubject;
import com.swardana.nayanika.control.PresentationSubject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * A toolbar menu visual.
 *
 * @author Sukma Wardana
 */
public class ToolbarVisual extends ToolBar implements ToolbarView {

    private static final int TOOL_BAR_SCREEN_INDEX = 1;
    private static final int TOOL_BAR_SLIDE_INDEX = 2;

    private static final SupportedPicture PICTURE_FILTER = SupportedPicture.ALL;

    private final Stage owner;

    private final Button openPictureBtn;
    private final Button enterFullScreenBtn;
    private final Button exitFullScreenBtn;
    private final Button startSlideShowBtn;
    private final Button stopSlideShowBtn;
    private final Button nextBtn;
    private final Button prevBtn;
    private final Button firstBtn;
    private final Button lastBtn;

    private final FullScreenSubject fullScreenSubject;
    private final PresentationSubject presentationSubject;
    private final ExhibitionSubject exhibitionSubject;
    private final ToolbarBehavior behavior;

    private final EventHandler<ActionEvent> openPictureAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                PICTURE_FILTER.name(), PICTURE_FILTER.extensions()
            );
            chooser.getExtensionFilters().add(filter);
            File pic = chooser.showOpenDialog(owner);
            behavior.onOpenPictureGallery(pic, PICTURE_FILTER);
        }
    };

    private final EventHandler<ActionEvent> fullScreenAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewEnterFullScreen();
        }
    };

    private final EventHandler<ActionEvent> exitFullScreenAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewExitFullScreen();
        }
    };

    private final EventHandler<ActionEvent> startSlideAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewStartPresentation();
        }
    };

    private final EventHandler<ActionEvent> stopSlideAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewStopPresentation();
        }
    };

    private final EventHandler<ActionEvent> nextAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewNextPicture();
        }
    };

    private final EventHandler<ActionEvent> previousAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewPreviousPicture();
        }
    };

    private final EventHandler<ActionEvent> firstAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewFirstPicture();
        }
    };

    private final EventHandler<ActionEvent> lastAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewLastPicture();
        }
    };

    /**
     * Creates new ToolbarVisual.
     *
     * @param stage the primary stage.
     * @param fullScreenSubject the full-screen observable subject.
     * @param exhibitionSubject the exhibition observable subject.
     * @param presentationSubject the presentation observable subject.
     */
    public ToolbarVisual(
        final Stage stage,
        final FullScreenSubject fullScreenSubject,
        final ExhibitionSubject exhibitionSubject,
        final PresentationSubject presentationSubject
    ) {
        this.owner = stage;
        this.openPictureBtn = new Button();
        this.enterFullScreenBtn = new Button();
        this.exitFullScreenBtn = new Button();
        this.startSlideShowBtn = new Button();
        this.stopSlideShowBtn = new Button();
        this.nextBtn = new Button();
        this.prevBtn = new Button();
        this.firstBtn = new Button();
        this.lastBtn = new Button();

        this.fullScreenSubject = fullScreenSubject;
        this.presentationSubject = presentationSubject;
        this.exhibitionSubject = exhibitionSubject;
        this.behavior = new ToolbarBehavior(
            this, fullScreenSubject, exhibitionSubject, presentationSubject
        );

        this.initGraphics();
        this.registerListeners();
    }

    @Override
    public final void enableSlideShowMenu() {
        this.startSlideShowBtn.setDisable(false);
    }

    @Override
    public final void showEnterFullScreenMenu() {
        this.getItems().remove(TOOL_BAR_SCREEN_INDEX);
        this.getItems().add(TOOL_BAR_SCREEN_INDEX, this.enterFullScreenBtn);
    }

    @Override
    public final void showExitFullScreenMenu() {
        this.getItems().remove(TOOL_BAR_SCREEN_INDEX);
        this.getItems().add(TOOL_BAR_SCREEN_INDEX, this.exitFullScreenBtn);
    }

    @Override
    public final void showStartSlideShowMenu() {
        this.getItems().remove(TOOL_BAR_SLIDE_INDEX);
        this.getItems().add(TOOL_BAR_SLIDE_INDEX, this.startSlideShowBtn);
    }

    @Override
    public final void showStopSlideShowMenu() {
        this.getItems().remove(TOOL_BAR_SLIDE_INDEX);
        this.getItems().add(TOOL_BAR_SLIDE_INDEX, this.stopSlideShowBtn);
    }

    @Override
    public final Gallery gallery() {
        return this.exhibitionSubject.galleryProperty().getValue();
    }

    private void initGraphics() {
        this.setId("toolbar");
        this.getStyleClass().add("tool-bar");

        var openIco = new MetroIcon(MetroIconFont.FOLDER_OPEN, 12, Color.GRAY);
        openIco.setMouseTransparent(true);
        this.openPictureBtn.setGraphic(openIco);

        var enlargeIco = new MetroIcon(MetroIconFont.ENLARGE, 12, Color.GRAY);
        enlargeIco.setMouseTransparent(true);
        this.enterFullScreenBtn.setGraphic(enlargeIco);

        var shrinkIco = new MetroIcon(MetroIconFont.SHRINK, 12, Color.GRAY);
        shrinkIco.setMouseTransparent(true);
        this.exitFullScreenBtn.setGraphic(shrinkIco);

        var slideIco = new MetroIcon(MetroIconFont.PLAY, 12, Color.GREEN);
        slideIco.setMouseTransparent(true);
        this.startSlideShowBtn.setGraphic(slideIco);
        this.startSlideShowBtn.setDisable(true);

        var stopIco = new MetroIcon(MetroIconFont.STOP, 12, Color.RED);
        stopIco.setMouseTransparent(true);
        this.stopSlideShowBtn.setGraphic(stopIco);

        var prevIco = new MetroIcon(MetroIconFont.PREVIOUS, 12, Color.GRAY);
        prevIco.setMouseTransparent(true);
        this.prevBtn.setGraphic(prevIco);
        this.prevBtn.setDisable(true);

        var nextIco = new MetroIcon(MetroIconFont.NEXT, 12, Color.GRAY);
        nextIco.setMouseTransparent(true);
        this.nextBtn.setGraphic(nextIco);
        this.nextBtn.setDisable(true);

        var firstIco = new MetroIcon(MetroIconFont.FIRST, 12, Color.GRAY);
        firstIco.setMouseTransparent(true);
        this.firstBtn.setGraphic(firstIco);
        this.firstBtn.setDisable(true);

        var lastIco = new MetroIcon(MetroIconFont.LAST, 12, Color.GRAY);
        lastIco.setMouseTransparent(true);
        this.lastBtn.setGraphic(lastIco);
        this.lastBtn.setDisable(true);

        this.getItems().setAll(
            this.openPictureBtn,
            this.enterFullScreenBtn,
            this.startSlideShowBtn,
            this.firstBtn,
            this.prevBtn,
            this.nextBtn,
            this.lastBtn
        );
    }

    private void registerListeners() {
        this.openPictureBtn.setOnAction(this.openPictureAction);

        this.enterFullScreenBtn.setOnAction(this.fullScreenAction);
        this.exitFullScreenBtn.setOnAction(this.exitFullScreenAction);

        this.startSlideShowBtn.setOnAction(this.startSlideAction);
        this.stopSlideShowBtn.setOnAction(this.stopSlideAction);

        this.nextBtn.setOnAction(this.nextAction);
        this.prevBtn.setOnAction(this.previousAction);
        this.firstBtn.setOnAction(this.firstAction);
        this.lastBtn.setOnAction(this.lastAction);

        this.fullScreenSubject.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldVal,
                final Boolean newVal
            ) {
                behavior.onFullScreenControlChange();
            }
        });
        this.presentationSubject.runningProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldVal,
                final Boolean newVal
            ) {
                behavior.onPresentationControlChange();
            }
        });
        this.exhibitionSubject.galleryProperty().addListener(new ChangeListener<GallerySubject>() {
            @Override
            public void changed(
                final ObservableValue<? extends GallerySubject> observable,
                final GallerySubject oldGallery,
                final GallerySubject newGallery
            ) {
                behavior.onPictureGalleryChange();
                presentationSubject.gallery(newGallery);
                bindGalleryNavigationControl(newGallery);
            }
        });
    }

    private void bindGalleryNavigationControl(final GallerySubject subject) {
        this.nextBtn.disableProperty().unbind();
        this.prevBtn.disableProperty().unbind();
        this.firstBtn.disableProperty().unbind();
        this.lastBtn.disableProperty().unbind();

        this.firstBtn.disableProperty().bind(subject.firstProperty());
        this.lastBtn.disableProperty().bind(subject.lastProperty());
        this.nextBtn.disableProperty().bind(subject.lastProperty());
        this.prevBtn.disableProperty().bind(subject.firstProperty());
    }

}
